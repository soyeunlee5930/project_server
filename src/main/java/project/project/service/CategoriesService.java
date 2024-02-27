package project.project.service;

import project.project.model.Categories;

import java.util.List;

public interface CategoriesService {

    List<Categories> getAllCategories();

    Categories findCategoryById(Integer id);

    void insertCategory(Categories category);

    void updateCategory(Categories category);

    void deleteCategory(Integer id);

}
