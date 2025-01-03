package org.example.springbootmasterclass;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.example.springbootmasterclass.jsonplaceholder.JsonPlaceholderService;
import org.example.springbootmasterclass.person.Person;
import org.example.springbootmasterclass.post.Post;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.*;


@SpringBootApplication
public class SpringBootMasterClassApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootMasterClassApplication.class, args);

        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
//            System.out.println(beanDefinitionName);
        }

        System.out.println(beanDefinitionNames.length);
    }

    //    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
//    @SessionScope
//    @ApplicationScope
    @Bean
    public String redBean() {
        return "Manchester United";
    }

    @Bean
    public String blueBean() {
        return "Chelsea";
    }

    @Bean
    CommandLineRunner commandLineRunner1(String redBean, String blueBean, UserService userService) {
        return args -> {
            System.out.println("Hello From CommandLineRunner 1");
            System.out.println(redBean);
            System.out.println(blueBean);
            System.out.println(userService.getUsers());
            System.out.println();
        };
    }

    @Bean
    CommandLineRunner commandLineRunner2(String redBean, String blueBean, UserService userService) {
        return args -> {
            System.out.println("Hello From CommandLineRunner 2");
            System.out.println(redBean);
            System.out.println(blueBean);
            System.out.println(userService.getUserById(2));
            System.out.println(userService.getUserById(3));
        };
    }

    @Bean
    CommandLineRunner commandLineRunner3(ObjectMapper objectMapper) throws JsonProcessingException {
        String personString = "{\"id\": 1, \"name\":\"John Doe\", \"gender\": \"MALE\", \"age\": 2}";
        Person person = objectMapper.readValue(personString, Person.class);
        System.out.println(person);
        System.out.println(objectMapper.writeValueAsString(person));
        return args -> {

        };
    }

    public record User(int id, String name) {}

    @Service
    public class UserService {

        public UserService() {
            System.out.println("UserService Constructor");
        }

        @PostConstruct
        public void init() {
            System.out.println("Fill redis cache ...");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("Clear redis cache ...");
        }

        public List<User> getUsers() {
            return List.of(
                    new User(1, "John Doe"),
                    new User(2, "Jane Doe")
            );
        }

        public Optional<User> getUserById(int id) {
            return getUsers().stream()
                    .filter(u -> u.id == id)
                    .findFirst();
        }
    }
}
