package uz.isystem.studentweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uz.isystem.studentweb.dto.GroupTypeDto;
import uz.isystem.studentweb.exception.ServerBadRequestException;
import uz.isystem.studentweb.model.GroupType;
import uz.isystem.studentweb.repository.GroupTypeRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class GroupTypeService {

    @Autowired
    private GroupTypeRepository classTypeRepository;

    // Main function

    public GroupTypeDto create(GroupTypeDto groupTypeDto) {
        GroupType entity = new GroupType();
        entity.setName(groupTypeDto.getName());
        entity.setStatus(true);
        entity.setCreatedAt(LocalDateTime.now());
        classTypeRepository.save(entity);
        groupTypeDto.setId(entity.getId());
        groupTypeDto.setName(entity.getName());
        groupTypeDto.setStatus(entity.getStatus());
        groupTypeDto.setCreatedAt(entity.getCreatedAt());
        return groupTypeDto;
    }

    public GroupTypeDto get(Integer id) {
        GroupType entity = getEntity(id);
        GroupTypeDto groupTypeDto = new GroupTypeDto();
        groupTypeDto.setId(entity.getId());
        groupTypeDto.setName(entity.getName());
        groupTypeDto.setStatus(entity.getStatus());
        groupTypeDto.setCreatedAt(entity.getCreatedAt());
        groupTypeDto.setUpdatedAt(entity.getUpdatedAt());
        return groupTypeDto;
    }

    public List<GroupTypeDto> getAll() {
        List<GroupType> entityList = classTypeRepository.findAllByDeletedAtIsNull();
        if (entityList.isEmpty()) {
            throw new ServerBadRequestException("Group Type not found");
        }

        List<GroupTypeDto> dtoList = new LinkedList<>();
        for (GroupType groupType : entityList) {
            GroupTypeDto groupTypeDto = new GroupTypeDto();
            groupTypeDto.setId(groupType.getId());
            groupTypeDto.setName(groupType.getName());
            groupTypeDto.setStatus(groupType.getStatus());
            groupTypeDto.setCreatedAt(groupType.getCreatedAt());
            groupTypeDto.setUpdatedAt(groupType.getUpdatedAt());
            dtoList.add(groupTypeDto);
        }
        return dtoList;
    }

    public GroupTypeDto update(Integer id, GroupTypeDto dto) {
        GroupType entity = getEntity(id);
        entity.setName(dto.getName());
        entity.setUpdatedAt(LocalDateTime.now());
        classTypeRepository.save(entity);
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public Boolean delete(Integer id) {
        GroupType classType = getEntity(id);
        classType.setDeletedAt(LocalDateTime.now());
        classTypeRepository.save(classType);
        return true;
    }


    // Secondary function

    public GroupType getEntity(Integer id) {
        Optional<GroupType> optional = classTypeRepository.findById(id);
        if (optional.isEmpty() || optional.get().getDeletedAt() != null) {
            throw new ServerBadRequestException("Group Type not found");
        }
        return optional.get();
    }
}
