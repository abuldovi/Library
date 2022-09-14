package main.library.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.Optional;

public class Book {
    private int id;

    @NotEmpty(message = "Название не должно быть пустым")
    private String name;

    @Pattern(regexp = "[а-яА-ЯёЁa-zA-Z]+\\s+[а-яА-ЯёЁa-zA-Z]+", message = "Поле должно быть в формате \"Имя Фамилия\"")
    private String author;

    private int year_published;

    public Book(){}

    public Book(int id, String name, String author, int year_published, Optional<Integer> person_id) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year_published = year_published;
    }


    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
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

    public int getYear_published() {
        return year_published;
    }

    public void setYear_published(int year_published) {
        this.year_published = year_published;
    }


}
