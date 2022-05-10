package uz.isystem.studentweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.isystem.studentweb.dto.AttendanceTypeDto;
import uz.isystem.studentweb.service.AttendanceTypeService;

import java.util.List;

@RestController
@RequestMapping("/api/attendance-type")
public class AttendanceTypeController {

    @Autowired
    private AttendanceTypeService attendanceTypeService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AttendanceTypeDto dto) {
        AttendanceTypeDto result = attendanceTypeService.create(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Integer id) {
        AttendanceTypeDto result = attendanceTypeService.get(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        List<AttendanceTypeDto> result = attendanceTypeService.getAll();
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody AttendanceTypeDto dto) {
        AttendanceTypeDto result = attendanceTypeService.update(id, dto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        Boolean result = attendanceTypeService.delete(id);
        return ResponseEntity.ok(result);
    }
}
