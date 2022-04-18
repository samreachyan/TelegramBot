import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MyTelegramBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        // TODO
        System.out.println(update.getMessage().getText());
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(update.getMessage().getText());

            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        // TODO
        return "ReachTester_bot";
    }

    @Override
    public String getBotToken() {
        // TODO
        return "5376958796:AAGURvh6VJ5Iba6m8r_e5Svnw4orew3Mafg";
    }
}
