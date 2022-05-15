import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class main {
    public static void main(String[] args) {
        String USERNAME = "telecomputetest_bot";
        String TOKEN = "5338144384:AAFxS3vsd4r704nWx1gi6Fz9w-hoADGYEK0";

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
//            botsApi.registerBot(new MyTelegramBot());
            botsApi.registerBot(new HelloBot(TOKEN, USERNAME));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
