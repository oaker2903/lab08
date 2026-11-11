package it.unibo.deathnote.impl;

/**
 * Represents a single entry of the death note: a person's name together with
 * the cause and the details of their death, and the timestamps at which
 * each piece of information was written.
 *
 * <p>All timestamps are expressed in milliseconds, as returned by
 * {@link System#currentTimeMillis()}.
 */
public class DeathImpl {

    private final String name;
    private String death;
    private String details;

    private Long writtenTime;
    private Long deathTime;
    private Long detailsTime;

    /**
     * Creates a new entry. All the timestamps are initialised to the current time.
     *
     * @param name the name of the person
     * @param death the cause of death (use an empty string if not specified)
     * @param details the details of the death (use an empty string if not specified)
     */
    DeathImpl(final String name, final String death, final String details) {
        this.name = name;
        this.death = death;
        this.details = details;
        final long now = System.currentTimeMillis();
        this.writtenTime = now;
        this.deathTime = now;
        this.detailsTime = now;
    }

    /**
     * Returns the name of the person.
     *
     * @return the name of the person
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the cause of death as stored in this entry.
     * The default cause (if none was written) is handled by the caller.
     *
     * @return the cause of death, or an empty string if not specified
     */
    public String getDeathCause() {
        return this.death;
    }

    /**
     * Returns the details of the death.
     *
     * @return the details of the death, or an empty string if not specified
     */
    public String getDetails() {
        return this.details;
    }

    /**
     * Returns the time at which the name was written.
     * It is the reference for the 40 ms window to write the cause of death.
     *
     * @return the timestamp of the name, in milliseconds
     */
    public Long getTimeWritten() {
        return this.writtenTime;
    }

    /**
     * Returns the death-related timestamp of this entry.
     *
     * @return the death timestamp, in milliseconds
     */
    public Long getDeathWritten() {
        return this.deathTime;
    }

    /**
     * Returns the time at which the cause of death was written.
     * It is the reference for the 6 seconds and 40 ms window to write the details.
     *
     * @return the timestamp of the cause of death, in milliseconds
     */
    public Long getDetailsTime() {
        return this.detailsTime;
    }

    /**
     * Sets the cause of death.
     *
     * @param death the new cause of death
     */
    public void setDeath(final String death) {
        this.death = death;
    }

    /**
     * Sets the details of the death.
     *
     * @param details the new details of the death
     */
    public void setDetails(final String details) {
        this.details = details;
    }

    /**
     * Sets the time at which the name was written.
     *
     * @param newTime the new timestamp, in milliseconds
     */
    public void setTimeWritten(final Long newTime) {
        this.writtenTime = newTime;
    }

    /**
     * Sets the death-related timestamp of this entry.
     *
     * @param newTime the new timestamp, in milliseconds
     */
    public void setDeathWritten(final Long newTime) {
        this.deathTime = newTime;
    }

    /**
     * Sets the time at which the cause of death was written.
     *
     * @param newTime the new timestamp, in milliseconds
     */
    public void setDetailsTime(final Long newTime) {
        this.detailsTime = newTime;
    }
}
