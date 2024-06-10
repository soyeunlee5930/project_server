package project.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.project.mapper.ColorsMapper;
import project.project.model.Colors;

import java.util.List;

@Service
public class ColorsServiceImpl implements ColorsService {
    @Autowired
    private ColorsMapper colorsMapper;

    @Override
    public List<Colors> getAllColors() {
        return colorsMapper.getAllColors();
    }

    @Override
    public Colors getColorById(Integer id) {
        return colorsMapper.getColorById(id);
    }

    @Override
    public void insertColor(Colors color) {
        colorsMapper.insertColor(color);
    }

    @Override
    public void deleteColor(Integer id) {
        colorsMapper.deleteColor(id);
    }
}
