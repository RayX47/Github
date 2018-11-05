package com.xys.videotest.controller;

import com.xys.videotest.Video;
import com.xys.videotest.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
public class VideoController {

    @Autowired
    private VideoService videoService;

    /**
     * 主页列表
     * @param map
     * @return
     */
    @RequestMapping("/")
    public String findAll(HashMap<String,Object>map){
        System.out.println("fina all student");
        List<Video> vList=videoService.findAll();
        if (vList.size()>0){
            System.out.println("find success");

        }
        map.put("vList",vList);
        return "videoList";
    }

    /**
     * 查找某个视频
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public String findById(HttpServletRequest request, @RequestParam("id")int id){
        System.out.println("accroding to id to find video");
        Video video=videoService.findByID(id);
        if (video !=null){
            System.out.println("query succeed");
            request.setAttribute("video",video);
            request.setAttribute("message","query");
            return video.toString();
        } else {
            System.out.println("query failed");
            return "error";
        }
    }

    /**
     * 点播页面
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/videoPlay")
    public String playVideo(Model model, @RequestParam("id")int id){
        Video video=videoService.findByID(id);
        String url;
        if (video != null){
            url=video.getUrl();
            url=url.substring(0,url.lastIndexOf("."))+".m3u8";
            model.addAttribute("url",url);
            return "videoPlay";
//            inputPath.substring(0,inputPath.lastIndexOf(".")).toLowerCase()+".m3u8";
        } else {
            return "faild";
        }

        //return "videoPlay";
    }

    /**
     * 直播页面
     * @return
     */
    @RequestMapping("/live")
    public String playLive(){
        return "liveTest";
    }

}
