package co.com.technicaltest.api;

import co.com.technicaltest.api.service.ApiRestService;
import co.com.technicaltest.api.util.Constants;
import co.com.technicaltest.model.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = ApiRest.class)
@ExtendWith(MockitoExtension.class)
class ApiRestTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private ApiRestService apiRestService;

    @InjectMocks
    private ApiRest apiRest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(apiRest).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createUser_ShouldReturnUser() throws Exception {

        var mockUser = new User("12334", "John dee");
        var user = new User();
        when(apiRestService.createUser(mockUser)).thenReturn(mockUser);

        mockMvc.perform(post("/api/v1/bankAccounts/newUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockUser)))
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.identityDocument").value("12334"))
                .andExpect(jsonPath("$.name").value("John dee"));


        verify(apiRestService, times(1)).createUser(any(User.class));
    }
}
