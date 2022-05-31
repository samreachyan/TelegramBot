package com.viettel.DAO;

import com.viettel.entity.Price;
import com.viettel.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PriceDAO {
    private static final Logger logger = Logger.getLogger(PriceDAO.class.getName());

    public List<Price> getAllPrice() {
        logger.info("begin get all price");
        List<Price> lstPrices = new ArrayList<Price>();
        try {
            Session session = HibernateUtils.getSessionFactory().openSession();
            String sql = "FROM Price where 1=1";
            Query query = session.createQuery(sql);
            if (query.list() != null) lstPrices = query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.info("Error " + ex.getMessage());
        }
        return lstPrices;
    }
}
