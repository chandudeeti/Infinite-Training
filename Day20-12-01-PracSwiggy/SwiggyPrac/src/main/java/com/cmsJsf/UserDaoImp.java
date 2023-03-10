package com.cmsJsf;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@ManagedBean(name = "uDao")
@SessionScoped
public class UserDaoImp implements UserDAO {

	private String userType;

	public String getUserType() {
	    return userType;
	}

	public void setUserType(String userType) {
	    this.userType = userType;
	}
	
	@Override
	public String addUser(User user) {
		String pwd = EntryptPassword.getCode(user.getPassword());
		user.setPassword(pwd);
		SessionFactory sf= SessionHelper.getConnection();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		session.save(user);
		tx.commit();
		return "Thanks.xhtml?faces-redirect=true";
	}
}
