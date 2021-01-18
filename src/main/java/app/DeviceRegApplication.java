package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude= HibernateJpaAutoConfiguration.class)
@EnableJpaRepositories(basePackages= "app.repository")
@EntityScan("app.*")
@EnableSwagger2
public class DeviceRegApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeviceRegApplication.class, args);
    }
}
