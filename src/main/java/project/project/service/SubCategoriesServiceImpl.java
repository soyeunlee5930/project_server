package project.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.project.mapper.SubCategoriesMapper;
import project.project.model.SubCategories;

import java.util.List;

@Service
public class SubCategoriesServiceImpl implements SubCategoriesService {

    @Autowired
    private SubCategoriesMapper subCategoriesMapper;

    @Override
    public List<SubCategories> getAllSubCategories() {
        return subCategoriesMapper.getAllSubCategories();
    }

    @Override
    public SubCategories findSubCategoryById(Integer id) {
        return subCategoriesMapper.findSubCategoryById(id);
    }

    @Override
    public void insertSubCategory(SubCategories subCategory) {
        subCategoriesMapper.insertSubCategory(subCategory);
    }

    @Override
    public void updateSubCategory(SubCategories subCategory) {
        subCategoriesMapper.updateSubCategory(subCategory);
    }

    @Override
    public void deleteSubCategory(Integer id) {
        subCategoriesMapper.deleteSubCategory(id);
    }
}
