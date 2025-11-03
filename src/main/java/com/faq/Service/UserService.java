package com.faq.Service;

import com.faq.Entity.UserEntity;
import com.faq.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserEntity> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public UserEntity createUser(UserEntity user) {
        validateUser(user);

        if (userRepository.findByUsernameIgnoreCase(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username '" + user.getUsername() + "' already exists");
        }

        return userRepository.save(user);
    }

    private void validateUser(UserEntity user) {
        if (user == null || !StringUtils.hasText(user.getUsername())) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (user.getEmail() != null && !user.getEmail().contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
}
