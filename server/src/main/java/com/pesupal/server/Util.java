package com.pesupal.server;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.pesupal.server.enums.Days;

public class Util {

    public static boolean isSameDate(Date date1, Date date2) {
        return date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth() && date1.getDate() == date2.getDate();
    }

    public static Days getCurrentDay() {

        // Get current date
        Date currentDate = new Date();

        // Define a SimpleDateFormat to extract the day of the week as a string
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE"); // "EEEE" gives the full name of the day

        // Format the current date to get the day name
        String dayName = sdf.format(currentDate).toUpperCase(); // Convert to uppercase to match Enum case

        // Convert the string to the corresponding Enum value
        Days currentDay = Days.valueOf(dayName);

        return currentDay;
    }
}
