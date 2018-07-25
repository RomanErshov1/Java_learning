package com.era.atm;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static com.era.atm.Denomination.*;

public class ATMTest {

    private ATM atm;

    @Before
    public void setUp() throws Exception{
        atm = new ATM();
    }

    @Test
    public void shouldDepositNotesOfDifferentDenominations() {
        atm.deposit(2, ONE_HUNDRED);
        atm.deposit(3, FIVE_HUNDREDS);
        int balance = atm.getBalance();
        Assert.assertEquals(1700, balance);
    }

    @Test
    public void shouldWithdrawByMinimumOfNotes() {
        atm.deposit(10, ONE_THOUSAND);
        atm.deposit(10, ONE_HUNDRED);
        atm.deposit(10, FIVE_HUNDREDS);
        atm.deposit(10, FIVE_THOUSANDS);
        int balanceBefore = atm.getBalance();
        Map<Denomination, Integer> withdrawal = atm.withdraw(1700);
        Assert.assertEquals(1, withdrawal.get(ONE_THOUSAND).intValue());
        Assert.assertEquals(1, withdrawal.get(FIVE_HUNDREDS).intValue());
        Assert.assertEquals(2, withdrawal.get(ONE_HUNDRED).intValue());
        int balanceAfter = atm.getBalance();
        Assert.assertEquals(balanceBefore - 1700, balanceAfter);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenAmountCanNotBeWithdraw() {
        atm.deposit(10, ONE_THOUSAND);
        atm.withdraw(1700);
    }

    @Test
    public void shouldNotDispenseWhenCantDispense() {
        atm.deposit(10, ONE_THOUSAND);
        Assert.assertEquals(10_000, atm.getBalance());
        try {
            atm.withdraw(1700);
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
        Assert.assertEquals(10_000, atm.getBalance());
    }

    @Test
    public void shouldNotDispenseMoreThanThereIsNotes() {
        atm.deposit(1, ONE_THOUSAND);
        atm.deposit(10, FIVE_HUNDREDS);
        int balanceBefore = atm.getBalance();
        Map<Denomination, Integer> withdrawal = atm.withdraw(2500);
        Assert.assertEquals(balanceBefore - 2500, atm.getBalance());
        Assert.assertEquals(3, withdrawal.get(FIVE_HUNDREDS).intValue());
        Assert.assertEquals(1, withdrawal.get(ONE_THOUSAND).intValue());
    }

}
