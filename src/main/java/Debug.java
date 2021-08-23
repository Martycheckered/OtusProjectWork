import utils.DateValidator;

import java.util.ArrayList;
import java.util.List;

public class Debug {
    public static void main(String[] args) {
       /* Date dateNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd MMM yyyy",  Locale.US);
        System.out.println(ft.format(dateNow));*/
       // 14 Jul - 12 Aug 2021
        // 1 Feb - 25 Aug 2025
        List <String> dates = new ArrayList<>();
        dates.add(" 9 Sep 2020");
        dates.add("11 Apr 2021");
        dates.add("15 Aug 2021");
        dates.add("14 Jul - 12 Aug 2021");
        dates.add("1 Feb - 25 Aug 2025");

        DateValidator dateValidator = new DateValidator();
        dateValidator.validateDatesOfUpcomingEvents(dates);
    }
}
