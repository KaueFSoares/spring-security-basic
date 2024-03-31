package br.com.kauesoares.simplespringsecurityproject.project.service;

import br.com.kauesoares.simplespringsecurityproject.project.messages.exception.BadRequestException;
import br.com.kauesoares.simplespringsecurityproject.project.model.User;
import br.com.kauesoares.simplespringsecurityproject.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException("User not found"));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

}
