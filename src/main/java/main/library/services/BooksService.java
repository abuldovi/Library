package main.library.services;

import main.library.models.Book;
import main.library.models.Person;
import main.library.repositories.BooksRepository;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(){
        return booksRepository.findAll();
    }


    public List<Book> findByPage(int page, int itemsPerPage, Optional<Boolean> sort){
        if (sort.isPresent() && sort.get()) {return booksRepository.findAll(PageRequest.of(page, itemsPerPage, Sort.by("yearpublished"))).getContent();}
        else {
            return booksRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();}
    }

    public Person getBookOwner(int id){
        return booksRepository.findById(id).get().getOwner();
    }

    public Book findOne(int id){
        return booksRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }


    @Transactional
    public void assign(int id, Person person){
        booksRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(person);
                    book.setTimestamp(LocalDateTime.now());
                }
        );

    }

    @Transactional
    public void deleteOwner(Book updatedBook){
        updatedBook.setOwner(null);
        updatedBook.setTimestamp(null);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }

    @Transactional
    public void update(int id, Book book) {
        Book bookToUpdate = booksRepository.findById(id).get();
        book.setOwner(bookToUpdate.getOwner());
        booksRepository.save(book);
    }

    @Transactional
    public List<Book> findByNameLike(String request) {
        return booksRepository.findBookByNameContains(request);
    }



}
