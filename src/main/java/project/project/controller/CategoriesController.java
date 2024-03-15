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
    public List<Categories> getAll() {
        return categoriesService.getAllCategories();
    }

    @GetMapping("/categories/{id}")
    public Categories findCategoryById(@PathVariable Integer id) {
        return categoriesService.findCategoryById(id);
    }

    @PostMapping("/categories/add")
    public ResponseEntity<String> insertCategory(@RequestBody Categories category) {
        // 중복값 확인
        int count = categoriesService.countByName(category.getCategory_name());
        if (count > 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("duplicate category_name");
        }
        categoriesService.insertCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Integer id, @RequestBody Categories category) {
        category.setId(id);
        // 중복값 확인
        int count = categoriesService.countByName(category.getCategory_name());
        if (count > 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("duplicate category_name");
        }
        categoriesService.updateCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/categories/{id}")
    public void deleteCategory(@PathVariable Integer id) {
        categoriesService.deleteCategory(id);
    }
}
