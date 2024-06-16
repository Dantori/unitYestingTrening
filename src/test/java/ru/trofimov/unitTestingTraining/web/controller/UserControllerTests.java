package ru.trofimov.unitTestingTraining.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.trofimov.unitTestingTraining.domain.User;
import ru.trofimov.unitTestingTraining.service.UserService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void test_getUser() throws Exception {
        User expectedUser = new User(1L, "Иванов Иван", "ivanov@gmail.com");
        when(userService.getUserById(1L)).thenReturn(expectedUser);
        mockMvc.perform(get("/api/v1/user/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Иванов Иван"))
                .andExpect(jsonPath("$.username").value("ivanov@gmail.com"));
        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void test_addUser() throws Exception {
        User newUser = new User(2L, "Иванов Иван", "ivanov@gmail.com");
        String jsonRequest = objectMapper.writeValueAsString(newUser);
        mockMvc.perform(post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated());
        verify(userService, times(1)).addUser(newUser);
    }

    @Test
    void test_updateUser() throws Exception {
        User updatedUser = new User(2L, "Иванов Иван", "ivanov@gmail.com");
        String jsonRequest = objectMapper.writeValueAsString(updatedUser);
        mockMvc.perform(put("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
        verify(userService, times(1)).updateUser(updatedUser);
    }

    @Test
    void test_deleteUser() throws Exception {
        mockMvc.perform(delete("/api/v1/user/{id}", 1L))
                .andExpect(status().isNoContent());
        verify(userService, times(1)).deleteUser(1L);
    }
}