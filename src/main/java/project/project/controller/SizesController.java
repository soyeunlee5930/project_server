package project.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.project.model.Sizes;
import project.project.service.SizesService;

import java.util.List;

@RestController
@RequestMapping("/admins")
@CrossOrigin(origins = "http://localhost:3000")
public class SizesController {
    @Autowired
    private SizesService sizesService;

    @GetMapping("/sizes")
    public ResponseEntity<List<Sizes>> getAllSizes() {
        List<Sizes> sizes = sizesService.getAllSizes();
        return ResponseEntity.status(HttpStatus.OK).body(sizes);
    }

    @PostMapping("/sizes/add")
    public ResponseEntity<Integer> insertSize(@RequestBody Sizes size) {
        sizesService.insertSize(size);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/sizes/{id}")
    public ResponseEntity<Void> deleteSize(@PathVariable Integer id) {
        sizesService.deleteSize(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
