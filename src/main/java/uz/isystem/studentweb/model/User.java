package uz.isystem.studentweb.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = ("users"))
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = ("user_type_id"), insertable = false, updatable = false)
    private UserType userType;

    @Column(name = ("user_type_id"))
    private Integer userTypeId;

    @Column(name = ("user_id"))
    private Long userId;

    @Column(name = ("chat_id"))
    private Long chatId;

    @Column(name = ("username"), nullable = false)
    private String userName;

    @Column(name = ("phone"), length = 45, nullable = false)
    private String phone;

    @Column(name = ("firstname"))
    private String firstName;

    @Column(name = ("lastname"))
    private String lastName;

    @Column(name = ("status"))
    private Boolean status;

    @Column(name = ("password"))
    private String password;

    @Column(name = ("created_at"))
    private LocalDateTime createdAt;

    @Column(name = ("updated_at"))
    private LocalDateTime updatedAt;

    @Column(name = ("deleted_at"))
    private LocalDateTime deletedAt;

}
