package ru.apolonov.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.apolonov.config.CredentialsConfig;
import ru.apolonov.helpers.Attach;

import static java.lang.String.format;

public class TestBase {
    public static CredentialsConfig credentials =
            ConfigFactory.create(CredentialsConfig.class);

    @BeforeAll
    static void setup() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        Configuration.browserSize = "1920x1080";

        //Configuration.remote = format("https://%s:%s@%s", credentials.login(), credentials.password(), System.getProperty("url"));
        Configuration.remote = format("https://%s:%s@%s", credentials.login(), credentials.password(), credentials.url());
        //Configuration.remote = format("https://%s:%s@selenoid.autotests.cloud/wd/hub/", credentials.login(), credentials.password());

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);

        Configuration.browserCapabilities = capabilities;
    }

    @AfterEach
    public void tearDown() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }
}
