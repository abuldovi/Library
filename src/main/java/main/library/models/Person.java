package main.library.models;

import javax.validation.constraints.*;

public class Person {
    private int id;
    @Pattern(regexp = "[а-яА-ЯёЁa-zA-Z]+\\s+[а-яА-ЯёЁa-zA-Z]+\\s+[а-яА-ЯёЁa-zA-Z]+", message = "Поле должно быть в формате \"Фамилия Имя Отчество\"")
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;

    @Max(value = 2022, message = "Вы из будущего?")
    @Min(value = 1880, message = "А вы долгожитель")
    private int year_of_birth;

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

    public int getYear_of_birth() {
        return year_of_birth;
    }

    public void setYear_of_birth(int year_of_birth) {
        this.year_of_birth = year_of_birth;
    }
}
