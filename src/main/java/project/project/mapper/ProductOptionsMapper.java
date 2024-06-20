package project.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import project.project.model.ProductOptions;
import project.project.requestParam.ProductDetails;
import project.project.requestParam.ProductOptionDetails;

import java.util.List;

@Mapper
public interface ProductOptionsMapper {
    List<ProductOptions> getAllProductOptions();

    ProductOptions findProductOptionById(Integer id);

    List<ProductOptions> getProductOptionsByProductId(@Param("productId") Integer productId);

    List<ProductDetails> getProductDetailsByProductId(Integer productId);

    List<ProductOptionDetails> getProductOptionDetailsByProductOptionsId(Integer productOptionsId);

    int checkDuplicate(ProductOptions productOption);

    int checkDuplicateExcludeId(ProductOptions productOption);

    void insertProductOption(ProductOptions productOption);

    void updateProductOption(ProductOptions productOption);

    void deleteProductOption(Integer id);

    void deleteProductOptions(@Param("productOptionsIds") List<Integer> productOptionsIds);
}
