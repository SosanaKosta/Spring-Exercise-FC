package facilizationProject.facilizationProject.service;

import facilizationProject.facilizationProject.Entity.UserEntity;
import facilizationProject.facilizationProject.dto.UserDTO;
import facilizationProject.facilizationProject.exception.BadRequestException;
import facilizationProject.facilizationProject.exception.ResourceNotFoundException;
import facilizationProject.facilizationProject.mapper.UserMapper;
import facilizationProject.facilizationProject.repository.UserRepository;
import facilizationProject.facilizationProject.service.IService.IUserService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        if(userRepository.existsByEmail(userDTO.getEmail())){
            throw new BadRequestException("This email already exists");
        }

        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new BadRequestException("This username already exists");
        }

        UserEntity userEntity = UserMapper.toEntity(userDTO);
        userEntity.setCreatedAt(LocalDateTime.now());

        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        UserEntity savedUser = userRepository.save(userEntity);
        return UserMapper.toDTO(savedUser);
    }

    @Override
    public UserDTO findById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if(userEntity.isPresent()){
            return UserMapper.toDTO(userEntity.get());
        } else{
            throw  new ResourceNotFoundException("User with id" +id+ "not found" );
        }
    }

    @Override
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserMapper::toDTO);
    }

}
