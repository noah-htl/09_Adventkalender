package at.htlsaafelden.adventkalender;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SoundController {
    private static SoundController INSTANCE;
    public static SoundController getInstance() {
        if(INSTANCE == null) INSTANCE = new SoundController();
        return INSTANCE;
    }

    private MediaPlayer mediaPlayer;
    private Thread mediaThread;
    private List<Media> mediaList = new ArrayList<>();
    private AtomicInteger currentSong = new AtomicInteger();

    public void add(String s) {
        mediaList.add(new Media(getClass().getResource(s).toExternalForm()));
    }

    private SoundController() {

    }

    public void play() {
        final boolean[] nextSong = {true};
        this.mediaThread = new Thread(() -> {
            while (true) {
                if(!nextSong[0]) {
                    continue;
                }
                System.out.println("new song");
                if(this.mediaPlayer != null) {
                    this.mediaPlayer.stop();
                    this.mediaPlayer.dispose();
                }
                this.mediaPlayer = new MediaPlayer(mediaList.get(currentSong.get()));
                mediaPlayer.setVolume(0.25);
                nextSong[0] = false;
                this.mediaPlayer.setOnEndOfMedia(() -> {
                    currentSong.getAndAdd(1);
                    this.mediaPlayer.stop();
                    nextSong[0] = true;
                });
                this.mediaPlayer.play();
            }
        });

        this.mediaThread.setDaemon(true);
        this.mediaThread.start();
    }

    public void pause() {
        this.mediaPlayer.pause();
    }

    public void resume() {
        this.mediaPlayer.play();
    }

    public void toggle() {
        /*this.mediaPlayer.stop();
        this.mediaPlayer.dispose();
        this.mediaPlayer.getOnEndOfMedia().run();*/
        if(this.mediaPlayer.getStatus() != MediaPlayer.Status.PAUSED) {
            pause();
        } else {
            resume();
        }
    }
}
