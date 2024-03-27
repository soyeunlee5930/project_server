package project.project.service;

import project.project.model.Products;
import project.project.requestParam.ProductsParam;

import java.util.List;

public interface ProductsService {
    List<Products> getAllProducts();

    void insertProduct(ProductsParam product, String file1, String file2, List<String> file3);
}
