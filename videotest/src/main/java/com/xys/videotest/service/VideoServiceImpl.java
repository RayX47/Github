package com.xys.videotest.service;

import com.xys.videotest.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService{

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Video> findAll() {
        String sql="select * from video";
        List<Video> vList=jdbcTemplate.query(sql,new VideoRowMapper());
        return vList;
    }

    @Override
    public Video findByID(int vID) {
        String sql="select * from video where id = ?";
        Video video=jdbcTemplate.queryForObject(sql,new VideoRowMapper(),vID);
        return video;
    }

    @Override
    public int create(String vName, String vURL) {
        String sql="insert into video(name,url) values(?,?)";
        return jdbcTemplate.update(sql,vName,vURL);
    }

    @Override
    public int update(int vID, String vName, String vURL) {
        return 0;
    }

    @Override
    public int delete(int vID) {
        String sql="delete from video where id = ?";
        return jdbcTemplate.update(sql,vID);
    }
}
