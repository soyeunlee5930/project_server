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
    @PostMapping("/categories/add")
    public void insertCategory(@RequestBody Categories category) {
        categoriesService.insertCategory(category);
    }
}
