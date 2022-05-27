import com.viettel.DAO.SaleTransDAO;
import com.viettel.entity.SaleTrans;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;

public class main {
    public static void main(String[] args) {
        String token =  "5376958796:AAGURvh6VJ5Iba6m8r_e5Svnw4orew3Mafg";
        String user = "ReachTester_bot";

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new MyTelegramBot());
//            botsApi.registerBot(new HelloAbility(token, user));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
