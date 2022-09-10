package pl.lotto.timegenerator;

import java.time.*;

public class AdjustableClock extends Clock {
    private Instant instant;
    private final ZoneId zone;

    AdjustableClock(Instant fixedInstant, ZoneId zone) {
        this.instant = fixedInstant;
        this.zone = zone;
    }

    @Override
    public ZoneId getZone() {
        return zone;
    }

    @Override
    public Clock withZone(ZoneId zone) {
        if (zone.equals(this.zone)) {  // intentional NPE
            return this;
        }
        return new AdjustableClock(instant, zone);
    }

    @Override
    public long millis() {
        return instant.toEpochMilli();
    }

    @Override
    public Instant instant() {
        return instant;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AdjustableClock other
                && instant.equals(other.instant)
                && zone.equals(other.zone);
    }

    @Override
    public int hashCode() {
        return instant.hashCode() ^ zone.hashCode();
    }

    @Override
    public String toString() {
        return "AdjustableClock[" + instant + "," + zone + "]";
    }

    public static AdjustableClock fromLocalDateAndLocalTime(LocalDate date, LocalTime time, ZoneId zone) {
        ZonedDateTime zoneDateTime = createZoneDateTime(date, time, zone);
        return new AdjustableClock(zoneDateTime.toInstant(), zone);
    }

    public void advanceInTimeBy(Duration clockOffset) {
        this.instant = instant.plus(clockOffset);
    }

    public void plusDays(int days) {
        Duration offset = Duration.ofDays(days);
        advanceInTimeBy(offset);
    }

    public void setClockFromLocalDateTime(LocalDateTime localDateTime) {
        ZonedDateTime zoneDateTime = createZoneDateTime(localDateTime.toLocalDate(), localDateTime.toLocalTime(), zone);
        this.instant = zoneDateTime.toInstant();
    }

    public void setClockFromLocalDate(LocalDate localDate) {
        LocalDateTime localDateTime = LocalDateTime.of(localDate, LocalTime.now(this));
        setClockFromLocalDateTime(localDateTime);
    }

    public void setClockFromLocalTime(LocalTime localTime) {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(this), localTime);
        setClockFromLocalDateTime(localDateTime);
    }

    private static ZonedDateTime createZoneDateTime(LocalDate date, LocalTime time, ZoneId zone) {
        return ZonedDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
                time.getHour(), time.getMinute(), time.getSecond(), time.getNano(), zone);
    }

}

