package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Constructs a course object that will be used store a course's individual object information.
 * 
 * A Course object knows its name, title, section, credits, instructorId, meetingDays, and meeting startTime and endTime. There are certain requirements for each field that make it either valid or invalid,
 * and meeting days are either Arranged or a list of meeting days represented by a series of non-repeating days of the week.
 * 
 * @author hmreese2
 *
 */
public class Course {
	
 /** Represents minimum length a course name can have. */
 private static final int MIN_NAME_LENGTH = 5;
	/** Represents maximum length a course name can have */
	private static final int MAX_NAME_LENGTH = 8;
	/** Represents minimum letter count a course can have */
	private static final int MIN_LETTER_COUNT = 1;
	/** Represents maximum letter count a course can have */
	private static final int MAX_LETTER_COUNT = 4;
	/** Represents digit count required for a course section */
	private static final int DIGIT_COUNT = 3;
	/** Represents required section length for a course section */
	private static final int SECTION_LENGTH = 3;
	/** Represents maximum credit hours a course can have */
	private static final int MAX_CREDITS = 5;
	/** Represents minimum credit hours a course can have */
	private static final int MIN_CREDITS = 1;
	/** Represents the upper hour bound for a course's meeting time */
	private static final int UPPER_HOUR = 24;
	/** Represents the upper minute bound for a course's meeting time */
	private static final int UPPER_MINUTE = 60;
	
	/** Course's name. */
	private String name;
	/** Course's title. */
	private String title;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;
	/** Course's meeting days */
	private String meetingDays;
	/** Course's starting time */
	private int startTime;
	/** Course's ending time */
	private int endTime;
	
	
	/**
	 * Constructs course object using values of all fields.
	 * @param name name of course.
	 * @param title title of course.
	 * @param section section of course.
	 * @param credits credit hours for course.
	 * @param instructorId instructor's unity id for course.
	 * @param meetingDays meeting days that a course meets (represented as series of chars).
	 * @param startTime starting time for a course.
	 * @param endTime ending time for a course.
	 * @throws IllegalArgumentException if any parameters are invalid.
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays,
			int startTime, int endTime) {
		setName(name);
		setTitle(title);
		setSection(section);
	    setCredits(credits);
	    setInstructorId(instructorId);
	    setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}

	/**
	 * Creates course with only name, title, section, credits, instructorId, and meetingDays for arranged courses.
	 * @param name name of course.
	 * @param title title of course.
	 * @param section section of course.
	 * @param credits credit hours for course.
	 * @param instructorId instructor's unity id for course.
	 * @param meetingDays meeting days for a course (represented as series of chars).
	 * @throws IllegalArgumentException if any of the parameters are invalid.
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays) {
		this(name, title, section, credits, instructorId, meetingDays, 0, 0);
	}



	/**
	 * Gets name of course
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets name of course
	 * @param name the name to set
	 * @throws IllegalArgumentException with message "Invalid course name." if name parameter is null, empty string, or improperly formatted
	 */
	private void setName(String name) {
		// check for null or empty string names
		if (name == null || name.length() == 0) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		
		// check length
		if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		
		// check properly formatted name pattern (L[LLL] NNN), i.e. correct number of letters/digits
		int letter = 0;
		int digit = 0;
		boolean hasSpace = false;
		for (int i = 0; i < name.length(); i++) {
			if (!hasSpace) {
				if (Character.isLetter(name.charAt(i))) {
					letter++;
				} else if (name.charAt(i) == ' ') {
					hasSpace = true;
				} else {
					throw new IllegalArgumentException("Invalid course name.");
				}
			} else if (hasSpace) {
				if (Character.isDigit(name.charAt(i))) {
					digit++;
				} else {
					throw new IllegalArgumentException("Invalid course name.");
				}
			}
		}
		
		// check number of letters
		if (letter < MIN_LETTER_COUNT || letter > MAX_LETTER_COUNT) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		
		// check number of digits
		if (digit != DIGIT_COUNT) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		
		// if valid, set field value
		this.name = name;
	}
	
	/**
	 * Gets title of course.
	 * @return the title.
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets title of course.
	 * @param title the title to set.
	 * @throws IllegalArgumentException with message "Invalid title." if title is null or empty string.
	 */
	public void setTitle(String title) {
		// check for null or empty string value.
		if (title == null || title.length() == 0) {
			throw new IllegalArgumentException("Invalid title.");
		}
		
		// if valid, set field.
		this.title = title;
	}
	
	/**
	 * Gets section of course.
	 * @return the section.
	 */
	public String getSection() {
		return section;
	}
	
	/**
	 * Sets section of course.
	 * @param section the section to set.
	 * @throws IllegalArgumentException with message "Invalid section." if section is null or not 3 characters.
	 */
	public void setSection(String section) {
		// check for null or invalid length
		if (section == null || section.length() != SECTION_LENGTH) {
			throw new IllegalArgumentException("Invalid section.");
		}
		
		// check if any characters are not digits
		for (int i = 0; i < section.length(); i++) {
			if (!Character.isDigit(section.charAt(i))) {
				throw new IllegalArgumentException("Invalid section.");
			}
		}
		
		// if valid set value.
		this.section = section;
	}
	
	/**
	 * Gets credits for course.
	 * @return the credits.
	 */
	public int getCredits() {
		return credits;
	}
	
	/**
	 * Sets credits for course.
	 * @param credits the credits to set.
	 * @throws IllegalArgumentException with message "Invalid credits." if credits value is out of bounds.
	 */
	public void setCredits(int credits) {
		// check that value is within bounds.
		if (credits < MIN_CREDITS || credits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid credits.");
		}
		
		// if value is valid set field.
		this.credits = credits;
	}
	
	/**
	 * Gets instructor id for course.
	 * @return the instructorId.
	 */
	public String getInstructorId() {
		return instructorId;
	}
	
	/**
	 * Sets instructor id for course.
	 * @param instructorId the instructorId to set.
	 * @throws IllegalArgumentException with message "Invalid instructor id." if parameter is null or empty string.
	 */
	public void setInstructorId(String instructorId) {
		// check null or empty string.
		if (instructorId == null || instructorId.length() == 0) {
			throw new IllegalArgumentException("Invalid instructor id.");
		}
		
		// if valid set value.
		this.instructorId = instructorId;
	}
	
	/**
	 * Gets meeting days for course.
	 * @return the meetingDays.
	 */
	public String getMeetingDays() {
		return meetingDays;
	}
	
	/**
	 * Gets start time for course.
	 * @return the startTime.
	 */
	public int getStartTime() {
		return startTime;
	}
	
	/**
	 * Gets end time for course.
	 * @return the endTime.
	 */
	public int getEndTime() {
		return endTime;
	}
	
	/**
	 * Error checks course meeting days and times and sets the appropriate value for the respective field.
	 * If the meeting days for Course are Arranged ("A") then start and end times are set to 0.
	 * If the meeting days for Course are NOT ARRANGED ("MTWHF"), then start and times must exist and must be valid.
	 * @param meetingDays the days that a course meets.
	 * @param startTime the time that a course starts.
	 * @param endTime the time that a course ends.
	 * @throws IllegalArgumentException with message "Invalid meeting days and times." if days are null, empty, or invalid chars; 
	 * If an arranged class has non-zero start/end times; If start/end time are incorrect times; If end time is less than start time.
	 */
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		// check null or empty meeting days
		if (meetingDays == null || meetingDays.length() == 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
		// check meeting days and times values
		if ("A".equals(meetingDays)) { // course is Arranged
			if (startTime != 0 || endTime != 0) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			
			// set fields
			this.meetingDays = meetingDays;
			this.startTime = 0;
			this.endTime = 0;
		} else { // course is NOT Arranged
			// variables that keep count of each week day Letter in meetingDays
			int m = 0;
			int t = 0;
			int w = 0;
			int h = 0;
			int f = 0;
			
			// iterate through meetingDays 
			for (int i = 0; i < meetingDays.length(); i++) {
				char day = meetingDays.charAt(i);
				
				// count up the amount of times a day was listed
				if (day == 'M') {
					m++;
				} else if (day == 'T') {
					t++;
				} else if (day == 'W') {
					w++;
				} else if (day == 'H') {
					h++;
				} else if (day == 'F') {
					f++;
				} else { // if any other letters are listed, throw IAE
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
			}
			
			// check that the counts are valid (no day should be listed more than once)
			if (m > 1 || t > 1 || w > 1 || h > 1 || f > 1) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			
			// check that times are valid (startTime should always be LESS that endTime)
			if (startTime > endTime) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			
			// break apart and compute start time and end time into hours and minutes (standard time)
			int startHour = startTime / 100;
			int startMin = startTime % 100;
			int endHour = endTime / 100;
			int endMin = endTime % 100;
			// check valid times
			if (startHour < 0 || startHour >= UPPER_HOUR) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			} else if (startMin < 0 || startMin >= UPPER_MINUTE) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			} else if (endHour < 0 || endHour >= UPPER_HOUR) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			} else if (endMin < 0 || endMin >= UPPER_MINUTE) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			} 
			
			// if all values are valid, set fields.
			this.meetingDays = meetingDays;
			this.startTime = startTime;
			this.endTime = endTime;
		}
	}
	
	/**
	 * Provides course meeting information (days and time) as a string, where the time is displayed as standard time.
	 * If meeting day is Arranged then return "Arranged".
	 * If meeting day us NOT Arranged then return the meeting days followed by start time (standard), a dash, then end time (standard).
	 * @return string representation of the meeting information for a course (where time is in standard time).
	 */
	public String getMeetingString() {
		// if meeting day is Arranged then string is simply "Arranged"
		if ("A".equals(meetingDays)) {
			return "Arranged";
		}
		
		// convert times
		String starting = getTimeString(startTime);
		String ending = getTimeString(endTime);
		
		return meetingDays + " " + starting + "-" + ending;
	}
	
	/**
	 * Private helper method for getMeetingString() that converts the given starting/ending course time from 24hr time to standard time.
	 * @param time the time that is being converted from 24hr time to standard time.
	 * @return converted time as a string of standard time.
	 */
	private String getTimeString(int time) {
		String timeString;
		// break into hours and minutes
		int hour = time / 100;
		int min = time % 100;
		
		if (hour >= 12) { // PM time
			// check if hour is 12, otherwise update hour to standard time
			if (hour > 12) {
				hour = hour - 12;
			}
			
			// check if minutes need a leading 0 (are less than 10)
			if (min < 10) {
				timeString = hour + ":0" + min + "PM";
			} else {
				timeString = hour + ":" + min + "PM";
			}
			
		} else { // AM time
			
			// check if hour is 0, if it is update it to standard time
			if (hour == 0) {
				hour = 12;
			}
			
			// check if minutes need a leading 0 (are less than 10)
			if (min < 10) {
				timeString = hour + ":0" + min + "AM";
			} else {
				timeString = hour + ":" + min + "AM";
			}
		}
		return timeString;
	}

	/**
	 * Generates a hashCode number value for Course using all fields.
	 * @return hashCode for course
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + credits;
		result = prime * result + endTime;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/**
	 * Compares two objects for equal values in all fields.
	 * @param obj object that is being compared
	 * @return true if the objects are found to be equal on all fields.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;
		if (endTime != other.endTime)
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		if (startTime != other.startTime)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	/**
	 * Returns a comma separated value String of all Course fields.
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
	    if ("A".equals(meetingDays)) {
	        return name + "," + title + "," + section + "," + credits + "," + instructorId + "," + meetingDays;
	    }
	    return name + "," + title + "," + section + "," + credits + "," + instructorId + "," + meetingDays + "," + startTime + "," + endTime; 
	}
}
