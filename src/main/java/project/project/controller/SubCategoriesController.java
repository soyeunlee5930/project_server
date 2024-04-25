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
    public ResponseEntity<List<SubCategories>> getAllSubCategories() {
        List<SubCategories> subCategories = subCategoriesService.getAllSubCategories();
        return ResponseEntity.status(HttpStatus.OK).body(subCategories);
    }

    @GetMapping("/subcategories/{id}")
    public ResponseEntity<SubCategories> findSubCategoryById(@PathVariable Integer id) {
        SubCategories subCategory = subCategoriesService.findSubCategoryById(id);
        if (subCategory != null) {
            return ResponseEntity.status(HttpStatus.OK).body(subCategory);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/subcategories/add")
    public ResponseEntity<String> insertSubCategory(@RequestBody SubCategories subCategory) {
        // 중복값 확인
        int count = subCategoriesService.countByName(subCategory);
        if (count > 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate subCategory");
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
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate subCategory");
        }
        subCategoriesService.updateSubCategory(subCategory);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/subcategories/{id}")
    public ResponseEntity<Void> deleteSubCategory(@PathVariable Integer id) {
        subCategoriesService.deleteSubCategory(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
