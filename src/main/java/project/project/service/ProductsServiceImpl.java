package project.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.project.mapper.ProductsMapper;
import project.project.model.Products;
import project.project.requestParam.ProductsParam;

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
    public void insertProduct(ProductsParam product, String productDescriptionUrl, String thumnailImgUrl, List<String> detailImgUrls) {
        productsMapper.insertProduct(product, productDescriptionUrl, thumnailImgUrl, detailImgUrls);
    }
}
