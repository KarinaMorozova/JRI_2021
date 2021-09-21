package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerValidation;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/*
 * Controller
 */

@Controller
@Validated
@RequestMapping("/rest/players")
public class PlayerController {

    private final PlayerService playerService;
    private final PlayerValidation playerValidation;

    @Autowired
    public PlayerController(PlayerService playerService, PlayerValidation playerValidation) {
        this.playerService = playerService;
        this.playerValidation = playerValidation;
    }

    @GetMapping
    public ResponseEntity<List<Player>> getPlayers(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "race", required = false) Race race,
            @RequestParam(value = "profession", required = false) Profession profession,
            @RequestParam(value = "after", required = false) Long after,
            @RequestParam(value = "before", required = false) Long before,
            @RequestParam(value = "banned", required = false) Boolean banned,
            @RequestParam(value = "minExperience", required = false) Integer minExperience,
            @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
            @RequestParam(value = "minLevel", required = false) Integer minLevel,
            @RequestParam(value = "maxLevel", required = false) Integer maxLevel,
            @RequestParam(value = "order", required = false, defaultValue = "ID") PlayerOrder order,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize
    ) {
        List<Player> playerList = playerService.findAllByParams(
                name, title, race, profession,
                millisToDate(after), millisToDate(before),
                banned, minExperience, maxExperience,
                minLevel, maxLevel,
                pageNumber, pageSize, order);
        return new ResponseEntity<>(playerList, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCount(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "race", required = false) Race race,
            @RequestParam(value = "profession", required = false) Profession profession,
            @RequestParam(value = "after", required = false) Long after,
            @RequestParam(value = "before", required = false) Long before,
            @RequestParam(value = "banned", required = false) Boolean banned,
            @RequestParam(value = "minExperience", required = false) Integer minExperience,
            @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
            @RequestParam(value = "minLevel", required = false) Integer minLevel,
            @RequestParam(value = "maxLevel", required = false) Integer maxLevel,
            @RequestParam(value = "order", required = false, defaultValue = "ID") PlayerOrder order,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize
    ) {
        Long count = playerService.getPlayersCount(name, title, race, profession,
                millisToDate(after), millisToDate(before),
                banned, minExperience, maxExperience,
                minLevel, maxLevel);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Player> createPlayer(@RequestBody @Validated Player player, BindingResult bindingResult) {
        playerValidation.validate(player, bindingResult);

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.playerService.create(player);

        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable("id") Long playerId){
        if (playerId == null || playerId <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!this.playerService.exists(playerId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Player player = this.playerService.getById(playerId);

        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @PostMapping(value = "/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable("id") Long playerId,
                                               @RequestBody @Validated Player playerToUpdate,
                                               BindingResult bindingResult){
        if (playerId == null || playerId <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!this.playerService.exists(playerId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Player currentPlayer = this.playerService.update(playerId, playerToUpdate);

        if (currentPlayer != null) {
            playerValidation.validate(currentPlayer, bindingResult);
            if (bindingResult.hasErrors()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(currentPlayer, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Player> deletePlayer(@PathVariable("id") Long playerId){
        if (playerId == null || playerId <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!this.playerService.exists(playerId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Player player = this.playerService.getById(playerId);
        this.playerService.delete(playerId);

        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    private Date millisToDate(Long millis){
        return millis == null ? null : new Date(millis);
    }
}
