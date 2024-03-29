package project.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import project.project.model.Prac;

@Mapper
public interface PracMapper {
    void insertPrac(Prac prac);
}
