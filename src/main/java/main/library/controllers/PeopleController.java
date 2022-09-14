package main.library.controllers;

import main.library.dao.BookDao;
import main.library.dao.PersonDao;
import main.library.models.Person;
import main.library.util.PersonValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDao personDao;

    private final BookDao bookDao;

    private final PersonValidator personValidator;

    public PeopleController(PersonDao personDao, BookDao bookDao, PersonValidator personValidator) {
        this.personDao = personDao;
        this.bookDao = bookDao;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people", personDao.index());
        return ("people/index");
}

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDao.show(id));
        model.addAttribute("books", bookDao.getBooksByOwner(id));
        return ("people/show");
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDao.show(id));
        return ("people/edit");
    }

    @PatchMapping("{id}")
    public String update(@PathVariable("id") int id, @Valid @ModelAttribute("person") Person person,  BindingResult bindingResult){

        personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors()){
            return "people/edit";
        }
        personDao.update(person, id);
        return ("redirect:/people/" + id);
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){

        return ("people/new");
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute("person") Person person, BindingResult bindingResult){

        personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors()){
            return "people/new";
        }
        personDao.save(person);
        return ("redirect:/people/");
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personDao.delete(id);
        return ("redirect:/people/");
    }

}

