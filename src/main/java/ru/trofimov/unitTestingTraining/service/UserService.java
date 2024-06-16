package ru.trofimov.unitTestingTraining.service;

import ru.trofimov.unitTestingTraining.domain.User;

public interface UserService {

    User getUserById(Long id);
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(Long id);
}
