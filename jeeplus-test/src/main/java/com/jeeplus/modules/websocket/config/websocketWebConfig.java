package com.jeeplus.modules.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author WangGuipin
 * @ClassName websocketWebConfig
 * @Description TODO
 * @create 2019-05-28 16:39
 * @desc web
 **/
@Configuration
public class websocketWebConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
