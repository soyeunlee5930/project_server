package project.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import project.project.model.Products;
import project.project.requestParam.ProductList;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductsMapper {
    List<Products> getAllProducts();

    // main page에 가져오는 상품리스트
    List<ProductList> getProductList();

    // pagination처리한 상품리스트(4개씩 출력)
    List<ProductList> getProductListWithPagination(Map<String, Object> params);

    // 상품 개수
    int getTotalProductCount();

    Products getProductById(Integer id);

    void insertProduct(Products product);

    void updateProduct(Products product);

    void deleteProduct(Integer id);

    // 카테고리 아이디와 연관된 상품 삭제
    void deleteProductsByCategoryId(Integer id);

    // 주어진 제품 ID와 연관된 모든 product_options ID를 반환
    List<Integer> getProductOptionsIdsByProductId(Integer productId);

    // 종속 데이터 삭제
    void deleteProductOptionsByProductId(Integer productId);
}
