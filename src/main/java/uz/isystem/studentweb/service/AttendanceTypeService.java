package uz.isystem.studentweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uz.isystem.studentweb.dto.AttendanceTypeDto;
import uz.isystem.studentweb.exception.ServerBadRequestException;
import uz.isystem.studentweb.model.AttendanceType;
import uz.isystem.studentweb.repository.AttendanceTypeRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class AttendanceTypeService {

    @Autowired
    private AttendanceTypeRepository repository;

    // Main function...
    public AttendanceTypeDto create(AttendanceTypeDto dto) {
        AttendanceType entity = new AttendanceType();
        entity.setName(dto.getName());
        entity.setStatus(true);
        entity.setCreatedAt(LocalDateTime.now());
        repository.save(entity);
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    public AttendanceTypeDto get(Integer id) {
        AttendanceType entity = getEntity(id);
        AttendanceTypeDto dto = new AttendanceTypeDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public List<AttendanceTypeDto> getAll() {
        List<AttendanceType> entityList = repository.findAllByDeletedAtIsNull();
        if (entityList.isEmpty()) {
            throw new ServerBadRequestException("Attendance Type not found");
        }

        List<AttendanceTypeDto> dtoList = new LinkedList<>();
        for (AttendanceType entity : entityList) {
            AttendanceTypeDto dto = new AttendanceTypeDto();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setStatus(entity.getStatus());
            dto.setCreatedAt(entity.getCreatedAt());
            dto.setUpdatedAt(entity.getUpdatedAt());
            dtoList.add(dto);
        }
        return dtoList;
    }

    public AttendanceTypeDto update(Integer id, AttendanceTypeDto dto) {
        AttendanceType entity = getEntity(id);
        entity.setName(dto.getName());
        entity.setUpdatedAt(LocalDateTime.now());
        repository.save(entity);
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public Boolean delete(Integer id) {
        AttendanceType entity = getEntity(id);
        entity.setDeletedAt(LocalDateTime.now());
        repository.save(entity);
        return true;
    }


    // Secondary function
    public AttendanceType getEntity(Integer id) {
        Optional<AttendanceType> optional = repository.findById(id);
        if (optional.isEmpty() || optional.get().getDeletedAt() != null) {
            throw new ServerBadRequestException("Attendance Type not found");
        }
        return optional.get();
    }
}
