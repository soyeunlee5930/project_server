package project.project.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.project.mapper.ProductsMapper;
import project.project.model.Products;

import java.util.List;

@Service
public class ProductsServiceImpl implements ProductsService {
    @Autowired
    private ProductsMapper productsMapper;

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
        productsMapper.deleteProduct(id);
    }

}
