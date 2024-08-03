package com.maverick.maverickhub.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.maverick.maverickhub.dtos.requests.UpdateMedRequest;
import com.maverick.maverickhub.dtos.requests.UpdateMediaRequest;
import com.maverick.maverickhub.dtos.requests.UploadMediaRequest;
import com.maverick.maverickhub.dtos.responses.MediaResponse;
import com.maverick.maverickhub.dtos.responses.UpdateMediaResponse;
import com.maverick.maverickhub.dtos.responses.UploadMediaResponse;
import com.maverick.maverickhub.exceptions.MediaUpdateFailedException;
import com.maverick.maverickhub.exceptions.MediaUploadFailedException;
import com.maverick.maverickhub.models.Media;
import com.maverick.maverickhub.models.User;
import com.maverick.maverickhub.repositories.MediaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class MavericksHubMediaService implements MediaService {
    private final MediaRepository mediaRepository;
    private final Cloudinary cloudinary;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Override
    public UploadMediaResponse upload(UploadMediaRequest request) {
        User uploader = userService.getById(request.getUserId());
        try{
            Map<?,?> response = cloudinary.uploader().upload(request.getMediaFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
            log.info("cloudinary upload response:: {}", response);
            String url = response.get("url").toString();
            Media media = modelMapper.map(request, Media.class);
            media.setUrl(url);
            media.setUploader(uploader);
            media = mediaRepository.save(media);
            return modelMapper.map(media, UploadMediaResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UploadMediaResponse uploadVideo(UploadMediaRequest request) {
        try {
            var response = cloudinary.uploader().upload(request.getMediaFile().getBytes(),
                    ObjectUtils.asMap("resource_type", "video"));
            log.info("cloudinary video upload response:: {}", response);
            String url = response.get("url").toString();
            Media media = modelMapper.map(request, Media.class);
            media.setUrl(url);
            media = mediaRepository.save(media);
            return modelMapper.map(media, UploadMediaResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Media getMediaById(long id) {
        return mediaRepository.findById(id).orElseThrow(()-> new MediaUploadFailedException("media not found"));
    }

    @Override
    public UpdateMediaResponse updateMedia(UpdateMediaRequest request) {
        Media media = getMediaById(request.getMediaId());
        media.setDescription(request.getDescription());
        media.setCategory(request.getCategory());

        UpdateMediaResponse response = new UpdateMediaResponse();
        response.setId(media.getId());
        mediaRepository.save(media);
        return response;
    }

    @Override
    public UpdateMediaResponse updateWith(long mediaId, JsonPatch jsonPatch) {
        try{
            //1. get target object
            Media media = getMediaById(mediaId);
            //2.convert object from above to jsonNode
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode mediaNode = objectMapper.convertValue(media, JsonNode.class);

            //3.apply jsonPatch to mediaNode
            mediaNode = jsonPatch.apply(mediaNode);

            //4.convert mediaNode to media object
            media = objectMapper.convertValue(mediaNode, Media.class);
            media = mediaRepository.save(media);
            return modelMapper.map(media, UpdateMediaResponse.class);
        } catch (JsonPatchException e) {
            throw new MediaUpdateFailedException(e.getMessage());
        }


    }

    @Override
    public List<MediaResponse> getMediaForUser(Long userId) {
        userService.getById(userId);
        List<Media> media = mediaRepository.findAllMediaFor(userId);
        var res = media.stream()
                .map(mediaItem -> modelMapper.map(mediaItem, MediaResponse.class)).toList();
        System.out.println("res: "+res);
        return res;
    }


}
