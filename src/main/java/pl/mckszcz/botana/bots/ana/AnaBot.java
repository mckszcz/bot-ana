package pl.mckszcz.botana.bots.ana;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pl.mckszcz.botana.bots.ana.services.AnaService;

import javax.annotation.PostConstruct;

@Component
public class AnaBot extends TelegramLongPollingBot {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnaBot.class);

    private final String botToken;

    private final String songLink;

    private final String responseText = "Det enda jag gör hela dagarna att sitta på den Ventrilo och spelar DotA ";

    private AnaService service;

    @Autowired
    public AnaBot(@Value("${telegram.bot.token}") String token, @Value("${telegram.bot.link}") String link, AnaService service) {
        this.botToken = token;
        this.songLink = link;
        this.service = service;
    }

    @PostConstruct
    private void registerBot() {
        TelegramBotsApi api = new TelegramBotsApi();
        try {
            api.registerBot(this);
            LOGGER.info("Bot registered correctly. Starting to listen...");
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {

        String receivedMessage = update.getMessage().getText();

        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        sendMessage.setText(responseText + songLink);

        if (service.containsDota(receivedMessage)) {
            LOGGER.debug("Message received: {}", receivedMessage);
            try {
                this.execute(sendMessage);
                LOGGER.debug("Message sent.");
            } catch (TelegramApiException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
