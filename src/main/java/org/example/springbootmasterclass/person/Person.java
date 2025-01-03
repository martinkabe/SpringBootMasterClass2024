package org.example.springbootmasterclass.person;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.springbootmasterclass.validation.Foo;

@Entity
@ToString
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Foo(message = "Name cannot be empty or Foo")
    private String name;

    @NotNull(message = "Age must not be null")
    @Min(value = 16, message = "Age must be at least 16")
    private Integer age;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Gender must not be null")
    private Gender gender;

    @Email
    @NotNull
    private String email;

    public Person(String name, Integer age, Gender gender, String email) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.email = email;
    }

    public String getProfile() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                '}';
    }

//    @JsonGetter("foobar")
//    public String getName() {
//        return name;
//    }
//
//    @JsonIgnore
//    public Integer getAge() {
//        return age;
//    }
}
