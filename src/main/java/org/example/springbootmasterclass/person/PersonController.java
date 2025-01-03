package org.example.springbootmasterclass.person;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.example.springbootmasterclass.SortingOrder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/persons")
public class PersonController {

    private final PersonService personService;
    private final Validator validator;

    public PersonController(PersonService personService, Validator validator) {
        this.personService = personService;
        this.validator = validator;
    }

    @GetMapping
    public List<Person> getPeople(
            HttpMethod httpMethod,
            ServletRequest request,
            ServletResponse response,
            @RequestHeader("Content-Type") String contentType,
            @RequestParam(value = "sort", required = false, defaultValue = "ASC") SortingOrder sort,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit
    ) {
        return personService.getPeople(sort, limit);
    }

    @GetMapping("{id}")
    public ResponseEntity<Person> getPersonById(
            @Valid @PathVariable("id") Integer id
    ) {
        Person person = personService.getPersonById(id);
        return ResponseEntity.ok().body(person);
    }

    @DeleteMapping("{id}")
    public void deletePersonById(@Valid @PathVariable("id") Integer id) {
        personService.deletePersonById(id);
    }

    @PostMapping
    public void addNewPerson(@Valid @RequestBody Person person) {
        /*
        Set<ConstraintViolation<Person>> validate = validator.validate(person);
        validate.forEach(error -> System.out.println(error.getMessage()));
        if (!validate.isEmpty()) {
            throw new ConstraintViolationException(validate);
        }
         */
        personService.addNewPerson(person);
    }

    @PutMapping("{id}")
    public ResponseEntity<Person> updatePerson(
            @Valid @PathVariable("id") Integer id,
            @RequestBody Person updatedPerson
    ) {
        Person person = personService.updatePerson(id, updatedPerson);
        return ResponseEntity.ok().body(person);
    }
}
