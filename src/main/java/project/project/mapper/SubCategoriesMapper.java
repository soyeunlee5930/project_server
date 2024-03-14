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

    // 메인 카테고리와 연관된 서브 카테고리 삭제
    void deleteSubCategoriesByCategoryId(Integer id);
}
