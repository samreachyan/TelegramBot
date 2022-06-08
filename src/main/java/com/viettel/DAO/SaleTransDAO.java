package com.viettel.DAO;

import com.viettel.entity.SaleTrans;
import com.viettel.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SaleTransDAO {
    private static final Logger logger = Logger.getLogger(SaleTransDAO.class.getName());
    public SaleTransDAO() {
    }

    public void saveSaleTrans(SaleTrans saleTrans) {
        logger.info("Begin save sale trans");
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(saleTrans);
            session.flush();
            session.clear();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            ex.printStackTrace();
            logger.info("save failed");
        } finally {
            session.getTransaction().commit();
            logger.info("save sale trans success");
        }
    }

    public List<SaleTrans> getALlSaleTrans() {
        logger.info("Begin get all sale trans");
        List<SaleTrans> lstSaleTrans = new ArrayList<SaleTrans>();
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();
            String sql = "FROM SaleTrans where 1=1";
            Query query = session.createQuery(sql);
            lstSaleTrans = query.list();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            ex.printStackTrace();
            logger.info("error " + ex.getMessage());
        } finally {
            session.getTransaction().commit();
        }
        return lstSaleTrans;
    }

    public List<SaleTrans> getALlSaleTransBuy() {
        logger.info("Begin get all sale trans");
        List<SaleTrans> lstSaleTrans = new ArrayList<SaleTrans>();
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();
            String sql = "FROM SaleTrans where 1=1 ORDER BY id DESC";
            Query query = session.createQuery(sql);
            lstSaleTrans = query.list();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            ex.printStackTrace();
            logger.info("error " + ex.getMessage());
        } finally {
            session.getTransaction().commit();
        }
        return lstSaleTrans;
    }
}
