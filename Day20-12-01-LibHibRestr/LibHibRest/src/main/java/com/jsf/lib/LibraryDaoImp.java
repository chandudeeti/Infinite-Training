package com.jsf.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;


import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import org.hibernate.HibernateException;

@ManagedBean(name = "Dao")
@SessionScoped
public class LibraryDaoImp implements LibraryDAO {
	private String searchType;
	private String searchValue;
	private List<Books> selectedBooks = new ArrayList<Books>();;
	
	public List<Books> getSelectedBooks() {
		return selectedBooks;
	}

	public void setSelectedBooks(List<Books> selectedBooks) {
		this.selectedBooks = selectedBooks;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	
	public void updateSelectedBooks(Books book) {
	    if (selectedBooks.contains(book)) {
	        selectedBooks.remove(book);
	    } else {
	        selectedBooks.add(book);
	    }
	}

	@Override
	public List<Books> searchBooks() throws HibernateException {

		if (searchType == null || searchValue == null) {
			return null;
		}
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Books.class);
		if (searchType.equals("id")) {
			cr.add(Restrictions.eq("id", Integer.parseInt(searchValue)));
		} else if (searchType.equals("bookname")) {
			cr.add(Restrictions.eq("name", searchValue));
		} else if (searchType.equals("authorname")) {
			cr.add(Restrictions.eq("author", searchValue));
		} else if (searchType.equals("dept")) {
			cr.add(Restrictions.eq("dept", searchValue));
		}else if (searchType.equals("all")) {
			cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		}
		List<Books> booksList = cr.list();
		session.close();
		return booksList;
	}
	
		
//	public String issueBooks(String userName, String bookId)
//	{
//		int count = issueOrNot(userName, bookId); 
//		if(count == 0)
//		{
//		 SessionFactory sf = new Configuration().configure().buildSessionFactory();
//		    Session session = sf.openSession();
//		    Transaction tx = session.beginTransaction();
//		    Books books = new Books();
//		    if(books.getNoOfBooks()>0)
//		    {
//		    	TranBook tranBook = new TranBook();
//		    	tranBook.setUserName(userName);
//		    	tranBook.setBookId(bookId);
//		    	session.save(tranBook);
//		    	books.setNoOfBooks(books.getNoOfBooks()-1);
//		    	session.update(books);
//		    	tx.commit();
//		    	return "Book with Id" +bookId+"Issued...."; 
//		    	
//		    }else
//		    {
//		    	return "BookId " +bookId+"Is not Available....";
//		    }
//		}else
//		{
//			return "Book Id " +bookId+ " for User " +userName + " Already Issued...";
//		}
//		    
//		    	
//	}
	
	
	public String issueBooks(String userName, String bookId) {
	    int count = issueOrNot(userName, bookId); 
	    if(count == 0) {
	        SessionFactory sf = new Configuration().configure().buildSessionFactory();
	        Session session = sf.openSession();
	        Transaction tx = session.beginTransaction();
	        Books book = (Books) session.get(Books.class, Integer.parseInt(bookId));
	        if(book != null && selectedBooks.contains(book) && book.getNoOfBooks()>0) {
	            TranBook tranBook = new TranBook();
	            tranBook.setUserName(userName);
	            tranBook.setBookId(bookId);
	            session.save(tranBook);
	            book.setNoOfBooks(book.getNoOfBooks()-1);
	            session.update(book);
	            selectedBooks.remove(book);
	            tx.commit();
	            return "Book with Id " + bookId + " Issued."; 
	        } else {
	            return "BookId " + bookId + " is not available or not selected.";
	        }
	    }else {
	        return "BookId " + bookId + " is already issued.";
	    }
	}

	
	
	
	public int issueOrNot(String userName, String bookId) throws HibernateException {
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
	    Session session = sf.openSession();
	    Criteria criteria = session.createCriteria(TranBook.class);
	    criteria.add(Restrictions.eq("userName", userName));
	    criteria.add(Restrictions.eq("bookId", bookId));
	    criteria.setProjection(Projections.rowCount());
	    Long count = (Long) criteria.uniqueResult();
	    return count.intValue();
	}

	@Override
	public String authenticate(LibUsers libUsers) {
		String encr = EntryptPassword.getCode(libUsers.getPassWord());
        Map<String,Object> sessionMap = 
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap();        
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();
        Criteria cr = session.createCriteria(LibUsers.class);
        cr.add(Restrictions.eq("userName", libUsers.getUserName()));
        cr.add(Restrictions.eq("passCode", encr.trim()));
        List<LibUsers> loginList = cr.list();
        System.out.println("Records are " +loginList.size());
        if (loginList.size()==1) {
            return "Menu.xhtml?faces-redirect=true";            
        } else {
            sessionMap.put("error", "Invalid Credentials...");
            return "Login.xhtml?faces-redirect=true";
        }
    }
	
	public String validateMe(LibUsers libUsers) {
        String encr = EntryptPassword.getCode(libUsers.getPassWord());
        Map<String,Object> sessionMap = 
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap();        
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();
        Criteria cr = session.createCriteria(LibUsers.class);
        cr.add(Restrictions.eq("userName", libUsers.getUserName()));
        cr.add(Restrictions.eq("passWord", encr.trim()));
        cr.setProjection(Projections.rowCount());
        long  count =(Long)cr.uniqueResult();
        System.out.println(count);
        if (count==1) {
            return "Menu.xhtml?faces-redirect=true";            
        } else {
            sessionMap.put("error", "Invalid Credentials...");
            return "Login.xhtml?faces-redirect=true";
        }
    }
	
	 
	    public String addUser(LibUsers login) {
	    	SessionFactory sf = new AnnotationConfiguration().configure().buildSessionFactory();
	        Session session = sf.openSession();
	        String encPwd = EntryptPassword.getCode(login.getPassWord());
	        login.setPassWord(encPwd);
	        Transaction t=session.beginTransaction();
	        session.save(login);
	        t.commit();
	        System.out.println("Record Inserted...");
	        return "Login.xhtml?faces-redirect=true";
	    }
	   
	   
	    
}

