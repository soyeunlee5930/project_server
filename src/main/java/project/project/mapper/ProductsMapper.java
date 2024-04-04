package project.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import project.project.model.Products;

import java.util.List;

@Mapper
public interface ProductsMapper {
    List<Products> getAllProducts();

    Products getProductById(Integer id);

    void insertProduct(Products product);

    void updateProduct(Products product);

    void deleteProduct(Integer id);

    // 카테고리 아이디와 연관된 상품 삭제
    void deleteProductsByCategoryId(Integer id);
}
