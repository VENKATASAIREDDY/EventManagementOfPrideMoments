package com.cg.empm.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.cg.empm.exception.EventManagementException;
import com.cg.empm.model.Event;
import com.cg.empm.service.EventAscendingDateSchedule;
import com.cg.empm.service.EventAscendingLocation;

public class EventDAOIOStreamImpl implements IEventDAO {
	public static final String DATA_STORE_FILE_NAME = "eventmanagement.dat";

	private Map<String, Event> events;

	public EventDAOIOStreamImpl() throws EventManagementException {
		File file = new File(DATA_STORE_FILE_NAME);

		if (!file.exists()) {
			events = new TreeMap<>();
		} else {
			try (ObjectInputStream fin = new ObjectInputStream(
					new FileInputStream(DATA_STORE_FILE_NAME))) {

				Object obj = fin.readObject();

				if (obj instanceof Map) {
					events = (Map<String, Event>) obj;
				} else {
					throw new EventManagementException("Not a valid DataStore");
				}
			} catch (IOException | ClassNotFoundException exp) {
				throw new EventManagementException("Loading Data Failed");
			}
		}
	}

	@Override
	public String add(Event event) throws EventManagementException {
		String id=null;
		if (event != null) {
			id = event.getId();
			events.put(id, event);
		}
		return id;
	}

	@Override
	public boolean delete(String id) throws EventManagementException {
		boolean isDone = false;
		if (id != null && events.containsKey(id)) {
			events.remove(id);
			isDone = true;
		}
		return isDone;
	}

	@Override
	public Event get(String id) throws EventManagementException {
		return events.get(id);
	}

	@Override
	public List<Event> getAll() throws EventManagementException {
		return new ArrayList<Event>(events.values());
	}
	
	@Override
	public List<Event> getAscendibgOrderDateScheduled() throws EventManagementException {
		List<Event> dateScheduledSort=new ArrayList<Event>(events.values());
		Collections.sort(dateScheduledSort, new EventAscendingDateSchedule());
		return dateScheduledSort;
	}

	@Override
	public List<Event> getAlphabeticalOrderLocation() throws EventManagementException {
		List<Event> locationSort=new ArrayList<Event>(events.values());
		Collections.sort(locationSort, new EventAscendingLocation());
		return locationSort;
		
	}

	@Override
	public List<Event> getParticularLocation(String location) throws EventManagementException {
		List<Event> locationList=new ArrayList<Event>(events.values());
		List<Event> finalList=new ArrayList<Event>();
		for(Event e:locationList) {
			if(e.getLocation().equalsIgnoreCase(location)) {
				finalList.add(e);
			}
		}
		return finalList;
	}

	@Override
	public List<Event> getParticularDateScheduled(String dateScheduled) throws EventManagementException {
		List<Event> dateScheduledList=new ArrayList<Event>(events.values());
		List<Event> finalList=new ArrayList<Event>();
		for(Event e:dateScheduledList) {
			if(e.getScheduledDate().toString().equalsIgnoreCase(dateScheduled)) {
				finalList.add(e);
			}
		}
		return finalList;
	}
	@Override
	public void persist() throws EventManagementException {
		try (ObjectOutputStream fout = new ObjectOutputStream(
				new FileOutputStream(DATA_STORE_FILE_NAME))) {
			fout.writeObject(events);
		} catch (IOException exp) {
			throw new EventManagementException("Saving Data Failed");
		}
	}

	@Override
	public boolean contains(String id) throws EventManagementException {
		return events.containsKey(id);
	}


	
}