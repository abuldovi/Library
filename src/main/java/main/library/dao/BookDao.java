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
public class BookDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index(){
        return jdbcTemplate.query("SELECT * FROM Book ORDER BY id ASC", new BeanPropertyRowMapper<>(Book.class));
    }

    public void create(Book book){
        jdbcTemplate.update("INSERT INTO Book(name, author, year_published) VALUES (?, ?, ?)", book.getName(), book.getAuthor(), book.getYear_published());
    }

    public void update(Book book){
        jdbcTemplate.update("UPDATE Book SET name = ?, author = ?, year_published = ? WHERE id = ?", book.getName(), book.getAuthor(), book.getYear_published(), book.getId());
    }

    public void release(int id){
        jdbcTemplate.update("UPDATE Book SET person_id = NULL WHERE id = ? ", id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id=?", id);
    }

    public Book show(int id){
        return jdbcTemplate.query("SELECT * FROM Book WHERE Book.id = ?", new BeanPropertyRowMapper<>(Book.class), id).stream().findAny().get();
    }


    public Optional<Person> getOwner(int id){
        return jdbcTemplate.query("SELECT Person.* FROM Book JOIN Person ON Book.person_id = Person.id WHERE Book.id = ?", new BeanPropertyRowMapper<>(Person.class), id).stream().findAny();
    }

    public List<Book> getBooksByOwner(int id){
        return jdbcTemplate.query("SELECT Book.* FROM Book JOIN Person ON Book.person_id = Person.id WHERE Person.id = ?", new BeanPropertyRowMapper<>(Book.class), id);
    }


    public void updateOwner(Person person, int id){
        jdbcTemplate.update("UPDATE Book SET person_id = ? WHERE id = ? ", person.getId(), id);
    }





}
