package base.datasets;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserDataSet extends DataSet {

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @OneToOne(cascade = CascadeType.ALL)
    private AddressDataSet address;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PhoneDataSet> phoneNumbers = new HashSet<>();

    public UserDataSet(){

    }

    public UserDataSet(String name, int age, AddressDataSet address, Set<PhoneDataSet> phones) {
        this.setId(-1);
        this.name = name;
        this.age = age;
        this.address = address;
        this.phoneNumbers = phones;
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

    public Set<PhoneDataSet> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Set<PhoneDataSet> phoneNumbers) {
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
