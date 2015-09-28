package com.mooreliu.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author zhiwu_yan @version 1.0 @since 2015-06-19  16:34
 */
public class DateUtil {
  public static String getCurrentTime() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss");
    String currentDateandTime = sdf.format(new Date());
    return currentDateandTime;
  }

  /**
   * @param str �ַ� @param pattern ��ʽ @return Date
   */
  public static Date StringToDate(String str, String pattern) {
    Date dateTime = null;
    try {
      if (str != null && !str.equals("")) {
        SimpleDateFormat formater = new SimpleDateFormat(pattern);
        dateTime = formater.parse(str);
      }
    } catch (Exception ex) {
    }
    return dateTime;
  }

  /**
   * ���ܣ����ش������ڶ���date��֮��afterDays��������ڶ��� @param date ���ڶ��� @param afterDays ��ǰ���� @return java.util.Date ����ֵ
   */
  public static Date getBeferDay(Date date, int afterDays) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.DATE, -afterDays);
    return cal.getTime();
  }

  /**
   * �õ����� ���µ��ַ�
   *
   * @return java.util.Date ����ֵ
   */
  public static String getNowDayMothString(int beferDays) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(getBeferDay(Calendar.getInstance().getTime(), beferDays));
    if (beferDays == 0) {
      return "����";
    } else if (beferDays == 1) {
      return "����";
    } else {
      int month = (cal.get(Calendar.MONTH)) + 1;
      int day_of_month = cal.get(Calendar.DAY_OF_MONTH);
      return month + "." + day_of_month;
    }
  }


  public static String getDate(String timeString) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(Long.valueOf(timeString));
    SimpleDateFormat sf = new SimpleDateFormat("MM��dd�� HH:mm");
    return sf.format(calendar.getTime());
  }


  /**
   * �Ƚ�2��ʱ���Ƿ�����ȣ�����������ָ�ꡢ�¡�����ȣ�����ʱ�䲻���Ƚ�
   *
   * @param time1
   * @param time2
   * @return
   */
  public static boolean isDateEquel(String time1, String time2) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(Long.valueOf(time1));
    int year1 = calendar.get(Calendar.YEAR);
    int day1 = calendar.get(Calendar.DAY_OF_MONTH);
    calendar.setTimeInMillis(Long.valueOf(time2));
    int year2 = calendar.get(Calendar.YEAR);
    int day2 = calendar.get(Calendar.DAY_OF_MONTH);
    if (year1 == year2 && day1 == day2) {
      return true;
    }
    return false;
  }


}
