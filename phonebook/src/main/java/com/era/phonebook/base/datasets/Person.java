package com.era.phonebook.base.datasets;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tbl_person")
public class Person extends DataSet {

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Column(name = "memo")
    private String memo;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "persons")
    private List<Contact> contacts = new ArrayList<>();

    public Person(){}

    public Person(String fullName, Date birthday, String memo) {
        this.fullName = fullName;
        this.birthday = birthday;
        this.memo = memo;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "Person{" +
                "fullName='" + fullName + '\'' +
                ", birthday=" + birthday +
                ", memo='" + memo + '\'' +
                '}';
    }
}
