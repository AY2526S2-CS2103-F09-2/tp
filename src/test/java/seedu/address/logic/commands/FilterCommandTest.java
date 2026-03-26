package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code FilterCommand}.
 */
public class FilterCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    // ──────────────────────────────────────────────
    // equals() tests
    // ──────────────────────────────────────────────

    @Test
    public void equals_sameObject_returnsTrue() {
        FilterCommand command = new FilterCommand(alwaysTrue());
        assertTrue(command.equals(command));
    }

    @Test
    public void equals_samePredicate_returnsTrue() {
        Predicate<Person> predicate = alwaysTrue();
        FilterCommand command1 = new FilterCommand(predicate);
        FilterCommand command2 = new FilterCommand(predicate);
        assertTrue(command1.equals(command2));
    }

    @Test
    public void equals_differentPredicate_returnsFalse() {
        FilterCommand command1 = new FilterCommand(alwaysTrue());
        FilterCommand command2 = new FilterCommand(alwaysFalse());
        assertFalse(command1.equals(command2));
    }

    @Test
    public void equals_symmetry() {
        Predicate<Person> predicate = alwaysTrue();
        FilterCommand command1 = new FilterCommand(predicate);
        FilterCommand command2 = new FilterCommand(predicate);
        assertTrue(command1.equals(command2));
        assertTrue(command2.equals(command1));
    }

    @Test
    public void equals_null_returnsFalse() {
        FilterCommand command = new FilterCommand(alwaysTrue());
        assertFalse(command.equals(null));
    }

    @Test
    public void equals_differentType_int_returnsFalse() {
        FilterCommand command = new FilterCommand(alwaysTrue());
        assertFalse(command.equals(42));
    }

    @Test
    public void equals_differentType_string_returnsFalse() {
        FilterCommand command = new FilterCommand(alwaysTrue());
        assertFalse(command.equals("filter"));
    }

    // ──────────────────────────────────────────────
    // execute() tests
    // ──────────────────────────────────────────────

    @Test
    public void execute_predicateMatchesNone_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        Predicate<Person> predicate = alwaysFalse();
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertTrue(model.getFilteredPersonList().isEmpty());
    }

    @Test
    public void execute_predicateMatchesAll_allPersonsFound() {
        Predicate<Person> predicate = alwaysTrue();
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        int expectedCount = expectedModel.getFilteredPersonList().size();
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, expectedCount);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertFalse(model.getFilteredPersonList().isEmpty());
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        FilterCommand command = new FilterCommand(alwaysTrue());
        org.junit.jupiter.api.Assertions.assertThrows(
                NullPointerException.class, () -> command.execute(null));
    }

    @Test
    public void execute_returnsCorrectMessageFormat() {
        Predicate<Person> predicate = alwaysTrue();
        FilterCommand command = new FilterCommand(predicate);
        CommandResult result = command.execute(model);
        assertTrue(result.getFeedbackToUser().contains(
                String.valueOf(model.getFilteredPersonList().size())));
    }

    @Test
    public void execute_updatesFilteredList() {
        FilterCommand command = new FilterCommand(alwaysFalse());
        command.execute(model);
        assertTrue(model.getFilteredPersonList().isEmpty());
    }

    @Test
    public void execute_calledTwiceWithDifferentPredicates_listUpdatesEachTime() {
        // First call: filter to nothing
        new FilterCommand(alwaysFalse()).execute(model);
        assertTrue(model.getFilteredPersonList().isEmpty());

        // Second call: show everything
        new FilterCommand(alwaysTrue()).execute(model);
        assertFalse(model.getFilteredPersonList().isEmpty());
    }

    // ──────────────────────────────────────────────
    // toString() tests
    // ──────────────────────────────────────────────

    @Test
    public void toString_notNull() {
        FilterCommand command = new FilterCommand(alwaysTrue());
        assertNotNull(command.toString());
    }

    @Test
    public void toString_containsPredicateField() {
        FilterCommand command = new FilterCommand(alwaysTrue());
        assertTrue(command.toString().contains("predicate"));
    }

    @Test
    public void toString_matchesExpectedFormat() {
        Predicate<Person> predicate = alwaysTrue();
        FilterCommand command = new FilterCommand(predicate);
        String expected = FilterCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, command.toString());
    }

    @Test
    public void toString_twoCommandsSamePredicate_equal() {
        Predicate<Person> predicate = alwaysTrue();
        FilterCommand command1 = new FilterCommand(predicate);
        FilterCommand command2 = new FilterCommand(predicate);
        assertEquals(command1.toString(), command2.toString());
    }

    // ──────────────────────────────────────────────
    // Helpers
    // ──────────────────────────────────────────────

    private static Predicate<Person> alwaysTrue() {
        return person -> true;
    }

    private static Predicate<Person> alwaysFalse() {
        return person -> false;
    }
}