package net.catstack.retrotv.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.catstack.retrotv.service.StreamerService;
import org.springframework.stereotype.Service;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.media.MediaRef;
import uk.co.caprica.vlcj.media.TrackType;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventListener;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

@Slf4j
@Service
public class VLCStreamService implements StreamerService {
    private Thread streamThread;
    private EmbeddedMediaPlayer mediaPlayer;
    private boolean isOnline = false;

    @Override
    public boolean isOnline() {
        return isOnline;
    }

    private static String formatHttpStream(String serverAddress, int serverPort) {
        StringBuilder sb = new StringBuilder();

        sb.append(":sout=#transcode{vvcodec=h264,vb=1500,vvenc=x264{profile=baseline},width=640,height=480,acodec=mp3,ab=128,channels=2,samplerate=44100,scodec=none}:http{mux=ffmpeg{mux=flv},dst=:5555/}");

        return sb.toString();
    }

    @Override
    public void startStream() {
        stopStream();
        log.info("Stream is starting");
        streamThread = new Thread(this::startStreamLocal);
        streamThread.start();
    }

    private void startStreamLocal() {
        if (mediaPlayer != null) {
            stopStream();
        }

        String media = "./playlist.m3u";
        String options = formatHttpStream("", 5555);

        System.out.println("Streaming '" + media + "' to '" + options + "'");

        MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory( "--loop");
        mediaPlayer = mediaPlayerFactory.mediaPlayers().newEmbeddedMediaPlayer();

        mediaPlayer.media().play(media, options, ":sout-keep");

        mediaPlayer.events().addMediaPlayerEventListener(new MediaPlayerEventListener() {
            @Override
            public void mediaChanged(MediaPlayer mediaPlayer, MediaRef mediaRef) {
                System.out.println("MEDIA CHANGED");
            }

            @Override
            public void opening(MediaPlayer mediaPlayer) {
                System.out.println("OPENING");
                isOnline = true;
            }

            @Override
            public void buffering(MediaPlayer mediaPlayer, float v) {

            }

            @Override
            public void playing(MediaPlayer mediaPlayer) {
                System.out.println("PLAYING");
                isOnline = true;
            }

            @Override
            public void paused(MediaPlayer mediaPlayer) {
                System.out.println("PAUSED");
            }

            @Override
            public void stopped(MediaPlayer mediaPlayer) {
                System.out.println("STOPPED");
                isOnline = false;
            }

            @Override
            public void forward(MediaPlayer mediaPlayer) {

            }

            @Override
            public void backward(MediaPlayer mediaPlayer) {

            }

            @Override
            public void finished(MediaPlayer mediaPlayer) {
                isOnline = false;
            }

            @Override
            public void timeChanged(MediaPlayer mediaPlayer, long l) {

            }

            @Override
            public void positionChanged(MediaPlayer mediaPlayer, float v) {

            }

            @Override
            public void seekableChanged(MediaPlayer mediaPlayer, int i) {

            }

            @Override
            public void pausableChanged(MediaPlayer mediaPlayer, int i) {

            }

            @Override
            public void titleChanged(MediaPlayer mediaPlayer, int i) {

            }

            @Override
            public void snapshotTaken(MediaPlayer mediaPlayer, String s) {

            }

            @Override
            public void lengthChanged(MediaPlayer mediaPlayer, long l) {

            }

            @Override
            public void videoOutput(MediaPlayer mediaPlayer, int i) {

            }

            @Override
            public void scrambledChanged(MediaPlayer mediaPlayer, int i) {

            }

            @Override
            public void elementaryStreamAdded(MediaPlayer mediaPlayer, TrackType trackType, int i) {

            }

            @Override
            public void elementaryStreamDeleted(MediaPlayer mediaPlayer, TrackType trackType, int i) {

            }

            @Override
            public void elementaryStreamSelected(MediaPlayer mediaPlayer, TrackType trackType, int i) {

            }

            @Override
            public void corked(MediaPlayer mediaPlayer, boolean b) {

            }

            @Override
            public void muted(MediaPlayer mediaPlayer, boolean b) {

            }

            @Override
            public void volumeChanged(MediaPlayer mediaPlayer, float v) {

            }

            @Override
            public void audioDeviceChanged(MediaPlayer mediaPlayer, String s) {

            }

            @Override
            public void chapterChanged(MediaPlayer mediaPlayer, int i) {

            }

            @Override
            public void error(MediaPlayer mediaPlayer) {

            }

            @Override
            public void mediaPlayerReady(MediaPlayer mediaPlayer) {

            }
        });
    }

    @Override
    public void stopStream() {
        log.info("Stream is stopping");
        isOnline = false;
        if (mediaPlayer != null) {
            mediaPlayer.controls().stop();
            mediaPlayer = null;
        }
        if (streamThread != null) {
            streamThread.interrupt();
        }
    }
}
