package uz.isystem.studentweb.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class GroupDto {

    private Integer id;
    private CourseDto course;
    @NotNull
    private Integer courseId;
    private GroupTypeDto groupType;
    private Integer groupTypeId;
    @NotBlank
    private String name;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
