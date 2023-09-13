package ru.bot.valera.bot.service.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.invoices.SendInvoice;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.bot.valera.bot.model.Command;
import ru.bot.valera.bot.model.Content;
import ru.bot.valera.bot.to.UpdateTO;

import java.util.List;
import java.util.Set;

import static ru.bot.valera.bot.model.Command.PAYMENT;

@Component
public class Payment extends AbstractContent {
    @Override
    public Content handle(UpdateTO updateTO) {
        SendInvoice sendInvoice = new SendInvoice(updateTO.getChatId().toString(), "title",
                "desc", "my_payload", "providerToken", "my_start_param",
                "USD", List.of(new LabeledPrice("label", 200)));
        sendInvoice.setNeedPhoneNumber(true);
        sendInvoice.setNeedShippingAddress(true);
        sendInvoice.setIsFlexible(true);

        InlineKeyboardButton button = new InlineKeyboardButton("just pay" );
        button.setPay(true);
        InlineKeyboardButton button1 = new InlineKeyboardButton("google it" );
        button1.setUrl("www.google.com" );

        sendInvoice.setReplyMarkup(new InlineKeyboardMarkup(List.of(
                List.of(
                        button, button1
                )
        )));

        return new Content(sendInvoice, PAYMENT);
    }

    @Override
    public Set<Command> getMessageType() {
        return Set.of(PAYMENT);
    }
}
