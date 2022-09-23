package main.library.controllers;


import main.library.models.Book;
import main.library.models.Person;
import main.library.services.BooksService;
import main.library.services.PeopleService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;

    private final PeopleService peopleService;


    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }



    @GetMapping()
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("books_per_page") Optional<Integer> booksPerPage, @RequestParam("sort_by_year") Optional<Boolean> sort){
        if(page.isPresent() && booksPerPage.isPresent()){
            model.addAttribute("books", booksService.findByPage(page.get(), booksPerPage.get(), sort));
        }
        else {
            model.addAttribute("books", booksService.findAll());
        }
        return ("books/index");
}

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, @ModelAttribute("person") Person person, Model model){
        model.addAttribute("book", booksService.findOne(id));
        model.addAttribute("owner", booksService.getBookOwner(id));
        model.addAttribute("people", peopleService.findAll());
        return ("books/show");
    }

    @GetMapping("search")
    public String index(Model model, @RequestParam("req") Optional<String> request){
        request.ifPresent(s -> model.addAttribute("books", booksService.findByNameLike(s)));
        return ("books/search");
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("book", booksService.findOne(id));
        return ("books/edit");
    }

    @PatchMapping("{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person person){
        booksService.assign(id, person);
        return ("redirect:/books/" + id);
    }

    @PatchMapping("{id}")
    public String deleteOwner(@PathVariable("id") int id){
        Book book = booksService.findOne(id);
        booksService.deleteOwner(book);
        return ("redirect:/books/" + id);
    }

    @PatchMapping("{id}/edit")
    public String edit(@PathVariable("id") int id, @ModelAttribute("book") Book book){
        booksService.update(id, book);
        return ("redirect:/books/" + id);
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){

        return ("books/new");
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "books/new";
        }

        booksService.save(book);
        return ("redirect:/books/");
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        booksService.delete(id);
        return ("redirect:/books/");
    }

}

