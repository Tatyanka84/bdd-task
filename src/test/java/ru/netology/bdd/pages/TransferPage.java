package ru.netology.bdd.pages;

import com.codeborne.selenide.SelenideElement;
import ru.netology.bdd.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private final SelenideElement transferButton =
            $("[data-test-id='action-transfer']");

    private final SelenideElement amountInput =
            $("[data-test-id='amount'] input");

    private final SelenideElement fromInput =
            $("[data-test-id='from'] input");

    private final SelenideElement transferHead =
            $(byText("Пополнение карты"));

    private final SelenideElement errorMessage =
            $("[data-test-id='error-message']");

    public TransferPage() {
        transferHead.shouldBe(visible, Duration.ofSeconds(15));
    }

    public DashboardPage makeValidTransfer(
            String amountToTransfer,
            DataHelper.CardInfo cardInfo
    ) {
        makeTransfer(amountToTransfer, cardInfo);
        return new DashboardPage();
    }

    public void makeTransfer(
            String amountToTransfer,
            DataHelper.CardInfo cardInfo
    ) {
        amountInput
                .shouldBe(visible, Duration.ofSeconds(15))
                .setValue(amountToTransfer);

        fromInput
                .shouldBe(visible, Duration.ofSeconds(15))
                .setValue(cardInfo.getCardNumber());

        transferButton
                .shouldBe(visible, Duration.ofSeconds(15))
                .click();
    }

    public void findErrorMessage(String expectedText) {
        errorMessage
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText(expectedText), Duration.ofSeconds(15));
    }
}


