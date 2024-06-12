package project.project.service;

import project.project.model.Colors;

import java.util.List;

public interface ColorsService {
    List<Colors> getAllColors();

    Colors getColorById(Integer id);

    int countByColor(String color);

    void insertColor(Colors color);

    void deleteColor(Integer id);
}
