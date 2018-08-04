package com.era.phonebook.base.datasets;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_city_code")
public class CityCode extends DataSet {

    @Column(name = "title")
    private String title;

    @Column(name = "code")
    private String code;

    @OneToMany(     mappedBy = "cityCode",
                    cascade = CascadeType.ALL,
                    orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();

    public CityCode(){}

    public CityCode(String title, String code) {
        this.title = title;
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "CityCode{" +
                "title='" + title + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
