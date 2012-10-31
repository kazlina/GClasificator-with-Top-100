package models;
import java.sql.Time;
import java.util.Date;
import java.util.Calendar;

public class TimeClass {
	public static void printCurrentTime () {
		Date currentTime = new Date();
		System.out.print(currentTime);
	}
	public static void printlnCurrentTime () {
		Date currentTime = new Date();
		System.out.println(currentTime);
	}
	public static void printlnReadyCurrentTime () {
		System.out.print("Current time is ");
		TimeClass.printlnCurrentTime();
		System.out.println("_____________________________________________");
	}
	public static Calendar getDuration (Calendar firstCalendar, Calendar secondCalendar) {
		int hours, minutes, seconds; 
		hours = secondCalendar.get(Calendar.HOUR_OF_DAY) - firstCalendar.get(Calendar.HOUR_OF_DAY);
		minutes = secondCalendar.get(Calendar.MINUTE) - firstCalendar.get(Calendar.MINUTE);
		seconds = secondCalendar.get(Calendar.SECOND) - firstCalendar.get(Calendar.SECOND);
		Calendar newCalendar = Calendar.getInstance();
		newCalendar.set(0, 0, 0, hours, minutes, seconds); //newCalendar.get(Calendar.HOUR_OF_DAY) newCalendar.get(Calendar.MINUTE) newCalendar.get(Calendar.SECOND)
		return newCalendar;
	}
}
