package ru.netology.bdd.pages;

import com.codeborne.selenide.SelenideElement;
import ru.netology.bdd.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private final SelenideElement amountInput =
            $("[data-test-id='amount'] input");

    private final SelenideElement fromInput =
            $("[data-test-id='from'] input");

    private final SelenideElement transferButton =
            $("[data-test-id='action-transfer']");

    public DashboardPage makeValidTransfer(
            String amountToTransfer,
            DataHelper.CardInfo fromCard
    ) {
        amountInput.setValue(amountToTransfer);
        fromInput.setValue(fromCard.getCardNumber());
        transferButton.click();

        return new DashboardPage();
    }
}
