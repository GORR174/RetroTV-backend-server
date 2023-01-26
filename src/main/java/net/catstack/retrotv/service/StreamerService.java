package net.catstack.retrotv.service;

public interface StreamerService {
    boolean isOnline();

    void startStream();

    void stopStream();
}
