package project.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.project.mapper.PracMapper;
import project.project.model.Prac;

@Service
public class PracServiceImpl implements PracService {

    @Autowired
    private PracMapper pracMapper;

    @Override
    public void insertPrac(Prac prac) {
        pracMapper.insertPrac(prac);
    }

}
