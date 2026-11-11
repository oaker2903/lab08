package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import it.unibo.deathnote.impl.DeathNoteImpl;

class TestDeathNote {
    private static final int ACCEPTABLE_MESSAGE_LENGTH = 10;
    private static final int NEG_RULE_NUMBER = -2;
    private static final int LARGE_RULE_NUMBER = 10_000;
    private static final String NAME_1 = "enrico";
    private static final String NAME_2 = "marco";
    private static final String KARTING_ACCIDENTE = "karting accident";
    private static final String DEAFULT_DEATH_CAUSE = "heart attack";
    private static final String DEAFULT_DEATH_DETAILS = "ran for too long";
    private static final Long DEATILS_TIMEOUT = 6100L;
    private static final int SLEEP_THREAD = 100;
    private DeathNoteImpl deathNote;

    @BeforeEach
    void setUp() {
        deathNote = new DeathNoteImpl();
    }

    private void checkExistingRuleNumber(final int ruleNumber) {
        final IllegalArgumentException e = assertThrows(
            IllegalArgumentException.class,
            new Executable() {
                @Override
                public void execute() throws Throwable {
                    deathNote.getRule(ruleNumber);
                }
            }
        );
        assertNotNull(e.getMessage());
        assertFalse(e.getMessage().isEmpty());
        assertFalse(e.getMessage().isBlank());
        assertTrue(e.getMessage().length() >= ACCEPTABLE_MESSAGE_LENGTH, "error message too short");
    }

    @Test 
    void negativeRulesFail() {
        checkExistingRuleNumber(NEG_RULE_NUMBER);
    }

    @Test 
    void largeRulesFail() {
        checkExistingRuleNumber(LARGE_RULE_NUMBER);
    }

    @Test 
    void existRule() {
        for (final String it : deathNote.RULES) {
            assertNotNull(it);
            assertFalse(it.isBlank());
        }
    }

    @Test 
    void writeHuman() {
        assertFalse(deathNote.isNameWritten(NAME_1));
        deathNote.writeName(NAME_1);
        assertTrue(deathNote.isNameWritten(NAME_1));
        assertFalse(deathNote.isNameWritten(NAME_2));
    }

    @Test 
    void deathCauseNoNames() {
        final IllegalStateException e = assertThrows(
            IllegalStateException.class,
            new Executable() {
                @Override
                public void execute() throws Throwable {
                    deathNote.writeDeathCause("death cause test ");
                } 
            }
        );
        assertNotNull(e.getMessage());
        assertFalse(e.getMessage().isEmpty());
        assertFalse(e.getMessage().isBlank());
        assertTrue(e.getMessage().length() >= ACCEPTABLE_MESSAGE_LENGTH, "error message too short");
    }

    @Test 
    void defaultDeathCause() throws InterruptedException {
        assertThrows(IllegalStateException.class,
        () -> deathNote.writeDeathCause(KARTING_ACCIDENTE));
        deathNote.writeName(NAME_1);
        assertEquals(DEAFULT_DEATH_CAUSE, deathNote.getDeathCause(NAME_1));
        deathNote.writeName(NAME_2);
        assertTrue(deathNote.writeDeathCause(KARTING_ACCIDENTE));
        assertEquals(KARTING_ACCIDENTE, deathNote.getDeathCause(NAME_2));
        Thread.sleep(SLEEP_THREAD);
        assertFalse(deathNote.writeDeathCause(DEAFULT_DEATH_CAUSE));
        assertEquals(KARTING_ACCIDENTE, deathNote.getDeathCause(NAME_2));
    }

    @Test 
    void detailsDeath() throws InterruptedException {
        deathNote.writeName(NAME_1);
        assertTrue(deathNote.getDeathDetails(NAME_1).isBlank());
        assertTrue(deathNote.writeDetails(DEAFULT_DEATH_DETAILS));
        assertEquals(DEAFULT_DEATH_DETAILS, deathNote.getDeathDetails(NAME_1));
        Thread.sleep(DEATILS_TIMEOUT);
        assertFalse(deathNote.writeDetails("another details bla bla"));
        assertEquals(DEAFULT_DEATH_DETAILS, deathNote.getDeathDetails(NAME_1));
    }
}
