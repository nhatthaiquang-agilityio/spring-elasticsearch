package sample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application-config-dev.properties")
public class DevProperties {

    @Value("${data.employee.firstname}")
    private String firstname;

    @Value("${data.employee.lastname}")
    private String lastname;

    //getters and setters

    @Override
    public String toString() {
        return firstname + " " + lastname;
    }
}