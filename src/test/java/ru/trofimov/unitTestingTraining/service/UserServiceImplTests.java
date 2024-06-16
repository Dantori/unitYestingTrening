package ru.trofimov.unitTestingTraining.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.trofimov.unitTestingTraining.domain.User;
import ru.trofimov.unitTestingTraining.exception.ResourceAlreadyExistsException;
import ru.trofimov.unitTestingTraining.exception.ResourceNotFoundException;
import ru.trofimov.unitTestingTraining.repository.UserRepository;
import ru.trofimov.unitTestingTraining.service.impl.UserServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    void test_getUserById_whenUserExists() {
        User expectedUser = new User(1L, "Иванов Иван", "ivanov@gmail.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(expectedUser));
        assertEquals(expectedUser, userServiceImpl.getUserById(1L));
    }

    @Test
    void test_getUserById_whenUserDoesNotExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userServiceImpl.getUserById(1L));
    }

    @Test
    void test_addUser_whenUserDoesNotExists() {
        User newUser = new User(1L, "Иванов Иван", "ivanov@gmail.com");
        when(userRepository.findByUsername("ivanov@gmail.com")).thenReturn(Optional.empty());
        userServiceImpl.addUser(newUser);
        verify(userRepository, times(1)).save(newUser);
    }

    @Test
    void test_addUser_whenUserExists() {
        User newUser = new User(1L, "Иванов Иван", "ivanov@gmail.com");
        when(userRepository.findByUsername("ivanov@gmail.com")).thenReturn(Optional.of(newUser));
        assertThrows(ResourceAlreadyExistsException.class, () -> userServiceImpl.addUser(newUser));
    }

    @Test
    void test_updateUser_whenUserExists() {
        User updatedUser = new User(1L, "Иванов Иван", "ivanov@gmail.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(updatedUser));
        userServiceImpl.updateUser(updatedUser);
        verify(userRepository, times(1)).save(updatedUser);
    }

    @Test
    void test_updateUser_whenUserDoesNotExists() {
        User nonexistentUser = new User(1L, "Иванов Иван", "ivanov@gmail.com");
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userServiceImpl.updateUser(nonexistentUser));
    }

    @Test
    void test_deleteUser_whenUserExists() {
        User user = new User(1L, "Иванов Иван", "ivanov@gmail.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        userServiceImpl.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void test_deleteUser_whenUserDoesNotExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userServiceImpl.deleteUser(1L));
    }
}
