package project.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import project.project.model.Notices;

import java.util.List;

@Mapper
public interface NoticesMapper {

    List<Notices> getAllNotices();

    Notices getNoticeById(Integer id);

    void insertNotice(Notices notice);

    // views count update
    int updateViewsCount(Integer id);

    void updateNotice(Notices notice);

    void deleteNotice(Integer id);
}
