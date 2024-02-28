package project.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import project.project.model.SubCategories;

import java.util.List;

@Mapper
public interface SubCategoriesMapper {
    List<SubCategories> getAllSubCategories();

    SubCategories findSubCategoryById(Integer id);

    void insertSubCategory(SubCategories subCategory);

    void updateSubCategory(SubCategories subCategory);

    void deleteSubCategory(Integer id);
}
