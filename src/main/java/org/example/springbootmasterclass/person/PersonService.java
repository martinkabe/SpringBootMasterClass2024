package org.example.springbootmasterclass.person;

import org.example.springbootmasterclass.SortingOrder;
import org.example.springbootmasterclass.exception.DuplicateResourceException;
import org.example.springbootmasterclass.exception.InvalidValueException;
import org.example.springbootmasterclass.exception.ResourceNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PersonService {

    private final FakePersonRepository fakePersonRepository;

    private final PersonProperties personProperties;

    private final PersonRepository personRepository;

    public PersonService(FakePersonRepository fakePersonRepository, PersonProperties personProperties, PersonRepository personRepository) {
        this.fakePersonRepository = fakePersonRepository;
        this.personProperties = personProperties;
        this.personRepository = personRepository;
    }

    public List<Person> getPeople(
            SortingOrder sort
    ) {
        if (sort != SortingOrder.ASC && sort != SortingOrder.DESC) {
            throw new InvalidValueException("Invalid sorting order: " + sort);
        }

        return personRepository.findAll(
                Sort.by(
                        Sort.Direction.valueOf(sort.name()),
                        "id"
                )
        );
    }

    public Person getPersonById(
            Integer id
    ) {
        return personRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Person with id " + id + " not found")
        );
//        return fakePersonRepository.getPeople().stream()
//                .filter(p -> Objects.equals(p.getId(), id))
//                .findFirst()
//                .orElseThrow(() -> new ResourceNotFoundException("Person with id " + id + " not found"));
    }

    public void deletePersonById(Integer id) {
        boolean removedPerson = personRepository.existsById(id);
        if (!removedPerson) {
            throw new ResourceNotFoundException("Person with id " + id + " not found");
        }
        personRepository.deleteById(id);
    }

    public void addNewPerson(Person personRequest) {

        boolean exists = personRepository.existsByEmail(personRequest.getEmail());

        if (exists) {
            throw new DuplicateResourceException("Person with email " + personRequest.getEmail() + " already exists");
        }

        Person person = new Person(
                personRequest.getName(),
                personRequest.getAge(),
                personRequest.getGender(),
                personRequest.getEmail()
        );

        personRepository.save(person);

//        if (person.getEmail() != null && !person.getEmail().isEmpty()) {
//            boolean emailExists = fakePersonRepository.getPeople().stream()
//                    .anyMatch(existingPerson -> existingPerson.getEmail().equals(person.getEmail()));
//            if (emailExists) {
//                throw new DuplicateResourceException("The email '" + person.getEmail() + "' is already taken.");
//            }
//        }
//        fakePersonRepository.getPeople().add(
//                new Person(fakePersonRepository.getIdCounter().incrementAndGet(),
//                        person.getName(),
//                        person.getAge(),
//                        Gender.MALE,
//                        person.getEmail())
//        );
    }

    public Person updatePerson(
            Integer id,
            Person updatedPerson
    ) {
        // Fetch the existing person or throw an exception if not found
        Person existingPerson = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person with id " + id + " not found"));

        // Check if the updated email already exists for a different person
        if (updatedPerson.getEmail() != null &&
                personRepository.existsByEmail(updatedPerson.getEmail()) &&
                !existingPerson.getEmail().equalsIgnoreCase(updatedPerson.getEmail())) {
            throw new DuplicateResourceException("The email '" + updatedPerson.getEmail() + "' is already taken");
        }

        // Check age constraint from properties
        if (updatedPerson.getAge() != null && updatedPerson.getAge() < personProperties.getMinimumAge()) {
            throw new InvalidValueException("Age must be " + personProperties.getMinimumAge() + " or older.");
        }

        if (updatedPerson.getName() != null &&
            !updatedPerson.getName().isEmpty() &&
            !updatedPerson.getName().equalsIgnoreCase(existingPerson.getName())) {

            existingPerson.setName(updatedPerson.getName());
        }

        if (updatedPerson.getAge() != null &&
                !updatedPerson.getAge().equals(existingPerson.getAge())) {

            existingPerson.setAge(updatedPerson.getAge());
        }

        if (updatedPerson.getEmail() != null &&
                !updatedPerson.getEmail().isEmpty() &&
                !updatedPerson.getEmail().isBlank() &&
                !updatedPerson.getEmail().equalsIgnoreCase(existingPerson.getEmail())) {

            existingPerson.setEmail(updatedPerson.getEmail());
        }
        return personRepository.save(existingPerson);
    }
}
