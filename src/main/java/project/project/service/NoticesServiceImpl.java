package project.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.project.mapper.NoticesMapper;
import project.project.model.Notices;

import java.util.List;

@Service
public class NoticesServiceImpl implements NoticesService {
    @Autowired
    private NoticesMapper noticesMapper;

    @Override
    public List<Notices> getAllNotices() {
        return noticesMapper.getAllNotices();
    }

    @Override
    public Notices getNoticeById(Integer id) {
        return noticesMapper.getNoticeById(id);
    }

    @Override
    public void insertNotice(Notices notice) {
        noticesMapper.insertNotice(notice);
    }

    @Override
    public int updateViewsCount(Integer id) {
        return noticesMapper.updateViewsCount(id);
    }

    @Override
    public void updateNotice(Notices notice) {
        noticesMapper.updateNotice(notice);
    }

    @Override
    public void deleteNotice(Integer id) {
        noticesMapper.deleteNotice(id);
    }
}
