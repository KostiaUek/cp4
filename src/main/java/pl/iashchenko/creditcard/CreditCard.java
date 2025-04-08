package pl.iashchenko.creditcard;

import java.math.BigDecimal;

public class CreditCard {
    public static final int CREDIT_THRESHOLD = 100;
    private final String cardNumber;
    private BigDecimal balance;
    private BigDecimal creditLimit;

    public CreditCard(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getNumber() {
        return cardNumber;
    }

    public void assignCredit(BigDecimal credit) {
        if (isCreditBelowThreshold(credit)) {
            throw new CreditBelowThresholdException();
        }

        if (isCreditLimitAlreadyAssigned()) {
            throw new CreditCantBeAssignedTwiceException();
        }

        this.balance = credit;
        this.creditLimit = credit;
    }

    private boolean isCreditLimitAlreadyAssigned() {
        return this.creditLimit != null;
    }

    public static boolean isCreditBelowThreshold(BigDecimal credit) {
        return BigDecimal.valueOf(CREDIT_THRESHOLD).compareTo(credit) > 0;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public void withdraw(BigDecimal withdrawAmount) {
        if (isNotEnoughBalance(withdrawAmount)) {
            throw new NotEnoughMoneyException();
        }

        this.balance = this.balance.subtract(withdrawAmount);
    }

    private boolean isNotEnoughBalance(BigDecimal withdrawAmount) {
        return withdrawAmount.compareTo(this.balance) > 0;
    }
}
