package uz.isystem.studentweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.isystem.studentweb.model.GroupType;

import java.util.List;

@Repository
public interface GroupTypeRepository extends JpaRepository<GroupType, Integer> {
    List<GroupType> findAllByDeletedAtIsNull();
}
