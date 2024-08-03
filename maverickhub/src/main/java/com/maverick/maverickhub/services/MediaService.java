package com.maverick.maverickhub.services;

import com.github.fge.jsonpatch.JsonPatch;
import com.maverick.maverickhub.dtos.requests.UpdateMedRequest;
import com.maverick.maverickhub.dtos.requests.UpdateMediaRequest;
import com.maverick.maverickhub.dtos.requests.UploadMediaRequest;
import com.maverick.maverickhub.dtos.responses.MediaResponse;
import com.maverick.maverickhub.dtos.responses.UpdateMediaResponse;
import com.maverick.maverickhub.dtos.responses.UploadMediaResponse;
import com.maverick.maverickhub.models.Media;

import java.util.List;

public interface MediaService {

    UploadMediaResponse upload(UploadMediaRequest request);

    UploadMediaResponse uploadVideo(UploadMediaRequest request);

    Media getMediaById(long id);

    UpdateMediaResponse updateMedia(UpdateMediaRequest request);

    UpdateMediaResponse updateWith(long mediaId, JsonPatch request);

    List<MediaResponse> getMediaForUser(Long userId);
}
