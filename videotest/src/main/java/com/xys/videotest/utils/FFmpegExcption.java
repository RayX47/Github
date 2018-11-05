package com.xys.videotest.utils;

public class FFmpegExcption extends Exception {
    private static final long serialVersionUID=1L;

    public FFmpegExcption(){
        super();
    }

    public FFmpegExcption(String message){
        super(message);
    }

    public FFmpegExcption(Throwable cause){
        super(cause);
    }

    public FFmpegExcption(String message, Throwable cause){
        super(message,cause);
    }

}
