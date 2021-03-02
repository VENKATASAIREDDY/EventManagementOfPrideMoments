package com.cg.empm.service;

import java.util.Comparator;
import com.cg.empm.model.*;

public class EventAscendingDateSchedule implements Comparator<Event> {

	@Override
	public int compare(Event e1, Event e2) {
		String firstDate=e1.getScheduledDate().toString();
		String secondDate=e2.getScheduledDate().toString();
		return firstDate.compareTo(secondDate);
	}

}
