package com.service.impl;

import com.mapper.NewsMapper;
import com.model.News;
import com.model.NewsExample;
import com.service.NewsService;
import com.vo.NewsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsMapper newsMapper;
    @Override
    public int addNews(NewsVo newsVo) {
        int i = newsMapper.insert(newsVo);
        return i;
    }

    @Override
    public List<News> search(NewsVo newsVo) {
        NewsExample example = new NewsExample();
        NewsExample.Criteria criteria = example.createCriteria();
        if (newsVo.getContent()!=null){
            criteria.andContentLike("%"+newsVo.getContent()+"%");
        }
        if(newsVo.getOpername()!=null){
            criteria.andOpernameLike("%"+newsVo.getOpername()+"%");
        }
        if (newsVo.getTitle()!=null){
            criteria.andTitleLike("%"+newsVo.getTitle()+"%");
        }
        List<News> newsList = newsMapper.selectByExample(example);
        return newsList;
    }

    @Override
    public int deleteNews(int id) {
        int i = newsMapper.deleteByPrimaryKey(id);
        return i;
    }

    @Override
    public int deleteBatchNews(Integer[] ids) {
        int allCount = 0;
        for (Integer id : ids) {
            int count = newsMapper.deleteByPrimaryKey(id);
            allCount += count;
        }
        return allCount;
    }

    @Override
    public int editNews(News news) {
        int i = newsMapper.updateByPrimaryKeySelective(news);
        return i;
    }

    @Override
    public News loadNewsById(Integer id) {
        return newsMapper.selectByPrimaryKey(id);
    }
}
