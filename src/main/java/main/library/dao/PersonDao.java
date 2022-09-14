package main.library.dao;

import main.library.models.Book;
import main.library.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index(){
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id){
        return jdbcTemplate.query("SELECT * FROM Person WHERE id = ?", new BeanPropertyRowMapper<>(Person.class), id).stream().findAny().get();
    }

    public Optional<Person> show(String name){
        return jdbcTemplate.query("SELECT * FROM Person WHERE name = ?", new BeanPropertyRowMapper<>(Person.class), name).stream().findAny();
    }

    public void update(Person person, int id){

        jdbcTemplate.update("UPDATE Person SET name = ?, year_of_birth = ? WHERE id = ?", person.getName(), person.getYear_of_birth(), id);
    }

    public void save(Person person){
        jdbcTemplate.update("INSERT INTO Person(name, year_of_birth) VALUES(?, ?)", person.getName(), person.getYear_of_birth());
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Person WHERE id = ?", id);
    }



}


