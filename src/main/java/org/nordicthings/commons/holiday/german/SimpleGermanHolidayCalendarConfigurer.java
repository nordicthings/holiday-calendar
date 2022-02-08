package org.nordicthings.commons.holiday.german;

import org.nordicthings.commons.holiday.*;

public class SimpleGermanHolidayCalendarConfigurer implements HolidayCalendarConfigurer {

    @Override
    public void configure(HolidayCalendar holidayCalendar) {
        holidayCalendar.addHoliday(FixedHoliday.of(1, 1, "Neujahr"));
        holidayCalendar.addHoliday(FixedHoliday.ofSpecificAreas(6,1, "Heilige Drei Könige",
                GermanFederalState.BADEN_WUERTTEMBERG,
                GermanFederalState.BAYERN,
                GermanFederalState.SACHSEN_ANHALT));
        holidayCalendar.addHoliday(FixedHoliday.ofSpecificAreas(8,3,"Frauentag",
                GermanFederalState.BERLIN));
        holidayCalendar.addHoliday(GoodFriday.of("Karfreitag"));
        holidayCalendar.addHoliday(EasterSunday.of("Ostersonntag"));
        holidayCalendar.addHoliday(EasterMonday.of("Ostermontag"));
        holidayCalendar.addHoliday(AscensionOfChrist.of("Christi Himmelfahrt"));
        holidayCalendar.addHoliday(WhitSunday.of("Pfingstsonntag"));
        holidayCalendar.addHoliday(WhitMonday.of("Pfingstmontag"));
        holidayCalendar.addHoliday(FixedHoliday.of(1,5,"Tag der Arbeit"));
        holidayCalendar.addHoliday(CorpusChristi.ofSpecificAreas("Fronleichnam",
                GermanFederalState.BADEN_WUERTTEMBERG,
                GermanFederalState.BAYERN,
                GermanFederalState.HESSEN,
                GermanFederalState.NORDRHEIN_WESTFALEN,
                GermanFederalState.RHEINLAND_PFALZ,
                GermanFederalState.SAARLAND));
        holidayCalendar.addHoliday(FixedHoliday.ofSpecificAreas(15, 8, "Mariä Himmelfahrt",
                GermanFederalState.SAARLAND));
        holidayCalendar.addHoliday(FixedHoliday.ofSpecificAreas(20,9,"Weltkinderag",
                GermanFederalState.THUERINGEN));
        holidayCalendar.addHoliday(FixedHoliday.of(3, 10, "Tag der deutschen Einheit"));
        holidayCalendar.addHoliday(FixedHoliday.ofSpecificAreas(31, 10, "Reformationstag",
                GermanFederalState.BRANDENBURG,
                GermanFederalState.BREMEN,
                GermanFederalState.HAMBURG,
                GermanFederalState.MECKLENBURG_VORPOMMERN,
                GermanFederalState.NIEDERSACHSEN,
                GermanFederalState.SACHSEN,
                GermanFederalState.SACHSEN_ANHALT,
                GermanFederalState.SCHLESWIG_HOLSTEIN,
                GermanFederalState.THUERINGEN
        ));
        holidayCalendar.addHoliday(FixedHoliday.ofSpecificAreas(1, 11, "Allerheiligen",
                GermanFederalState.BADEN_WUERTTEMBERG,
                GermanFederalState.BAYERN,
                GermanFederalState.NORDRHEIN_WESTFALEN,
                GermanFederalState.RHEINLAND_PFALZ,
                GermanFederalState.SAARLAND));
        holidayCalendar.addHoliday(DayOfPrayerAndRepentance.ofSpecificAreas("Buß- und Bettag",
                GermanFederalState.SACHSEN));
        holidayCalendar.addHoliday(FixedHoliday.of(25, 12, "Erster Weihnachtstag"));
        holidayCalendar.addHoliday(FixedHoliday.of(26, 12, "Zweiter Weihnachtstag"));
    }

}
