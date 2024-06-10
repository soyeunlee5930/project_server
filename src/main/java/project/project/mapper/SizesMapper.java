package project.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import project.project.model.Sizes;

import java.util.List;

@Mapper
public interface SizesMapper {
    List<Sizes> getAllSizes();

    Sizes getSizeById(Integer id);

    void insertSize(Sizes color);

    void deleteSize(Integer id);
}
