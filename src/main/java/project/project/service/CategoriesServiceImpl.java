package project.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.project.mapper.CategoriesMapper;
import project.project.mapper.ProductsMapper;
import project.project.mapper.SubCategoriesMapper;
import project.project.model.Categories;

import java.util.List;

@Service
public class CategoriesServiceImpl implements CategoriesService {
    @Autowired
    private CategoriesMapper categoriesMapper;

    // 메인 카테고리에 연관된 서브 카테고리 삭제를 위함
    @Autowired
    private SubCategoriesMapper subCategoriesMapper;

    // 카테고리 아이디에 연관된 상품 삭제를 위함
    @Autowired
    private ProductsMapper productsMapper;

    @Override
    public List<Categories> getAllCategories() {
        return categoriesMapper.getAllCategories();
    }

    @Override
    public Categories findCategoryById(Integer id) {
        return categoriesMapper.findCategoryById(id);
    }

    @Override
    public int countByName (String categoryName) {
        return categoriesMapper.countByName(categoryName);
    }

    @Override
    public void insertCategory(Categories category) {
        categoriesMapper.insertCategory(category);
    }

    @Override
    public void updateCategory(Categories category) {
        categoriesMapper.updateCategory(category);
    }

    @Override
    public void deleteCategory(Integer id) {
        // 카테고리 아이디와 연관된 모든 상품 삭제
        productsMapper.deleteProductsByCategoryId(id);

        // 메인 카테고리에 연관된 모든 서브 카테고리 먼저 삭제
        subCategoriesMapper.deleteSubCategoriesByCategoryId(id);

        // 그 후 메인 카테고리 삭제
        categoriesMapper.deleteCategory(id);
    }
}
