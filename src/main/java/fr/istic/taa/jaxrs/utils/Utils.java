package fr.istic.taa.jaxrs.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    public static Date getDateFromString(String dateString) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
