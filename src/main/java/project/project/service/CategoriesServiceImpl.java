package project.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.project.mapper.CategoriesMapper;
import project.project.model.Categories;

import java.util.List;

@Service
public class CategoriesServiceImpl implements CategoriesService {
    @Autowired
    private CategoriesMapper categoriesMapper;

    @Override
    public List<Categories> getAllCategories() {
        return categoriesMapper.getAllCategories();
    }

    @Override
    public void insertCategory(Categories category) {
        categoriesMapper.insertCategory(category);
    }
}
