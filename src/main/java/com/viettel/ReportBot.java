package com.viettel;

import com.viettel.DAO.PriceDAO;
import com.viettel.DAO.SaleTransDAO;
import com.viettel.DAO.TelegramUserDAO;
import com.viettel.config.BotConfig;
import com.viettel.config.Constant;
import com.viettel.entity.Price;
import com.viettel.entity.SaleTrans;
import com.viettel.entity.TelegramUser;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReportBot extends TelegramLongPollingBot {
    List<TelegramUser> users = TelegramUserDAO.getAllTelegramUsers();
    @Override
    public void onUpdateReceived(Update update) {
        String step = "";
        if (update.hasMessage()) {
            // find user and save
            String chat_id = update.getMessage().getChatId().toString();
            TelegramUser user = TelegramUserDAO.findTelegramUser(chat_id);
            if (user == null) {
                // start
                if (update.getMessage().hasText()) {
                    if (update.getMessage().getText().equals("ភាសា")
                            || update.getMessage().getText().equals("lang")
                            || update.getMessage().getText().equals("/lang@telecomputetest_bot")
                            || update.getMessage().getText().equals("/start")
                            || update.getMessage().getText().equals("/start@telecomputetest_bot")) {
                        selectLanguageSession(chat_id);
                        step = Constant.SELECT_LANG;
                        user = TelegramUserDAO.saveTelegramUser(new TelegramUser(chat_id, Constant.SELECT_LANG, null, new Date()));
                    } else if (step.equals(Constant.SELECT_REPORT)) {
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setText("Here is option:");
                        sendMessage.setChatId(chat_id);
                        sendMessage.setParseMode(ParseMode.MARKDOWN);

                        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                        List<List<InlineKeyboardButton>> listInlineButton = new ArrayList<>();
                        List<InlineKeyboardButton> reportSaleBtn = new ArrayList<>();
                        List<InlineKeyboardButton> reportBuyBtn = new ArrayList<>();
                        List<InlineKeyboardButton> reportPriceBtn = new ArrayList<>();
                        InlineKeyboardButton saleBtn = new InlineKeyboardButton();
                        InlineKeyboardButton buyBtn = new InlineKeyboardButton();
                        InlineKeyboardButton priceBtn = new InlineKeyboardButton();

                        saleBtn.setText(Constant.SALE_REPORT_TEXT);
                        saleBtn.setCallbackData(Constant.SALE_REPORT);

                        buyBtn.setText(Constant.BUY_REPORT_TEXT);
                        buyBtn.setCallbackData(Constant.BUY_REPORT);

                        priceBtn.setText(Constant.PRICE_TEXT);
                        priceBtn.setCallbackData(Constant.PRICE_REPORT);

                        reportSaleBtn.add(saleBtn);
                        reportBuyBtn.add(buyBtn);
                        reportPriceBtn.add(priceBtn);
                        listInlineButton.add(reportSaleBtn);
                        listInlineButton.add(reportBuyBtn);
                        listInlineButton.add(reportPriceBtn);
                        inlineKeyboardMarkup.setKeyboard(listInlineButton);
                        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
                        try {
                            execute(sendMessage);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    } else if (update.getMessage().getText().equals("/help")
                            || update.getMessage().getText().equals("/help@telecomputetest_bot")) {
                        SendMessage message = new SendMessage();
                        message.setChatId(chat_id);
                        message.setText("សូមសរសេរអក្សរ /start ដើម្បីចាប់ផ្តើមឡើងវិញ និងសរសេរអក្សរ ភាសា ដើម្បីជ្រើសរើសភាសា\n\nOur bot is currently support only command /start and write /lang to select language");
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                step = user.getStep();
                if (update.getMessage().getText().equals("ភាសា")
                        || update.getMessage().getText().equals("lang")
                        || update.getMessage().getText().equals("/lang")
                        || update.getMessage().getText().equals("/lang@telecomputetest_bot")) {
                    step = Constant.SELECT_LANG;
                    selectLanguageSession(chat_id);
                }
                else if (update.getMessage().getText().equals("/start")
                        || update.getMessage().getText().equals("/start@telecomputetest_bot")) {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText("Here is option:");
                    sendMessage.setChatId(chat_id);
                    sendMessage.setParseMode(ParseMode.MARKDOWN);

                    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                    List<List<InlineKeyboardButton>> listInlineButton = new ArrayList<>();
                    List<InlineKeyboardButton> reportSaleBtn = new ArrayList<>();
                    List<InlineKeyboardButton> reportBuyBtn = new ArrayList<>();
                    List<InlineKeyboardButton> reportPriceBtn = new ArrayList<>();
                    InlineKeyboardButton saleBtn = new InlineKeyboardButton();
                    InlineKeyboardButton buyBtn = new InlineKeyboardButton();
                    InlineKeyboardButton priceBtn = new InlineKeyboardButton();

                    saleBtn.setText(Constant.SALE_REPORT_TEXT);
                    saleBtn.setCallbackData(Constant.SALE_REPORT);

                    buyBtn.setText(Constant.BUY_REPORT_TEXT);
                    buyBtn.setCallbackData(Constant.BUY_REPORT);

                    priceBtn.setText(Constant.PRICE_TEXT);
                    priceBtn.setCallbackData(Constant.PRICE_REPORT);

                    reportSaleBtn.add(saleBtn);
                    reportBuyBtn.add(buyBtn);
                    reportPriceBtn.add(priceBtn);
                    listInlineButton.add(reportSaleBtn);
                    listInlineButton.add(reportBuyBtn);
                    listInlineButton.add(reportPriceBtn);
                    inlineKeyboardMarkup.setKeyboard(listInlineButton);
                    sendMessage.setReplyMarkup(inlineKeyboardMarkup);
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else if (update.getMessage().getText().equals("/help")
                        || update.getMessage().getText().equals("/help@telecomputetest_bot")) {
                    SendMessage message = new SendMessage();
                    message.setChatId(chat_id);
                    message.setText("សូមសរសេរអក្សរ /start ដើម្បីចាប់ផ្តើមឡើងវិញ និងសរសេរអក្សរ ភាសា ដើម្បីជ្រើសរើសភាសា\n\nOur bot is currently support only command /start and write /lang to select language");
                    try {
                        execute(message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String data = callbackQuery.getData();
            String chat_id = callbackQuery.getMessage().getChat().getId().toString();

            SendChatAction sendChatAction = new SendChatAction();
            if (data.equals(Constant.SALE_REPORT)) {
                sendChatAction.setChatId(chat_id);
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText("Generating report, please wait!");
                sendMessage.setChatId(chat_id);
                try {
                    sendChatAction.setAction(ActionType.TYPING);
                    execute(sendChatAction);
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

                // send photo from FILE path local
                SendPhoto message = new SendPhoto();
                message.setChatId(chat_id);
                String imgName = null;
                try {
                    imgName = ChartReport();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                message.setCaption("Here is Report read from Database. Generate image: " + imgName);
                message.setPhoto(new InputFile(new File(imgName)));

                try {
                    step = Constant.SELECT_REPORT;
                    execute(message); // Call method to send the message
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            else if (data.equals(Constant.BUY_REPORT)) {
                sendChatAction.setChatId(chat_id);
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText("Generating report, please wait!");
                sendMessage.setChatId(chat_id);
                try {
                    sendChatAction.setAction(ActionType.TYPING);
                    execute(sendChatAction);
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

                // send photo from FILE path local
                SendPhoto message = new SendPhoto();
                message.setChatId(chat_id);
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
                    step = Constant.SELECT_REPORT;
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            else if (data.equals(Constant.PRICE_REPORT)) {
                sendChatAction.setChatId(chat_id);
                SendMessage msg = new SendMessage();
                msg.setChatId(chat_id);
                msg.setText("Generating Report the current Price in Stock");

                try {
                    sendChatAction.setAction(ActionType.TYPING);
                    execute(sendChatAction);
                    execute(msg);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

                // send photo from FILE path local
                SendPhoto message = new SendPhoto();
                message.setChatId(chat_id);
                String imgName = null;
                try {
                    imgName = createPriceReport();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                message.setCaption("Here is Price In Stock");
                message.setPhoto(new InputFile(new File(imgName)));

                try {
                    execute(message); // Call method to send the message
                    step = Constant.SELECT_REPORT;
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (data.equals(Constant.LANG_KH)) {
                defaultKhmerLangService(chat_id);
                step = Constant.SELECT_REPORT;
            } else if (data.equals(Constant.LANG_EN)) {
                defaultEnglishService(chat_id);
                step = Constant.SELECT_REPORT;
            }

        }
    }

    public void selectLanguageSession(String chat_id) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("សូមមេត្តាជ្រើសរើសភាសា \nPlease select the language below:\n");
        sendMessage.setChatId(chat_id);
        sendMessage.setParseMode(ParseMode.MARKDOWN);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> listInlineButton = new ArrayList<>();
        List<InlineKeyboardButton> langKh = new ArrayList<>();
        List<InlineKeyboardButton> langEn = new ArrayList<>();
        InlineKeyboardButton langKhBtn = new InlineKeyboardButton();
        InlineKeyboardButton langeEnBtn = new InlineKeyboardButton();

        langKhBtn.setText(Constant.LANG_KH_TEXT);
        langKhBtn.setCallbackData(Constant.LANG_KH);

        langeEnBtn.setText(Constant.LANG_EN_TEXT);
        langeEnBtn.setCallbackData(Constant.LANG_EN);

        langKh.add(langKhBtn);
        langEn.add(langeEnBtn);
        listInlineButton.add(langKh);
        listInlineButton.add(langEn);
        inlineKeyboardMarkup.setKeyboard(listInlineButton);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup); // set keyboard on text
        try {
            execute(sendMessage);
        } catch (TelegramApiException ex) {
            ex.printStackTrace();
        }
    }

    public void defaultKhmerLangService(String chat_id) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("សូមជ្រើសរើសការបង្ហាញរបាយការណ៍៖ ");
        sendMessage.setChatId(chat_id);
        sendMessage.setParseMode(ParseMode.MARKDOWN);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> listInlineButton = new ArrayList<>();
        List<InlineKeyboardButton> reportSaleBtn = new ArrayList<>();
        List<InlineKeyboardButton> reportBuyBtn = new ArrayList<>();
        List<InlineKeyboardButton> reportPriceBtn = new ArrayList<>();
        InlineKeyboardButton saleBtn = new InlineKeyboardButton();
        InlineKeyboardButton buyBtn = new InlineKeyboardButton();
        InlineKeyboardButton priceBtn = new InlineKeyboardButton();

        saleBtn.setText(Constant.SALE_REPORT_TEXT_KH);
        saleBtn.setCallbackData(Constant.SALE_REPORT);

        buyBtn.setText(Constant.BUY_REPORT_TEXT_KH);
        buyBtn.setCallbackData(Constant.BUY_REPORT);

        priceBtn.setText(Constant.PRICE_TEXT_KH);
        priceBtn.setCallbackData(Constant.PRICE_REPORT);

        reportSaleBtn.add(saleBtn);
        reportBuyBtn.add(buyBtn);
        reportPriceBtn.add(priceBtn);
        listInlineButton.add(reportSaleBtn);
        listInlineButton.add(reportBuyBtn);
        listInlineButton.add(reportPriceBtn);
        inlineKeyboardMarkup.setKeyboard(listInlineButton);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void defaultEnglishService(String chat_id) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Here is option:");
        sendMessage.setChatId(chat_id);
        sendMessage.setParseMode(ParseMode.MARKDOWN);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> listInlineButton = new ArrayList<>();
        List<InlineKeyboardButton> reportSaleBtn = new ArrayList<>();
        List<InlineKeyboardButton> reportBuyBtn = new ArrayList<>();
        List<InlineKeyboardButton> reportPriceBtn = new ArrayList<>();
        InlineKeyboardButton saleBtn = new InlineKeyboardButton();
        InlineKeyboardButton buyBtn = new InlineKeyboardButton();
        InlineKeyboardButton priceBtn = new InlineKeyboardButton();

        saleBtn.setText(Constant.SALE_REPORT_TEXT);
        saleBtn.setCallbackData(Constant.SALE_REPORT);

        buyBtn.setText(Constant.BUY_REPORT_TEXT);
        buyBtn.setCallbackData(Constant.BUY_REPORT);

        priceBtn.setText(Constant.PRICE_TEXT);
        priceBtn.setCallbackData(Constant.PRICE_REPORT);

        reportSaleBtn.add(saleBtn);
        reportBuyBtn.add(buyBtn);
        reportPriceBtn.add(priceBtn);
        listInlineButton.add(reportSaleBtn);
        listInlineButton.add(reportBuyBtn);
        listInlineButton.add(reportPriceBtn);
        inlineKeyboardMarkup.setKeyboard(listInlineButton);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
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


    /**
     * create Image Convert from Table Price to Image
     */
    public static String createPriceReport() throws IOException {
        JFreeChart pieChart = ChartFactory.createPieChart(
                "Report Price In Stock",
                createPieDataset(),
                true, true, false);

        int width = 680;    /* Width of the image */
        int height = 500;   /* Height of the image */

        SimpleDateFormat simpleFormatter = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
        String date = simpleFormatter.format(new Date());
        String fileName = "ChartReportPrice_" + date + ".jpeg";
        File ChartReport = new File(fileName);
        ChartUtilities.saveChartAsJPEG( ChartReport , pieChart , width , height );
//        System.out.println("Image name: " + ChartReport.getAbsolutePath());
        return ChartReport.getAbsolutePath();
    }

    private static PieDataset createPieDataset( ) {
        DefaultPieDataset dataset = new DefaultPieDataset( );
        List<Price> listPrices = new PriceDAO().getAllPrice();
        for (Price item : listPrices) {
            dataset.setValue(item.getName(), item.getPrice());
        }
        return dataset;
    }

    @Override
    public String getBotUsername() {
        // TODO
        return BotConfig.BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        // TODO
        return BotConfig.BOT_TOKEN;
    }
}
