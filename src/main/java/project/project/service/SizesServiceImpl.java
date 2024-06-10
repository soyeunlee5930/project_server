package project.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.project.mapper.SizesMapper;
import project.project.model.Sizes;

import java.util.List;

@Service
public class SizesServiceImpl implements SizesService {

    @Autowired
    private SizesMapper sizesMapper;

    @Override
    public List<Sizes> getAllSizes() {
        return sizesMapper.getAllSizes();
    }

    @Override
    public Sizes getSizeById(Integer id) {
        return sizesMapper.getSizeById(id);
    }

    @Override
    public void insertSize(Sizes size) {
        sizesMapper.insertSize(size);
    }

    @Override
    public void deleteSize(Integer id) {
        sizesMapper.deleteSize(id);
    }
}
