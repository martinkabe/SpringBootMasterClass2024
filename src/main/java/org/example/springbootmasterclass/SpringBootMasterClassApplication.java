package org.example.springbootmasterclass;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.example.springbootmasterclass.person.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;


@SpringBootApplication
@EnableScheduling
@EnableAsync
public class SpringBootMasterClassApplication {

    @Value("${app.stripe.api-key}")
    private String stripeKey;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootMasterClassApplication.class, args);

        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
//            System.out.println(beanDefinitionName);
        }

        System.out.println(beanDefinitionNames.length);
    }

    @Bean
    CommandLineRunner commandLineRunnerStripeKey() {
        return args -> {
            System.out.println(stripeKey);
        };
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
    CommandLineRunner commandLineRunnerApiURL(Environment environment) {
        return args -> {
            System.out.println(environment.getProperty("app.stripe.api-url"));
        };
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

    @Scheduled(
//            fixedRate = 5, timeUnit = TimeUnit.SECONDS, initialDelay = 10
            cron = "*/5 * * * * *"
    )
    @Async
    public void sendEmails() throws InterruptedException {
        System.out.println("Start sending email");
        Thread.sleep(2000);
        System.out.println("End sending email");
    }

    @Scheduled(cron = "*/5 * * * * *")
    @Async
    public void generateSalesReport() throws InterruptedException {
        System.out.println("Start sales report");
        Thread.sleep(2000);
        System.out.println("End sales report");
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
