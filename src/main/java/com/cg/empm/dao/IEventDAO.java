package com.cg.empm.dao;

import java.util.List;

import com.cg.empm.exception.EventManagementException;
import com.cg.empm.model.Event;

public interface IEventDAO {
	String add(Event event) throws EventManagementException;
	boolean delete(String id)throws EventManagementException;
	Event get(String id) throws EventManagementException;
	boolean contains(String id) throws EventManagementException;
	List<Event> getAll() throws EventManagementException;
	List<Event> getAscendibgOrderDateScheduled() throws EventManagementException;
	List<Event> getAlphabeticalOrderLocation() throws EventManagementException;
	List<Event> getParticularLocation(String location) throws EventManagementException;
	List<Event> getParticularDateScheduled(String dateScheduled) throws EventManagementException;
	void persist() throws EventManagementException;
}
