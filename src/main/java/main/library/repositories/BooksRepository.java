package main.library.repositories;

import main.library.models.Book;
import main.library.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByOwner(Person person);
    List<Book> findBookByNameContains(String string);

}
