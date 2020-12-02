package ru.notology.appcarddelivery;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppCardDeliveryTest {

    //Метод генерирует случайную дату в диапазоне от +3 до +30 дней от сегодняшней даты
    String generateDate() {
        final Random random = new Random();
        int randomInt = random.nextInt(31) + 3;
        LocalDate date = LocalDate.now().plusDays(randomInt);
        String formattedDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return formattedDate;
    }

    @Test
    void shouldSubmitRequest() {
        String date = generateDate();
        open("http://localhost:9999/");
        $("[data-test-id='city'] .input__control").setValue("Москва");
        SelenideElement dateForm = $("[data-test-id='date'] .input__control");
        dateForm.sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        dateForm.setValue(date);
        $("[name='name']").setValue("Василий Петров");
        $("[name='phone']").setValue("+79067778899");
        $(".checkbox__box").click();
        $(".button__content").click();
        String text = $(withText("Встреча успешно")).waitUntil(Condition.visible, 15000 ).getText();
        assertEquals("Встреча успешно забронирована на " + date, text.trim());
    }
}
