package project.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import project.project.model.Sizes;

import java.util.List;

@Mapper
public interface SizesMapper {
    List<Sizes> getAllSizes();

    Sizes getSizeById(Integer id);

    int countBySize(Integer size);

    void insertSize(Sizes size);

    void deleteSize(Integer id);
}
