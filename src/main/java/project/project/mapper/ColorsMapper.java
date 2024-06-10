package project.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import project.project.model.Colors;

import java.util.List;

@Mapper
public interface ColorsMapper {
    List<Colors> getAllColors();

    Colors getColorById(Integer id);

    void insertColor(Colors color);

    void deleteColor(Integer id);
}
