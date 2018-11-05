package com.xys.videotest;

import com.xys.videotest.utils.FFmpegExcption;
import com.xys.videotest.utils.ffmpegUtil;

public class ConvertVideo {

    public static int convertVideo(String inputPath){
        String ffmpegPath=getFFmpegPath();
        String outputPath_ts=getOutputPath(1,inputPath);
        String outputPath_mts=getOutputPath(3,inputPath);
        String outPath_m3u8=getOutputPath(2,inputPath);
        try {
//            ffmpegUtil.ffmpeg_section(ffmpegPath,outputPath_ts,outputPath_mts,outPath_m3u8);
            ffmpegUtil.ffmpeg(ffmpegPath,inputPath,outputPath_ts,outputPath_mts,outPath_m3u8);
            return 1;
        } catch (FFmpegExcption fFmpegExcption) {
            fFmpegExcption.printStackTrace();
        }
        return 0;
    }


    /**
     * 获取ffmpeg的目录
     * @return
     */
    private static String getFFmpegPath(){

        return "E:\\Program Files\\ffmpeg-20180716-b8c4d2b-win64-static\\bin";
    }

    /**
     * type：
     *  1.ts
     *  2.m3u8
     *  3.%03d.ts
     * @param type
     * @param inputPath
     * @return
     */
    private static String getOutputPath(int type,String inputPath){
        if (type==1){
            return inputPath.substring(0,inputPath.lastIndexOf(".")).toLowerCase()+".ts";
        }else if (type==2){
            return inputPath.substring(0,inputPath.lastIndexOf(".")).toLowerCase()+".m3u8";
        }else if (type==3){
            return inputPath.substring(0,inputPath.lastIndexOf(".")).toLowerCase()+"%03d.ts";
        } else {
            return null;
        }

    }
}
