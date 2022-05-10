package uz.isystem.studentweb.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserTypeDto {

    private Integer id;

    @NotBlank(message = "Invalid name, send correct")
    private String name;

    @NotBlank(message = "Invalid display_name, send correct")
    private String displayName;

    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
