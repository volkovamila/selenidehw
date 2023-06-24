import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class DeliveryOfCardsTest {

    private String generateData(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void HowTheCardDeliveryAppWorksTest() {
        open("http://localhost:9999");
        $("[data-test-id = 'city'] input").setValue("Иркутск");
        String currentDate = generateData(5,"dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(currentDate);
        $("[data-test-id='name'] input").setValue("Сидоров Сидор");
        $("[data-test-id='phone'] input").setValue("+71234567899");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Успешно " + currentDate));
    }
}
