package com.example.springbot.bot.commands;

import com.example.springbot.bot.events.MessageEvent;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.springframework.context.ApplicationEventPublisher;

@Component
public class LanguageCommand implements Command {

    private final ApplicationEventPublisher eventPublisher;

    public LanguageCommand(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public boolean canHandle(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return false;
        }
        return update.getMessage().getText().equals("/language");
    }

    @Override
    public void handle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            SendMessage message = SendMessage
                    .builder()
                    .chatId(chatId)
                    .text("Пожалуйста, выберите язык")
                    .build();
            eventPublisher.publishEvent(new MessageEvent(this, message));
        }
    }

    @Override
    public String getCommand() {
        return CommandName.LANGUAGE.getName();
    }

}
