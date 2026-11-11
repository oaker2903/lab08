package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.print.attribute.standard.MediaSize.NA;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

class TestDeathNote {
    private static final int ACCEPTABLE_MESSAGE_LENGTH = 10;
    private static final int NEG_RULE_NUMBER = -2;
    private static final int LARGE_RULE_NUMBER = 10000;
    private static final String NAME_1 = "enrico";
    private static final String NAME_2 = "marco";

    private static final String DEAFULT_DEATH_CAUSE = "heart attack";



    DeathNoteImpl deathNote;

    @BeforeEach
    void setUp() {
        deathNote = new DeathNoteImpl();
    }

    private void checkExistingRuleNumber(final int ruleNumber) {
        final IllegalArgumentException e = assertThrows(
            IllegalArgumentException.class,
            // () -> deathNote.getRule(NEG_RULE_NUMBER)
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
        for (String it : deathNote.RULES) {
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
    /*
    4. If the cause of death is written within the next 40 milliseconds of writing the person's name, it will happen.
        If the cause of death is not specified, the person will simply die of a heart attack.
      * check that writing a cause of death before writing a name throws the correct exception
      * write the name of a human in the notebook
      * verify that the cause of death is a heart attack
      * write the name of another human in the notebook
      * set the cause of death to "karting accident"
      * verify that the cause of death has been set correctly (returned true, and the cause is indeed "karting accident")
      * sleep for 100ms
      * try to change the cause of death 
      * verify that the cause of death has not been changed
    
        @throws IllegalArgumentException if the provider name is not written in this DeathNote.
    
    */
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
        deathNote.writeName(NAME_1);
        assertEquals(DEAFULT_DEATH_CAUSE, deathNote.getDeathCause(NAME_1));
        deathNote.writeName(NAME_2);
        assertTrue(deathNote.writeDeathCause("karting accident"));
        assertEquals("karting accident", deathNote.getDeathCause(NAME_2));
        Thread.sleep(100);
        assertFalse(deathNote.writeDeathCause(DEAFULT_DEATH_CAUSE));
        assertEquals("karting accident", deathNote.getDeathCause(NAME_2));
    }
    /*
    5. After writing the cause of death, details of the death should be written in the next 6 seconds and 40 milliseconds of writing the death's cause.
      * check that writing the death details before writing a name throws the correct exception
      * write the name of a human in the notebook
      * verify that the details of the death are currently empty
      * set the details of the death to "ran for too long"
      * verify that death details have been set correctly (returned true, and the details are indeed "ran for too long")
      * write the name of another human in the notebook
      * sleep for 6100ms
      * try to change the details
      * verify that the details have not been changed
    */
    @Test 
    void detailsDeath() throws InterruptedException {
        deathNote.writeName(NAME_1);
        assertTrue(deathNote.getDeathCause(NAME_1).isEmpty());
        assertTrue(deathNote.writeDetails("ran for too long"));
        assertEquals("ran for too long", deathNote.getDeathDetails(NAME_1));
        Thread.sleep(6100);
        assertFalse(deathNote.writeDetails(DEAFULT_DEATH_CAUSE));
        assertEquals("ran for too long", deathNote.getDeathCause(NAME_1));
    }



}