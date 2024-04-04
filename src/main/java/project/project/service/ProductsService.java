package project.project.service;

import project.project.model.Products;

import java.util.List;

public interface ProductsService {
    List<Products> getAllProducts();

    Products getProductById(Integer id);

    void insertProduct(Products product);

    void updateProduct(Products product);

    void deleteProduct(Integer id);

}
