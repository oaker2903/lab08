package it.unibo.deathnote.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.deathnote.api.DeathNote;

/**
 * Implementation of {@link DeathNote}.
 *
 * <p>Names are stored in insertion order. The cause of death and the details
 * always refer to the last name written, and can be written only within
 * a limited time window.
 */
public class DeathNoteImpl implements DeathNote {

    /** Cause of death used when none is specified. */
    private static final String DEFAULT_DEATH_CAUSE = "heart attack";
    /** Time window (ms) to write the details, counted from the cause of death. */
    private static final long DETAILS_TIMEOUT_MS = 6040;
    /** Time window (ms) to write the cause of death, counted from the name. */
    private static final long DEATH_CAUSE_TIMEOUT_MS = 40;

    private final List<DeathImpl> names = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRule(final int ruleNumber) {
        if (ruleNumber < 1 || ruleNumber > RULES.size()) {
            throw new IllegalArgumentException(
                "Rule number must be between 1 and " + RULES.size() + ": " + ruleNumber);
        }
        return RULES.get(ruleNumber - 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeName(final String name) {
        if (name == null) {
            throw new IllegalArgumentException("Given name is null");
        }
        names.add(new DeathImpl(name, DEFAULT_DEATH_CAUSE, ""));
    }

    /**
     * {@inheritDoc}
     *
     * <p>The cause is accepted only if written within 40 ms from the last name.
     */
    @Override
    public boolean writeDeathCause(final String cause) {
        if (cause == null) {
            throw new IllegalStateException("Cause cannot be null");
        }
        if (names.isEmpty()) {
            throw new IllegalStateException("No name written in this DeathNote");
        }
        final DeathImpl last = names.get(names.size() - 1);
        final long now = System.currentTimeMillis();
        if (now - last.getTimeWritten() <= DEATH_CAUSE_TIMEOUT_MS) {
            last.setDeath(cause);
            last.setDetailsTime(now);
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The details are accepted only if written within 6 seconds and 40 ms
     * from the cause of death of the last name.
     */
    @Override
    public boolean writeDetails(final String details) {
        if (details == null) {
            throw new IllegalStateException("Details cannot be null");
        }
        if (names.isEmpty()) {
            throw new IllegalStateException("No name written in this DeathNote");
        }
        final DeathImpl last = names.get(names.size() - 1);
        final long now = System.currentTimeMillis();
        if (now - last.getDetailsTime() <= DETAILS_TIMEOUT_MS) {
            last.setDetails(details);
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDeathCause(final String name) {
        for (final DeathImpl human : names) {
            if (name.equals(human.getName())) {
                if (human.getDeathCause().isEmpty()) {
                    return DEFAULT_DEATH_CAUSE;
                }
                return human.getDeathCause();
            }
        }
        throw new IllegalArgumentException("Name not present in the death note: " + name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDeathDetails(final String name) {
        for (final DeathImpl human : names) {
            if (name.equals(human.getName())) {
                return human.getDetails();
            }
        }
        throw new IllegalArgumentException("Name not present in the death note: " + name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNameWritten(final String name) {
        for (final DeathImpl human : names) {
            if (human.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
