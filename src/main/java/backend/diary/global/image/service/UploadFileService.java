package backend.diary.global.image.service;

import backend.diary.global.image.exception.BadRequestUploadFileException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UploadFileService {

    private final S3Client s3Client;
    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFile(MultipartFile multipartFile) {
        validateMultipartFile(multipartFile);
        String originalFilename = getOriginalFilename(multipartFile);
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        String storeFileName = createStoreFileName(ext);

        return uploadS3File(multipartFile, storeFileName);
    }

    public String uploadS3File(MultipartFile file, String storeFileName) {
        try{
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .key(storeFileName)
                    .build();

            RequestBody requestBody = RequestBody.fromBytes(file.getBytes());
            s3Client.putObject(putObjectRequest, requestBody);
        } catch (IOException e) {
            throw new BadRequestUploadFileException();
        }
        GetUrlRequest getUrlRequest = GetUrlRequest.builder()
                .bucket(bucket)
                .key(storeFileName)
                .build();

        return s3Client.utilities().getUrl(getUrlRequest).toString();
    }

    private void validateMultipartFile(MultipartFile multipartFile) {
        if(multipartFile.isEmpty()){
            throw new BadRequestUploadFileException();
        }

    }

    private String getOriginalFilename(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        if(originalFilename == null || originalFilename.isEmpty()){
            originalFilename = "";
        }
        return originalFilename;
    }

    private String createStoreFileName(String ext){
        String uuid = UUID.randomUUID().toString();
        return uuid + ext;
    }


}
