package main.library.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Название не должно быть пустым")
    private String name;

    @Pattern(regexp = "[а-яА-ЯёЁa-zA-Z]+\\s+[а-яА-ЯёЁa-zA-Z]+", message = "Поле должно быть в формате \"Имя Фамилия\"")
    private String author;

    private int yearpublished;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;


    private LocalDateTime timestamp;

    public Book(){}

    public Book(int id, String name, String author, int yearpublished, Optional<Integer> person_id) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.yearpublished = yearpublished;
    }

    @Transient
    public boolean isExpired(){
        System.out.println(timestamp.isBefore(LocalDateTime.now().minusDays(10)));
        return timestamp.isBefore(LocalDateTime.now().minusDays(10));
    }


    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getyearpublished() {
        return yearpublished;
    }

    public void setyearpublished(int yearpublished) {
        this.yearpublished = yearpublished;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
