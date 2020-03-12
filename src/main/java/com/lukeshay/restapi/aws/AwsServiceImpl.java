package com.lukeshay.restapi.aws;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AwsServiceImpl implements AwsService {
  private static final String TEMP_FILE_NAME = "file_name.jpg";

  AWSCredentialsProvider credentialsProvider;

  @Value("${file.bucket.name}")
  private String bucketName;

  @Value("${file.bucket.url}")
  private String bucketUrl;

  @Value("${do.key.access")
  private String accessKey;

  @Value("${do.key.secret")
  private String secretKey;

  public AwsServiceImpl() {
    if (accessKey == null || secretKey == null) {
      credentialsProvider = new AWSStaticCredentialsProvider(new BasicAWSCredentials("", ""));
    } else {
      credentialsProvider =
          new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey));
    }
  }

  @Override
  public File convertFile(MultipartFile file) {
    if (file == null) return null;
    File convertedFile = new File(TEMP_FILE_NAME);

    try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
      fos.write(file.getBytes());
    } catch (IOException e) {
      LOG.error("Exception when converting MultipartFile to File.", e);
      return null;
    }

    return convertedFile;
  }

  @Override
  public String uploadFileToS3(String fileName, MultipartFile file) {
    File converted = convertFile(file);

    AmazonS3 awsBuckets =
        AmazonS3ClientBuilder.standard()
            .withCredentials(credentialsProvider)
            .withEndpointConfiguration(new EndpointConfiguration(bucketUrl, "nyc3"))
            .build();

    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, converted);
    putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
    awsBuckets.putObject(putObjectRequest);

    if (awsBuckets.doesObjectExist(bucketName, fileName)) {
      return String.format("%s.%s/%s", bucketName, bucketUrl, fileName);
    } else {
      return null;
    }
  }
}
