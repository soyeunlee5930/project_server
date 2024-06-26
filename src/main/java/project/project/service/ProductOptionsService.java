package project.project.service;

import org.apache.ibatis.annotations.Param;
import project.project.model.ProductOptions;
import project.project.model.Stock;
import project.project.requestParam.ProductDetails;
import project.project.requestParam.ProductOptionDetails;

import java.util.List;

public interface ProductOptionsService {
    List<ProductOptions> getAllProductOptions();

    ProductOptions findProductOptionById(Integer id);

    List<ProductOptions> getProductOptionsByProductId(@Param("productId") Integer productId);

    List<ProductDetails> getProductDetailsByProductId(Integer productId);

    List<ProductOptionDetails> getProductOptionDetailsByProductOptionsId(@Param("productOptionsId") Integer productOptionsId);

    int checkDuplicate(ProductOptions productOption);

    int checkDuplicateExcludeId(ProductOptions productOption);

    void updateProductOption(ProductOptions productOption);

    void deleteProductOption(Integer id);

    void deleteProductOptions(@Param("list") List<Integer> productOptionsIds);

    // stock 관련
    // 상품 옵션 및 재고(수량) 등록
    void addProductOptionAndStock(ProductOptions productOptions, Stock stock);

    void updateStock(Stock stock);
}
