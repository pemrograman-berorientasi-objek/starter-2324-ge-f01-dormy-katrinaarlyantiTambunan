package pbo.f01.model;
import javax.persistence.*;
@Entity
@Table(name = "student")
public class Student {
    @Id
    @Column(name = "id",nullable = false,length = 25)
   private String id;
    @Column(name = "name",nullable = false,length = 50)
   private String name;
    @Column(name = "yearstudent",nullable = false,length = 25)
   private String yearstudent;
   @Column(name="gender",nullable = false,length = 25)
   private String gender;
   @ManyToOne
    @JoinColumn(name = "DORM_name")
    private Dorm dorm;
   public Student(String id, String name, String yearstudent,String gender){
    this.id = id;
    this.name=name;
    this.yearstudent= yearstudent;
    this.gender=gender;
   }
   public Student(){
   }
   public String getId(){
       return id;
   }
    public void setId(String id){
         this.id=id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getyearstudent(){
        return yearstudent;
    }
    public void setYear(String yearstudent){
        this.yearstudent=yearstudent;
    }
    public String getGender(){
        return gender;
    }
    public void setGender(String gender){
        this.gender=gender;
    }
    public Dorm getDorm() {
        return this.dorm;
    }
}

 
