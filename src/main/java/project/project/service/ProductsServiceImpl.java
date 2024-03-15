package project.project.service;

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
}
