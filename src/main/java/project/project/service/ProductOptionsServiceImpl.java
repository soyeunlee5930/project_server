package project.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.project.mapper.ProductOptionsMapper;
import project.project.model.ProductOptions;

import java.util.List;

@Service
public class ProductOptionsServiceImpl implements ProductOptionsService {

    @Autowired
    private ProductOptionsMapper productOptionsMapper;

    @Override
    public List<ProductOptions> getAllProductOptions() {
        return productOptionsMapper.getAllProductOptions();
    }

    @Override
    public ProductOptions findProductOptionById(Integer id) {
        return productOptionsMapper.findProductOptionById(id);
    }

    @Override
    public int checkDuplicate(ProductOptions productOption) {
        return productOptionsMapper.checkDuplicate(productOption);
    }

    @Override
    public void insertProductOption(ProductOptions productOption) {
        productOptionsMapper.insertProductOption(productOption);
    }

    @Override
    public void updateProductOption(ProductOptions productOption) {
        productOptionsMapper.updateProductOption(productOption);
    }

    @Override
    public void deleteProductOption(Integer id) {
        productOptionsMapper.deleteProductOption(id);
    }
}
