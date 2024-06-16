package ru.trofimov.unitTestingTraining.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.trofimov.unitTestingTraining.domain.User;
import ru.trofimov.unitTestingTraining.exception.ResourceAlreadyExistsException;
import ru.trofimov.unitTestingTraining.exception.ResourceNotFoundException;
import ru.trofimov.unitTestingTraining.repository.UserRepository;
import ru.trofimov.unitTestingTraining.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id [" + id + "] not found in DB."));
    }

    @Override
    public void addUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isEmpty()) {
            userRepository.save(user);
        } else {
            throw new ResourceAlreadyExistsException("User with " + user.getUsername() + " already exist.");
        }
    }

    @Override
    public void updateUser(User user) {
        if (userRepository.findById(user.getId()).isPresent()) {
            userRepository.save(user);
        } else {
            throw new ResourceNotFoundException("User with " + user.getId() + " not exist.");
        }
    }

    @Override
    public void deleteUser(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("User with id [" + id + "] not found in DB.");
        }
    }
}
