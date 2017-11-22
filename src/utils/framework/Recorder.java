package utils.framework;

/**
 * Created by sshrimali on 8/28/16.
 */
import org.monte.media.math.Rational;
import org.monte.media.Format;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.monte.media.AudioFormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;
import static utils.framework.BaseDriver.testcaseId;


import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.Properties;

public class Recorder {



    private org.monte.screenrecorder.ScreenRecorder screenRecorder;

    private File location;
    private String defaultMovieName;
    private String customMovieName;
    private String videoLocation;

    public Recorder() throws Exception {
        initialize();

    }


    private void initialize() throws Exception {
        Properties globalProperties = new Properties();
        FileInputStream config = new FileInputStream("config/config.cfg");
        globalProperties.load(config);
        config.close();
        videoLocation = globalProperties.getProperty("videoLocation");

        location = new File(videoLocation);
        defaultMovieName = "defaultMovie.avi";
    }
    public void startRecording() throws Exception
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        Rectangle captureSize = new Rectangle(0,0, width, height);

        GraphicsConfiguration gc = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();

        this.screenRecorder = new SpecializedScreenRecorder(gc, captureSize,
                new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                        CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                        DepthKey, 24, FrameRateKey, Rational.valueOf(15),
                        QualityKey, 1.0f,
                        KeyFrameIntervalKey, 15 * 60),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black",
                        FrameRateKey, Rational.valueOf(30)),
                null, location, defaultMovieName);

        this.screenRecorder.start();
    }
    public void stopRecording() throws Exception
    {
        this.screenRecorder.stop();
        customMovieName = "test_" + testcaseId.replaceAll("_.*","") + "_movie.avi";
        Files.copy(new File(videoLocation + "//" + defaultMovieName).toPath(), new File(videoLocation + "//" + customMovieName).toPath(), REPLACE_EXISTING);
    }

}
