package pbo.f01.model;
import javax.persistence.*;
import java.util.*;
@Entity
@Table(name = "dorm")
public class Dorm {
    @Id
    @Column(name = "name",nullable = false,length = 50)
    private String name;
    @Column(name = "capacity",nullable = false,length = 25)
    private int capacity;
    @Column(name = "gender",nullable = false,length = 25)
    private String gender;
    @OneToMany
    private List<Student> students = new ArrayList<>();
    public Dorm( String name,int capacity,String gender){
        this.name=name;
        this.capacity=capacity;
        this.gender=gender;
    }
    public Dorm(){

    }
public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public int getCapacity() {
    return capacity;
}

public void setCapacity(int capacity) {
    this.capacity = capacity;
}

public String getGender() {
    return gender;
}

public void setGender(String gender) {
    this.gender = gender;
}
public List<Student> getStudents() {
    return students;
}
}