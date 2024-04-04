package project.project.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
@Service
public class S3Uploader {
    @Autowired
    private AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // MultipartFile을 전달받아 File로 전환한 후 S3에 업로드
    public String uploadFiles(MultipartFile multipartFile, String dirName) throws IOException {
        // 파일을 변환할 수 없으면 에러발생
        File uploadFile = convert(multipartFile).orElseThrow(() -> new IllegalArgumentException("Error : MultipartFile -> File 전환 실패"));
        return upload(uploadFile, dirName, multipartFile.getOriginalFilename());
    }

    private String upload(File uploadFile, String dirName, String originalName) {
        // S3에 저장된 파일 이름
        String fileName = dirName + "/" + UUID.randomUUID() + originalName;
        // S3로 업로드
        String uploadImageUrl = putS3(uploadFile, fileName);
        // 로컬에 생성된 File 삭제 (MultipartFile -> File 전환 하며 로컬에 파일 생성됨)
        removeNewFile(uploadFile);
        // 업로드된 파일의 S3 URL 주소 반환
        return uploadImageUrl;
    }

    // S3로 업로드
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 로컬에 저장된 이미지 삭제
    private void removeNewFile(File targetFile) {
        if(targetFile.delete()) {
            System.out.println("파일 삭제 완료");
            return;
        } else {
            System.out.println("파일 삭제 실패");
        }
    }

    // 로컬에 파일 업로드
    private Optional<File> convert(MultipartFile file) throws  IOException {
        File convertFile = new File(System.getProperty("user.dir") + "/" + UUID.randomUUID());
        if(convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
            try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    // S3에 업로드된 이미지 삭제처리(input file 수정, 삭제의 경우)
    public void fileDelete(String fileKey) {
        try {
            amazonS3Client.deleteObject(this.bucket, fileKey);
            System.out.println("파일 삭제 완료: " + fileKey);
        } catch (AmazonServiceException e) {
            System.err.println("파일 삭제 실패: " + e.getErrorMessage());
        }
    }

}
