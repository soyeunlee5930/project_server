package project.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.project.model.Colors;
import project.project.service.ColorsService;

import java.util.List;

@RestController
@RequestMapping("/admins")
@CrossOrigin(origins = "http://localhost:3000")
public class ColorsController {

    @Autowired
    private ColorsService colorsService;

    @GetMapping("/colors")
    public ResponseEntity<List<Colors>> getAllColors() {
        List<Colors> colors = colorsService.getAllColors();
        return ResponseEntity.status(HttpStatus.OK).body(colors);
    }

    @PostMapping("/colors/add")
    public ResponseEntity<String> insertColor(@RequestBody Colors color) {
        // 중복값 확인
        int count = colorsService.countByColor(color.getColor());
        if (count > 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("duplicate color");
        }
        colorsService.insertColor(color);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/colors/{id}")
    public ResponseEntity<Void> deleteColor(@PathVariable Integer id) {
        colorsService.deleteColor(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
