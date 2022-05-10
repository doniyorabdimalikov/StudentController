package uz.isystem.studentweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.isystem.studentweb.model.AttendanceType;

import java.util.List;

public interface AttendanceTypeRepository extends JpaRepository<AttendanceType, Integer> {
    List<AttendanceType> findAllByDeletedAtIsNull();
}
