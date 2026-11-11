package it.unibo.deathnote.impl;

public class DeathImpl {

    private final String name;
    private String death;
    private String details;

    private Long writtenTime;
    private Long deathTime;
    private Long detailsTime;

    DeathImpl(final String name, final String death, final String details) {
        this.name = name;
        this.death = death;
        this.details = details;
        final long now = System.currentTimeMillis();
        this.writtenTime = now;
        this.deathTime = now;
        this.detailsTime = now;
    }

    public String getName() {
        return this.name;
    }
    public String getDeathCause() {
        return this.death;
    }
    public String getDetails() {
        return this.details;
    }
    public Long getTimeWritten() {
        return this.writtenTime;
    }
    public Long getDeathWritten() {
        return this.deathTime;
    }
    public Long getDetailsTime() {
        return this.detailsTime;
    }


    public void setDeath(final String death) {
        this.death = death;
    }
    
    public void setDetails(final String details) {
        this.details = details;
    }

    public void setTimeWritten(final Long newTime) {
        this.writtenTime = newTime;
    }
    public void setDeathWritten(final Long newTime) {
        this.deathTime = newTime;
    }
    public void setDetailsTime(final Long newTime) {
        this.detailsTime = newTime;
    }
}
