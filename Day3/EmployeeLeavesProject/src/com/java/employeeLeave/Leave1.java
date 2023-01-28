package com.java.employeeLeave;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Leave1 {
	private int empId;
	private Date LeaveStartDate; 
	private Date leaveEndDate; 
	private String LeaveReason; 
	private int noOfDays;
	private LeaveType leaveType;
	private Date leaveAppliedOn;
	
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public Date getLeaveStartDate() {
		return LeaveStartDate;
	}
	public void setLeaveStartDate(Date leaveStartDate) {
		LeaveStartDate = leaveStartDate;
	}
	public Date getLeaveEndDate() {
		return leaveEndDate;
	}
	public void setLeaveEndDate(Date leaveEndDate) {
		this.leaveEndDate = leaveEndDate;
	}
	public String getLeaveReason() {
		return LeaveReason;
	}
	public void setLeaveReason(String leaveReason) {
		LeaveReason = leaveReason;
	}
	public int getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(int l) {
		this.noOfDays = l;
	}
	public LeaveType getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
	}
	public Date getLeaveAppliedOn() {
		return leaveAppliedOn;
	}
	public void setLeaveAppliedOn(Date leaveAppliedOn) {
		this.leaveAppliedOn = leaveAppliedOn;
	}
	
	
	public Leave1(int empId, Date leaveStartDate, Date leaveEndDate, String leaveReason, int noOfDays,
			LeaveType leaveType, Date leaveAppliedOn) {
		super();
		this.empId = empId;
		this.LeaveStartDate = leaveStartDate;
		this.leaveEndDate = leaveEndDate;
		this.LeaveReason = leaveReason;
		this.noOfDays = noOfDays;
		this.leaveType = leaveType;
		this.leaveAppliedOn = leaveAppliedOn;
	
	}
	public Leave1() 
	{
		
	}
	@Override
	public String toString() {
		SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
		
		return "Employee [empId=" + empId + ", LeaveStartDate=" + sd.format(LeaveStartDate) + ", leaveEndDate=" + sd.format(leaveEndDate)
				+ ", LeaveReason=" + LeaveReason + ", noOfDays=" + noOfDays + ", leaveType=" + leaveType
				+ ", leaveAppliedOn=" + leaveAppliedOn + "]";
	}
	
	
	
	
	
	
	

}