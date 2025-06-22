import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class AudioPlayer {
    private Clip clip;
    private boolean isPaused = false;

    public void load(File audioFile) throws Exception {
        AudioInputStream ais = AudioSystem.getAudioInputStream(audioFile);
        clip = AudioSystem.getClip();
        clip.open(ais);
    }

    public void playLoop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void pause() {
        if (clip.isRunning()) {
            clip.stop();
            isPaused = true;
        }
    }

    public void resume() {
        if (isPaused) {
            clip.start();
            isPaused = false;
        }
    }

    public void stop() {
        clip.stop();
        clip.close();
    }

    public boolean isPlaying() {
        return clip.isOpen();
    }
}
