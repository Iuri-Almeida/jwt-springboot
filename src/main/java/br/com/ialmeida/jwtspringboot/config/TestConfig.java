package br.com.ialmeida.jwtspringboot.config;

import br.com.ialmeida.jwtspringboot.entities.User;
import br.com.ialmeida.jwtspringboot.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    private final UserRepository userRepository;

    public TestConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        User u1 = new User(null, "Iuri Almeida", "123456");
        User u2 = new User(null, "Bob Green", "123456");
        User u3 = new User(null, "Luiz Fernando", "123456");
        userRepository.saveAll(Arrays.asList(u1, u2, u3));

    }
}
