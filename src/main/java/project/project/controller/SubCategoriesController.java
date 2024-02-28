package project.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    public void insertSubCategory(@RequestBody SubCategories subCategory) {
        subCategoriesService.insertSubCategory(subCategory);
    }

    @PutMapping("/subcategories/{id}")
    public void updateSubCategory(@PathVariable Integer id, @RequestBody SubCategories subCategory) {
        subCategory.setId(id);
        subCategoriesService.updateSubCategory(subCategory);
    }

    @DeleteMapping("/subcategories/{id}")
    public void deleteSubCategory(@PathVariable Integer id) {
        subCategoriesService.deleteSubCategory(id);
    }
}
