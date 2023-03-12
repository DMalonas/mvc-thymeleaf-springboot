package com.udacity.jwdnd.course1.cloudstorage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

//@Configuration
//public class FileUploadConfig {
//    @Bean
//    public MultipartResolver multipartResolver() {
//        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//        resolver.setMaxUploadSize(1024 * 1024 * 5); // set maximum file size
//        resolver.setMaxInMemorySize(1024 * 1024 * 5); // set maximum in-memory size
//        resolver.setDefaultEncoding("UTF-8");
//        return resolver;
//    }
//}
