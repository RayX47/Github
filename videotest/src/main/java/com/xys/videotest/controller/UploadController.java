package com.xys.videotest.controller;

import com.xys.videotest.Video;
import com.xys.videotest.service.VideoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UploadController {
    private String URL="http://192.168.80.131/video/";//流媒体服务器ip
    @Autowired
    private VideoServiceImpl videoService;

    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "/home/kenny/www/video/";
//    private static String UPLOADED_FOLDER = "e:/test/";
    private File dir=new File(UPLOADED_FOLDER);

    @GetMapping("/upload")
    public String index() {
        return "upload";
    }

    @PostMapping("/upload") //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        Path path=null;
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus"; //转发到对应的controller
        }

        try {
            //if dir does not exist,create it first.
            if (!dir.exists()){
                dir.mkdir();
            }
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

            //上传的数据video写入数据库
            videoService.create(file.getOriginalFilename(),URL+file.getOriginalFilename());

        } catch (IOException e) {
            e.printStackTrace();
        }
        String convPath=UPLOADED_FOLDER+file.getOriginalFilename();
        redirectAttributes.addFlashAttribute("convPath",convPath);
        return "redirect:uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }
}
