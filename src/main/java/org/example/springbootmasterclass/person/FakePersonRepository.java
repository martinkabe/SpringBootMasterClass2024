package org.example.springbootmasterclass.person;

import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Repository
public class FakePersonRepository {

    private final AtomicInteger idCounter = new AtomicInteger(0);

    private final List<Person> people = new ArrayList<>();

    {
        people.add(new Person("John", 20, Gender.FEMALE, "john@example.com"));
        people.add(new Person("Jane", 19, Gender.FEMALE,  "jane@example.com"));
        people.add(new Person("Bob", 18, Gender.MALE,  "bob@example.com"));
    }

}
