package com.example.springbot.bot.commands;

import com.example.springbot.bot.events.MessageEvent;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.springframework.context.ApplicationEventPublisher;

import java.util.ArrayList;
import java.util.List;

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
                    .replyMarkup(languageInline())
                    .build();
            eventPublisher.publishEvent(new MessageEvent(this, message));
        }
    }
    private ReplyKeyboard languageInline() {
        List<InlineKeyboardRow> rows = new ArrayList<>();
        rows.add (new InlineKeyboardRow(InlineKeyboardButton.builder()
                .text("Русский")
                .callbackData("lang_ru")
                .build()));
        rows.add (new InlineKeyboardRow(InlineKeyboardButton.builder()
                .text("English")
                .callbackData("lang_en")
                .build()));

        return InlineKeyboardMarkup
                .builder()
                .keyboard(rows)
                .build();
    }
    @Override
    public String getCommand() {
        return CommandName.LANGUAGE.getName();
    }

}
