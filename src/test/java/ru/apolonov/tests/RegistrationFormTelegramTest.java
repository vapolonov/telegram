package ru.apolonov.tests;

import com.github.javafaker.Faker;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class RegistrationFormTelegramTest extends TestBase {
    public String url = "https://demoqa.com/automation-practice-form";
    Faker faker = new Faker();

    String firstName = faker.name().firstName(),
            lastName = faker.name().lastName(),
            email = faker.internet().emailAddress(),
            mobile = faker.number().digits(10),
            country = faker.address().country(),
            city = faker.address().cityName(),
            street = faker.address().streetName(),
            houseNumber = faker.address().buildingNumber();

    @DisplayName("Форма регистрации")
    @Owner("Vasiliy Apolonov")
    @Tag("registrationTest")
    @Test
    void fillFormTest() {

        step("Открываем страницу с формой регистрации", () ->
                open(url));

        step("Проверяем что на странице есть заголовок \"Student Registration Form\"", () -> {
            $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
        });

        step("Заполняем форму регистрации данными", () -> {

            step("Заполняем данные пользователя", () -> {
                $("#firstName").setValue(firstName);
                $("#lastName").setValue(lastName);
                $("#userEmail").setValue(email);
                $("#genterWrapper").$(byText("Female")).click();
                $("#userNumber").setValue(mobile);
            });

            step("Выбираем дату рождения", () -> {
                $("#dateOfBirthInput").click();
                $(".react-datepicker__month-select").selectOption("September");
                $(".react-datepicker__year-select").selectOption("1974");
                $(".react-datepicker__day--009:not(.react-datepicker__day--outside-month)").click();
            });

            step("Устанавливаем предметы", () -> {
                $("#subjectsInput").setValue("Maths").pressEnter();
                $("#subjectsInput").setValue("English").pressEnter();
            });

            step("Выбираем хобби", () -> {
                $("#hobbiesWrapper").$(byText("Sports")).click();
                $("#hobbiesWrapper").$(byText("Music")).click();
            });

            step("Прикрепляем файл", () -> {
                $("#uploadPicture").uploadFile(new File("src/test/resources/img/new.jpg"));
            });

            step("Заполняем адрес", () -> {
                $("#currentAddress").setValue(country + ", " + city + ", " + street + ", " + houseNumber);
            });

            step("Выбираем штат", () -> {
                $("#state").scrollTo().click();
                $("#stateCity-wrapper").$(byText("Haryana")).click();
            });

            step("Выбираем город", () -> {
                $("#city").click();
                $("#stateCity-wrapper").$(byText("Panipat")).click();
            });
        });

        step("Отправляем форму", () ->
                $("#submit").click());

        step("Проверяем успешную отправку формы", () -> {
            $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
            $(".table-responsive").shouldHave(text(firstName + " " + lastName),
                    text(email),
                    text("Female"),
                    text(mobile),
                    text("09 September,1974"),
                    text("Maths, English"),
                    text("Sports, Music"),
                    text("new.jpg"),
                    text(country + ", " + city + ", " + street + ", " + houseNumber),
                    text("Haryana Panipat"));
        });
    }
}
