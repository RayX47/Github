package com.xys.videotest.controller;

import com.xys.videotest.ConvertVideo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConvertController {
    @RequestMapping("/convert")
    public String convert(@RequestParam("path")String path){
        if (ConvertVideo.convertVideo(path)==1){
            return "convert success.\nPlease ENTER main Page to check.";
        }else {
            return "convert failed.";
        }

    }
}
