package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateValidator {
    protected static Logger logger = LogManager.getLogger(DateValidator.class);
    static  SimpleDateFormat ft = new SimpleDateFormat("dd MMM yyyy", Locale.US);

    public static List <Integer> validateDatesOfUpcomingEvents(List<String> eventDates) {
        List <Integer> results = new ArrayList<>();
        Date dateNow = new Date();

        for (String dateString: eventDates ) {
            //дата мероприятия - 1 день
            if (!dateString.contains("-")) {
               Date parsed = parseStringWithSingleDate(dateString);
                if(dateNow.before(parsed)) {
                    results.add(0);
                }  else {
                    results.add(1);
                    logger.info("Некорректная дата:" + dateString);
                }
            }
            // диапазон дат мероприятия
            else {
               String firstDateString = extractFirstDateOfRange(dateString);
               String lastDateString = extractLastDateOfRange(dateString);

               Date parsedFirstDate = parseStringWithSingleDate(firstDateString);
               Date parsedLastDate = parseStringWithSingleDate(lastDateString);

                if(dateNow.after(parsedFirstDate) && dateNow.before(parsedLastDate)) {
                    // In between
                    results.add(0);
                }
                else if (dateNow.before(parsedFirstDate) && dateNow.before(parsedLastDate)) {
                    // Both dates are in future
                    results.add(0);
                }
                else {
                    //Dates of upcoming event are incorrect
                    results.add(1);
                    logger.info("Некорректная дата:" + dateString);
                }

            }
        }
        return  results;
    }
    public static List <Integer> validateDatesOfPastEvents(List<String> eventDates) {
        List <Integer> results = new ArrayList<>();
        Date dateNow = new Date();

        for (String dateString: eventDates ) {
            //дата мероприятия - 1 день
            if (!dateString.contains("-")) {
               Date parsed = parseStringWithSingleDate(dateString);
                if(dateNow.after(parsed)) {
                    results.add(0);
                }  else {
                    results.add(1);
                    logger.info("Некорректная дата:" + dateString);
                }
            }
            // диапазон дат мероприятия
            else {
               String firstDateString = extractFirstDateOfRange(dateString);
               String lastDateString = extractLastDateOfRange(dateString);

               Date parsedFirstDate = parseStringWithSingleDate(firstDateString);
               Date parsedLastDate = parseStringWithSingleDate(lastDateString);

                if(dateNow.after(parsedFirstDate) && dateNow.after(parsedLastDate)) {
                    results.add(0);
                }

                else {
                    //Dates of past event are incorrect
                    results.add(1);
                    logger.info("Некорректная дата:" + dateString);
                }

            }
        }
        return  results;
    }

    public static String extractFirstDateOfRange(String input) {
        String [] words = input.split(" ");
        String firstDate;
        if( words[1].equals("-")) {
            //Если диапазон дат внутри 1 месяца, например,   19 - 20 Aug 2020
            firstDate = words[0] + " " + words[words.length-2] + " "+ words[words.length-1];
        } else {
            //Если диапазон дат включает переход на новый месяц, например,   14 Jul - 12 Aug 2021
            firstDate = words[0] + " " + words[1] + " "+ words[words.length-1];
        }

       return firstDate;
    }

    public static String extractLastDateOfRange(String input) {
        String [] words = input.split("-");
        String lastDate = words[words.length-1];
        return lastDate;
    }

    public static Date parseStringWithSingleDate(String dateString) {
        Date parsedDate;
        try {
            parsedDate = ft.parse(dateString);
            /*if(dateNow.before(parsedDate)) {
                results.add(0);
            }  else {
                results.add(1);
            }*/
        }
        catch (ParseException e) {
            System.out.println("Can't parse this string to Date : " + dateString);
            parsedDate = null;
        }
        return parsedDate;
    }

}
