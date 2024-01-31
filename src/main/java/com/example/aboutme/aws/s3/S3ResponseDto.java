package com.example.aboutme.aws.s3;

import lombok.Getter;

@Getter
public class S3ResponseDto {
  private String imgUrl;

  public S3ResponseDto(String imgUrl){
    this.imgUrl = imgUrl;
  }

}
