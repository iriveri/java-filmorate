package ru.yandex.practicum.filmorate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    private static ObjectMapper objectMapper;

    @BeforeAll
    static void init() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    @DisplayName("Correct user should be validated correctly")
    void correctFilmShouldNotThrowException() throws Exception {
        User testUser = new User(null, "admin@admin.ru",
                "admin", "alex",
                LocalDate.of(2000, 1, 1));

        performUserPostAndExpectStatus(testUser, HttpStatus.CREATED);
    }

    @Test
    @DisplayName("User email should be validated correctly")
    void userEmailShouldNotBeEmailLike() throws Exception {
        User testUser = new User(null, "это-неправильный?эмейл@",
                "admin", "alex",
                LocalDate.of(2000, 1, 1));
        performUserPostAndExpectStatus(testUser, HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("User login should be validated correctly")
    void userLoginShouldNotEmptyOrContainSpaces() throws Exception {
        User testUser = new User(null, "admin@admin.ru",
                "a d m i n", "alex",
                LocalDate.of(2000, 1, 1));
        performUserPostAndExpectStatus(testUser, HttpStatus.BAD_REQUEST);
        testUser.setLogin("");
        performUserPostAndExpectStatus(testUser, HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("User birthdate should be validated correctly")
    void userBirthdateShouldBeInPast() throws Exception {
        User testUser = new User(null, "admin@admin.ru",
                "admin", "alex",
                LocalDate.of(2700, 1, 1));
        performUserPostAndExpectStatus(testUser, HttpStatus.BAD_REQUEST);
    }

    private void performUserPostAndExpectStatus(User user, HttpStatus status) throws Exception {
        String filmJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .content(filmJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(status.value()));
    }
}
