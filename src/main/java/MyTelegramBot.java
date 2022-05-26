import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class MyTelegramBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = new SendMessage();

        // TODO
        System.out.println(update.getMessage().getText());
//        if (update.hasMessage() && update.getMessage().hasText()) {
//            SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
//            message.setChatId(update.getMessage().getChatId().toString());
//            message.setText(update.getMessage().getText());
//
//            try {
//                execute(message); // Call method to send the message
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
//        }

        if(update.getMessage().getText().equals("/start")
                || update.getMessage().getText().equals("Back")
                || update.getMessage().getText().equals("/start@telecomputetest_bot")){

            sendMessage.setChatId(update.getMessage().getChatId().toString());
            sendMessage.setText("Here is your options below:");
            // Create ReplyKeyboardMarkup object
            ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
            // Create the keyboard (list of keyboard rows)
            List<KeyboardRow> keyboard = new ArrayList<>();
            // Create a keyboard row
            KeyboardRow row = new KeyboardRow();
            // Set each button, you can also use KeyboardButton objects if you need something else than text
            row.add("Report Sale");
            row.add("Report Buy");
            // Add the first row to the keyboard
            keyboard.add(row);
            // Create another keyboard row
            row = new KeyboardRow();
            // Set each button for the second line
            row.add("Facebook");
            row.add("Github");
            row.add("Website");
            // Add the second row to the keyboard
            keyboard.add(row);

            // Set the keyboard to the markup
            keyboardMarkup.setKeyboard(keyboard);
            // Add it to the message
            sendMessage.setReplyMarkup(keyboardMarkup);
            try {
                execute(sendMessage); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        else if (update.getMessage().getText().equals("Report Sale")) {
            SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(update.getMessage().getText() + "Belalalala");

            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        else if (update.getMessage().getText().equals("/hide")) {
            sendMessage.setChatId(update.getMessage().getChatId().toString());
            sendMessage.setText("Keyboard hidden");
            ReplyKeyboardRemove keyboardMarkup = new ReplyKeyboardRemove();
            sendMessage.setReplyMarkup(keyboardMarkup);
            try {
                execute(sendMessage); // Call method to send the photo
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        // TODO
        return "telecomputetest_bot";
    }

    @Override
    public String getBotToken() {
        // TODO
        return "5338144384:AAEl190SuQqDGo_EcTsuQinQ2NkW5aj-9XQ";
    }
}
