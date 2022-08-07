package br.com.marcelpinotti.jsonviewsexample.repositories;

import br.com.marcelpinotti.jsonviewsexample.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
