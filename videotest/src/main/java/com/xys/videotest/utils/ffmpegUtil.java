package com.xys.videotest.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ffmpegUtil {

    /**
     * ffmpeg转码、切片
     * @param ffmpegPath
     * @param inputPath
     * @param outputPath_ts
     * @param outputPath_mts
     * @param outPath_m3u8
     * @return
     * @throws FFmpegExcption
     */
    public static Boolean ffmpeg(String ffmpegPath,String inputPath,String outputPath_ts,String outputPath_mts,String outPath_m3u8) throws FFmpegExcption{
        if (!checkfile(inputPath)){
            throw new FFmpegExcption("文件格式不合法。");
        }
        int type=checkContentType(inputPath);
        List commond=getFFmpegCommand(type,ffmpegPath,inputPath,outputPath_ts);
        boolean res=process(commond);
        if (res){

            List commond_convert=VideoSectioning(ffmpegPath,outputPath_ts,outputPath_mts,outPath_m3u8);
            return process(commond_convert);
        }else {
            return false;
        }
    }

    /**
     * 切片操作
     * @param ffmpegPath
     * @param outputPath_ts
     * @param outputPath_mts
     * @param outPath_m3u8
     * @return
     * @throws FFmpegExcption
     */
    public static Boolean ffmpeg_section(String ffmpegPath,String outputPath_ts,String outputPath_mts,String outPath_m3u8) throws FFmpegExcption{

        //int type=checkContentType(inputPath);
        List commond=VideoSectioning(ffmpegPath,outputPath_ts,outputPath_mts,outPath_m3u8);
        return process(commond);
    }

    /**
     * 获取FFMPEG命令
     * @param type
     * @param ffmpegPath
     * @param oldFilePath
     * @param outputPath
     * @return
     * @throws FFmpegExcption
     */
    private static List getFFmpegCommand(int type,String ffmpegPath,String oldFilePath,String outputPath) throws FFmpegExcption{
        List command = new ArrayList();
        if (type == 1) {
//            command.add(ffmpegPath +"\\ffmpeg");
            command.add("ffmpeg");
            command.add("-i");
            command.add(oldFilePath);
            command.add("-c:v");
            command.add("libx264");
            command.add("-x264opts");
            command.add("force-cfr=1");
            command.add("-c:a");
            command.add("mp2");
            command.add("-b:a");
            command.add("256k");
            command.add("-vsync");
            command.add("cfr");
            command.add("-f");
            command.add("mpegts");
            command.add(outputPath);
        } else if(type==0){
//            command.add(ffmpegPath +"\\ffmpeg");
            command.add("ffmpeg");
            command.add("-i");
            command.add(oldFilePath);
            command.add("-c:v");
            command.add("libx264");
            command.add("-x264opts");
            command.add("force-cfr=1");
            command.add("-vsync");
            command.add("cfr");
            command.add("-vf");
            command.add("idet,yadif=deint=interlaced");
            command.add("-filter_complex");
            command.add("aresample=async=1000");
            command.add("-c:a");
            command.add("libmp3lame");
            command.add("-b:a");
            command.add("192k");
            command.add("-pix_fmt");
            command.add("yuv420p");
            command.add("-f");
            command.add("mpegts");
            command.add(outputPath);
        }else {
            throw new FFmpegExcption("不支持当前上传的文件格式");
        }
        return command;
    }
    public static List VideoSectioning(String ffmpegPath,String outputPath_ts,String outputPath_mts,String outPath_m3u8) throws FFmpegExcption{
        List command=new ArrayList();

        command.add("ffmpeg");
//        command.add(ffmpegPath+"/ffmpeg");
        command.add("-i");
        command.add(outputPath_ts);
        command.add("-c");
        command.add("copy");
        command.add("-map");
        command.add("0");
        command.add("-f");
        command.add("segment");
        command.add("-segment_list");
        command.add(outPath_m3u8);
        command.add("-segment_time");
        command.add("10");
        command.add(outputPath_mts);

        return command;
    }

    /**
     * 判断视频类型
     * @param inputPath
     * @return
     */
    private static int checkContentType(String inputPath){
        String type=inputPath.substring(inputPath.lastIndexOf(".")+1,inputPath.length()).toLowerCase();
        if (type.equals("avi")) {
            return 1;
        } else if (type.equals("mpg")){
            return 1;
        } else if (type.equals("wmv")){
            return 1;
        } else if (type.equals("3gp")){
            return 1;
        } else if (type.equals("mov")){
            return 1;
        } else if (type.equals("mp4")){
            return 1;
        } else if(type.equals("mkv")){
            return 1;
        }else if (type.equals("asf")){
            return 0;
        } else if (type.equals("flv")){
            return 0;
        }else if (type.equals("rm")){
            return 0;
        } else if (type.equals("rmvb")){
            return 1;
        }
        return 9;
    }

    /**
     * 判断路径是否是一个文件
     * @param path
     * @return
     */
    private static boolean checkfile(String path){
        File file=new File(path);
        if (!file.isFile()){
            return false;
        }else {
            return true;
        }

    }

    /**
     * 转码过程
     * @param commend
     * @return
     * @throws FFmpegExcption
     */
    private static boolean process(List commend) throws FFmpegExcption{

        try {
            if (null == commend || commend.size() == 0)
                return false;
            Process videoProcess=new ProcessBuilder(commend).redirectErrorStream(true).start();
            new PrintStream(videoProcess.getErrorStream()).start();
            new PrintStream(videoProcess.getInputStream()).start();

            int exitCode=videoProcess.waitFor();
            if (exitCode==1){
                return false;
            }else {
                return true;
            }
        } catch (Exception e) {
            throw new FFmpegExcption("file upload faild",e);
        }
    }

//    /**
//     * 获取视频信息
//     * @param filename
//     */
//    public static void getVideoInfo(String filename){
//
//        IContainer container=IContainer.make();
//        int result=container.open(filename,IContainer.Type.READ,null);
//        if (result<0){
//            return;
//        }
//
//        int numStreaming = container.getNumStreams();
//        long duration =container.getDuration();
//        long fileSize=container.getFileSize();
//        long secondDuration=duration/1000000;
//
//        System.out.println("时长："+secondDuration+" s");
//        System.out.println("文件大小："+fileSize+" M");
//
//        for (int i=0;i<fileSize;i++){
//            IStream stream=container.getStream(i);
//            IStreamCoder coder=stream.getStreamCoder();
//            if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO){
//                System.out.println("视频宽度："+coder.getWidth());
//                System.out.println("视频高度："+coder.getHeight());
//            }
//        }
//    }

}

