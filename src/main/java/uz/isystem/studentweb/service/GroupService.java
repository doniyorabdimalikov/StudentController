package uz.isystem.studentweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.isystem.studentweb.dto.GroupDto;
import uz.isystem.studentweb.exception.ServerBadRequestException;
import uz.isystem.studentweb.model.Group;
import uz.isystem.studentweb.repository.GroupRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private GroupTypeService groupTypeService;

    //Main functions
    public GroupDto get(Integer id) {
        Group group = getEntity(id);
        GroupDto dto = new GroupDto();
        dto.setId(group.getId());
        dto.setCourse(courseService.get(group.getCourseId()));
        dto.setCourseId(group.getCourseId());
        dto.setGroupType(groupTypeService.get(group.getGroupTypeId()));
        dto.setGroupTypeId(group.getGroupTypeId());
        dto.setName(group.getName());
        dto.setStatus(group.getStatus());
        dto.setCreatedAt(group.getCreatedAt());
        dto.setUpdatedAt(group.getUpdatedAt());
        return dto;
    }

    public List<GroupDto> getAll() {
        List<Group> groupList = groupRepository.findAllByDeletedAtIsNull();
        if (groupList.isEmpty()) {
            throw new ServerBadRequestException("Group not found");
        }
        List<GroupDto> groupDtoList = new LinkedList<>();
        for (Group group : groupList) {
            GroupDto dto = new GroupDto();
            dto.setId(group.getId());
            dto.setCourse(courseService.get(group.getCourseId()));
            dto.setCourseId(group.getCourseId());
            dto.setGroupType(groupTypeService.get(group.getGroupTypeId()));
            dto.setGroupTypeId(group.getGroupTypeId());
            dto.setName(group.getName());
            dto.setStatus(group.getStatus());
            dto.setCreatedAt(group.getCreatedAt());
            dto.setUpdatedAt(group.getUpdatedAt());
            groupDtoList.add(dto);
        }
        return groupDtoList;
    }

    public GroupDto create(GroupDto groupDto) {
        //TODO qaysi biri menytoone bo'ladi. group va grouptype
        courseService.getEntity(groupDto.getCourseId());
        groupTypeService.getEntity(groupDto.getGroupTypeId());
        Group group = new Group();
        group.setCourseId(groupDto.getCourseId());
        group.setGroupTypeId(groupDto.getGroupTypeId());
        group.setName(groupDto.getName());
        group.setStatus(true);
        group.setCreatedAt(LocalDateTime.now());
        groupRepository.save(group);
        groupDto.setId(group.getId());
        groupDto.setStatus(group.getStatus());
        groupDto.setCreatedAt(group.getCreatedAt());
        return groupDto;
    }

    public GroupDto update(Integer id, GroupDto groupDto) {
        Group entity = getEntity(id);
        courseService.getEntity(groupDto.getCourseId());
        groupTypeService.getEntity(groupDto.getGroupTypeId());
        entity.setCourseId(groupDto.getCourseId());
        entity.setGroupTypeId(groupDto.getGroupTypeId());
        entity.setName(groupDto.getName());
        entity.setUpdatedAt(LocalDateTime.now());
        groupRepository.save(entity);
        groupDto.setId(entity.getId());
        groupDto.setStatus(entity.getStatus());
        groupDto.setCreatedAt(entity.getCreatedAt());
        groupDto.setUpdatedAt(entity.getUpdatedAt());
        return groupDto;
    }

    public Boolean delete(Integer id) {
        Group group = getEntity(id);
        group.setDeletedAt(LocalDateTime.now());
        groupRepository.save(group);
        return true;
    }



    //Secondary functions
    public Group getEntity(Integer id) {
        Optional<Group> optional = groupRepository.findById(id);
        if (optional.isEmpty() || optional.get().getDeletedAt() != null) {
            throw new ServerBadRequestException("Group not found");
        }
        return optional.get();
    }
}
