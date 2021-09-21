package com.game.entity;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import javax.persistence.Column;
import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/*
 * Entity Player
 */

@Entity
@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 12)
    @NonNull
    String name;

    @Column(name = "title", length = 30)
    @NonNull
    String title;

    @Column(name = "race")
    @Enumerated(EnumType.STRING)
    @NonNull
    Race race;

    @Column(name = "profession")
    @Enumerated(EnumType.STRING)
    @NonNull
    Profession profession;

    @Column(name = "birthday")
    @DateTimeFormat
    @Temporal(TemporalType.DATE)
    @NonNull
    Date birthday;

    @Column(name = "banned")
    Boolean banned;

    @Column(name = "experience")
    @NonNull
    Integer experience;

    @Column(name = "level")
    Integer level;

    @Column(name = "untilNextLevel")
    Integer untilNextLevel;

    public Player(){}

    public Player(String name, String title, Race race, Profession profession, Date birthday, Boolean banned, Integer experience) {
        this.name = name;
        this.title = title;
        this.race = race;
        this.profession = profession;
        this.birthday = birthday;
        this.banned = (banned == null) ? false : banned;
        this.experience = experience;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getUntilNextLevel() {
        return untilNextLevel;
    }

    public void setUntilNextLevel(Integer untilNextLevel) {
        this.untilNextLevel = untilNextLevel;
    }

    public void updateValues(Player playerToUpdate){
        if (playerToUpdate.getName() != null) {
            this.setName(playerToUpdate.getName());
        }
        if (playerToUpdate.getTitle() != null) {
            this.setTitle(playerToUpdate.getTitle());
        }
        if (playerToUpdate.getExperience() != null) {
            this.setExperience(playerToUpdate.getExperience());
        }
        if (playerToUpdate.getBanned() != null) {
            this.setBanned(playerToUpdate.getBanned());
        }
        if (playerToUpdate.getBirthday() != null) {
            this.setBirthday(playerToUpdate.getBirthday());
        }
        if (playerToUpdate.getProfession() != null) {
            this.setProfession(playerToUpdate.getProfession());
        }
        if (playerToUpdate.getRace() != null) {
            this.setRace(playerToUpdate.getRace());
        }
        if (playerToUpdate.getLevel() != null) {
            this.setLevel(playerToUpdate.getLevel());
        }
        if (playerToUpdate.getUntilNextLevel() != null) {
            this.setUntilNextLevel(playerToUpdate.getUntilNextLevel());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id) &&
                name.equals(player.name) &&
                Objects.equals(title, player.title) &&
                race == player.race &&
                profession == player.profession &&
                Objects.equals(birthday, player.birthday) &&
                Objects.equals(banned, player.banned) &&
                Objects.equals(experience, player.experience) &&
                Objects.equals(level, player.level) &&
                Objects.equals(untilNextLevel, player.untilNextLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, title, race, profession, birthday, banned, experience, level, untilNextLevel);
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", race=" + race +
                ", profession=" + profession +
                ", birthday=" + birthday +
                ", banned=" + banned +
                ", experience=" + experience +
                ", level=" + level +
                ", untilNextLevel=" + untilNextLevel +
                '}';
    }

}
