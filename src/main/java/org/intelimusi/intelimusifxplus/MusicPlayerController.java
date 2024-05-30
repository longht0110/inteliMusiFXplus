package org.intelimusi.intelimusifxplus;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MusicPlayerController {

    @FXML
    private Label songLabel;
    @FXML
    private Button playPauseButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button previousButton;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private VBox playlistBox; // Thay thế ListView bằng VBox để chứa các Card    @FXML
    @FXML
    private Button selectFolderButton; // Khai báo nút chọn thư mục
    @FXML
    private MediaView videoView;
    private MediaPlayer videoPlayer;

    private MediaPlayer mediaPlayer;
    private ImageView playPauseImageView; // Biến ImageView cho nút Play/Pause
    private boolean isPlaying = false; // Biến để theo dõi trạng thái phát
    private Image pauseImage;
    private Image playImage;
    private File selectedFolder; // Biến lưu trữ thư mục được chọn
    private List<File> musicFiles = new ArrayList<>(); // Danh sách các file nhạc

    @FXML
    public void initialize() {
        // Khởi tạo images cho nút Play/Pause
        playImage = new Image(getClass().getResource("/images/play-icon.png").toExternalForm());
        pauseImage = new Image(getClass().getResource("/images/pause-icon.png").toExternalForm());
        playPauseImageView = new ImageView(playImage); // Ban đầu hiển thị icon Play
        playPauseImageView.setFitWidth(45);
        playPauseImageView.setFitHeight(45);
        playPauseButton.setGraphic(playPauseImageView);

        // Xử lý sự kiện cho nút Play/Pause
        playPauseButton.setOnAction(event -> {
            if (isPlaying) {
                pause();
            } else {
                play();
            }
        });

        // Hiệu ứng hover cho nút play
        playPauseButton.setOnMouseEntered(event -> {
            playPauseButton.setStyle("-fx-background-color: #f0f0f0; -fx-cursor: hand;");
        });

        playPauseButton.setOnMouseExited(event -> {
            playPauseButton.setStyle("-fx-background-color: transparent;"); // Trở về style ban đầu
        });

        // Tạo ImageView cho nút Next
        Image nextImage = new Image(getClass().getResource("/images/next-icon.png").toExternalForm());
        ImageView nextImageView = new ImageView(nextImage);
        nextImageView.setFitWidth(25);
        nextImageView.setFitHeight(25);
        nextButton.setGraphic(nextImageView);

        // Hiệu ứng hover cho nút Next
        nextButton.setOnMouseEntered(event -> {
            nextButton.setStyle("-fx-background-color: #f0f0f0; -fx-cursor: hand;");
        });

        nextButton.setOnMouseExited(event -> {
            nextButton.setStyle("-fx-background-color: transparent;"); // Trở về style ban đầu
        });

        // Tạo ImageView cho nút Previous
        Image previousImage = new Image(getClass().getResource("/images/previous-icon.png").toExternalForm());
        ImageView previousImageView = new ImageView(previousImage);
        previousImageView.setFitWidth(25);
        previousImageView.setFitHeight(25);
        previousButton.setGraphic(previousImageView);

        // Hiệu ứng hover cho nút Previous
        previousButton.setOnMouseEntered(event -> {
            previousButton.setStyle("-fx-background-color: #f0f0f0; -fx-cursor: hand;");
        });

        previousButton.setOnMouseExited(event -> {
            previousButton.setStyle("-fx-background-color: transparent;"); // Trở về style ban đầu
        });

        // Xử lý sự kiện cho nút chọn thư mục
        selectFolderButton.setOnAction(event -> {
            selectFolder();
        });

        // Hiển thị danh sách bài hát lên playlistView
        updatePlaylistView();
    }

    // Phương thức selectFolder
    public void selectFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Chọn thư mục gốc");
        selectedFolder = directoryChooser.showDialog(null);
        if (selectedFolder != null) {
            // Cập nhật danh sách bài hát
            musicFiles = getMusicFiles(selectedFolder);
            updatePlaylistView();
        }
    }

    // Phương thức getMusicFiles
    private List<File> getMusicFiles(File folder) {
        List<File> files = new ArrayList<>();
        File[] listFiles = folder.listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                if (file.isFile() && (file.getName().endsWith(".mp3") || file.getName().endsWith(".wav") || file.getName().endsWith(".mp4"))) {
                    files.add(file);
                }
            }
        }
        return files;
    }

    private void updatePlaylistView() {
        playlistBox.getChildren().clear();
        for (File file : musicFiles) {
            playlistBox.getChildren().add(createSongCard(file));
        }
    }

    // Phương thức tạo Card cho mỗi bài hát
    private Region createSongCard(File file) {
        HBox songCard = new HBox(10);
        songCard.getStyleClass().add("songCard"); // Thêm dòng này
        songCard.setPadding(new Insets(5));
        songCard.setStyle("-fx-background-color: white; -fx-background-radius: 5;");

        // Phần ảnh bìa
        Image placeholderImage = new Image(getClass().getResource("/images/music-placeholder.png").toExternalForm());
        ImageView albumArtView = new ImageView(placeholderImage); // Giả sử ảnh placeholder
        albumArtView.setFitHeight(50);
        albumArtView.setFitWidth(50);

        // Phần thông tin bài hát
        VBox songInfo = new VBox(5);
        Label songTitle = new Label(file.getName());
        songTitle.setStyle("-fx-font-weight: bold;");
        Label songArtist = new Label("Unknown Artist"); // Giả sử chưa có thông tin nghệ sĩ
        Label songDuration = new Label("0:00"); // Giả sử chưa có thông tin thời lượng

        songInfo.getChildren().addAll(songTitle, songArtist, songDuration);

        // Thêm sự kiện click để phát nhạc
        songCard.setOnMouseClicked(event -> playTrack(file));

        songCard.getChildren().addAll(albumArtView, songInfo);
        return songCard;
    }

    // Các phương thức xử lý sự kiện
    public void play() {
        if (mediaPlayer == null) {
            // Nếu chưa có bài hát nào được chọn, hiển thị FileChooser
            if (musicFiles.isEmpty()) {
                // Nếu chưa chọn thư mục gốc, hiển thị thông báo
                System.out.println("Vui lòng chọn thư mục gốc trước!");
                return;
            }
            // Chọn bài hát đầu tiên trong danh sách
            File selectedFile = musicFiles.get(0);
            playTrack(selectedFile);
        } else {
            // Đã có bài hát được chọn, tiếp tục phát
            mediaPlayer.play();
            isPlaying = true; // Cập nhật trạng thái
            playPauseImageView.setImage(pauseImage); // Chuyển sang icon Pause
        }
    }

    @FXML
    private void pause() {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
            isPlaying = false; // Cập nhật trạng thái
            playPauseImageView.setImage(playImage); // Chuyển sang icon Play
        }
    }

    public void next() {
        if (mediaPlayer != null && !musicFiles.isEmpty()) {
            int currentTrackIndex = getCurrentTrackIndex();
            if (currentTrackIndex < musicFiles.size() - 1) {
                currentTrackIndex++;
                playTrack(musicFiles.get(currentTrackIndex));
            } else {
                // Chuyển về bài hát đầu tiên nếu đã là bài hát cuối cùng
                playTrack(musicFiles.get(0));
            }
        }
    }

    public void previous() {
        if (mediaPlayer != null && !musicFiles.isEmpty()) {
            int currentTrackIndex = getCurrentTrackIndex();
            if (currentTrackIndex > 0) {
                currentTrackIndex--;
                playTrack(musicFiles.get(currentTrackIndex));
            } else {
                // Chuyển về bài hát cuối cùng nếu đã là bài hát đầu tiên
                playTrack(musicFiles.get(musicFiles.size() - 1));
            }
        }
    }

    private int getCurrentTrackIndex() {
        for (int i = 0; i < musicFiles.size(); i++) {
            if (musicFiles.get(i).toURI().toString().equals(mediaPlayer.getMedia().getSource())) {
                return i;
            }
        }
        return -1;
    }

    // Phương thức playTrack
    private void playTrack(File file) {
        String filePath = file.toURI().toString();
        Media media = new Media(filePath);

        // Nếu đã có mediaPlayer, hãy dừng trước khi phát file mới
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnReady(() -> {
            songLabel.setText(file.getName());
        });

        mediaPlayer.setOnError(() -> {
            System.out.println("Lỗi MediaPlayer: " + mediaPlayer.getError().getMessage());
        });

        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            progressBar.setProgress(mediaPlayer.getCurrentTime().toSeconds() / mediaPlayer.getTotalDuration().toSeconds());
        });

        mediaPlayer.play();
        isPlaying = true;
        playPauseImageView.setImage(pauseImage);
    }
}
