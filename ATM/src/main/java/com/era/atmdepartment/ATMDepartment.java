package com.era.atmdepartment;

import com.era.atm.ATM;
import com.era.atm.ATMState;

import java.util.ArrayList;
import java.util.List;

public class ATMDepartment {
    private List<ATM> atmList = new ArrayList<>();
    private List<ATMState> beginStates = new ArrayList<>();


    public void addATM(ATM atm) {
        atmList.add(atm);
        beginStates.add(atm.saveState());
    }

    public int collectBalance() {

        return atmList.stream().mapToInt(ATM::getBalance).sum();
    }

    public ATM getATM(int num) {
        return atmList.get(num);
    }

    public void resetATM() {
        for (int num = 0; num < atmList.size(); num++){
            atmList.get(num).restoreState(beginStates.get(num));
        }
    }
}
