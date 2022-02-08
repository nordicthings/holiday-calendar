```
  _   _       _ _     _                ____      _                _            
 | | | | ___ | (_) __| | __ _ _   _   / ___|__ _| | ___ _ __   __| | __ _ _ __ 
 | |_| |/ _ \| | |/ _` |/ _` | | | | | |   / _` | |/ _ \ '_ \ / _` |/ _` | '__|
 |  _  | (_) | | | (_| | (_| | |_| | | |__| (_| | |  __/ | | | (_| | (_| | |   
 |_| |_|\___/|_|_|\__,_|\__,_|\__, |  \____\__,_|_|\___|_| |_|\__,_|\__,_|_|   
                              |___/                                            
```
# General Purpose
This library provides a configurable public holiday calendar that can depict national and regional public holidays.
# Basic Usage
## Calendar creation
A new holiday calendar can be created using the associated builder.
A public holiday calendar is empty by default; it must be configured with the relevant public holidays:
```
HolidayCalendar calendar = HolidayCalendar.builder()
    .forYear(2021)
    .build();
calendar.addHoliday(EasterSunday.standard());
calendar.addHoliday(EasterMonday.standard());
```
Once generated, the calendar can check given dates whether they are holidays or calculate the next working day based on a given date.
```
LocalDate testedDate = LocalDate.of(2021, 4, 4);
System.out.println("The " + testedDate + " is " + (calendar.isHoliday(testedDate)?"":"not ") + "a public holiday");
System.out.println("The first working day after Easter Sunday is " + calendar.getNextWorkingDay(testedDate));
```
```
The 2021-04-04 is a public holiday
The first working day after Easter Sunday is 2021-04-06
```
A public holiday calendar can be configured using a dedicated class:
```
HolidayCalendarConfigurer configurer = new HolidayCalendarConfigurer() {
    @Override
    public void configure(HolidayCalendar holidayCalendar) {
        holidayCalendar.addHoliday(EasterSunday.standard());
        holidayCalendar.addHoliday(EasterMonday.standard());
    }
};

HolidayCalendar calendar = HolidayCalendar.builder()
        .forYear(2021)
        .configurer(configurer)
        .build();
```
## Holidays
The library supports all holidays of the Western Church as well as any fixed holidays:
```
holidayCalendar.addHoliday(CorpusChristi.standard());
holidayCalendar.addHoliday(FixedHoliday.of(1, 1, "New Year"));
```
Every holiday has a unique key. By default, this is the canonical name of the holiday class, but it can be overwritten with your own custom key.
In addition, a public holiday can be assigned a human-readable name.
```
holidayCalendar.addHoliday(CorpusChristi.of("customKey", "Corpus Christi"));
```
## Administrative Areas
Some holidays are only valid in certain administrative areas. 
The library offers an interface with which administrative areas can be formed. 
The validity of a public holiday can be restricted to individual areas.
```
AdministrativeArea bavaria = new AdministrativeArea() {
    @Override
    public String getISO() {
        return "DE-BY";
    }

    @Override
    public String getName() {
        return "Bavaria";
    }
};
        
HolidayCalendarConfigurer configurer = new HolidayCalendarConfigurer() {
    @Override
    public void configure(HolidayCalendar holidayCalendar) {
       holidayCalendar.addHoliday(CorpusChristi.ofSpecificAreas(bavaria));
    }
};

HolidayCalendar calendar = HolidayCalendar.builder()
        .forYear(2021)
        .configurer(configurer)
        .build();

LocalDate testedDate = LocalDate.of(2021, 6, 3);
System.out.println("The " + testedDate + " is " + (calendar.isHoliday(testedDate) ? "" : "not ") + "a nationwide public holiday");
System.out.println("The " + testedDate + " is " + (calendar.isHoliday(testedDate, bavaria) ? "" : "not ") + "a public holiday in " + bavaria.getName());
```
```
The 2021-06-03 is not a nationwide public holiday
The 2021-06-03 is a public holiday in Bavaria
```
The library offers a fully configured standard implementation of a holiday calendar for Germany.
```
HolidayCalendar calendar = HolidayCalendar.builder()
        .forYear(2021)
        .configurer(new SimpleGermanHolidayCalendarConfigurer())
        .build();

LocalDate testedDate = LocalDate.of(2021, 6, 3);
System.out.println("The " + testedDate + " is " + (calendar.isHoliday(testedDate) ? "" : "not ") + "a nationwide public holiday");
System.out.println("The " + testedDate + " is " + (calendar.isHoliday(testedDate, GermanFederalState.BAYERN) ? "" : "not ") + "a public holiday in " + GermanFederalState.BAYERN.getName());
```
```
The 2021-06-03 is not a nationwide public holiday
The 2021-06-03 is a public holiday in BAYERN
```
