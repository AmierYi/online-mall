package com.hxx.yi.config;

import com.hxx.yi.service.MinioUtilsService;
import io.minio.MinioClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@Slf4j
@EnableConfigurationProperties({MinIOConfigProperties.class})
//当引入MinIOUtilsService接口时
@ConditionalOnClass(MinioUtilsService.class)
public class MinioConfig {

    @Autowired
    private MinIOConfigProperties minIOConfigProperties;

    @Bean
    public MinioClient buildMinioClient() {

        log.info("开始创建 MinioClient...");

        MinioClient minioClient = MinioClient
                .builder()
                .credentials(minIOConfigProperties.getAccessKey(), minIOConfigProperties.getSecretKey())
                .endpoint(minIOConfigProperties.getEndpoint())
                .build();
        log.info("创建完毕 MinioClient...");
        return minioClient;

    }
}
