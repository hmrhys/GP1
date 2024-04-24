/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.scheduler;

import java.io.IOException;
import java.util.ArrayList;

import edu.ncsu.csc216.wolf_scheduler.course.Course;
import edu.ncsu.csc216.wolf_scheduler.io.CourseRecordIO;

/**
 * WolfScheduler class is the primary class responsible for managing and and allowing the user to manipulate data related
 * to the course and the schedules and catalogs that contain these courses.
 * 
 * WolfScheduler allows the user to edit the name of the schedule, reset the schedule, add courses from the course catalog to course schedule,
 * and remove courses from the schedule. Additionally, WolfScheduler provides the funcitonality to view and display the course and schedule information
 * in the form of a 2D String array.
 * 
 * @author hmreese2
 *
 */
public class WolfScheduler {
	
	/** Represents the title of the schedule in the WolfScheduler view */
	private String title;
	/** Represents an ArrayList of Courses available in a course catalog. */
	private ArrayList<Course> catalog;
	/** Represents an ArrayList of Courses currently in a schedule */
	private ArrayList<Course> schedule;

	/**
	 * Constructs a WolfScheduler object with an empty schedule ArrayList, title set to "My Schedule", and catalog ArrayList full
	 * of the courses added from a given course record input file.
	 * @param fileName name of file that is being used to construct the course catalog of the WolfScheduler
	 * @throws IllegalArgumentException with message "Cannot find file." if there is an issue reading the course records and populating course catalog object.
	 */
	public WolfScheduler(String fileName) {
		// construct empty ArrayList for schedule field
		this.schedule = new ArrayList<Course>();
		// initialize title to "My Schedule"
		this.title = "My Schedule";
		// add Course objects from file to catalog
		try {
			this.catalog = CourseRecordIO.readCourseRecords(fileName);
		} catch (Exception e) {
			throw new IllegalArgumentException("Cannot find file.");
		}
	}

	/**
	 * Gets a 2D String array of the courses listed in the course catalog.
	 * Format: a row for each Course, 3 columns for course name, section, and title.
	 * If no courses are in the catalog, then an empty String[][] is returned.
	 * @return 2D String array representation of the course catalog.
	 */
	public String[][] getCourseCatalog() {
		// check if any courses exist
		if (catalog.isEmpty()) {
			return new String[0][0];
		}
		
		// construct a String[][] for course catalog
		String[][] arr = new String[catalog.size()][3];
		for (int i = 0; i < catalog.size(); i++) {
			arr[i][0] = catalog.get(i).getName();
			arr[i][1] = catalog.get(i).getSection();
			arr[i][2] = catalog.get(i).getTitle();
		}
		
		return arr;
	}

	/**
	 * Gets 2D String array of the courses currently in the schedule.
	 * Format: a row for each course, 3 columns for course name, section, and title.
	 * If no course exists in schedule, an empty 2D String array is returned.
	 * @return 2D String array representation of the courses listed in the schedule.
	 */
	public String[][] getScheduledCourses() {
		// check if any courses exist
		if (schedule.isEmpty()) {
			return new String[0][0];
		}
		
		// construct a String[][] for course schedule
		String[][] arr = new String[schedule.size()][3];
		for (int i = 0; i < schedule.size(); i++) {
			arr[i][0] = schedule.get(i).getName();
			arr[i][1] = schedule.get(i).getSection();
			arr[i][2] = schedule.get(i).getTitle();
		}
		
		return arr;
	}

	/**
	 * Gets 2D String array of all of course information from the schedule.
	 * Format: row for each course, 6 columns for course name, section, title, credits, instructorId, and meetingDays string.
	 * If no courses are in the schedule, return an empty 2D String array.
	 * @return 2D String array representation of the full information from a course schedule.
	 */
	public String[][] getFullScheduledCourses() {
		// check if any courses exist
		if (schedule.isEmpty()) {
			return new String[0][0];
		}
		
		// construct a String[][] for course schedule
		String[][] arr = new String[schedule.size()][6];
		for (int i = 0; i < schedule.size(); i++) {
			arr[i][0] = schedule.get(i).getName();
			arr[i][1] = schedule.get(i).getSection();
			arr[i][2] = schedule.get(i).getTitle();
			arr[i][3] = schedule.get(i).getCredits() + "";
			arr[i][4] = schedule.get(i).getInstructorId();
			arr[i][5] = schedule.get(i).getMeetingString();
		}
		
		return arr;
	}

	/**
	 * Finds a course within the course catalog using the course's name and section.
	 * If no course can be found with the given parameters, return null.
	 * @param name a course's name in a catalog
	 * @param section a course's section in a catalog
	 * @return course from course catalog using the given course name and section, or null if course DNE.
	 */
	public Course getCourseFromCatalog(String name, String section) {
		// iterate through catalog until a course is found
		for (int i = 0; i < catalog.size(); i++) {
			if (catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {
				return catalog.get(i);
			}
		}
		
		// else if no course can be found, return null
		return null;
	}

	/**
	 * Determines if a course already exists in course schedule using the given course name and section,
	 * if it does not then true is returned (because course can be added), otherwise false.
	 * @param name name of the course that is attempting to be added to schedule
	 * @param section section of the course that is attempting to be added to schedule
	 * @return true if course can be added to schedule (DNE), false if it cannot.
	 * @throws IllegalArgumentException with message "You are already enrolled in [course name]" if course name already 
	 * exists in schedule.
	 */
	public boolean addCourseToSchedule(String name, String section) {
		// see if course exists in catalog
		for (int i = 0; i < catalog.size(); i++) {
			if (catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {
				
				// check if course name is already in schedule
				for (int j = 0; j < schedule.size(); j++) {
					if (schedule.get(j).getName().equals(name)) {
						throw new IllegalArgumentException("You are already enrolled in " + name);
					} 
				}
				
				// if course doesn't exist, add course to schedule and return true
				schedule.add(catalog.get(i));
				return true;
				
			}
		}
		
		// otherwise course can't be added, return false
		return false;
	}

	/**
	 * Checks if a course is removable by verifying that it exists in schedule using given course name and section.
	 * If course is in schedule, course is removed from schedule and method returns true, otherwise return false.
	 * @param name name of course to be removed from schedule
	 * @param section section of course to be removed from schedule
	 * @return true if course exists in schedule and course has been removed, otherwise return false
	 */
	public boolean removeCourseFromSchedule(String name, String section) {
		// check that course exists in schedule
		for (int i = 0; i < schedule.size(); i++) {
			// if a match is found, remove course and return true
			if (schedule.get(i).getName().equals(name) && schedule.get(i).getSection().equals(section)) {
				schedule.remove(schedule.get(i));
				return true;
			}
		}
		
		// otherwise course cannot be removed, return false
		return false;
	}

	/**
	 * Creates a new empty ArrayList for the schedule
	 */
	public void resetSchedule() {
		this.schedule = new ArrayList<Course>();
	}

	/**
	 * Gets title of schedule
	 * @return title of schedule
	 */
	public String getScheduleTitle() {
		return title;
	}

	/**
	 * Error checks and sets title of schedule
	 * @param title title of schedule
	 * @throws IllegalArgumentException with message "Title cannot be null." if title is null
	 */
	public void setScheduleTitle(String title) {
		if (title == null) {
			throw new IllegalArgumentException("Title cannot be null.");
		}
		
		this.title = title;		
	}
	
	/**
	 * Saves schedule to a given file by working with CourseRecordIO.writeCourseRecords()
	 * @param filename name of file that student's schedule will be saved to
	 * @throws IllegalArgumentException with message "The file cannot be saved." if CourseRecordIO.writeCourseRecords()
	 * throws an IOException while attempting to export the schedule
	 */
	public void exportSchedule(String filename) {
		try {
			CourseRecordIO.writeCourseRecords(filename, schedule);
		} catch (IOException e) {
			throw new IllegalArgumentException("The file cannot be saved.");
		}
	}

}
