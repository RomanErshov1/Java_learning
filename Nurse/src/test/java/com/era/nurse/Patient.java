package com.era.nurse;

class Patient {

    @Inject
    private Glucose glucose;

    Glucose getGlucose() {
        return glucose;
    }
}
