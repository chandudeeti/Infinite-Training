package com.java.inventoryProject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class StockDaoImpl implements StockDAO {
	
	Connection connection;
	PreparedStatement pst;
	static List<Stock> stockList;
	static
	{
		stockList = new ArrayList<Stock>();
	}
	
	public String generateId() throws ClassNotFoundException, SQLException
	{
		connection = ConnectionHelper.getConnection();
		
		String str = "select * from stock order by stockid desc";
		pst = connection.prepareStatement(str);
		ResultSet rs = pst.executeQuery();
		rs.next();
		String strFound = rs.getString("stockid");
		String subString = strFound.substring(1);
		int tId = Integer.parseInt(subString);
		tId += 1;
		str = String.format("S%03d", tId);
		return str;
	}

	@Override
	public String addStock(Stock stock) throws ClassNotFoundException, SQLException {
		String StockId =generateId();
		
		stock.setStockId(StockId);
		String cmd = "insert into stock(stockid, ItemName, Price, QuantityAvail) values(?,?,?,?)";
		
		connection = ConnectionHelper.getConnection();
		pst = connection.prepareStatement(cmd);
		pst.setString(1, stock.getStockId());
		pst.setString(2, stock.getItemName());
		pst.setDouble(3, stock.getPrice());
		pst.setInt(4, stock.getQuantityAvail());
		pst.executeUpdate();
		return "Stock ordered successfully...";
	}

	@Override
	public Stock searchStock(String stockId) throws ClassNotFoundException, SQLException {
		
		connection = ConnectionHelper.getConnection();
		String cmd = "select * from stock where stockid=?";
		pst = connection.prepareStatement(cmd);
		pst.setString(1, stockId);
		ResultSet rs = pst.executeQuery();
		Stock stock = null;
		if(rs.next())
		{
			stock = new Stock();
			stock.setStockId((rs.getString("stockId")));
			stock.setItemName(rs.getString("itemName"));
			stock.setPrice(rs.getDouble("price"));
			stock.setQuantityAvail(rs.getInt("quantityAvail"));
			
		}
		return stock;
	}

	

	@Override
	public List<Stock> showStock() throws ClassNotFoundException, SQLException {
		Stock stock;
		connection = ConnectionHelper.getConnection();
		String cmd = "select * from stock";
		pst = connection.prepareStatement(cmd);
		ResultSet rs = pst.executeQuery();
		while(rs.next())
		{
			stock = new Stock();
			stock.setStockId((rs.getString("stockId")));
			stock.setItemName(rs.getString("itemName"));
			stock.setPrice(rs.getDouble("price"));
			stock.setQuantityAvail(rs.getInt("quantityAvail"));
			stockList.add(stock);
		}
		return stockList;
	}
	

}