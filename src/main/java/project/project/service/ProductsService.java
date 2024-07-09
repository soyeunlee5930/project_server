package project.project.service;

import project.project.model.Products;
import project.project.requestParam.ProductList;

import java.util.List;

public interface ProductsService {
    List<Products> getAllProducts();

    // main page에 가져오는 상품리스트
    List<ProductList> getProductList();

    Products getProductById(Integer id);

    void insertProduct(Products product);

    void updateProduct(Products product);

    void deleteProduct(Integer id);
}
