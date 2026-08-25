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
    private DashboardPage dashboardPage;
    private final DataHelper.CardInfo firstCardInfo = DataHelper.getFirstCardInfo();
    private final DataHelper.CardInfo secondCardInfo = DataHelper.getSecondCardInfo();

    @BeforeEach
    void setup() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = getVerificationCode();
        dashboardPage = verificationPage.validVerification(verificationCode);
    }

    @Test
    void shouldTransferFromFirstToSecond() {
        int firstBalanceBefore =
                dashboardPage.getCardBalance(firstCardInfo);

        int secondBalanceBefore =
                dashboardPage.getCardBalance(secondCardInfo);

        int amount =
                DataHelper.generateValidAmount(firstBalanceBefore);

        TransferPage transferPage =
                dashboardPage.getCard(firstCardInfo);

        dashboardPage =
                transferPage.makeValidTransfer(
                        String.valueOf(amount),
                        secondCardInfo
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

    @Test
    void shouldGetErrorMessageIfAmountIsMoreThanBalance() {
        int firstBalanceBefore =
                dashboardPage.getCardBalance(firstCardInfo);

        int secondBalanceBefore =
                dashboardPage.getCardBalance(secondCardInfo);

        int amount =
                DataHelper.generateInValidAmount(secondBalanceBefore);

        TransferPage transferPage =
                dashboardPage.getCard(secondCardInfo);

        transferPage.makeTransfer(
                String.valueOf(amount),
                firstCardInfo
        );

        transferPage.findErrorMessage(
                "Выполнена попытка перевода суммы, превышающей остаток на карте списания"
        );

        assertEquals(
                firstBalanceBefore,
                dashboardPage.getCardBalance(firstCardInfo)
        );

        assertEquals(
                secondBalanceBefore,
                dashboardPage.getCardBalance(secondCardInfo)
        );
    }
}








