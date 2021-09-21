package com.game.service;

import com.game.BadRequestException;
import com.game.entity.Player;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Component
public class PlayerValidation implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Player.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // Player class only
        if (!supports(target.getClass())) {
            errors.reject("Неверный класс");
        }

        Player playerToCheck = (Player) target;

        if (playerToCheck.getId() == null && playerToCheck.getName() == null && playerToCheck.getTitle() == null
                && playerToCheck.getRace() == null && playerToCheck.getProfession() == null
                && playerToCheck.getBirthday() == null
                && playerToCheck.getBanned() == null && playerToCheck.getExperience() == null
        ) {
            errors.reject("player", "Данные игрока не указаны");
            throw new BadRequestException("Данные игрока не указаны");
        }

        // Проверка длины имени
        if (playerToCheck.getName().length() > 12 || playerToCheck.getName().length() == 0) {
            errors.reject("name", "Слишком длинное имя");
        }

        // Проверка длины заголовка
        if (playerToCheck.getTitle().length() > 30) {
            errors.reject("title", "Слишком длинный заголовок");
        }

        // Проверка поля Опыт
        if (playerToCheck.getExperience() < 0 || playerToCheck.getExperience() > 10_000_000) {
            errors.reject("experience", "Опыт не соответствует картине придуманного мира");
        }

        // Проверка поля Дата
        if (playerToCheck.getBirthday().before(new GregorianCalendar(2000, Calendar.JANUARY, 1).getTime()) ||
            playerToCheck.getBirthday().after(new GregorianCalendar(3000, Calendar.DECEMBER, 31).getTime())
        ) {
            errors.rejectValue("birthday","", "Дата рождения не вписывается в границы обозримого прошлого и будущего");
        }
    }
}
