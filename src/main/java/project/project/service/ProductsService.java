package project.project.service;

import project.project.model.Products;
import project.project.requestParam.ProductsParam;

import java.util.List;

public interface ProductsService {
    List<Products> getAllProducts();

    void insertProduct(ProductsParam product, String productDescriptionUrl, String thumnailImgUrl, List<String> detailImgUrls);
}
