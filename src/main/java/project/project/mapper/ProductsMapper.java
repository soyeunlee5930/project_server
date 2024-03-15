package project.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import project.project.model.Products;

import java.util.List;

@Mapper
public interface ProductsMapper {
    List<Products> getAllProducts();

}
