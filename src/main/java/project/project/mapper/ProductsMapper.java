package project.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import project.project.model.Products;
import project.project.requestParam.ProductsParam;

import java.util.List;

@Mapper
public interface ProductsMapper {
    List<Products> getAllProducts();

    void insertProduct(ProductsParam product, String file1, String file2, List<String> file3);
}
