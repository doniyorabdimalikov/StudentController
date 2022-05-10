package uz.isystem.studentweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.isystem.studentweb.dto.UserTypeDto;
import uz.isystem.studentweb.exception.ServerBadRequestException;
import uz.isystem.studentweb.model.UserType;
import uz.isystem.studentweb.repository.UserTypeRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class UserTypeService {

    @Autowired
    private UserTypeRepository userTypeRepository;

    //Main functions
    public UserTypeDto get(Integer id) {
        UserType entity = getEntity(id);
        UserTypeDto dto = new UserTypeDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDisplayName(entity.getDisplayName());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public List<UserTypeDto> getByName(String name) {
        List<UserType> userTypeList = userTypeRepository.findAllByName(name);
        if (userTypeList.isEmpty()) {
            throw new ServerBadRequestException("UserType not found");
        }

        List<UserTypeDto> userTypeDtoList = new LinkedList<>();
        for (UserType e : userTypeList) {
            UserTypeDto dto = new UserTypeDto();
            dto.setId(e.getId());
            dto.setName(e.getName());
            dto.setDisplayName(e.getDisplayName());
            dto.setCreatedAt(e.getCreatedAt());
            dto.setUpdatedAt(e.getUpdatedAt());
            dto.setStatus(e.getStatus());
            userTypeDtoList.add(dto);
        }
        return userTypeDtoList;
    }

    public List<UserTypeDto> getAll() {
        List<UserType> entityList = userTypeRepository.findAllByDeletedAtIsNull();
        if (entityList.isEmpty()) {
            throw new ServerBadRequestException("UserType not found");
        }

        List<UserTypeDto> dtoList = new LinkedList<>();
        for (UserType userType : entityList) {
            UserTypeDto userTypeDto = new UserTypeDto();
            userTypeDto.setId(userType.getId());
            userTypeDto.setName(userType.getName());
            userTypeDto.setDisplayName(userType.getDisplayName());
            userTypeDto.setStatus(userType.getStatus());
            userTypeDto.setCreatedAt(userType.getCreatedAt());
            userTypeDto.setUpdatedAt(userType.getUpdatedAt());
            dtoList.add(userTypeDto);
        }
        return dtoList;
    }

    public UserTypeDto create(UserTypeDto dto) {
        UserType entity = new UserType();
        entity.setName(dto.getName());
        entity.setDisplayName(dto.getDisplayName());
        entity.setStatus(true);
        entity.setCreatedAt(LocalDateTime.now());
        userTypeRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public UserTypeDto update(Integer id, UserTypeDto userTypeDto) {
        UserType oldEntity = getEntity(id);
        oldEntity.setName(userTypeDto.getName());
        oldEntity.setDisplayName(userTypeDto.getDisplayName());
        oldEntity.setUpdatedAt(LocalDateTime.now());
        userTypeRepository.save(oldEntity);
        userTypeDto.setId(oldEntity.getId());
        userTypeDto.setStatus(oldEntity.getStatus());
        userTypeDto.setCreatedAt(oldEntity.getCreatedAt());
        userTypeDto.setUpdatedAt(oldEntity.getUpdatedAt());
        return userTypeDto;
    }

    public Boolean delete(Integer id) {
        UserType userType = getEntity(id);
        userType.setDeletedAt(LocalDateTime.now());
        userTypeRepository.save(userType);
        return true;
    }


    //Secondary functions
    public UserType getEntity(Integer id) {
        Optional<UserType> optional = userTypeRepository.findById(id);
        if (optional.isEmpty() || optional.get().getDeletedAt() != null) {
            throw new ServerBadRequestException("UserType not found");
        }
        return optional.get();
    }
}
