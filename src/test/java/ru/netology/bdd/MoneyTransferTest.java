package ru.netology.bdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.bdd.pages.DashboardPage;
import ru.netology.bdd.pages.LoginPage;
import ru.netology.bdd.pages.TransferPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.bdd.DataHelper.getAuthInfo;
import static ru.netology.bdd.DataHelper.getVerificationCode;


public class MoneyTransferTest {
    private final DataHelper.CardInfo firstCardInfo = DataHelper.getFirstCardInfo();
    private final DataHelper.CardInfo secondCardInfo = DataHelper.getSecondCardInfo();
    private DashboardPage dashboardPage;

    @BeforeEach
    void setup() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = getVerificationCode();
        dashboardPage = verificationPage.validVerification(verificationCode);
    }

    @Test
    void shouldTransferFromSecondToFirst() {
        int firstBalanceBefore =
                dashboardPage.getCardBalance(firstCardInfo);

        int secondBalanceBefore =
                dashboardPage.getCardBalance(secondCardInfo);

        int amount =
                DataHelper.generateValidAmount(secondBalanceBefore);

        TransferPage transferPage =
                dashboardPage.getCard(secondCardInfo);

        dashboardPage =
                transferPage.makeValidTransfer(
                        String.valueOf(amount),
                        firstCardInfo
                );

        assertEquals(
                firstBalanceBefore + amount,
                dashboardPage.getCardBalance(firstCardInfo)
        );

        assertEquals(
                secondBalanceBefore - amount,
                dashboardPage.getCardBalance(secondCardInfo)
        );
    }


    }









