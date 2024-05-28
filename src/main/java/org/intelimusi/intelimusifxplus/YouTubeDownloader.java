package org.intelimusi.intelimusifxplus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class YouTubeDownloader {

    // Đường dẫn thư mục lưu trữ
    private static final String DOWNLOAD_DIR = "src/main/resources/downloads/";

    public static void downloadVideo(String url) {
        // Tạo thư mục nếu chưa tồn tại
        try {
            Path downloadPath = Paths.get(DOWNLOAD_DIR);
            if (!Files.exists(downloadPath)) {
                Files.createDirectories(downloadPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        ProcessBuilder processBuilder = new ProcessBuilder();
        // Lệnh để tải video với yt-dlp và lưu vào thư mục chỉ định
        processBuilder.command("yt-dlp", "-o", DOWNLOAD_DIR + "%(title)s.%(ext)s", url);

        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = process.waitFor();
            System.out.println("\nExited with code : " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // URL video YouTube cần tải
        String videoUrl = "https://www.youtube.com/watch?v=XyAypzE6pt0";
        downloadVideo(videoUrl);
    }
}
