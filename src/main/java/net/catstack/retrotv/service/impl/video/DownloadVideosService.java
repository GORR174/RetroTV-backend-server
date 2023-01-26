package net.catstack.retrotv.service.impl.video;

import com.xuggle.xuggler.IContainer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.catstack.retrotv.annotations.LoggingAspect;
import net.catstack.retrotv.entity.schedule.TodayScheduleModel;
import net.catstack.retrotv.entity.source.SourceModel;
import net.catstack.retrotv.repository.DailyScheduleRepository;
import net.catstack.retrotv.repository.TodayScheduleRepository;
import net.catstack.retrotv.utils.StreamGobbler;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DownloadVideosService {
    private final DailyScheduleRepository scheduleRepository;
    private final TodayScheduleRepository todayScheduleRepository;

    private final Random random = new Random();

    @LoggingAspect
    public void downloadVideos() {
        try {
            FileUtils.deleteDirectory(new File("downloads"));
            Thread.sleep(1000);
            log.info("Video download: Starting downloading...");
            var downloaded = new HashMap<SourceModel, Integer>();

            var currentDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
            if (currentDay == 0) {
                currentDay = 1;
            }
            var dayModel = scheduleRepository.getByDayNumber(currentDay);
            log.info("Video download: Current day number: {}", currentDay);

            dayModel.getSlots().forEach(slot -> {
                slot.getHeadings().forEach(heading -> {
                    heading.getSources().forEach(source -> {
                        if (downloaded.containsKey(source)) {
                            downloaded.put(source, downloaded.get(source) + 1);
                        } else {
                            downloaded.put(source, 1);
                            log.info("Video download: new source {}", source.getName());
                        }
                    });
                });
            });

            todayScheduleRepository.deleteAll();
            downloaded.keySet().forEach(source -> {
                log.info("Video download: Downloading source: {}", source.getName());

                var processBuilder = new ProcessBuilder();
                try {
                    var process = processBuilder
                            .command(
                                    "yt-dlp",
                                    "-f", "bestvideo[height<=360][ext=mp4]+ba[ext=m4a]",
                                    "--playlist-end", String.valueOf((int) (downloaded.get(source))),
                                    "--merge-output-format", "mp4",
                                    "--yes-playlist",
                                    "-o", "downloads/" + LocalDate.now() + "/" + source.getHeading().getName() + "/temp/%(title)s_%(id)s",
                                    "--embed-chapters", source.getLink()
                            ).start();

                    StreamGobbler outputGobbler = new StreamGobbler(process.getInputStream(), log::info);
                    StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream(), log::error);

                    new Thread(outputGobbler).start();
                    new Thread(errorGobbler).start();

                    process.waitFor();

                    var tempFolder = new File("downloads/" + LocalDate.now() + "/" + source.getHeading().getName() + "/temp/");
                    Arrays.stream(tempFolder.listFiles()).forEach(file -> {
                        try {
                            var todayScheduleModel = new TodayScheduleModel();

                            var fileName = file.getName();

                            System.out.println(fileName);
                            todayScheduleModel.setName(fileName.substring(0, fileName.lastIndexOf('_')));
                            todayScheduleModel.setSourceId(source.getId());
                            todayScheduleModel.setHeadingId(source.getHeading().getId());

                            var path = Files.move(Path.of(file.getPath()), Path.of("downloads/" + LocalDate.now() + "/" + source.getHeading().getName() + "/" + fileName.substring(fileName.lastIndexOf("_") + 1)));

                            todayScheduleModel.setPath(path.toString());

                            todayScheduleRepository.save(todayScheduleModel);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });

                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }

                log.info("Video download: Source {} has been downloaded", source.getName());
            });

            final LocalTime[] localTime = {LocalTime.of(10, 0)};

            dayModel.getSlots().forEach(slot -> {
                var setOfSources = new HashSet<Long>();
                slot.getHeadings().forEach(headingModel -> {
                    headingModel.getSources().forEach(sourceModel -> {
                        setOfSources.add(sourceModel.getId());
                    });
                });

                if (setOfSources.isEmpty()) {
                    return;
                }
                System.out.println("Current hour: " + slot.getHour());

                var sources = new ArrayList<>(setOfSources.stream().toList());

                while (true) {
                    if (slot.getHour() < localTime[0].getHour() || todayScheduleRepository.findByTimeIsNull().isEmpty() || setOfSources.size() == 0) {
                        System.out.println("AAA");
                        System.out.println(slot.getHour());
                        System.out.println(localTime[0].getHour());
                        System.out.println(todayScheduleRepository.findByTimeIsNull().isEmpty());
                        System.out.println(setOfSources.size() == 0);
                        return;
                    }

                    var randomSource = sources.get(random.nextInt(sources.size()));

                    var videoList = todayScheduleRepository.findBySourceIdAndTimeIsNull(randomSource);

                    if (videoList.isEmpty()) {
                        System.out.println("Source deleted: " + randomSource);
                        sources.remove((Long) randomSource);
                        continue;
                    }

                    var randomVideo = videoList.get(random.nextInt(videoList.size()));

                    var container = IContainer.make();
                    var result = container.open(randomVideo.getPath(), IContainer.Type.READ, null);
                    var duration = container.getDuration();
                    container.close();

                    var formatter = DateTimeFormatter.ofPattern("HH:mm");
                    randomVideo.setTime(formatter.format(localTime[0]));
                    todayScheduleRepository.save(randomVideo);

                    System.out.println("DURATION = " + duration);
                    localTime[0] = localTime[0].plusSeconds(duration / 1000000);
                }
            });

            Path playlistPath = Path.of("./playlist.m3u");
            Files.deleteIfExists(playlistPath);
            Files.createFile(playlistPath);

            var sb = new StringBuilder();

            todayScheduleRepository.findAllByTimeIsNotNullOrderByTime().forEach(todayScheduleModel -> {
                sb.append(todayScheduleModel.getPath());
                sb.append("\n");
            });

            Files.writeString(playlistPath, sb.toString());

            log.info("Video download: Downloaded successful");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error: ", e);
        }
    }
}
