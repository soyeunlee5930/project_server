package project.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import project.project.model.Categories;

import java.util.List;

@Mapper
public interface CategoriesMapper {
    List<Categories> getAllCategories();

    void insertCategory(Categories category);
}
