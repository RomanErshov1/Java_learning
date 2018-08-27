package base.datasets;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "user")
public class UserDataSet extends DataSet {

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @OneToOne(cascade = CascadeType.ALL)
    private AddressDataSet address;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<PhoneDataSet> phoneNumbers = new ArrayList<>();

    public UserDataSet(){

    }

    public UserDataSet(String name, int age, AddressDataSet address, PhoneDataSet ... phones) {
        this.setId(-1);
        this.name = name;
        this.age = age;
        this.address = address;
        List<PhoneDataSet> userPhones = Arrays.asList(phones);
        this.setPhoneNumbers(userPhones);
        userPhones.forEach(phoneDataSet -> phoneDataSet.setUser(this));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    public List<PhoneDataSet> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneDataSet> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    @Override
    public String toString() {

        StringBuilder phonesBuilder = new StringBuilder();
        phoneNumbers.forEach(phone -> phonesBuilder.append(phone.toString()));

        return "UserDataSet{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address +
                ", phoneNumbers=" + phonesBuilder.toString() +
                '}';
    }
}
