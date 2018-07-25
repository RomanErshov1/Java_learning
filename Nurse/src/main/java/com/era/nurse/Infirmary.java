package com.era.nurse;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Infirmary {

    private List<Note> notes = new ArrayList<>();

    public List<Note> getNotes() {
        return notes;
    }

    @XmlElement(name = "note")
    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
