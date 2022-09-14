package main.library.controllers;

import main.library.dao.BookDao;
import main.library.dao.PersonDao;
import main.library.models.Book;
import main.library.models.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDao bookDao;

    private final PersonDao personDao;

    public BooksController(BookDao bookDao, PersonDao personDao){
        this.bookDao = bookDao;
        this.personDao = personDao;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("books", bookDao.index());
        return ("books/index");
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return ("books/new");
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("book") Book book){
        bookDao.create(book);
        return ("redirect:/books/");
    }

    @GetMapping("{id}")
    public String show(@PathVariable("id") int id, @ModelAttribute("person") Person person, Model model){

        model.addAttribute("book", bookDao.show(id));
        model.addAttribute("owner", bookDao.getOwner(id));
        model.addAttribute("people", personDao.index());

        return "books/show";
    }

    @PatchMapping("{id}")
    public String release(@PathVariable("id") int id, Model model){
        bookDao.release(id);
        return "redirect:" + id;
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookDao.show(id));
        return "books/edit";
    }

    @PatchMapping("{id}/edit")
    public String update(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "books/edit";
        }
        bookDao.update(book);
        return "redirect:/books/" + book.getId();
    }

    @PatchMapping("/{id}/editOwner")
    public String updateOwner(@ModelAttribute("person") Person person, @PathVariable("id") int id){
        bookDao.updateOwner(person, id);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookDao.delete(id);
        return ("redirect:/books/");
    }



}
