package com.era.atm;

import java.util.HashMap;
import java.util.Map;

public class ATMState {
    private Map<Denomination, Integer> dispensers = new HashMap<>();

    public void setState(Map<Denomination, Integer> dispensers){
        this.dispensers = (Map<Denomination, Integer>)((HashMap<Denomination, Integer>)dispensers).clone();
    }

    public Map<Denomination, Integer> getState(){
        return dispensers;
    }
}
