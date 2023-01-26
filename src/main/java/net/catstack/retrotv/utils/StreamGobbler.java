package net.catstack.retrotv.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public class StreamGobbler implements Runnable {
    private InputStream inputStream;
    private Consumer<String> consumeInputLine;

    private int lastDownloadLog = 10000;

    public StreamGobbler(InputStream inputStream, Consumer<String> consumeInputLine) {
        this.inputStream = inputStream;
        this.consumeInputLine = consumeInputLine;
    }

    public void run() {
        new BufferedReader(new InputStreamReader(inputStream)).lines().forEach(line -> {
            if (line.startsWith("[download] ") && Character.isDigit(line.charAt(13))) {
                var progress = Integer.valueOf(line.substring(11, 14).trim());
                if (Math.abs(progress - lastDownloadLog) >= 10) {
                    lastDownloadLog = (progress / 10) * 10;
                    consumeInputLine.accept(line);
                }
            } else {
                consumeInputLine.accept(line);
            }
        });
    }
}