package pl.mckszcz.botana;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApiContextInitializer.init(); // necessary to register the bot
        SpringApplication.run(Application.class, args);
    }

}
