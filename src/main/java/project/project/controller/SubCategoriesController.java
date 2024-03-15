package project.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.project.model.SubCategories;
import project.project.service.SubCategoriesService;

import java.util.List;

@RestController
@RequestMapping("/admins")
@CrossOrigin(origins = "http://localhost:3000")
public class SubCategoriesController {

    @Autowired
    private SubCategoriesService subCategoriesService;

    @GetMapping("/subcategories")
    public List<SubCategories> getAllSubCategories() {
        return subCategoriesService.getAllSubCategories();
    }

    @GetMapping("/subcategories/{id}")
    public SubCategories findSubCategoryById(@PathVariable Integer id) {
        return subCategoriesService.findSubCategoryById(id);
    }

    @PostMapping("/subcategories/add")
    public ResponseEntity<String> insertSubCategory(@RequestBody SubCategories subCategory) {
        // 중복값 확인
        int count = subCategoriesService.countByName(subCategory);
        if (count > 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate sub-category");
        }
        subCategoriesService.insertSubCategory(subCategory);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/subcategories/{id}")
    public ResponseEntity<String> updateSubCategory(@PathVariable Integer id, @RequestBody SubCategories subCategory) {
        subCategory.setId(id);
        // 중복값 확인
        int count = subCategoriesService.countByName(subCategory);
        if (count > 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate sub-category");
        }
        subCategoriesService.updateSubCategory(subCategory);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/subcategories/{id}")
    public void deleteSubCategory(@PathVariable Integer id) {
        subCategoriesService.deleteSubCategory(id);
    }
}
