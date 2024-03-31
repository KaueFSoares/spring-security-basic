package br.com.kauesoares.simplespringsecurityproject.project.repository;

import br.com.kauesoares.simplespringsecurityproject.project.base.BaseRepository;
import br.com.kauesoares.simplespringsecurityproject.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long>{

    Optional<User> findByUsername(String username);
}
