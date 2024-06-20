package project.project.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.project.mapper.ProductsMapper;
import project.project.mapper.StockMapper;
import project.project.model.Products;

import java.util.List;

@Service
public class ProductsServiceImpl implements ProductsService {
    @Autowired
    private ProductsMapper productsMapper;

    @Autowired
    private StockMapper stockMapper;

    @Override
    public List<Products> getAllProducts() {
        return productsMapper.getAllProducts();
    }

    @Override
    public Products getProductById(Integer id) {
        return productsMapper.getProductById(id);
    }

    @Override
    @Transactional
    public void insertProduct(Products product) {
        productsMapper.insertProduct(product);
    }

    @Override
    @Transactional
    public void updateProduct(Products product) {
        productsMapper.updateProduct(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Integer id) {
        // 주어진 제품 ID와 연관된 모든 product_options ID를 반환
        List<Integer> productOptionsIds = productsMapper.getProductOptionsIdsByProductId(id);

        // product_options 삭제 전 종속된 stock 데이터 삭제
        stockMapper.deleteStockByProductOptionsIds(productOptionsIds);

        // product_options 데이터 삭제
        productsMapper.deleteProductOptionsByProductId(id);
        
        // 제품 삭제
        productsMapper.deleteProduct(id);
    }

}
