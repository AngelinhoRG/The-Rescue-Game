package com.mycompany.a4;

import com.codename1.ui.Display;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;

import java.io.InputStream;

public class BGSound implements Runnable {
    private Media media;

    public BGSound(String fileName) {
        try {
            InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/" + fileName);
            if (is == null) {
                System.out.println("Error: Background sound file not found - " + fileName);
            } else {
                media = MediaManager.createMedia(is, "audio/wav", this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (media != null) {
            media.play();
        } else {
            System.out.println("Background sound is not initialized.");
        }
    }

    public void pause() {
        if (media != null) {
            media.pause();
        }
    }

    @Override
    public void run() {
        if (media != null) {
            media.setTime(0); // Loop from the beginning
            media.play();
        }
    }
}
