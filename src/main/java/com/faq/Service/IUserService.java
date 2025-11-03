package com.faq.Service;

import com.faq.Entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {

    List<UserEntity> getAllUsers();

    Optional<UserEntity> getUserById(UUID id);

    UserEntity createUser(UserEntity user);
}
