package com.cmsJsf;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;



import java.util.logging.*;


@ManagedBean(name="oDao")
@SessionScoped
public class OrdersDAOImpl implements OrderDetailsDAO {
	
	private ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
	
	private static final Logger logger = Logger.getLogger(OrdersDAOImpl.class.getName());
	
	

	public ScheduledExecutorService getService() {
		return service;
	}

	public void setService(ScheduledExecutorService service) {
		this.service = service;
	}

	@Override
	public String placeOrder(int custId, int venId, int menuId, String walSource, int qty, String ordComments) {
		System.out.println("Order Customer Id  " +custId);
		System.out.println("Order Vendor Id  " +venId);
		System.out.println("Order Menu Id  " +menuId);
		System.out.println("Order Wallet Source  " +walSource);
		System.out.println("Order Quantity "+qty);
		System.out.println("Order Comments " +ordComments);
		Menu menu = new MenuDaoImp().searchByMenuId(menuId);
		//System.out.println("Price  " +menu.getMenuPrice());
		double billAmount = menu.getMenuPrice() * qty;
		System.out.println("bill Amount  " +billAmount);
		Wallet wallet = new WalletDaoImp().showCustomerBalanceDao(custId, walSource);
		System.out.println(wallet);
		System.out.println(wallet.getWalletAmount());
		double walletAmount = wallet.getWalletAmount();
		if (walletAmount - billAmount >= 0) {
			SessionFactory sf = SessionHelper.getConnection();
			Session session = sf.openSession();
			Orders orders = new Orders();
			orders.setCustId(custId);
			orders.setMenuId(menuId);
			orders.setVenId(venId);
			orders.setWalSource(walSource);
			orders.setOrderStatus(OrderStatus.PENDING);
			orders.setOrderQuantity(qty);
			orders.setOrderComments(ordComments);
			orders.setOrderDate(new Date());
			orders.setOrderBillAmount(billAmount);
			wallet.setWalletAmount(walletAmount-billAmount);
			Transaction t = session.beginTransaction();
			session.save(orders);
			session.update(wallet);
			t.commit();
			Boolean b = false;
			service.schedule(new Runnable() {
				
				@Override
				public void run() {
					autoCancelOrder(orders.getOrderId());
					System.out.println("Hello");
				}
			}, 1, TimeUnit.MINUTES);
			return "Success.xhtml?faces-redirect=true";
		} else {
			return "Insufficient funds...";
		}

		//		System.out.println(custId + " " +venId + " " +menuId + " " +walSource + ""+qty+ "" +ordComments);
	}

	public Orders searchOrderByOrderId(int oId)
	{
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Orders.class);
		cr.add(Restrictions.eqOrIsNull("orderId", oId));
		Orders orders = (Orders) cr.uniqueResult();
		return orders;
	}

	public List<Orders> showOrdersByVendor(int vid)
	{
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Criteria cr = session.createCriteria(Orders.class);
		cr.add(Restrictions.eq("venId", vid));
		List<Orders> list = cr.list();
		return list;

	}

	public String approve(int oid)
	{
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		Orders orders = searchOrderByOrderId(oid);
		if(orders.getOrderStatus().equals(OrderStatus.PENDING))
		{
			orders.setOrderStatus(OrderStatus.ACCEPTED);
		}
		session.update(orders);
		tx.commit();
		return "Accepted.xhtml?faces-redirect=true";

	}

	public String rejectOrder(int oId) {
		SessionFactory sf = SessionHelper.getConnection();
		Session session = sf.openSession();
		Orders order = searchOrderByOrderId(oId);
		Wallet wallet = new WalletDaoImp().showCustomerBalanceDao(order.getCustId(), order.getWalSource());
		if(order.getOrderStatus().equals(OrderStatus.PENDING) || 
				order.getOrderStatus().equals(OrderStatus.ACCEPTED)) 
		{
			wallet.setWalletAmount(wallet.getWalletAmount() + order.getOrderBillAmount());
			order.setOrderStatus(OrderStatus.DENIED);
		}
		Transaction t = session.beginTransaction();
		session.update(order);
		session.update(wallet);
		t.commit();
		return "Rejected.xhtml?faces-redirect=true";
	}
	
	public void autoCancelOrder(int oId) {
		SessionFactory sf = SessionHelper.getConnection();
        Session session = sf.openSession();
        Transaction t = session.beginTransaction();
		 try {
	            logger.info("Order with id " + oId + " has been cancelled");
	        } catch (Exception e) {
	            logger.severe("Error cancelling order with id " + oId + " : " + e);
	        }

	    Orders order = searchOrderByOrderId(oId);
	    long orderedTime = order.getOrderDate().getTime();
	    System.out.println(orderedTime);
	    long currentTime = new Date().getTime();
	    long elapsedTime = currentTime - orderedTime;
	    System.out.println(elapsedTime);
	    long fiveMinutesInMilliseconds = 1 * 60 * 1000;
	    System.out.println(fiveMinutesInMilliseconds);
	    if (elapsedTime > fiveMinutesInMilliseconds && order.getOrderStatus() == OrderStatus.PENDING) {
	        order.setOrderStatus(OrderStatus.DENIED);
	        
	        session.update(order);
	        t.commit();
	    }
	}


	//	public List<Orders> showOrders()
	//	{
	//		SessionFactory sf = SessionHelper.getConnection();
	//		Session session = sf.openSession();
	//		Criteria cr = session.createCriteria(Orders.class);
	//		List<Orders> list = cr.list();
	//		return list;
	//	}


}
