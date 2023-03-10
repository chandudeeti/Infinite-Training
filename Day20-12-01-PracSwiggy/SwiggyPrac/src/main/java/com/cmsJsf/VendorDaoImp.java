package com.cmsJsf;

import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

@ManagedBean(name = "vDao")
@SessionScoped
public class VendorDaoImp implements VendorDAO{

//private String localCode;
	
//	private Integer vendorId;
//	
//	public String getLocalCode() {
//		return localCode;
//	}
//
//	public void setLocalCode(String localCode) {
//		this.localCode = localCode;
//	}
//
//	
//	public Integer getVendorId() {
//		return vendorId;
//	}
//
//	public void setVendorId(Integer vendorId) {
//		this.vendorId = vendorId;
//	}

private String localCode;
private int vid;



public int getVid() {
	return vid;
}

public void setVid(int vid) {
	this.vid = vid;
}

public String getLocalCode() {
	return localCode;
}

public void setLocalCode(String localCode) {
	this.localCode = localCode;
}

@Override
public List<String> showVendorNamesDao() {
	SessionFactory sf = SessionHelper.getConnection();
	Session session = sf.openSession();
	Criteria cr = session.createCriteria(Vendor.class);
	Projection projection = Projections.property("vendorName"); 
	cr.setProjection(projection); 
	List<String> list = cr.list();
	return list;
}

@Override
public Vendor searchByVendorNameDao(String name) {
	SessionFactory sf = SessionHelper.getConnection();
	Session session = sf.openSession();
	Criteria cr = session.createCriteria(Vendor.class);
	cr.add(Restrictions.eq("vendorName", name));
	Vendor vendor = (Vendor)cr.uniqueResult();
	return vendor;
}

public void restaurantLocaleCodeChanged(ValueChangeEvent e){
	String rname = e.getNewValue().toString();
	System.out.println(" name Vendor  " +rname);
	Vendor vendor = searchByVendorNameDao(rname);
	this.setVid(vendor.getVendorId());
	System.out.println("Vendor Id  " +this.getVid());
}

public void vendorLocaleCodeChanged(ValueChangeEvent e){
	String rname = e.getNewValue().toString();
	System.out.println(rname);
}



	@Override
	public String addVendorDao(Vendor vendor) {
		String pwd = EntryptPassword.getCode(vendor.getPassword());
		vendor.setPassword(pwd);
		SessionFactory sf = SessionHelper.getConnection();
		Session session =sf.openSession();
		Transaction trans = session.beginTransaction();
		session.save(vendor);
		trans.commit();
		return "Thanks.xhtml?faces-redirect=true";	
		
	}
	
	

	@Override
	public String validateMe(Vendor vendor) {
		String encr = EntryptPassword.getCode(vendor.getPassword());
		Map<String,Object> sessionMap = 
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap();		
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Vendor.class);
		cr.add(Restrictions.eq("userName", vendor.getUserName()));
		cr.add(Restrictions.eq("password", encr.trim()));
		cr.setProjection(Projections.rowCount());
		long  count =(Long)cr.uniqueResult();
		if (count==1) {
			Vendor v = searchByVendorUser(vendor.getUserName());
			sessionMap.put("vendorInfo", v);
			return "VendorDashBoard.xhtml?faces-redirect=true";			
		} else {
			sessionMap.put("error", "Invalid Credentials...");
			return "VendorLogin.xhtml?faces-redirect=true";
		}

		
	}

	@Override
	public Vendor searchByVendorUser(String userName) {
		SessionFactory sf = SessionHelper.getConnection();
		Session session =sf.openSession();
		Criteria cr = session.createCriteria(Vendor.class);
		cr.add(Restrictions.eq("userName", userName));
		Vendor vendor = (Vendor)cr.uniqueResult();
		return vendor;
		
	}
	
	
//	public Integer searchByVendorDao(String vendorName) {
//		SessionFactory sf = SessionHelper.getConnection();
//		Session session = sf.openSession();
//		Criteria cr = session.createCriteria(Vendor.class);
//		cr.add(Restrictions.eq("vendorName", vendorName));
//		Projection projection = Projections.property("vendorId"); 
//		cr.setProjection(projection); 
//		Integer rid = (Integer)cr.uniqueResult();
//		System.out.println("method " +rid);
//		return rid;
//	}
//
//	public void vendorLocaleCodeChanged(ValueChangeEvent e){
//		String rname = e.getNewValue().toString();
//		Integer rid = searchByVendorDao(rname);
//		System.out.println(rname);
//		this.vendorId = rid;
//		this.localCode=rname;
//	}
//	
//	public List<String> showVendorNames() {
//		SessionFactory sf = SessionHelper.getConnection();
//		Session session = sf.openSession();
//		Criteria cr = session.createCriteria(Vendor.class);
//		Projection projection = Projections.distinct(Projections.property("vendorName")); 
//		cr.setProjection(projection); 
//		List<String> list = cr.list();
//		return list;
//		
//	}
	
	
	
	
	
	

}
