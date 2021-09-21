package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public interface PlayerService {

    Player getById(Long id);

    boolean exists(Long id);

    void create(Player player);

    void delete(Long id);

    Player update(Long id, Player playerToUpdate);

    public Long getPlayersCount(
            String name, String title,
            Race race, Profession profession,
            Date after, Date before,
            Boolean banned,
            Integer minExperience, Integer maxExperience,
            Integer minLevel, Integer maxLevel);

    List<Player> findAllByParams(
            String name, String title,
            Race race, Profession profession,
            Date after, Date before,
            Boolean banned,
            Integer minExperience, Integer maxExperience,
            Integer minLevel, Integer maxLevel,
            Integer pageNumber, Integer pageSize, PlayerOrder order);

}
