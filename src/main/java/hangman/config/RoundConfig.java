package hangman.config;

import hangman.domain.Round;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class RoundConfig {

    @Bean
    public ConcurrentHashMap<String, Round> initialiseList (){

        ConcurrentHashMap<String,Round> concurrentHashMap = new ConcurrentHashMap<String, Round>();

        return concurrentHashMap;
    }


}
