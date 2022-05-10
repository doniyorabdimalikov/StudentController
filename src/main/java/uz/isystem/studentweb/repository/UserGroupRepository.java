package uz.isystem.studentweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.isystem.studentweb.model.UserGroup;

import java.util.List;

public interface UserGroupRepository extends JpaRepository<UserGroup, Integer> {
    List<UserGroup> findAllByDeletedAtIsNull();
}
