package uz.isystem.studentweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.isystem.studentweb.dto.GroupTypeDto;
import uz.isystem.studentweb.service.GroupTypeService;

import java.util.List;

@RestController
@RequestMapping("/api/group-type")
public class GroupTypeController {

    @Autowired
    private GroupTypeService classTypeService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody GroupTypeDto dto) {
        GroupTypeDto result = classTypeService.create(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Integer id) {
        GroupTypeDto result = classTypeService.get(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        List<GroupTypeDto> result = classTypeService.getAll();
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody GroupTypeDto dto) {
        GroupTypeDto result = classTypeService.update(id, dto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        Boolean result = classTypeService.delete(id);
        return ResponseEntity.ok(result);
    }
}
