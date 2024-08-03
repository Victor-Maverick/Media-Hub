package com.maverick.maverickhub.utils;

import com.maverick.maverickhub.dtos.requests.UploadMediaRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static com.maverick.maverickhub.models.Category.ACTION;

public class TestUtils {
    public static final String TEST_IMAGE_LOCATION = "C:\\Users\\DELL\\Downloads\\maverickhub\\maverickhub\\src\\main\\resources\\static\\portable.jpeg";
    public static final String TEST_VIDEO_LOCATION = "C:\\Users\\DELL\\Downloads\\maverickhub\\maverickhub\\src\\main\\resources\\static\\[30 SECONDS] SHORT ESP NMH Video 1.mp4";

    public static UploadMediaRequest buildUploadRequest(InputStream inputStream) throws IOException {
        UploadMediaRequest request = new UploadMediaRequest();
        MultipartFile file = new MockMultipartFile("spider man portable", inputStream);
        request.setCategory(ACTION);
        request.setUserId(201L);
        request.setMediaFile(file);
        return request;
    }
}
