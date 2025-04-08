package pl.iashchenko.creditcard;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class CreditCardTest {
    @Test
    void creditCardIsIdentifiedWithNumber() {
        //Arrange      // Given     // Input
        var number = "1234-5678";
        //Act          // When      // Action
        var card = new CreditCard(number);
        //Assert       // Then      // Output
        assertEquals("1234-5678", card.getNumber());

    }

    @Test
    void itAllowsToAssignCreditLimit() {
        //Arrange      // Given     // Input
        var card = new CreditCard("1234-56678");
        //Act          // When      // Action
        card.assignCredit(BigDecimal.valueOf(1500));
        //Assert       // Then      // Output
        assertEquals(BigDecimal.valueOf(1500), card.getBalance());

    }

    @Test
    void creditLimitCantBeLowerThenCertainThreshold() {
        var card = new CreditCard("1234-56678");

        try {
            card.assignCredit(BigDecimal.valueOf(90));
            fail("Exception should be thrown");
        } catch (CreditBelowThresholdException e) {
            assertTrue(true);
        }


    }

    @Test
    void creditLimitCantBeLowerThenCertainThresholdV2() {
        var card = new CreditCard("1234-56678");


        assertThrows(
                CreditBelowThresholdException.class,
                () -> card.assignCredit(BigDecimal.valueOf(99))
        );

        assertDoesNotThrow(
                () -> card.assignCredit(BigDecimal.valueOf(100))
        );
    }


    @Test
    void creditCantBeAssignedTwice() {
        var card = new CreditCard("1234-56678");
        card.assignCredit(BigDecimal.valueOf(1500));

        assertThrows(
                CreditCantBeAssignedTwiceException.class,
                () -> card.assignCredit(BigDecimal.valueOf(2000))
        );
    }

    @Test
    void iamAbleToWithdrawSomeMoney() {
        var card = new CreditCard("1234-56678");
        card.assignCredit(BigDecimal.valueOf(1500));

        card.withdraw(BigDecimal.valueOf(100));
        card.withdraw(BigDecimal.valueOf(100));
        card.withdraw(BigDecimal.valueOf(100));

        assertEquals(
                BigDecimal.valueOf(1200),
                card.getBalance()
        );
    }

    @Test
    void iamAbleToWithdrawSomeMoneyV2() {
        var card = new CreditCard("1234-56678");
        card.assignCredit(BigDecimal.valueOf(1500));

        assertThrows(
                NotEnoughMoneyException.class,
                () -> card.withdraw(BigDecimal.valueOf(1600))
        );

    }

}
