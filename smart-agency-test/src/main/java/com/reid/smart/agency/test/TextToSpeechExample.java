package com.reid.smart.agency.test;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * <p>
 *
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class TextToSpeechExample {

    public static void main(String[] args) {
        try {
            // 获取默认语音
            Voice voice = VoiceManager.getInstance().getVoice("kevin16");

            // 设置文本
            voice.allocate();
            voice.speak("Hello, this is a text to speech example.");

            // 将音频写入文件
            OutputStream audioStream = new FileOutputStream("output.wav");

            // 释放资源
            audioStream.close();
            voice.deallocate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
