package pbo.f01;
import pbo.f01.model.Student;
import javax.persistence.*;
import pbo.f01.model.Dorm;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


/**
 * NIM - Nama
 * NIM - Nama
 */
public class App {
    private static EntityManager entityManager;
    private static EntityManagerFactory factory;
       private static Map<String, Dorm> dorms = new HashMap<>();
    private static Map<String, Student> students = new HashMap<>();
    public static void main(String[] _args) {
        factory= Persistence.createEntityManagerFactory("dormy_pu");
        entityManager=factory.createEntityManager();
        cleanTablesStudent();
        cleanTablesdorm();
        // seedTablesStudent();
        // seedTablesDorm();
        // displayStudents();
        // displayDorm();
        Scanner scanner = new Scanner(System.in);
        String input;
        while (!(input = scanner.nextLine()).equals("---")) {
            String[] parts = input.split("#");
            switch (parts[0]) {
                case "dorm-add":
                    addDorm(parts[1], Integer.parseInt(parts[2]), parts[3]);
                    break;
                case "student-add":
                    addStudent(parts[1], parts[2], parts[3], parts[4]);
                    break;
                case "assign":
                    assignStudentToDorm(parts[1], parts[2]);
                    break;
                case "display-all":
                    displayAll();
                    break;
            }
        }
        scanner.close();
        entityManager.close();
        // System.out.println("Hello guys");
    }
    // private static void displayStudents(){
    //     String jpql="SELECT s FROM Student s ORDER BY s.id";
    //     List<Student> students=entityManager.createQuery(jpql,Student.class).getResultList();
    //     // System.out.println("dispalyAllStudents:");
    //     for(Student student:students){
    //         System.out.println(student.getId()+"|"+student.getName()+"|"+student.getyearstudent()+"|"+student.getGender());
    //     }
    // }
    // private static void displayDorm(){
    //     String jpql="SELECT s FROM Dorm s ORDER BY s.name";
    //     List<Dorm> dorms=entityManager.createQuery(jpql,Dorm.class).getResultList();
    //     // System.out.println("dispalyAllStudents:");
    //     for(Dorm dorm:dorms){
    //         System.out.println(dorm.getName()+"|"+dorm.getCapacity()+"|"+dorm.getGender());
    //     }
    // }
    // private static void seedTablesStudent(){
    //     entityManager.getTransaction().begin();
    //     Student student1=new Student("19001","katrina","2019","perempuan");
    //     Student student2=new Student("19002","james","2019","laki-laki");
    //     Student student3=new Student("19003","jane","2019","perempuan");
    //     Student student4=new Student("19004","john","2019","laki-laki");
    //     entityManager.persist(student1);
    //     entityManager.persist(student2);
    //     entityManager.persist(student3);
    //     entityManager.persist(student4);
    //     entityManager.flush();
    //     entityManager.getTransaction().commit();
    // }
    // private static void seedTablesDorm(){
    //     entityManager.getTransaction().begin();
    //     Dorm dorm1=new Dorm("A",2,"perempuan");
    //     Dorm dorm2=new Dorm("B",2,"laki-laki");
    //     entityManager.persist(dorm1);
    //     entityManager.persist(dorm2);
    //     entityManager.flush();
    //     entityManager.getTransaction().commit();
    // }
    private static void addDorm(String name, int capacity, String gender) {
        Dorm dorm = new Dorm(name, capacity, gender);
        entityManager.getTransaction().begin();
        entityManager.persist(dorm);
        entityManager.getTransaction().commit();
        dorms.put(name, dorm);
    }
    private static void addStudent(String id, String name, String entranceYear, String gender) {
        Student student = new Student(id, name, entranceYear, gender);
        entityManager.getTransaction().begin();
        entityManager.persist(student);
        entityManager.getTransaction().commit();
        students.put(id, student);
    }
    private static void assignStudentToDorm(String studentId, String dormName) {
        Student student = students.get(studentId);
        Dorm dorm = dorms.get(dormName);
        if (student != null && dorm != null && dorm.getGender().equals(student.getGender()) && dorm.getStudents().size() < dorm.getCapacity()) {
            dorm.getStudents().add(student);
            entityManager.getTransaction().begin();
            entityManager.merge(dorm);
            entityManager.getTransaction().commit();
        }
    }
    private static void displayAll() {
        dorms.values().stream().sorted(Comparator.comparing(Dorm::getName)).forEach(dorm -> {
            System.out.println(dorm.getName() + "|" + dorm.getGender() + "|" + dorm.getCapacity() + "|" + dorm.getStudents().size());
            dorm.getStudents().stream().sorted(Comparator.comparing(Student::getName)).forEach(student -> {
                System.out.println(student.getId() + "|" + student.getName() + "|" + student.getyearstudent());
            });
        });
    }
    private static void cleanTablesStudent(){
        String jpql="DELETE FROM Student s";
        entityManager.getTransaction().begin();
        List<Student> students = entityManager.createQuery("SELECT s FROM Student s", Student.class).getResultList();
        for (Student student : students) {
            student.getDorm().getStudents().remove(student);
            entityManager.merge(student);
        }
        entityManager.createQuery(jpql).executeUpdate();
        entityManager.flush();
        entityManager.getTransaction().commit();
    }
    private static void cleanTablesdorm(){
        String jpql="DELETE FROM Dorm s";
        entityManager.getTransaction().begin();
        List<Dorm> dorms = entityManager.createQuery("SELECT d FROM Dorm d", Dorm.class).getResultList();
        for (Dorm dorm : dorms) {
            dorm.getStudents().clear();
            entityManager.merge(dorm);
        }
        entityManager.createQuery(jpql).executeUpdate();
        entityManager.flush();
        entityManager.getTransaction().commit();
    } 
    
}