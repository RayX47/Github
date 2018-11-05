package com.xys.videotest.service;

import com.xys.videotest.Video;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VideoRowMapper implements RowMapper<Video> {
    @Override
    public Video mapRow(ResultSet resultSet, int i) throws SQLException {
        int vID=resultSet.getInt("id");
        String vName=resultSet.getString("name");
        String vURL=resultSet.getString("url");

        Video video=new Video();
        video.setId(vID);
        video.setName(vName);
        video.setUrl(vURL);
        return video;
    }
}
