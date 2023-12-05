package com.work.easystep2.handler;

import java.util.HashMap;
import java.util.Map;
import com.cloudinary.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

	private final String CLOUD_NAME = "dciwmbvcl";
    private final String API_KEY = "763344965641178";
    private final String API_SECRET = "YnLPMC1yTcI5SbPwIqi7oOmsBhw";
    
    
    @Bean
    public Cloudinary cloudinary(){
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name",CLOUD_NAME);
        config.put("api_key",API_KEY);
        config.put("api_secret",API_SECRET);
        return new Cloudinary(config);
    }
    
}
 

