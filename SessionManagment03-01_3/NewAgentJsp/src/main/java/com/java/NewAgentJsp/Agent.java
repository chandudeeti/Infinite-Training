package com.java.NewAgentJsp;

public class Agent {
	
	private int agentId;
	private String name;
	private String city;
	private Gender gender;
	private int materialStatus;
	private double premium;
	public int getAgentId() {
		return agentId;
	}
	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public int getMaterialStatus() {
		return materialStatus;
	}
	public void setMaterialStatus(int materialStatus) {
		this.materialStatus = materialStatus;
	}
	public double getPremium() {
		return premium;
	}
	public void setPremium(double premium) {
		this.premium = premium;
	}
	public Agent(int agentId, String name, String city, Gender gender, int materialStatus, double premium) {
		
		this.agentId = agentId;
		this.name = name;
		this.city = city;
		this.gender = gender;
		this.materialStatus = materialStatus;
		this.premium = premium;
	}
	public Agent() {
		
	}
	@Override
	public String toString() {
		return "Agent [agentId=" + agentId + ", name=" + name + ", city=" + city + ", gender=" + gender
				+ ", materialStatus=" + materialStatus + ", premium=" + premium + "]";
	}
	
	
	

}
