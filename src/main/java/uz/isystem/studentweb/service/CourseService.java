package uz.isystem.studentweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.isystem.studentweb.dto.CourseDto;
import uz.isystem.studentweb.exception.ServerBadRequestException;
import uz.isystem.studentweb.model.Course;
import uz.isystem.studentweb.repository.CourseRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    //Main functions
    public CourseDto get(Integer id) {
        Course course = getEntity(id);
        CourseDto courseDto = new CourseDto();
        courseDto.setId(course.getId());
        courseDto.setName(course.getName());
        courseDto.setStatus(course.getStatus());
        courseDto.setCreatedAt(course.getCreatedAt());
        courseDto.setUpdatedAt(course.getUpdatedAt());
        return courseDto;
    }

    public List<CourseDto> getAll() {
        List<Course> courseList = courseRepository.findAllByDeletedAtIsNull();
        if (courseList.isEmpty()) {
            throw new ServerBadRequestException("Course not found");
        }
        List<CourseDto> courseDtoList = new LinkedList<>();
        for (Course course : courseList) {
            CourseDto courseDto = new CourseDto();
            courseDto.setId(course.getId());
            courseDto.setName(course.getName());
            courseDto.setStatus(course.getStatus());
            courseDto.setCreatedAt(course.getCreatedAt());
            courseDto.setUpdatedAt(course.getUpdatedAt());
            courseDtoList.add(courseDto);
        }
        return courseDtoList;
    }

    public CourseDto create(CourseDto courseDto) {
        Course course = new Course();
        course.setName(courseDto.getName());
        course.setStatus(true);
        course.setCreatedAt(LocalDateTime.now());
        courseRepository.save(course);
        courseDto.setId(course.getId());
        courseDto.setStatus(course.getStatus());
        courseDto.setCreatedAt(course.getCreatedAt());
        return courseDto;
    }

    public CourseDto update(Integer id, CourseDto courseDto) {
        Course entity = getEntity(id);
        entity.setName(courseDto.getName());
        entity.setUpdatedAt(LocalDateTime.now());
        courseRepository.save(entity);
        courseDto.setId(entity.getId());
        courseDto.setStatus(entity.getStatus());
        courseDto.setCreatedAt(entity.getCreatedAt());
        courseDto.setUpdatedAt(entity.getUpdatedAt());
        return courseDto;
    }

    public Boolean delete(Integer id) {
        Course course = getEntity(id);
        course.setDeletedAt(LocalDateTime.now());
        courseRepository.save(course);
        return true;
    }


    //Secondary functions
    public Course getEntity(Integer id) {
        Optional<Course> optional = courseRepository.findById(id);
        if (optional.isEmpty() || optional.get().getDeletedAt() != null) {
            throw new ServerBadRequestException("Course not found");
        }
        return optional.get();
    }



}
