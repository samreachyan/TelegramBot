package com.viettel.DAO;

import com.viettel.entity.TelegramUser;
import com.viettel.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TelegramUserDAO {
    private static final Logger logger = Logger.getLogger(TelegramUserDAO.class.getName());
    public TelegramUserDAO() {
    }

    public static TelegramUser findTelegramUser(String chat_id) {
        try {
            Session session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();
            String sql = "FROM TelegramUser where chatId = :chat_id";
            Query query = session.createQuery(sql);
            query.setParameter("chat_id", chat_id);
            if (query.list() != null) return (TelegramUser) query.list().get(0);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static TelegramUser saveTelegramUser(TelegramUser user) {
        logger.info("Call saving single telegram user");
        TelegramUser oldUser = findTelegramUser(user.getChatId());
        if (oldUser != null) {
            logger.info("Telegram user already existed");
            return oldUser;
        }
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(user);
            session.flush();
        } catch (Exception e) {
            logger.info("Saving telegram user failed");
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.getTransaction().commit();
        }
        return user;
    }

    public static List<TelegramUser> getAllTelegramUsers() {
        List<TelegramUser> lstUser = new ArrayList<>();
        try {
            Session session = HibernateUtils.getSessionFactory().openSession();
            String sql = "FROM TelegramUser where 1 = 1 order by createdDate desc";
            Query query = session.createQuery(sql);
            lstUser = query.list();
        } catch (Exception e) {
            logger.info("Error get all telegram user");
        }
        return lstUser;
    }

}
