package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import data.DataGenerator;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        Configuration.holdBrowserOpen = true;
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 6;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        $("[data-test-id='city'] input").sendKeys(DataGenerator.generateCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(firstMeetingDate);
        $("[data-test-id='name'] input").sendKeys(validUser.getName());
        $("[data-test-id='phone'] input").sendKeys(DataGenerator.generatePhone("ru"));
        $("[data-test-id='agreement']").click();
        $$("button").findBy(text("Запланировать")).click();

        $("[data-test-id='success-notification']")
                .shouldHave(Condition.text("Успешно!"))
                .shouldHave(Condition.text("Встреча успешно запланирована на " + firstMeetingDate))
                .shouldHave(visible);

        $("[data-test-id='date'] input").doubleClick().sendKeys(secondMeetingDate);
        $$("button").findBy(text("Запланировать")).click();
        $("[data-test-id='replan-notification']")
                .shouldHave(Condition.text("Необходимо подтверждение"))
                .shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"))
                .shouldHave(visible);

        $$("button").findBy(text("Перепланировать")).click();
        $("[data-test-id='success-notification']")
                .shouldHave(Condition.text("Успешно!"))
                .shouldHave(Condition.text("Встреча успешно запланирована на " + secondMeetingDate))
                .shouldHave(visible);

    }

}
