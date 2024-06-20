package project.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import project.project.model.Stock;

import java.util.List;

@Mapper
public interface StockMapper {
    List<Stock> getAllStocks();

    Stock findStockById(Integer id);

    void insertStock(Stock stock);

    void updateStock(Stock stock);

    void deleteStockByProductOptionsId(Integer productOptionsId);

    // 다수의 상품 옵션 ID에 대한 재고 삭제
    void deleteStockByProductOptionsIds(@Param("productOptionsIds") List<Integer> productOptionsIds);
}
