package uz.isystem.studentweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.isystem.studentweb.dto.UserDto;
import uz.isystem.studentweb.dto.UserTypeDto;
import uz.isystem.studentweb.exception.ServerBadRequestException;
import uz.isystem.studentweb.model.User;
import uz.isystem.studentweb.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTypeService userTypeService;

    //Main functions
    public UserDto get(Integer id) {
        User user = getEntity(id);
        UserDto userDto = new UserDto();
        UserTypeDto userTypeDto = userTypeService.get(user.getUserTypeId());
        userDto.setId(user.getId());
        userDto.setUserType(userTypeDto);
        userDto.setUserTypeId(user.getUserTypeId());
        userDto.setUserId(user.getUserId());
        userDto.setChatId(user.getChatId());
        userDto.setUserName(user.getUserName());
        userDto.setPhone(user.getPhone());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPassword(user.getPassword());
        userDto.setStatus(user.getStatus());
        userDto.setCreatedAt(user.getCreatedAt());
        userDto.setUpdatedAt(user.getUpdatedAt());
        return userDto;
    }

    public List<UserDto> getAll() {
        List<User> userList = userRepository.findAllByDeletedAtIsNull();
        if (userList.isEmpty()) {
            throw new ServerBadRequestException("User not found");
        }
        List<UserDto> userDtoList = new LinkedList<>();
        for (User user : userList) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setUserType(userTypeService.get(user.getUserTypeId()));
            userDto.setUserTypeId(user.getUserTypeId());
            userDto.setUserId(user.getUserId());
            userDto.setChatId(user.getChatId());
            userDto.setUserName(user.getUserName());
            userDto.setPhone(user.getPhone());
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDto.setPassword(user.getPassword());
            userDto.setStatus(user.getStatus());
            userDto.setCreatedAt(user.getCreatedAt());
            userDto.setUpdatedAt(user.getUpdatedAt());
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    public UserDto create(UserDto dto) {
        //ToDo check user unique fields
        Optional<User> optional = userRepository.findByUserIdAndPhoneAndDeletedAtIsNull(dto.getUserId(), dto.getPhone());
        if (optional.isPresent()) {
            throw new ServerBadRequestException("User already  exist!");
        }

        userTypeService.getEntity(dto.getUserTypeId());
        User user = new User();
        user.setUserTypeId(dto.getUserTypeId());
        user.setUserId(dto.getUserId());
        user.setChatId(dto.getChatId());
        user.setUserName(dto.getUserName());
        user.setPhone(dto.getPhone());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPassword(dto.getPassword());
        user.setStatus(true);
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        dto.setId(user.getId());
        dto.setStatus(user.getStatus());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }

    public UserDto update(Integer id, UserDto userDto) {
        User entity = getEntity(id);
        userTypeService.getEntity(userDto.getUserTypeId());
        entity.setUserTypeId(userDto.getUserTypeId());
        entity.setUserId(userDto.getUserId());
        entity.setChatId(userDto.getChatId());
        entity.setUserName(userDto.getUserName());
        entity.setPhone(userDto.getPhone());
        entity.setFirstName(userDto.getFirstName());
        entity.setLastName(userDto.getLastName());
        entity.setPassword(userDto.getPassword());
        entity.setUpdatedAt(LocalDateTime.now());
        userRepository.save(entity);
        userDto.setId(entity.getId());
        userDto.setStatus(true);
        userDto.setCreatedAt(entity.getCreatedAt());
        userDto.setUpdatedAt(entity.getUpdatedAt());
        return userDto;
    }

    public Boolean delete(Integer id) {
        User user = getEntity(id);
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
        return true;
    }


    //Secondary functions
    public User getEntity(Integer id) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isEmpty() || optional.get().getDeletedAt() != null) {
            throw new ServerBadRequestException("User not found");
        }
        return optional.get();
    }

}
