package facilizationProject.facilizationProject.mapper;

import facilizationProject.facilizationProject.Entity.UserEntity;
import facilizationProject.facilizationProject.dto.UserDTO;

public class UserMapper {

    public static UserDTO toDTO(UserEntity userEntity){
        if(userEntity == null){
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setUsername(userEntity.getUsername());
       // userDTO.setPassword(userEntity.getPassword());
        userDTO.setCreatedAt(userEntity.getCreatedAt());
        return userDTO;
    }

    public static UserEntity toEntity(UserDTO dto){
        if(dto == null){
            return null;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setId(dto.getId());
        userEntity.setUsername(dto.getUsername());
        userEntity.setEmail(dto.getEmail());
        userEntity.setPassword(dto.getPassword());
        userEntity.setCreatedAt(dto.getCreatedAt());

        return userEntity;
    }
}
