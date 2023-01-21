import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class DeliveryTest {

    private String actualDate(long daysToAdd, String pattern) {
        return LocalDate.now().plusDays(daysToAdd).format(DateTimeFormatter.ofPattern(pattern));
    }

    @BeforeAll
    static void setup() {
        open("http://localhost:9999");
    }

    @Test
    void checkAppeyorNotHappy() {
        //Configuration.holdBrowserOpen = true;

        $("[data-test-id='city'] input").sendKeys("Казань");
        $("[data-test-id='date'] input").sendKeys(actualDate(3, "dd MM yyyy"));
        $("[data-test-id='name'] input").sendKeys("IVAN AVANOV");
        $("[data-test-id='phone'] input").sendKeys("+79991112233");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(text("Запланировать")).click();

        $("[data-test-id='success-notification']")
                .shouldHave(Condition.text("Успешно!"))
                .shouldHave(visible);
    }
}
