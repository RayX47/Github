package com.xys.videotest.controller;

import com.xys.videotest.Video;
import com.xys.videotest.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;


@RestController
public class FileController {
//    private static String SERVER_FOLDER="e:/test/";
    private static String SERVER_FOLDER="/home/kenny/www/video/";
    @Autowired
    private VideoService videoService;

    /**
     * 删除单个文件
     * @param fileName
     * @return
     */
    public static boolean deleteFile(String fileName){

//        String name=SERVER_FOLDER+fileName;
        File file=new File(fileName);
        if (!file.exists()){
            System.out.println("file does not exist");
            return false;
        }else {
            if (file.delete()){
                return true;
            } else {
                System.out.println("delete file failed");
                return false;
            }
        }
    }

    /**
     * 删除多个同名文件
     * @param path
     * @param likeName
     * @return
     */
    public static boolean deleteFiles(String path,String likeName){

        File file=new File(path);
        File[] tempList=file.listFiles();
        likeName=likeName.substring(0,likeName.lastIndexOf("."));
        for (int i=0;i<tempList.length;i++){
            if (tempList[i].isFile() && tempList[i].getName().contains(likeName)){
//                files.add(tempList[i].getName());
                deleteFile(SERVER_FOLDER+tempList[i].getName());
            }
        }
        return true;
    }


    @RequestMapping("/delete") //new annotation since 4.3

    public String singleFileDelete(HttpServletRequest request,@RequestParam("id") int id) {
        Video video=videoService.findByID(id);

        int res=videoService.delete(id);
//        deleteFile(SERVER_FOLDER+video.getName())
        if (res==1 && deleteFiles(SERVER_FOLDER,video.getName())) {

            return "delete success";
        } else {
            return "delete faild";
        }


    }


}
