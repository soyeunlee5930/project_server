package project.project.service;

import org.springframework.stereotype.Service;
import project.project.model.SubCategories;

import java.util.List;

public interface SubCategoriesService {
    List<SubCategories> getAllSubCategories();

    SubCategories findSubCategoryById(Integer id);

    void insertSubCategory(SubCategories subCategory);

    void updateSubCategory(SubCategories subCategory);

    void deleteSubCategory(Integer id);
}
