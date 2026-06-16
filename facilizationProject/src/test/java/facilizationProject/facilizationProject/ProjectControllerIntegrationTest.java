package facilizationProject.facilizationProject;

import facilizationProject.facilizationProject.Entity.UserEntity;
import facilizationProject.facilizationProject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProjectControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void initDatabaseUser(){
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("test");
        userEntity.setEmail("test@gm.com");
        userEntity.setPassword(passwordEncoder.encode("kmregvekr"));
        userEntity.setCreatedAt(LocalDateTime.now());
        userRepository.save(userEntity);
    }

    @Test
    void createProject_NoAuth_ShouldReturn401() throws Exception{
        String jsonPayload = "{\"name\":\"Project X\", \"description\":\"Missing credentials\"}";

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createProject_WithValidAuth_ShouldReturn201() throws Exception {
        String jsonPayload = "{\"name\":\"New Automated Test Project\", \"description\":\"Full context coverage validation\"}";

        mockMvc.perform(post("/api/projects")
                        // Inject credentials right into the request header matching your configuration patterns
                        .with(httpBasic("test", "kmregvekr"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("New Automated Test Project"))
                .andExpect(jsonPath("$.description").value("Full context coverage validation"));
    }
}
