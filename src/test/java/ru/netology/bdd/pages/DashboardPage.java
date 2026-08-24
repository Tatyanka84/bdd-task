package ru.netology.bdd.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.bdd.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    private final SelenideElement header =
            $("[data-test-id='dashboard']");
    private final ElementsCollection cards =
            $$(".list__item div");

    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        header.shouldBe(visible);
        header.shouldHave(text("Личный кабинет"));
    }

    private SelenideElement getCardElement(DataHelper.CardInfo cardInfo) {
        return cards.find(attribute("data-test-id", cardInfo.getTestId()));
    }

    public int getCardBalance(DataHelper.CardInfo cardInfo) {
        String cardText = getCardElement(cardInfo).text();
        return extractBalance(cardText);
    }

    public TransferPage getCard(DataHelper.CardInfo cardInfo) {
        getCardElement(cardInfo).$("button").click();
        return new TransferPage();
    }

    private int extractBalance(String text) {
        int start = text.indexOf(balanceStart);
        int finish = text.indexOf(balanceFinish);
        String value = text.substring(start + balanceStart.length(), finish).replace(" ", "");
        return Integer.parseInt(value);
    }
}


