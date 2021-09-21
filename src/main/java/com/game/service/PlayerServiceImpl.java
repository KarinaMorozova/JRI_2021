package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {

    private PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> findAllByParams(String name, String title,
                                        Race race, Profession profession,
                                        Date after, Date before,
                                        Boolean banned,
                                        Integer minExperience, Integer maxExperience,
                                        Integer minLevel, Integer maxLevel,
                                        Integer pageNumber, Integer pageSize, PlayerOrder order) {
        return playerRepository.findAllByParams(
                name, title,
                race, profession,
                after, before,
                banned,
                minExperience,maxExperience,
                minLevel,maxLevel,
                PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName())) );
    }

    @Override
    public Long getPlayersCount(
            String name, String title,
            Race race, Profession profession,
            Date after, Date before,
            Boolean banned,
            Integer minExperience, Integer maxExperience,
            Integer minLevel, Integer maxLevel
    ) {
        return (long) playerRepository.findAllByParams(name, title, race, profession, after, before,
                banned, minExperience, maxExperience, minLevel, maxLevel,
                PageRequest.of(0, Integer.MAX_VALUE) ).size();

    }

    @Override
    public Player getById(Long id) {
        return playerRepository.findById(id).orElse(null);
    }

    private void save(Player player){
        playerRepository.save(player);
    }

    private void calculateLevels(Player player){
        if (player.getExperience() != null && player.getExperience() != 0) {
            int level = Math.toIntExact((long) ((Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100.0));
            player.setLevel(level);
            player.setUntilNextLevel(50 * (level + 1) * (level + 2) - player.getExperience());
        }
    }

    @Override
    public void create(Player player) {
        calculateLevels(player);
        save(player);
    }

    @Override
    public Player update(Long id, Player playerToUpdate) {
        if (playerToUpdate == null) { return null; }

        calculateLevels(playerToUpdate);

        Player currentPlayer = playerRepository.findById(id).orElse(null);
        if (exists(id) && currentPlayer != null) {
            currentPlayer.updateValues(playerToUpdate);
            playerRepository.save(currentPlayer);
        }

        return currentPlayer;
    }

    @Override
    public void delete(Long id) {
        playerRepository.deleteById(id);
    }

    @Override
    public boolean exists(Long id) {
        return playerRepository.existsById(id);
    }



}
