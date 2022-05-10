package uz.isystem.studentweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.isystem.studentweb.dto.UserGroupDto;
import uz.isystem.studentweb.exception.ServerBadRequestException;
import uz.isystem.studentweb.model.UserGroup;
import uz.isystem.studentweb.repository.UserGroupRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class UserGroupService {

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    //Main functions
    public UserGroupDto get(Integer id) {
        UserGroup userGroup = getEntity(id);
        UserGroupDto userGroupDto = new UserGroupDto();
        userGroupDto.setId(userGroup.getId());
        userGroupDto.setUser(userService.get(userGroup.getUserId()));
        userGroupDto.setUserId(userGroup.getUserId());
        userGroupDto.setGroup(groupService.get(userGroup.getGroupId()));
        userGroupDto.setGroupId(userGroup.getGroupId());
        userGroupDto.setStatus(userGroup.getStatus());
        userGroupDto.setCreatedAt(userGroup.getCreatedAt());
        userGroupDto.setUpdatedAt(userGroup.getUpdatedAt());
        return userGroupDto;
    }

    public List<UserGroupDto> getAll() {
        List<UserGroup> userGroupList = userGroupRepository.findAllByDeletedAtIsNull();
        if (userGroupList.isEmpty()) {
            throw new ServerBadRequestException("User Group not found");
        }
        List<UserGroupDto> userGroupDtoList = new LinkedList<>();
        for (UserGroup userGroup : userGroupList) {
            UserGroupDto userGroupDto = new UserGroupDto();
            userGroupDto.setId(userGroup.getId());
            userGroupDto.setUser(userService.get(userGroup.getUserId()));
            userGroupDto.setUserId(userGroup.getUserId());
            userGroupDto.setGroup(groupService.get(userGroup.getGroupId()));
            userGroupDto.setGroupId(userGroup.getGroupId());
            userGroupDto.setStatus(userGroup.getStatus());
            userGroupDto.setCreatedAt(userGroup.getCreatedAt());
            userGroupDto.setUpdatedAt(userGroup.getUpdatedAt());
            userGroupDtoList.add(userGroupDto);
        }
        return userGroupDtoList;
    }

    public UserGroupDto create(UserGroupDto userGroupDto) {
        //TODO course_id ni bor yoki yoqligini qayerdan tekshiramiz.
        UserGroup userGroup = new UserGroup();
        userService.getEntity(userGroupDto.getUserId());
        groupService.getEntity(userGroupDto.getGroupId());
        userGroup.setUserId(userGroupDto.getUserId());
        userGroup.setGroupId(userGroupDto.getGroupId());
        userGroup.setStatus(true);
        userGroup.setCreatedAt(LocalDateTime.now());
        userGroupRepository.save(userGroup);
        userGroupDto.setId(userGroup.getId());
//        userGroupDto.setStatus(userGroup.getStatus());
        userGroupDto.setCreatedAt(userGroup.getCreatedAt());
        return userGroupDto;
    }

    public UserGroupDto update(Integer id, UserGroupDto userGroupDto) {
        UserGroup entity = getEntity(id);
        userService.getEntity(userGroupDto.getUserId());
        groupService.getEntity(userGroupDto.getGroupId());
        entity.setUserId(userGroupDto.getUserId());
        entity.setGroupId(userGroupDto.getGroupId());
        entity.setUpdatedAt(LocalDateTime.now());
        userGroupRepository.save(entity);
        userGroupDto.setId(id);
        userGroupDto.setStatus(entity.getStatus());
        userGroupDto.setCreatedAt(entity.getCreatedAt());
        userGroupDto.setUpdatedAt(entity.getUpdatedAt());
        return userGroupDto;
    }

    public Boolean delete(Integer id) {
        UserGroup userGroup = getEntity(id);
        userGroup.setDeletedAt(LocalDateTime.now());
        userGroupRepository.save(userGroup);
        return true;
    }


    //Secondary functions
    public UserGroup getEntity(Integer id) {
        Optional<UserGroup> optional = userGroupRepository.findById(id);
        if (optional.isEmpty() || optional.get().getDeletedAt() != null) {
            throw new ServerBadRequestException("User Group not found");
        }
        return optional.get();
    }


}
