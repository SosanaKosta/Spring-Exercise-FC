package facilizationProject.facilizationProject;

import facilizationProject.facilizationProject.Entity.UserEntity;
import facilizationProject.facilizationProject.dto.UserDTO;
import facilizationProject.facilizationProject.exception.BadRequestException;
import facilizationProject.facilizationProject.repository.UserRepository;
import facilizationProject.facilizationProject.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserDTO userDTO;

    @BeforeEach
    void setUp(){
        userDTO = new UserDTO();
        userDTO.setUsername("john_doe");
        userDTO.setEmail("johndoe@example.com");
        userDTO.setPassword("pass123");
    }

    @Test
    void createUser_Success_ShouldReturnSavedUserDto(){
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(false);
        when(userRepository.existsByUsername(userDTO.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("encodedHash");

        UserEntity savedEntity = new UserEntity();
        savedEntity.setId(1l);
        savedEntity.setUsername(userDTO.getUsername());
        savedEntity.setEmail(userDTO.getEmail());
        savedEntity.setPassword("encodedHash");

        when(userRepository.save(any(UserEntity.class))).thenReturn(savedEntity);

        UserDTO resultDTO = userService.createUser(userDTO);

        assertNotNull(resultDTO);
        assertEquals(1L, resultDTO.getId());
        assertEquals("john_doe", resultDTO.getUsername());
        assertNull(resultDTO.getPassword());

        verify(userRepository, times(1)).save(any(UserEntity.class));
    }


    @Test
    void createUser_DuplicateEmail_ShouldThrowBadRequestException(){
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(true);

        assertThrows(BadRequestException.class, ()->{
            userService.createUser(userDTO);
        });

        verify(userRepository, never()).save(any(UserEntity.class));
    }
}
