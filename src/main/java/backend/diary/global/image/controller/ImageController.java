package backend.diary.global.image.controller;

import backend.diary.global.image.dto.response.UploadImageResponse;
import backend.diary.global.image.service.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/image")
public class ImageController {

    private final UploadFileService uploadFileService;

    @PostMapping
    public ResponseEntity<UploadImageResponse> uploadImage(
            @RequestPart MultipartFile image
    ){
        String url = uploadFileService.uploadFile(image);
        return ResponseEntity.ok(new UploadImageResponse(url));
    }

}
