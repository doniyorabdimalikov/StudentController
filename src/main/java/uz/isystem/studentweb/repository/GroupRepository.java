package uz.isystem.studentweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.isystem.studentweb.model.Group;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Integer> {
    List<Group> findAllByDeletedAtIsNull();
}
