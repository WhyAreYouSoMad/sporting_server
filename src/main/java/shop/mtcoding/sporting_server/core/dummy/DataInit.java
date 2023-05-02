package shop.mtcoding.sporting_server.core.dummy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import shop.mtcoding.sporting_server.modules.user.repository.UserRepository;

@Component
public class DataInit extends DummyEntity {

    @Profile("dev")
    @Bean
    CommandLineRunner init(UserRepository userRepository) {
        return args -> {
            userRepository.save(newPlayerUser("ssar", "쌀"));
            userRepository.save(newCompanyUser("cos", "코스"));
        };
    }
}
