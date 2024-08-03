package com.maverick.maverickhub.services;

import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import com.maverick.maverickhub.dtos.requests.UpdateMedRequest;
import com.maverick.maverickhub.dtos.requests.UpdateMediaRequest;
import com.maverick.maverickhub.dtos.requests.UploadMediaRequest;
import com.maverick.maverickhub.dtos.responses.MediaResponse;
import com.maverick.maverickhub.dtos.responses.UpdateMediaResponse;
import com.maverick.maverickhub.dtos.responses.UploadMediaResponse;
import com.maverick.maverickhub.models.Category;
import com.maverick.maverickhub.models.Media;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.maverick.maverickhub.models.Category.*;
import static com.maverick.maverickhub.utils.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
public class MediaServiceTest {
    @Autowired
    private MediaService mediaService;

    @Test
    public void uploadMediaTest(){
        Path path = Paths.get(TEST_IMAGE_LOCATION);
        try(var inputStream = Files.newInputStream(path)){
            UploadMediaRequest request = buildUploadRequest(inputStream);
            UploadMediaResponse response = mediaService.upload(request);

            assertThat(response).isNotNull();
            assertThat(response.getMediaUrl()).isNotNull();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    @Test
    public void uploadVideoTest(){
        Path path = Paths.get(TEST_VIDEO_LOCATION);
        try(var inputStream = Files.newInputStream(path)){
            UploadMediaRequest request = buildUploadRequest(inputStream);
            UploadMediaResponse response = mediaService.upload(request);
            assertThat(response).isNotNull();
            assertThat(response.getMediaUrl()).isNotNull();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getMediaByIdTest(){
        Media media = mediaService.getMediaById(100L);
        assertThat(media).isNotNull();
        assertThat(media.getId()).isNotNull();
    }

    @Test
    public void uploadImageTest(){
        UpdateMediaRequest request = new UpdateMediaRequest();
        request.setMediaId(100L);
        request.setCategory(HORROR);
        request.setDescription("Sweet terror");
        UpdateMediaResponse response = mediaService.updateMedia(request);
        Media media = mediaService.getMediaById(response.getId());
        assertThat(media).isNotNull();
        assertThat(media.getId()).isNotNull();
        assertEquals("Sweet terror", media.getDescription());
    }

    @Test
    @DisplayName("test update media files")
    public void updateMediaTest() throws JsonPointerException {
        Category category = mediaService.getMediaById(100L).getCategory();
        assertThat(category).isNotEqualTo(SCI_FI);

        List<JsonPatchOperation> operations = List.of(new ReplaceOperation(new JsonPointer("/category"), new TextNode(SCI_FI.name())));
        JsonPatch request = new JsonPatch(operations);
        UpdateMediaResponse response = mediaService.updateWith(100L, request);
        assertThat(response).isNotNull();
        category = mediaService.getMediaById(100L).getCategory();
        assertThat(category).isEqualTo(SCI_FI);
    }

    @Test
    public void getMediaForUserTest(){
        Long userId = 200L;
        List<MediaResponse> media = mediaService.getMediaForUser(userId);
        assertThat(media).hasSize(4);
    }

}
