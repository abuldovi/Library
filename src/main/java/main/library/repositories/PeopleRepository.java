package main.library.repositories;

import main.library.models.Book;
import main.library.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PeopleRepository extends JpaRepository<Person, Integer> {

}
