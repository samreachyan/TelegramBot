import com.viettel.DAO.SaleTransDAO;
import com.viettel.entity.SaleTrans;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyTelegramBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = new SendMessage();

        // TODO
        System.out.println(update.getMessage().getText());

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
            SendChatAction sendChatAction = new SendChatAction();
            sendChatAction.setChatId(update.getMessage().getChatId().toString());
            try {
                sendChatAction.setAction(ActionType.TYPING);
                execute(sendChatAction);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

            // send photo from FILE path local
            SendPhoto message = new SendPhoto();
            message.setChatId(update.getMessage().getChatId().toString());
            String imgName = null;
            try {
                imgName = ChartReport();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            message.setCaption("Here is Report read from Database. Generate image: " + imgName);
            message.setPhoto(new InputFile(new File(imgName)));

            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        }
        else if (update.getMessage().getText().equals("Report Buy")) {
            SendChatAction sendChatAction = new SendChatAction();
            sendChatAction.setChatId(update.getMessage().getChatId().toString());
            try {
                sendChatAction.setAction(ActionType.TYPING);
                execute(sendChatAction);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            SendMessage msg = new SendMessage();
            msg.setChatId(update.getMessage().getChatId().toString());
            msg.setText("Thank for waiting");

            try {
                execute(msg);
                execute(sendChatAction);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

            // send photo
            // send photo from FILE path local
            SendPhoto message = new SendPhoto();
            message.setChatId(update.getMessage().getChatId().toString());
            String imgName = null;
            try {
                imgName = ChartReportBuy();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            message.setCaption("Here is Buy Report");
            message.setPhoto(new InputFile(new File(imgName)));

            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        else if (update.getMessage().getText().equals("Facebook")) {
            SendChatAction sendChatAction = new SendChatAction();
            sendChatAction.setChatId(update.getMessage().getChatId().toString());
            try {
                sendChatAction.setAction(ActionType.TYPING);
                execute(sendChatAction);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            SendMessage msg = new SendMessage();
            msg.setChatId(update.getMessage().getChatId().toString());
            msg.setText("Thank for waiting! Clicked Facebook");

            try {
                execute(msg);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        else if (update.getMessage().getText().equals("Github")) {
            SendChatAction sendChatAction = new SendChatAction();
            sendChatAction.setChatId(update.getMessage().getChatId().toString());
            try {
                sendChatAction.setAction(ActionType.TYPING);
                execute(sendChatAction);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            SendMessage msg = new SendMessage();
            msg.setChatId(update.getMessage().getChatId().toString());
            msg.setText("Thank for waiting! Clicked Github");

            try {
                execute(msg);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }


    public static String ChartReport() throws IOException {
        JFreeChart barChart = ChartFactory.createBarChart(
                "Report Sale Chart",
                "Sale date", "Amount without tax",
                createDataset(), PlotOrientation.VERTICAL.VERTICAL,
                true, true, false);

        int width = 640;    /* Width of the image */
        int height = 480;   /* Height of the image */

        SimpleDateFormat simpleFormatter = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
        String date = simpleFormatter.format(new Date());
        String fileName = "ChartReport_" + date + ".jpeg";
        File ChartReport = new File(fileName);
        ChartUtilities.saveChartAsJPEG( ChartReport , barChart , width , height );
//        System.out.println("Image name: " + ChartReport.getAbsolutePath());
        return ChartReport.getAbsolutePath();
    }

    private static DefaultCategoryDataset createDataset() {
        try{
            DefaultCategoryDataset dataset = new DefaultCategoryDataset( );

            SaleTransDAO saleTransDAO = new SaleTransDAO();
            List<SaleTrans> saleTransList = saleTransDAO.getALlSaleTrans();
            for(SaleTrans item: saleTransList){
                dataset.addValue(item.getAmount(), "date", item.getSaleDate());
            }

            return dataset;
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static String ChartReportBuy() throws IOException {
        JFreeChart barChart = ChartFactory.createBarChart(
                "Report Buy Chart",
                "Sale date", "Amount without tax",
                createDatasetBuy(), PlotOrientation.VERTICAL.VERTICAL,
                true, true, false);

        int width = 680;    /* Width of the image */
        int height = 500;   /* Height of the image */

        SimpleDateFormat simpleFormatter = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
        String date = simpleFormatter.format(new Date());
        String fileName = "ChartReportBuy_" + date + ".jpeg";
        File ChartReport = new File(fileName);
        ChartUtilities.saveChartAsJPEG( ChartReport , barChart , width , height );
//        System.out.println("Image name: " + ChartReport.getAbsolutePath());
        return ChartReport.getAbsolutePath();
    }
    private static DefaultCategoryDataset createDatasetBuy() {
        try{
            DefaultCategoryDataset dataset = new DefaultCategoryDataset( );

            SaleTransDAO saleTransDAO = new SaleTransDAO();
            List<SaleTrans> saleTransList = saleTransDAO.getALlSaleTransBuy();
            for(SaleTrans item: saleTransList){
                dataset.addValue(item.getAmount(), "date", item.getSaleDate());
            }

            return dataset;
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
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
