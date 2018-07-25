package com.era.atmdepartment;

import com.era.atm.ATM;
import com.era.atm.Denomination;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ATMDepartmentTest {
    private ATMDepartment atmDepartment;

    @Before
    public void setUp(){
        atmDepartment = new ATMDepartment();
        ATM atm1 = new ATM();
        atm1.deposit(5, Denomination.FIVE_HUNDREDS);
        atm1.deposit(10, Denomination.ONE_THOUSAND);

        ATM atm2 = new ATM();
        atm2.deposit(20, Denomination.ONE_HUNDRED);
        atm2.deposit(10, Denomination.ONE_THOUSAND);

        ATM atm3 = new ATM();
        atm3.deposit(14, Denomination.ONE_HUNDRED);
        atm3.deposit(7, Denomination.FIVE_HUNDREDS);

        atmDepartment.addATM(atm1);
        atmDepartment.addATM(atm2);
        atmDepartment.addATM(atm3);
    }

    @Test
    public void shouldCollectBalanceFromAllATM(){
        int balance = atmDepartment.collectBalance();
        Assert.assertEquals(29400, balance);
    }

    @Test
    public void shouldResetATM(){
        atmDepartment.getATM(0).deposit(1, Denomination.FIVE_THOUSANDS);
        atmDepartment.getATM(1).withdraw(2700);
        atmDepartment.getATM(2).deposit(2, Denomination.ONE_THOUSAND);
        int balanceBefore = atmDepartment.collectBalance();
        Assert.assertEquals(33700, balanceBefore);
        atmDepartment.resetATM();
        int balanceAfter = atmDepartment.collectBalance();
        Assert.assertEquals(29400, balanceAfter);
    }
}
