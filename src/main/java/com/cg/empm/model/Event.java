package com.cg.empm.model;
import java.io.Serializable;
import java.time.LocalDate;

public class Event implements Serializable,Comparable<Event>{
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String title;
	private LocalDate scheduledDate;
	private String location;
	private double cost;
	public Event() {
		
	}
	
	public Event(String id, String title, LocalDate scheduledDate, String location, double cost) {
		super();
		this.id = id;
		this.title = title;
		this.scheduledDate = scheduledDate;
		this.location = location;
		this.cost = cost;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(LocalDate scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		StringBuilder output=new StringBuilder("Book Code : ");
		output.append(id);
		output.append("\tTitle : ");
		output.append(title);
		output.append("\tScheduled Date :");
		output.append(scheduledDate);
		output.append("\tLocation :");
		output.append(location);
		output.append("\tCost : ");
		output.append(cost);
		return output.toString();
	}
	@Override
	public int compareTo(Event event) {
		return this.id.compareTo(event.id);
	}
		
	@Override
	public int hashCode() {
		int hashCode =0;

		char[] chars = id.toCharArray();
		for(int i=1;i<=chars.length;i++){
			hashCode += ((int)chars[i-1])*i;
		}
		
		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		boolean flag = false;

		if (obj instanceof Event) {
			Event event = (Event)obj;
			String firstId = this.id;
			String secondId = event.id;
			flag= firstId.equals(secondId);
		}
		
		return flag;		
	}


}
