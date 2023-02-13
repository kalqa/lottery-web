package pl.lotto.timegenerator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class TimeGenerator {

    private final CurrentDateTimeGenerator currentDateTimeGenerator;
    private final DrawDateTimeGenerator drawDateTimeGenerator;
    private final ExpirationDateTimeGenerator expirationDateTimeGenerator;
    private LocalDateTime drawDate;

    TimeGenerator(CurrentDateTimeGenerator currentDateTimeGenerator, DrawDateTimeGenerator drawDateTimeGenerator, ExpirationDateTimeGenerator expirationDateTimeGenerator) {
        this.currentDateTimeGenerator = currentDateTimeGenerator;
        this.drawDateTimeGenerator = drawDateTimeGenerator;
        this.expirationDateTimeGenerator = expirationDateTimeGenerator;
        this.drawDate = drawDateTimeGenerator.generateDrawDate(currentDateTimeGenerator.getCurrentDateAndTime());
    }

    LocalDateTime getCurrentDateAndTime() {
        return currentDateTimeGenerator.getCurrentDateAndTime();
    }

    LocalDateTime getDrawDateAndTime() {
        String dateTimeString = "2023-02-18T12:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return LocalDateTime.parse(dateTimeString, formatter);

//        if (checkIfDrawDateIsOutdated()) {
//            LocalDateTime timeNow = currentDateTimeGenerator.getCurrentDateAndTime();
//            this.drawDate = drawDateTimeGenerator.generateDrawDate(timeNow);
//        }
//        return drawDate;
    }

    LocalDateTime getExpirationDateTime() {
        return expirationDateTimeGenerator.generateExpirationDate(getDrawDateAndTime());
    }

    private boolean checkIfDrawDateIsOutdated() {
        LocalDateTime timeNow = currentDateTimeGenerator.getCurrentDateAndTime();
        return timeNow.isAfter(drawDate);
    }

}
