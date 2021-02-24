package ru.netology.web;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;

public class AppOrderTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitRequest(){
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Евлампий Кашечкин");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id=order-success]")
                .shouldHave(exactText("Ваша заявка успешно отправлена! " +
                        "Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldNotSubmitRequestWithEmptyName(){
        SelenideElement form = $("form");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        form.$(".input_invalid span").$(".input__sub")
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSubmitRequestWithEngName(){
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Evlampy Kashechkin");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        form.$(".input_invalid span").$(".input__sub")
                .shouldHave(exactText("Имя и Фамилия указаные неверно. " +
                        "Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotSubmitRequestWithEmptyPhone(){
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Евлампий Кашечкин");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        form.$(".input_invalid span").$(".input__sub")
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSubmitRequestWithWrongPhone() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Евлампий Кашечкин");
        form.$("[data-test-id=phone] input").setValue("79270000000");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        form.$(".input_invalid span").$(".input__sub")
                .shouldHave(exactText("Телефон указан неверно. " +
                        "Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNotSubmitRequestWithoutAgree(){
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Евлампий Кашечкин");
        form.$("[data-test-id=phone] input").setValue("+79270000000");
        form.$("button").click();
        form.$(".input_invalid").$(".checkbox__text")
                .shouldHave(exactText("Я соглашаюсь с условиями обработки" +
                        " и использования моих персональных данных" +
                        " и разрешаю сделать запрос в бюро кредитных историй"));
    }
}
