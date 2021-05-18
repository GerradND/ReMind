package com.adpro.remind;

import com.adpro.remind.event.InputEventListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.security.auth.login.LoginException;

@Configuration
public class BotConfiguration {

    @Value("${token}")
    private String token;

    @Autowired
    private InputEventListener inputEventListener;

    @Bean
    public void configure() throws LoginException {
        JDA jdaClient = JDABuilder.createDefault(token)
                .addEventListeners(inputEventListener)
                .build();
    }

}
