package project.project.service;

import project.project.model.Notices;

import java.util.List;

public interface NoticesService {
    List<Notices> getAllNotices();

    Notices getNoticeById(Integer id);

    void insertNotice(Notices notice);

    int updateViewsCount(Integer id);

    void updateNotice(Notices notice);

    void deleteNotice(Integer id);
}
