package com.xys.videotest.service;

import com.xys.videotest.Video;

import java.util.List;

public interface VideoService {

    List<Video> findAll();
    Video findByID(int vID);
    int create(String vName,String vURL);
    int update(int vID,String vName,String vURL);
    int delete(int vID);

}
