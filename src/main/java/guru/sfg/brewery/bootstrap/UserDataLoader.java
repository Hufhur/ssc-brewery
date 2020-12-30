package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.AuthorityRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserDataLoader implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;

    public UserDataLoader(AuthorityRepository authorityRepository, UserRepository userRepository) {
        this.authorityRepository = authorityRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        if(authorityRepository.count() == 0) {
            loadUserData();
        }
    }

    private void loadUserData() {
        Authority adminRole = authorityRepository.save(Authority.builder().role("ROLE_ADMIN").build());
        Authority userRole = authorityRepository.save(Authority.builder().role("ROLE_USER").build());
        Authority customerRole = authorityRepository.save(Authority.builder().role("ROLE_CUSTOMER").build());

        User spring = User.builder()
                .username("spring")
                .password("{bcrypt}$2a$10$BuE.3DUP55mrTHvU.4cfiOh1WPyHsvH6XyqTGsNoF6lpOIn7NEstq")
                .authority(adminRole)
                .build();
        userRepository.save(spring);

        User user = User.builder()
                .username("user")
                .password("{sha256}390530137a662069c39793a9a0323714079a561e1b424cefe2786e661a471d147f1039eebda465c7")
                .authority(userRole)
                .build();
        userRepository.save(user);

        User scott = User.builder()
                .username("scott")
                .password("{bcrypt15}$2a$15$lEK6yX1E/gCbBUvnDNR1qOyM8KkBgbqF.A6qBfKUo3B26UH0kYo/u")
                .authority(customerRole)
                .build();
        userRepository.save(scott);


    }
}
