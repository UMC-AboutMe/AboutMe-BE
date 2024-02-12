package com.example.aboutme.aws.s3;

import java.io.IOException;
import java.net.URLEncoder;

import com.example.aboutme.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping("/pictures")
    public ApiResponse<S3ResponseDto> uploadFiles(
            @RequestPart(value = "file", required = false) MultipartFile multipartFile) {
        return ApiResponse.onSuccess(s3Service.uploadFile(multipartFile));
    }

    @GetMapping("/pictures")
    public ResponseEntity<ByteArrayResource> downloadFiles(@RequestParam String fileName){
        try{
            byte[] data = s3Service.download(fileName);
            ByteArrayResource resource = new ByteArrayResource(data);
            return ResponseEntity
                    .ok()
                    .contentLength(data.length)
                    .header("Content-type", "application/octet-stream")
                    .header("Content-disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "utf-8") + "\"")
                    .body(resource);
        } catch (IOException ex){
            return ResponseEntity.badRequest().contentLength(0).body(null);
        }
    }
}