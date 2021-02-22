package ru.netology.web;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;

public class AppOrderTest {
    @Test
    public void shouldSubmitRequest(){
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Евлампий Кашечкин");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    public void shouldNotSubmitRequestWithEmptyName(){
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("button").click();
        $(".input_invalid").find(".input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldNotSubmitRequestWithEngName(){
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Evlampy Kashechkin");
        form.$("button").click();
        $(".input_invalid").find(".input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldNotSubmitRequestWithEmptyNumber(){
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Евлампий Кашечкин");
        form.$("button").click();
        $(".input_invalid").find(".input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldNotSubmitRequestWithWrongNumber() {
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Евлампий Кашечкин");
        form.$("[data-test-id=phone] input").setValue("+792700000000");
        form.$("button").click();
        $(".input_invalid").find(".input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldNotSubmitRequestWithoutAgree(){
        open("http://localhost:9999");
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Евлампий Кашечкин");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("button").click();
        $(".input_invalid").find(".checkbox__text").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй"));
    }
}
