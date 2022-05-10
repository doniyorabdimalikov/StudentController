package uz.isystem.studentweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.isystem.studentweb.dto.GroupDto;
import uz.isystem.studentweb.service.GroupService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/group")
public class GroupController {

    @Autowired
    public GroupService groupService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@Valid @PathVariable("id") Integer id) {
        GroupDto result = groupService.get(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        List<GroupDto> result = groupService.getAll();
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody GroupDto groupDto) {
        GroupDto result = groupService.create(groupDto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable("id") Integer id,
                                    @RequestBody GroupDto groupDto) {
        GroupDto result = groupService.update(id, groupDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        Boolean result = groupService.delete(id);
        return ResponseEntity.ok(result);
    }
}
