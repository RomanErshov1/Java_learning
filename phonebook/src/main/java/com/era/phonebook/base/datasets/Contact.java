package com.era.phonebook.base.datasets;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_contact")
public class Contact extends DataSet{

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "type")
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_code_id")
    private CityCode cityCode;

    @ManyToMany(    fetch = FetchType.LAZY)
    @JoinTable(name = "tbl_x_ref", joinColumns = {
            @JoinColumn(name = "contact_id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "person_id")
    })
    private List<Person> persons = new ArrayList<>();

    public Contact() {
    }

    public Contact(String phoneNumber, String type, CityCode cityCode, List<Person> persons) {
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.cityCode = cityCode;
        this.persons = persons;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCityCode(CityCode cityCode) {
        this.cityCode = cityCode;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", type='" + type + '\'' +
                ", cityCode=" + cityCode +
                ", persons=" + persons +
                '}';
    }
}
