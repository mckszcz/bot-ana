package pl.mckszcz.botana.bots.ana.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class AnaService {

    private static final String KEY_WORD = "dot";

    public boolean containsDota(String message) {
        return StringUtils.containsIgnoreCase(message, KEY_WORD);
    }
}
