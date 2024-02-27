package project.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import project.project.model.Categories;

import java.util.List;

@Mapper
public interface CategoriesMapper {
    List<Categories> getAllCategories();

    Categories findCategoryById(Integer id);

    void insertCategory(Categories category);

    void updateCategory(Categories category);

    void deleteCategory(Integer id);
}
