package project.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    public void insertCategory(@RequestBody Categories category) {
        categoriesService.insertCategory(category);
    }

    @PutMapping("/categories/{id}")
    public void updateCategory(@PathVariable Integer id, @RequestBody Categories category) {
        category.setId(id);
        categoriesService.updateCategory(category);
    }

    @DeleteMapping("/categories/{id}")
    public void deleteCategory(@PathVariable Integer id) {
        categoriesService.deleteCategory(id);
    }
}
