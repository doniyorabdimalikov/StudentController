package uz.isystem.studentweb.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserGroupDto {

    private Integer id;
    private UserDto user;
    private Integer userId;
    private GroupDto group;
    private Integer groupId;
    private Integer courseId;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
