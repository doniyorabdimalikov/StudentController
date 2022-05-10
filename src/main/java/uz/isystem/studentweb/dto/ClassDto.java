package uz.isystem.studentweb.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ClassDto {

    private Integer id;
    private RoomDto room;
    private Integer roomId;
    private UserGroupDto userGroup;
    private Integer userGroupId;
    private AttendanceTypeDto attendanceType;
    private Integer attendanceTypeId;
    private String classScheled;
    private LocalDate date;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
