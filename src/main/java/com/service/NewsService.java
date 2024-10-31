package com.service;

import com.model.News;
import com.vo.NewsVo;

import java.util.List;


public interface NewsService {
    int addNews(NewsVo newsVo);

    List<News> search(NewsVo newsVo);

    int deleteNews(int id);

    int deleteBatchNews(Integer[] ids);

    News loadNewsById(Integer id);

    int editNews(News news);
}
