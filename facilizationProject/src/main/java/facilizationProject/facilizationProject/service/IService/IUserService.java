package facilizationProject.facilizationProject.service.IService;

import facilizationProject.facilizationProject.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IUserService {

    UserDTO createUser(UserDTO userDTO);
    UserDTO findById(Long id);
    Page<UserDTO> getAllUsers(Pageable pageable);
}
