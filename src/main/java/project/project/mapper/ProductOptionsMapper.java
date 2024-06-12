package project.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import project.project.model.ProductOptions;

import java.util.List;

@Mapper
public interface ProductOptionsMapper {
    List<ProductOptions> getAllProductOptions();

    ProductOptions findProductOptionById(Integer id);

    int checkDuplicate(ProductOptions productOption);

    void insertProductOption(ProductOptions productOption);

    void updateProductOption(ProductOptions productOption);

    void deleteProductOption(Integer id);
}
