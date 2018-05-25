package com.era.nurse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NurseTest {

    private Nurse nurse;

    @Before
    public void setUp(){
        nurse = new Nurse();
    }

    @Test
    public void shouldBuildRegister(){
        Register register = nurse.register(Patient.class)
                            .register(new Glucose())
                            .register(new Water())
                            .build();

        Glucose glucose = register.get(Glucose.class).get();
        Patient patient = register.get(Patient.class).get();
        Assert.assertEquals(glucose, patient.getGlucose());
    }

    @Test
    public void shouldScanPackagesForCures(){
        nurse.scan("com.era.nurse");
        Register register = nurse.build();

        Glucose glucose = register.get(Glucose.class).get();
        Assert.assertNotNull(glucose);
    }

    @Test
    public void shouldScanPackagesRecursivelyForCures(){
        nurse.scan("com.era");
        Register register = nurse.build();

        Glucose glucose = register.get(Glucose.class).get();
        Water water = register.get(Water.class).get();
        Assert.assertNotNull(glucose);
        Assert.assertNotNull(water);
        Assert.assertFalse(register.get(Patient.class).isPresent());
    }
}
