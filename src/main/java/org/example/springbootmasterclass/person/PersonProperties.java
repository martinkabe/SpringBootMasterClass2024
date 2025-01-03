package org.example.springbootmasterclass.person;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "person")
public class PersonProperties {
    private int minimumAge;
}
