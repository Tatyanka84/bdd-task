package ru.netology.bdd.pages;

import com.codeborne.selenide.SelenideElement;
import ru.netology.bdd.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private final SelenideElement amountField =
            $("[data-test-id='amount'] input");
    private final SelenideElement fromField =
            $("[data-test-id='from'] input");
    private final SelenideElement toField =
            $("[data-test-id='to'] input");
    private final SelenideElement transferButton =
            $("[data-test-id='action-transfer']");
    private final SelenideElement cancelButton =
            $("[data-test-id='action-cancel']");

    public TransferPage() {
        amountField.shouldBe(visible, Duration.ofSeconds(15));
    }

    public DashboardPage transferFrom(
            DataHelper.CardInfo fromCard,
            DataHelper.CardInfo toCard,
            int amount
    ) {
        amountField.setValue(String.valueOf(amount));
        fromField.setValue(fromCard.getCardNumber());
        toField.setValue(toCard.getCardNumber());
        transferButton.click();
        return new DashboardPage();
    }

    public DashboardPage cancel() {
        cancelButton.click();
        return new DashboardPage();
    }
}

