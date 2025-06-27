package taskmanagementsystem.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import taskmanagementsystem.dto.user.UserRequest;
import taskmanagementsystem.dto.user.UserResponse;
import taskmanagementsystem.service.impl.UserServiceImpl;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = UserController.class)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WithMockUser
@ExtendWith(MockitoExtension.class)
@DisplayName("Тестирование контроллера UserController")
class UserControllerTest {

    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @MockBean
    UserServiceImpl userService;
    @Autowired
    private WebApplicationContext context;
    @MockBean
    private SecurityContext securityContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @Test

    @DisplayName("Проверка метода getById при успешном выполнении")
    public void testGetUserById_Success() throws Exception {
        var responseFromService = UserResponse.builder().username("user").email("1234@mai.com").username("user").build();
        when(userService.getById(1L)).thenReturn(responseFromService);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.email", is("1234@mai.com")))
                .andExpect(jsonPath("$.username", is("user")));
        verify(userService, Mockito.times(1)).getById(1L);
    }

    @Test
    @DisplayName("Проверка метода update() при успешном выполнении")
    @WithMockUser(roles = "ADMIN")
    public void testUpdate_Success() throws Exception {
        var actualRecord = UserRequest
                .builder()
                .username("user")
                .email("1234@mai.com")
                .password("1234")
                .build();
        var responseFromService = UserResponse
                .builder()
                .username("user 2")
                .email("12345@mai.com")
                .build();

        when(userService.update(1L,actualRecord)).thenReturn(responseFromService);
        Authentication mockAuthentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("user");
        when(mockAuthentication.isAuthenticated()).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.email", is("12345@mai.com")))
                .andExpect(jsonPath("$.username", is("user 2")));
        verify(userService, Mockito.times(1)).update(1L,actualRecord);
    }


    @Test
    @DisplayName("Проверка метода delete() при успешном выполнении")
    public void delete_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
