package com.cg.empm.ui;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import com.cg.empm.exception.EventManagementException;
import com.cg.empm.model.Event;
import com.cg.empm.model.EventManagementAppMenu;
import com.cg.empm.service.EventManagementServiceImpl;
import com.cg.empm.service.IEventManagementService;

public class EventManagementUI {

	private static IEventManagementService eventManagementService;
	private static Scanner scan;
	private static DateTimeFormatter dtFormater;

	public static void main(String[] args) {

		try {
			eventManagementService = new EventManagementServiceImpl();
		} catch (EventManagementException e) {
			e.printStackTrace();
		}


		scan = new Scanner(System.in);
		dtFormater = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		EventManagementAppMenu menu = null;

		while (menu != EventManagementAppMenu.QUIT) {

			System.out.println("Choice\tQuery");
			System.out.println("===========================");
			for (EventManagementAppMenu m : EventManagementAppMenu.values()) {
				System.out.println(m.ordinal() + "\t" + m.name());
			}
			System.out.print("Choice: ");
			int choice = -1;
			if (scan.hasNextInt())
				choice = scan.nextInt();
			else {
				scan.next();
				System.out.println("Please choose a choice displayed");
				continue;
			}

			if (choice < 0 || choice >= EventManagementAppMenu.values().length) {
				System.out.println("Invalid Choice");
			} else {
				menu = EventManagementAppMenu.values()[choice];
				switch (menu) {
				case ADD:
					doAdd();
					break;
				case REMOVE:
					doRemove();
					break;
				case LIST:
					doList();
					break;
				case LISTOFASCENDINGORDEROFDATESHEDULED:
					doAscendingOrderOfDateScheduled();
					break;
				case LISTOFALPHABETICALORDEROFLOCATION:
					doAlphabeticalOrderOfLocation();
					break;
				case LISTOFPARTICULARLOCATION:
					doSearchLocation();
					break;
				case LISTOFPARTICULARDATESHEDULED:
					doSearchDateSchedule();
					break;
				case QUIT:
					System.out.println("Thanks");
					break;
				}
			}

		}

		scan.close();
		try {
			eventManagementService.persist();
		} catch (EventManagementException e) {
			e.printStackTrace();
		}

	}

	private static void doAdd() {
		try {
			Event event = new Event();
			System.out.print("Id: ");
			event.setId(scan.next());
			System.out.print("Title: ");
			event.setTitle(scan.next());
			System.out.print("DateScheduled(dd-MM-yyyy): ");
			String pubDtStr = scan.next();
			
			try {
				event.setScheduledDate(LocalDate.parse(pubDtStr, dtFormater));
			} catch (DateTimeException exp) {
				throw new EventManagementException(
						"Date must be in the format day(dd)-month(MM)-year(yyyy)");
			}
			System.out.print("Location : ");
			event.setLocation(scan.next());
			System.out.print("Cost: ");
			if (scan.hasNextDouble())
				event.setCost(scan.nextDouble());
			else {
				scan.next();
				throw new EventManagementException("Price is a number");
			}

			String id = eventManagementService.add(event);
			System.out.println("Event is Added with code: " + id);
		} catch (EventManagementException exp) {
			System.out.println(exp.getMessage());
		}
	}

	private static void doList() {
		List<Event> events;
		try {
			events = eventManagementService.getAll();
			if (events.isEmpty()) {
				System.out.println("No Events To display");
			} else {
				for (Event e : events)
					System.out.println(e);
			}
		} catch (EventManagementException exp) {
			System.out.println(exp.getMessage());
		}
	}

	private static void doSearchLocation() {
		System.out.print("Location : ");
		String location = scan.next();

		List<Event> events;
		try {
			events = eventManagementService.getParticularLocation(location);
			if (events.isEmpty()) {
				System.out.println("No Events To display at this location");
			} else {
				for (Event e : events)
					System.out.println(e);
			}
		} catch (EventManagementException exp) {
			System.out.println(exp.getMessage());
		}
	}
	private static void doSearchDateSchedule() {
		System.out.print("DateScheduled(yyyy-MM-dd): ");
		String dateScheduledStr = scan.next();
		
		List<Event> events;		
		try {

			events = eventManagementService.getParticularDateScheduled(dateScheduledStr);
			if (events.isEmpty()) {
				System.out.println("No Events on that Date");
			} else {
				for (Event e : events)
					System.out.println(e);
			}
		} catch (EventManagementException exp) {
			System.out.println(exp.getMessage());
		}
	}
	private static void doAlphabeticalOrderOfLocation() {
				
		List<Event> events;		
		try {

			events = eventManagementService.getAlphabeticalOrderLocation();
			if (events.isEmpty()) {
				System.out.println("No Events to display");
			} else {
				for (Event e : events)
					System.out.println(e);
			}
		} catch (EventManagementException exp) {
			System.out.println(exp.getMessage());
		}
	}
	private static void doAscendingOrderOfDateScheduled() {
		
		List<Event> events;		
		try {

			events = eventManagementService.getAscendibgOrderDateScheduled();
			if (events.isEmpty()) {
				System.out.println("No Events to display");
			} else {
				for (Event e : events)
					System.out.println(e);
			}
		} catch (EventManagementException exp) {
			System.out.println(exp.getMessage());
		}
	}
	private static void doRemove() {
		System.out.print("Id: ");
		String id = scan.next();
		try {
			boolean isDone = eventManagementService.delete(id);
			if (isDone) {
				System.out.println("Event is Removed.");
			} else {
				System.out.println("No Such Event");
			}
		} catch (EventManagementException exp) {
			System.out.println(exp.getMessage());
		}
	}
}