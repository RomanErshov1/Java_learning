package com.era.nurse;

import org.junit.Test;

public class XMLTest {
    private Pen pen = new XmlPen("target/infirmary.xml");

    @Test
    public void shouldSaveConfigurationToXmlFile(){
        Nurse nurse = new Nurse();
        nurse.scan("com.era");
        nurse.register(Patient.class);
        Register reg = nurse.build();
        pen.write(reg.createInfirmary());
    }
}
