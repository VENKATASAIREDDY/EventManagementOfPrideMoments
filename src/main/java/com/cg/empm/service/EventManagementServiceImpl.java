package com.cg.empm.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cg.empm.dao.EventDAOIOStreamImpl;
import com.cg.empm.dao.IEventDAO;
import com.cg.empm.exception.EventManagementException;
import com.cg.empm.model.Event;

public class EventManagementServiceImpl implements IEventManagementService {
	
	private IEventDAO eventDao;
	
	public IEventDAO getDAO() {
		return eventDao;
	}
	
	public EventManagementServiceImpl() throws EventManagementException{
	
		eventDao=new EventDAOIOStreamImpl();
	}
	
	public boolean isValidId(String id) {
		Pattern idPattern=Pattern.compile("[A-Z]\\d{3}");
		Matcher idMatcher=idPattern.matcher(id);
		
		return idMatcher.matches();
	}
	
	public boolean isValidTitle(String title){
		Pattern titlePattern = Pattern.compile("[A-Za-z]\\w{3,19}");
		Matcher titleMatcher = titlePattern.matcher(title);
		
		return titleMatcher.matches();
	}
	
	public boolean isValidLocation(String location){
		Pattern locationPattern = Pattern.compile("[A-Za-z]\\w{3,19}");
		Matcher locationMatcher = locationPattern.matcher(location);
		
		return locationMatcher.matches();
	}
	
	public boolean isValidCost(double cost){
		return cost>=5000 && cost<=500000;
	}
	
	public boolean isValidScheduledDate(LocalDate publishDate) {
		LocalDate today = LocalDate.now();
		return today.isBefore(publishDate);
	}
	
	public boolean isValidEvent(Event event) throws EventManagementException{
		boolean isValid=false;
		List<String> errMsgs = new ArrayList<>();
		if(!isValidId(event.getId())) {
			errMsgs.add("Id should start with a capital alphabet followed by 3 digits");
		}
		if(!isValidTitle(event.getTitle())) {
			errMsgs.add("Title must Alphabeticals and must be in between 4 to 20 chars in length");
		}
		if(!isValidLocation(event.getLocation())) {
			errMsgs.add("Location must be Alphabetical and must be in between 4 to 20 chars in length");
		}
		if(!isValidScheduledDate(event.getScheduledDate())) {
			errMsgs.add("Scheduled Date should not be a past date");
		}
		if(!isValidCost(event.getCost())) {
			errMsgs.add("Price must be between INR.5000 and INR.500000");
		}
		if(errMsgs.isEmpty()) {
			isValid=true;
		}else {
			throw new EventManagementException(errMsgs.toString());
			
		}
		return isValid;
	}
	@Override
	public String add(Event event) throws EventManagementException {
		String id=null;
		if(event!=null && isValidEvent(event)){
			id=eventDao.add(event);
		}
		return id;
	}
	@Override
	public boolean delete(String id) throws EventManagementException {
		boolean isDone=false;
		if(id!=null && isValidId(id) && eventDao.contains(id)){
			eventDao.delete(id);
			isDone = true;
		} else{
			throw new EventManagementException("Id Doesnt Exist in Event");
		}
		return isDone;
	}

	@Override
	public List<Event> getParticularLocation(String location) throws EventManagementException {
		return eventDao.getParticularLocation(location);
	}
	@Override
	public List<Event> getAll() throws EventManagementException {
		return eventDao.getAll();
	}
	
	@Override
	public List<Event> getAscendibgOrderDateScheduled() throws EventManagementException {
		return eventDao.getAscendibgOrderDateScheduled();
	}

	@Override
	public List<Event> getAlphabeticalOrderLocation() throws EventManagementException {
		return eventDao.getAlphabeticalOrderLocation();
	}

	@Override
	public List<Event> getParticularDateScheduled(String dateScheduled) throws EventManagementException {
		return eventDao.getParticularDateScheduled(dateScheduled);
	}
	
	@Override
	public void persist() throws EventManagementException {
		eventDao.persist();
	}

	

}
