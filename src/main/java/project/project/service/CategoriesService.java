package project.project.service;

import project.project.model.Categories;

import java.util.List;

public interface CategoriesService {

    List<Categories> getAllCategories();

    void insertCategory(Categories category);

}
