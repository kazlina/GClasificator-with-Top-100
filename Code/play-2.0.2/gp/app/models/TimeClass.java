package models;
import java.util.Date;

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
}
