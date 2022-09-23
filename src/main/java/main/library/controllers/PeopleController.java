package main.library.controllers;


import main.library.models.Person;
import main.library.services.BooksService;
import main.library.services.PeopleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;

    private final BooksService booksService;






    @Autowired
    public PeopleController(PeopleService peopleService, BooksService booksService) {
        this.peopleService = peopleService;

        this.booksService = booksService;
    }



    @GetMapping()
    public String index(Model model){
        model.addAttribute("people", peopleService.findAll());
        return ("people/index");
}

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("person", peopleService.findOne(id));
        model.addAttribute("books", peopleService.findBooksByPersonId(id));
        return ("people/show");
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("person", peopleService.findOne(id));
        return ("people/edit");
    }

    @PatchMapping("{id}")
    public String update(@PathVariable("id") int id, @Valid @ModelAttribute("person") Person person,  BindingResult bindingResult){



        if(bindingResult.hasErrors()){
            return "people/edit";
        }
        peopleService.update(person, id);
        return ("redirect:/people/" + id);
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){

        return ("people/new");
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute("person") Person person, BindingResult bindingResult){



        if(bindingResult.hasErrors()){
            return "people/new";
        }
        peopleService.save(person);
        return ("redirect:/people/");
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        peopleService.delete(id);
        return ("redirect:/people/");
    }

}

