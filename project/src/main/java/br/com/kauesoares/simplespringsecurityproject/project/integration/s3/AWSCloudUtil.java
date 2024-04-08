package br.com.kauesoares.simplespringsecurityproject.project.integration.s3;

import br.com.kauesoares.simplespringsecurityproject.project.config.properties.S3Properties;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Scope("singleton")
public class AWSCloudUtil {

    private final S3Properties s3Properties;
    private static AmazonS3 s3Client;

    private AWSStaticCredentialsProvider getCredentials() {
        return new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(
                        s3Properties.getAccessKey(),
                        s3Properties.getSecretKey()
                )
        );
    }

    private AmazonS3 getS3Client() {
        if (s3Client == null) {
            s3Client = AmazonS3ClientBuilder
                    .standard()
                    .withCredentials(this.getCredentials())
                    .withRegion(Regions.fromName(s3Properties.getRegion()))
                    .build();
        }

        return s3Client;
    }

    public S3Object getFile(String fileKey) {
        return this.getS3Client().getObject(this.s3Properties.getBucketName(), fileKey);
    }

}
