package user_domain.competition;

import java.time.Duration;

public class TimeDisplay {
    private final Duration duration;

    public TimeDisplay(long totalSeconds) {
        this.duration = Duration.ofSeconds(totalSeconds);
    }

    public String getDisplayTime() {
        long minutes = duration.toMinutes();
        long seconds = duration.minusMinutes(minutes).getSeconds();
        return String.format("%02d:%02d", minutes, seconds);
    }

    public long getDurationInSeconds() {
        return duration.toSeconds();
    }
}
