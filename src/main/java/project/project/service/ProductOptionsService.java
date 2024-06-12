package project.project.service;

import project.project.model.ProductOptions;

import java.util.List;

public interface ProductOptionsService {
    List<ProductOptions> getAllProductOptions();

    ProductOptions findProductOptionById(Integer id);

    int checkDuplicate(ProductOptions productOption);

    void insertProductOption(ProductOptions productOption);

    void updateProductOption(ProductOptions productOption);

    void deleteProductOption(Integer id);
}
