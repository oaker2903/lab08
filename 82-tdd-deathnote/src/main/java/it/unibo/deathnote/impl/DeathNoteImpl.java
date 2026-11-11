package it.unibo.deathnote.impl;

import java.beans.DefaultPersistenceDelegate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImpl implements DeathNote {

    private static final String DEAFULT_DEATH_CAUSE = "heart attack";
    private static final long DETAILS_TIMEOUT_MS = 6040;
    private static final long DEATH_CAUSE_TIMEOUT_MS = 40;
    List<DeathImpl> names = new ArrayList<>();

    @Override
    public String getRule(int ruleNumber) {
        if (ruleNumber < 1 || ruleNumber > RULES.size()) {
            throw new IllegalArgumentException("rule number cannot be smaller than 1 or bigger than rule last number");
        } else {
            return RULES.get(ruleNumber - 1);
        }
    }

    @Override
    public void writeName(String name) {
        if (name == null) {
            throw new NullPointerException("given name is null, not correct");
        } else {
            names.add(new DeathImpl(name, DEAFULT_DEATH_CAUSE, null));
        }
    }
    @Override
    public boolean writeDeathCause(String cause) {
        if(cause == null) { 
            throw new IllegalStateException("given details cannot be null");
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

    @Override
    public boolean writeDetails(String details) {
       if(details == null) { 
            throw new IllegalStateException("given details cannot be null");
        }
        if (names.isEmpty()) {
            throw new IllegalStateException("No name written in this DeathNote");
        }
        final DeathImpl last = names.get(names.size() - 1);
        final long now = System.currentTimeMillis();
        if (now - last.getTimeWritten() <= DETAILS_TIMEOUT_MS) {
            last.setDetails(details);
            return true;
        }
        return false;
    }

    @Override
    public String getDeathCause(String name) {
        for (final DeathImpl human : names) {
            if(name.equals(human.getName())) {   //nel caso lo trovassi
                //nel caso cause death non sia specificato
                if(human.getDeathCause().isEmpty()) {
                    return DEAFULT_DEATH_CAUSE;
                } 
                return human.getDeathCause();
            }
        }    
        throw new IllegalArgumentException("Name not present in the death note: " + name);
    }
    @Override
    public String getDeathDetails(String name) {
       for (final DeathImpl human : names) {
            if (name.equals(human.getName())) {
                return human.getDetails();
            }
        }
        throw new IllegalArgumentException("Name not present in the death note: " + name);
    }

    @Override
    public boolean isNameWritten(String name) {
        for (DeathImpl human : names) {
                if (human.getName().equals(name)) {
                    return true;
                }
        }
        return false;
    }
}
