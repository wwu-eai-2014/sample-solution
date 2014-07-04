package de.java.ejb.statistic;
import java.io.Serializable;

public class Duration implements Serializable {

  private static final long serialVersionUID = -1284676130222211633L;

  private long duration;

  /**
   * @param duration in seconds
   */
  public Duration(final long duration) {
    this.duration = duration;
  }

  /**
   * @return duration in seconds
   */
  public long getTotalSeconds() {
    return duration;
  }

  private long getAbsoluteDuration() {
    return Math.abs(duration);
  }
  
  long days() {
    return getAbsoluteDuration() / (24*60*60);
  }
  
  long hours() {
    long daysInHours = days() * 24;
    return getAbsoluteDuration() / (60*60) - daysInHours;
  }

  long minutes() {
    long hoursInMinutes = hours() * 60;
    long daysInMinutes = days() * 24 * 60;
    return getAbsoluteDuration() / 60 - hoursInMinutes - daysInMinutes;
  }

  long seconds() {
    return getAbsoluteDuration() % 60;
  }

  @Override
  public String toString() {
    return "" + dayString() + hourString() + minuteString() + secondString();
  }

  private String dayString() {
    long days = days();
    if (days == 1)
      return days + " day, ";
    if (days > 1)
      return days + " days, ";
    return "";
  }

  private String hourString() {
    long hours = hours();
    if (hours == 1)
      return hours + " hour, ";
    // also show hours when days are present
    if (hours > 1 || days() > 0)
      return hours + " hours, ";
    return "";
  }

  private String minuteString() {
    long minutes = minutes();
    if (minutes == 1)
      return minutes + " minute, ";
    // also show minutes when hours or days are present
    if (minutes > 1 || hours() > 0 || days() > 0)
      return minutes + " minutes, ";
    return "";
  }

  private String secondString() {
    long seconds = seconds();
    if (seconds == 1)
      return seconds + " second";
    // always show seconds
    return seconds + " seconds";
  }

}
