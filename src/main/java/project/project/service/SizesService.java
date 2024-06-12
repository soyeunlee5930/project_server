package project.project.service;

import project.project.model.Sizes;

import java.util.List;

public interface SizesService {
    List<Sizes> getAllSizes();

    Sizes getSizeById(Integer id);

    int countBySize(Integer size);

    void insertSize(Sizes size);

    void deleteSize(Integer id);
}
