package org.intelimusi.intelimusifxplus;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;

public class GeminiAPIIntegration {

    public static void main(String[] args) {
        // Thay thế bằng giá trị thực của bạn
        String apiKey = "AIzaSyBUkiR9NaJ6RYMFlVYF-qcD-VZ5_erXZs8";

        // Tạo HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Tạo request body
        String requestBody = "{\"contents\":[{\"parts\":[{\"text\":\"phân tích tên bài hát sau để biết tên bài hát và ca sĩ, lưu ý không dựa vào viết hoa thường: CHANGG  HƯỚNG DƯƠNG  Đến đây với em nào  Official Lyric Video, trả về kết quả theo dạng Tên-Ca-Sĩ|Tên-Bài-Hát\"}]}]}";

        // Tạo HttpRequest
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        // Gửi request và xử lý response
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response status code: " + response.statusCode());
            System.out.println("Response body: " + response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
