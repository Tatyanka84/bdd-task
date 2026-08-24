package ru.netology.bdd;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import ru.netology.bdd.pages.DashboardPage;
import ru.netology.bdd.pages.LoginPage;
import ru.netology.bdd.pages.TransferPage;
import ru.netology.bdd.pages.VerificationPage;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MoneyTransferTest {

    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getVerificationCode();
        var firstCardInfo = DataHelper.getFirstCardInfo();
        var secondCardInfo = DataHelper.getSecondCardInfo();

        LoginPage loginPage = Selenide.open("http://localhost:9999", LoginPage.class);

        VerificationPage verificationPage = loginPage.validLogin(authInfo);
        DashboardPage dashboardPage = verificationPage.validVerification(verificationCode);

        int firstBalanceBefore = dashboardPage.getCardBalance(firstCardInfo);
        int secondBalanceBefore = dashboardPage.getCardBalance(secondCardInfo);

        int amount = 100;

        TransferPage transferPage = dashboardPage.getCard(firstCardInfo);
        dashboardPage = transferPage.transferFrom(secondCardInfo, firstCardInfo, amount);

        int firstBalanceAfter = dashboardPage.getCardBalance(firstCardInfo);
        int secondBalanceAfter = dashboardPage.getCardBalance(secondCardInfo);

        assertEquals(firstBalanceBefore + amount, firstBalanceAfter);
        assertEquals(secondBalanceBefore - amount, secondBalanceAfter);
    }
}



