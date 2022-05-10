package uz.isystem.studentweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.isystem.studentweb.dto.UserGroupDto;
import uz.isystem.studentweb.service.UserGroupService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user-group")
public class UserGroupController {

    @Autowired
    private UserGroupService userGroupService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@Valid @PathVariable("id") Integer id) {
        UserGroupDto result = userGroupService.get(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        List<UserGroupDto> result = userGroupService.getAll();
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserGroupDto userGroupDto) {
        UserGroupDto result = userGroupService.create(userGroupDto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody UserGroupDto userGroupDto) {
        UserGroupDto result = userGroupService.update(id, userGroupDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        Boolean result = userGroupService.delete(id);
        return ResponseEntity.ok(result);
    }
}
