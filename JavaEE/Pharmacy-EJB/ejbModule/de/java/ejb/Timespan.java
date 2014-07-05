package de.java.ejb;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Timespan implements Serializable {

  private static final long serialVersionUID = -4279228693824230803L;
  
  public static final long MILLISECS_PER_DAY = 24 * 60 * 60 * 1000;
	
	public static final int DEFAULT_DURATION = 30;

	private Date start;
	private Date end;

	private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * Initialises a timespan with a start which is set 30 days before the
	 * current day. The time of the start is set to 00:00:00.000, the time of
	 * the end is set to 23:59:59:999
	 */
	public Timespan() {
		Calendar calendar = Calendar.getInstance();
		end = adjustToMidnight(calendar.getTime());
		calendar.add(DAY_OF_MONTH, -DEFAULT_DURATION);
		start = adjustToEarlyMorning(calendar.getTime());
	}

	/**
	 * Initialises a timespan by parsing a string. See
	 * {@link Timespan#Timespan()} for details on the role of time
	 * (hours/minutes/seconds).
	 * 
	 * @param stringRepresentationOfTimespan Has to be in the form of "[yyyy-MM-dd,yyyy-MM-dd]"
	 */
	public Timespan(String stringRepresentationOfTimespan) {
		final String start = stringRepresentationOfTimespan.substring(1, 11);
		final String end = stringRepresentationOfTimespan.substring(12, 22);
		try {
			this.start = adjustToEarlyMorning(formatter.parse(start));
			this.end = adjustToMidnight(formatter.parse(end));
		} catch (ParseException e) {
			throw new RuntimeException("Timespan constructor received illegal input. Could not parse start and/or end.", e);
		}
	}
	
	private Date adjustToEarlyMorning(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(HOUR_OF_DAY, 0);
		calendar.set(MINUTE, 0);
		calendar.set(SECOND, 0);
		calendar.set(MILLISECOND, 0);
		return calendar.getTime();
	}

	private Date adjustToMidnight(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(HOUR_OF_DAY, 23);
		calendar.set(MINUTE, 59);
		calendar.set(SECOND, 59);
		calendar.set(MILLISECOND, 999);
		return calendar.getTime();
	}

	@Override
	public String toString() {
		final String start = formatter.format(getStart());
		final String end = formatter.format(getEnd());
		return String.format("[%s,%s]", start, end);
	}

	/**
	 * @return <tt>true</tt>, iff start is before or same as end. <tt>false</tt>, iff start is after end.
	 */
	public boolean isValid() {
		return start.before(end) || start.equals(end);
	}

	public Date getStart() {
		return start;
	}
	
	public void setStart(Date start) {
		this.start = start ;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

}
