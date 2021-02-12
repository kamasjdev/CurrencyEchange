package repository;

import java.util.ArrayList;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import abstracts.CurrencyExchangeRateRepository;
import entity.CurrencyExchange;
import entity.CurrencyRate;

public class CurrencyRepository implements CurrencyExchangeRateRepository {
	SessionFactory sessionFactory;
    
	public CurrencyRepository() {
		// TODO Auto-generated constructor stub
//		sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();//configure("hibernate.cfg.xml").build();  
	}
	
	@Override
	public Long addCurrency(CurrencyExchange currencyExchanged) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        System.out.println("Creating currency trade");
        try {
        	session.beginTransaction();
			session.save(currencyExchanged);
			session.getTransaction().commit();
        }
        catch(Exception ex) {
        	System.out.println(ex.getMessage());
        }
        finally {
        	session.close();
        }
		return currencyExchanged.getId();
	}

	@Override
	public Long addRate(CurrencyRate rate) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        System.out.println("Creating currency trade");
        try {
        	session.beginTransaction();
			session.save(rate);
			session.getTransaction().commit();
        }
        catch(Exception ex) {
        	System.out.println(ex.getMessage());
        }
        finally {
        	session.close();
        }
		return rate.getId();
	}

	@Override
	public CurrencyRate getRateById(Long id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        System.out.println("Getting object by id = " + id);
        CurrencyRate currencyRate = null;
        try {
        	currencyRate =  (CurrencyRate) session.get(CurrencyRate.class, id);
        } 
        catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        finally {
        	session.close();
        }   
		return currencyRate;
	}
	
	@Override
	public CurrencyRate getRateByDateAndCode(Date date, String code) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        System.out.println("Getting object by date and currency code = " + date + " " + code);
        CurrencyRate currencyRate = null;
        try {
        	//currencyRate =  (CurrencyRate) session.get(CurrencyRate.class, id);
        	Query query= session.
        	        createQuery("SELECT c FROM CurrencyRate c where currency_date='" + date + "' AND currency_code='" + code + "'");
        	currencyRate = (CurrencyRate) query.uniqueResult();
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        finally {
        	session.close();
        }   
		return currencyRate;
	}

	@Override
	public CurrencyExchange getCurrencyById(Long id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        System.out.println("Getting object by id = " + id);
        CurrencyExchange currencyExchanged = null;
        try {
        	currencyExchanged =  (CurrencyExchange) session.get(CurrencyExchange.class, id);
        } 
        catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        finally {
        	session.close();
        }   
		return currencyExchanged;
	}

	@Override
	public void updateCurrencyExchange(CurrencyExchange currencyExchanged) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        System.out.println("Updating object id = " + currencyExchanged.getId());  
		try {
        	session.beginTransaction();
        	session.update(currencyExchanged);
        	session.getTransaction().commit();
        } 
        catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        finally {  
        	session.close();
        }
	}

	@Override
	public void updateCurrencyRate(CurrencyRate currencyRate) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        System.out.println("Updating object id = " + currencyRate.getId());  
		try {
        	session.beginTransaction();
        	session.update(currencyRate);
        	session.getTransaction().commit();
        } 
        catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        finally {  
        	session.close();
        }
	}

	@Override
	public void deleteCurrencyExchange(CurrencyExchange currencyExchanged) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        System.out.println("Deleting object id = " + currencyExchanged.getId());  
		try {
        	session.beginTransaction();
        	session.delete(currencyExchanged);
            session.getTransaction().commit();
        } 
        catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        finally {
        	session.close();
        }
	}

	@Override
	public void deleteCurrencyRate(CurrencyRate rate) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        System.out.println("Deleting object id = " + rate.getId());  
		try {
        	session.beginTransaction();
        	session.delete(rate);
            session.getTransaction().commit();
        } 
        catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        finally {
        	session.close();
        }
	}

	@Override
	public List<CurrencyExchange> getAllCurrencies() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        System.out.println("Getting all of objects!");  
        List<CurrencyExchange> currenciesExchanged = new ArrayList<CurrencyExchange>();
        try {
        	currenciesExchanged = loadAllData(CurrencyExchange.class, session);
        } 
        catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        finally {
        	session.close();
        } 
		return currenciesExchanged;
	}

	@Override
	public List<CurrencyRate> getAllRates() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        System.out.println("Getting all of objects!");  
        List<CurrencyRate> currenciesRate = new ArrayList<CurrencyRate>();
        try {
        	currenciesRate = loadAllData(CurrencyRate.class, session);
        } 
        catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        finally {
        	session.close();
        } 
		return currenciesRate;
	}
	
	private static <T> List<T> loadAllData(Class<T> type, Session session) {
	    CriteriaBuilder builder = session.getCriteriaBuilder();
	    CriteriaQuery<T> criteria = builder.createQuery(type);
	    criteria.from(type);
	    List<T> data = session.createQuery(criteria).getResultList();
	    return data;
	}
}