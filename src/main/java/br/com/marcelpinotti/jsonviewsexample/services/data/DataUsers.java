package br.com.marcelpinotti.jsonviewsexample.services.data;

import br.com.marcelpinotti.jsonviewsexample.entities.User;
import br.com.marcelpinotti.jsonviewsexample.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DataUsers implements ApplicationRunner {

    private final UserRepository repository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        User userOne = new User(null, "Marcel", "Pinotti", "marcel@xpto.com", "Marcel@123");
        repository.save(userOne);

        User userTwo = new User(null, "Pedro", "Pires", "pedro@xpto.com", "Pedro@123");
        repository.save(userTwo);

        User userThree = new User(null, "Mario", "Martins", "mario@xpto.com", "Mario@123");
        repository.save(userThree);
    }
}
