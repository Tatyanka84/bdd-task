package ru.netology.bdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.bdd.pages.DashboardPage;
import ru.netology.bdd.pages.LoginPage;
import ru.netology.bdd.pages.TransferPage;
import ru.netology.bdd.pages.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.bdd.DataHelper.*;

public class MoneyTransferTest {

    private final DataHelper.CardInfo firstCardInfo =
            getFirstCardInfo();
    private final DataHelper.CardInfo secondCardInfo =
            getSecondCardInfo();
    private DashboardPage dashboardPage;

    @BeforeEach
    void setup() {
        LoginPage loginPage =
                open("http://localhost:9999", LoginPage.class);

        VerificationPage verificationPage =
                loginPage.validLogin(getAuthInfo());

        dashboardPage =
                verificationPage.validVerification(
                        getVerificationCode()
                );
    }

    @Test
    void shouldTransferFromFirstToSecond() {
        int firstBalanceBefore =
                dashboardPage.getCardBalance(firstCardInfo);

        int secondBalanceBefore =
                dashboardPage.getCardBalance(secondCardInfo);

        int amount =
                generateValidAmount(firstBalanceBefore);

        TransferPage transferPage =
                dashboardPage.getCard(secondCardInfo);

        dashboardPage =
                transferPage.makeValidTransfer(
                        String.valueOf(amount),
                        firstCardInfo
                );

        assertEquals(
                firstBalanceBefore - amount,
                dashboardPage.getCardBalance(firstCardInfo)
        );

        assertEquals(
                secondBalanceBefore + amount,
                dashboardPage.getCardBalance(secondCardInfo)
        );
    }
}









