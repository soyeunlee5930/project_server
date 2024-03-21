package project.project.service;

import org.springframework.web.multipart.MultipartFile;
import project.project.model.Products;
import project.project.requestParam.ProductsParam;

import java.util.List;

public interface ProductsService {
    List<Products> getAllProducts();

    void insertProduct(ProductsParam product);
}
