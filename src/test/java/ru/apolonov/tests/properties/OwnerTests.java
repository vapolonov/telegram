package ru.apolonov.tests.properties;

import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.*;
import ru.apolonov.config.CredentialsConfig;

import static java.lang.String.format;

@DisplayName("Данные из файла credentials.properties")
@Tag("owner")
public class OwnerTests {
    public CredentialsConfig credentials =
            ConfigFactory.create(CredentialsConfig.class);

    @Test
    void readCredentialsTest() {
        String login = credentials.login();
        String password = credentials.password();
        String message = format("https://%s:%s@selenoid.autotests.cloud/wd/hub/", login, password);
        System.out.println("Логин: " + login);
        System.out.println("Пароль: " + password);
        System.out.println("Адрес удаленного браузера: " + message);
    }
}
