package uz.isystem.studentweb.service;

import org.springframework.stereotype.Component;
import uz.isystem.studentweb.dto.ClassDto;
import uz.isystem.studentweb.exception.ServerBadRequestException;
import uz.isystem.studentweb.model.Class;
import uz.isystem.studentweb.repository.ClassRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class ClassService {

    private final ClassRepository classRepository;
    private final RoomService roomService;
    private final UserGroupService userGroupService;
    private final AttendanceTypeService attendanceTypeService;

    public ClassService(ClassRepository classRepository, RoomService roomService,
                        UserGroupService userGroupService,
                        AttendanceTypeService attendanceTypeService) {
        this.classRepository = classRepository;
        this.roomService = roomService;
        this.userGroupService = userGroupService;
        this.attendanceTypeService = attendanceTypeService;
    }

    // Main function

    public ClassDto create(ClassDto dto) {
        roomService.getEntity(dto.getRoomId());
        userGroupService.getEntity(dto.getUserGroupId());
        attendanceTypeService.getEntity(dto.getAttendanceTypeId());
        Class entity = new Class();
        entity.setRoomId(dto.getRoomId());
        entity.setUserGroupId(dto.getUserGroupId());
        entity.setAttendanceTypeId(dto.getAttendanceTypeId());
        entity.setDate(dto.getDate());
        LocalDate localDate = dto.getDate();
        int day = localDate.getDayOfMonth();
        int month = localDate.getMonthValue();
        String name = String.format("%s.%s", day, month);
        entity.setClassScheled(name);
        entity.setStatus(true);
        entity.setCreatedAt(LocalDateTime.now());
        classRepository.save(entity);
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    public ClassDto get(Integer id) {
        Class entity = getEntity(id);
        ClassDto dto = new ClassDto();
        dto.setId(entity.getId());
        dto.setRoom(roomService.getRoom(entity.getRoomId()));
        dto.setRoomId(entity.getRoomId());
        dto.setUserGroup(userGroupService.get(entity.getUserGroupId()));
        dto.setUserGroupId(entity.getUserGroupId());
        dto.setAttendanceType(attendanceTypeService.get(entity.getAttendanceTypeId()));
        dto.setAttendanceTypeId(entity.getAttendanceTypeId());
        dto.setClassScheled(entity.getClassScheled());
        dto.setDate(entity.getDate());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public List<ClassDto> getAll() {
        List<Class> classList = classRepository.findAllByDeletedAtIsNull();
        if (classList.isEmpty()) {
            throw new ServerBadRequestException("Class not found");
        }

        List<ClassDto> dtoList = new LinkedList<>();
        for (Class entity : classList) {
            ClassDto dto = new ClassDto();
            dto.setId(entity.getId());
            dto.setRoom(roomService.getRoom(entity.getRoomId()));
            dto.setRoomId(entity.getRoomId());
            dto.setUserGroup(userGroupService.get(entity.getUserGroupId()));
            dto.setUserGroupId(entity.getUserGroupId());
            dto.setAttendanceType(attendanceTypeService.get(entity.getAttendanceTypeId()));
            dto.setAttendanceTypeId(entity.getAttendanceTypeId());
            dto.setClassScheled(entity.getClassScheled());
            dto.setDate(entity.getDate());
            dto.setStatus(entity.getStatus());
            dto.setCreatedAt(entity.getCreatedAt());
            dto.setUpdatedAt(entity.getUpdatedAt());
            dtoList.add(dto);
        }
        return dtoList;
    }

    public ClassDto update(Integer id, ClassDto dto) {
        Class entity = getEntity(id);
        roomService.getEntity(dto.getRoomId());
        userGroupService.getEntity(dto.getUserGroupId());
        attendanceTypeService.getEntity(dto.getAttendanceTypeId());
        entity.setRoomId(dto.getRoomId());
        entity.setUserGroupId(dto.getUserGroupId());
        entity.setAttendanceTypeId(dto.getAttendanceTypeId());
        //TODO date update bo'ladimi????
        entity.setStatus(dto.getStatus());
        entity.setUpdatedAt(LocalDateTime.now());
        classRepository.save(entity);
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public Boolean delete(Integer id) {
        Class entity = getEntity(id);
        entity.setDeletedAt(LocalDateTime.now());
        classRepository.save(entity);
        return true;
    }



    // Secondary function
    public Class getEntity(Integer id) {
        Optional<Class> optional = classRepository.findById(id);
        if (optional.isEmpty() || optional.get().getDeletedAt() != null) {
            throw new ServerBadRequestException("Class not found");
        }
        return optional.get();
    }
}
