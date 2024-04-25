package project.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.project.model.Notices;
import project.project.service.NoticesService;

import java.util.List;

@RestController
@RequestMapping("/admins")
@CrossOrigin(origins = "http://localhost:3000")
public class NoticesController {
    @Autowired
    private NoticesService noticesService;

    @GetMapping("/notices")
    public ResponseEntity<List<Notices>> getAllNotices() {
        List<Notices> notices = noticesService.getAllNotices();
        return ResponseEntity.status(HttpStatus.OK).body(notices);
    }

    @GetMapping("/notices/{id}")
    public ResponseEntity<Notices> getNoticeById(@PathVariable Integer id) {
        Notices notice = noticesService.getNoticeById(id);
        if (notice != null) {
            noticesService.updateViewsCount(id);
            return ResponseEntity.status(HttpStatus.OK).body(notice);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/notices/add")
    public ResponseEntity<String> insertNotice(@RequestBody Notices notice) {

        noticesService.insertNotice(notice);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/notices/{id}")
    public ResponseEntity<String> updateNotice(@PathVariable Integer id, @RequestBody Notices notice) {
        notice.setId(id);
        noticesService.updateNotice(notice);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
