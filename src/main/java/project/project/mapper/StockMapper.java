package project.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import project.project.model.Stock;

import java.util.List;

@Mapper
public interface StockMapper {
    List<Stock> getAllStocks();

    Stock findStockById(Integer id);

    void insertStock(Stock stock);

    void updateStock(Stock stock);

    void deleteStockByProductOptionsId(Integer productOptionsId);
}
