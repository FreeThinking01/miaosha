package com.mooc.miaosha.kafka;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MQConfig {

    public static final String MIAOSHA_TOPIC = "miaosha.topic";
    public static final String MIAOSHA_GROUP = "miaosha.group";


}
