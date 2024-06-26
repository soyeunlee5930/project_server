package project.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.project.model.Categories;
import project.project.service.CategoriesService;

import java.util.List;

@RestController
@RequestMapping("/admins")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;

    @GetMapping("/categories")
    public ResponseEntity<List<Categories>> getAll() {
        List<Categories> categories = categoriesService.getAllCategories();
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<Categories> findCategoryById(@PathVariable Integer id) {
        Categories category = categoriesService.findCategoryById(id);
        if (category != null) {
            return ResponseEntity.status(HttpStatus.OK).body(category);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/categories/add")
    public ResponseEntity<String> insertCategory(@RequestBody Categories category) {
        // 중복값 확인
        int count = categoriesService.countByName(category.getCategoryName());
        if (count > 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("duplicate categoryName");
        }
        categoriesService.insertCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Integer id, @RequestBody Categories category) {
        category.setId(id);
        // 중복값 확인
        int count = categoriesService.countByName(category.getCategoryName());
        if (count > 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("duplicate categoryName");
        }
        categoriesService.updateCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        categoriesService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
