package com.era.nurse;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XmlPen implements Pen {

    private String fileName;

    public XmlPen(String fileName){
        this.fileName = fileName;
    }

    @Override
    public void write(Infirmary infirmary) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Infirmary.class,
                    Note.class, Injection.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(infirmary, new File(fileName));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Infirmary read() {
        Infirmary infirmary;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Infirmary.class, Note.class, Injection.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            infirmary = (Infirmary) unmarshaller.unmarshal(new File(fileName));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return infirmary;
    }
}
