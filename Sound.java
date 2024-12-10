package com.mycompany.a4;

import com.codename1.ui.Display;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;

import java.io.IOException;
import java.io.InputStream;

public class Sound {
    private Media m;

    public Sound(String fileName) {
        try {
            InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/" + fileName);
            if (is == null) {
                System.out.println("Error: Sound file not found - " + fileName);
            } else {
                System.out.println("Loading sound: " + fileName);
                m = MediaManager.createMedia(is, "audio/wav");
            }
        } catch (IOException e) {
            System.out.println("Error initializing sound file: " + fileName);
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("General error initializing sound file: " + fileName);
            e.printStackTrace();
        }
    }

    public void play() {
        if (m != null) {
            m.setTime(0);
            m.play();
        } else {
            System.out.println("Sound is not initialized.");
        }
    }

    public void pause() {
        if (m != null) {
            m.pause();
        }
    }
}
