package project.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.project.mapper.ProductOptionsMapper;
import project.project.mapper.StockMapper;
import project.project.model.ProductOptions;
import project.project.model.Stock;
import project.project.requestParam.ProductDetails;
import project.project.requestParam.ProductOptionDetails;

import java.util.List;

@Service
public class ProductOptionsServiceImpl implements ProductOptionsService {

    @Autowired
    private ProductOptionsMapper productOptionsMapper;

    @Autowired
    private StockMapper stockMapper;

    @Override
    public List<ProductOptions> getAllProductOptions() {
        return productOptionsMapper.getAllProductOptions();
    }

    @Override
    public ProductOptions findProductOptionById(Integer id) {
        return productOptionsMapper.findProductOptionById(id);
    }

    @Override
    public List<ProductOptions> getProductOptionsByProductId(Integer productId) {
        return productOptionsMapper.getProductOptionsByProductId(productId);
    }

    @Override
    public List<ProductDetails> getProductDetailsByProductId(Integer productId) {
        return productOptionsMapper.getProductDetailsByProductId(productId);
    }

    @Override
    public List<ProductOptionDetails> getProductOptionDetailsByProductOptionsId(Integer productOptionsId) {
        return productOptionsMapper.getProductOptionDetailsByProductOptionsId(productOptionsId);
    }

    @Override
    public int checkDuplicate(ProductOptions productOption) {
        return productOptionsMapper.checkDuplicate(productOption);
    }

    @Override
    public int checkDuplicateExcludeId(ProductOptions productOption) {
        return productOptionsMapper.checkDuplicateExcludeId(productOption);
    }
//    @Override
//    public int checkDuplicateExcludeId(ProductOptions productOption, Integer id) {
//        return productOptionsMapper.checkDuplicateExcludeId(productOption, id);
//    }

    //    @Override
    //    public void insertProductOption(ProductOptions productOption) {
    //        productOptionsMapper.insertProductOption(productOption);
    //    }

    @Override
    @Transactional
    public void addProductOptionAndStock(ProductOptions productOptions, Stock stock) {
        productOptionsMapper.insertProductOption(productOptions);
        stock.setProductOptionsId(productOptions.getId());
        stockMapper.insertStock(stock);
    }

    @Override
    @Transactional
    public void updateProductOption(ProductOptions productOption) {
        productOptionsMapper.updateProductOption(productOption);
    }

    @Override
    @Transactional
    public void updateStock(Stock stock) {
        stockMapper.updateStock(stock);
    }

    @Override
    @Transactional
    public void deleteProductOption(Integer id) {
        stockMapper.deleteStockByProductOptionsId(id);
        productOptionsMapper.deleteProductOption(id);
    }

    @Override
    @Transactional
    public void deleteProductOptions(List<Integer> productOptionsIds) {
        // 재고 삭제
        stockMapper.deleteStockByProductOptionsIds(productOptionsIds);

        // 상품 옵션 삭제
        productOptionsMapper.deleteProductOptions(productOptionsIds);
    }
}
