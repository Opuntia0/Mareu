package mathilde.petit.mareu;


import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import mathilde.petit.mareu.di.DI;
import mathilde.petit.mareu.ui.ListMeetingActivity;
import mathilde.petit.mareu.utils.DeleteViewAction;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static mathilde.petit.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MeetingListInstrumentedTest {
    // TODO Instrumented test


    private static int ITEMS_COUNT = 4;

    private ListMeetingActivity mActivity;

    @Rule
    public ActivityTestRule<ListMeetingActivity> mActivityRule =
            new ActivityTestRule(ListMeetingActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }


    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myMeetingsList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(allOf(withId(R.id.activity_meeting_rv), isDisplayed()))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myMeetingsList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(allOf(withId(R.id.activity_meeting_rv), isDisplayed())).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(allOf(withId(R.id.activity_meeting_rv), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 3
        onView(allOf(withId(R.id.activity_meeting_rv), isDisplayed())).check(withItemCount(ITEMS_COUNT-1));
    }

    /**
     * When we add a meeting, it appears in our view
     */
    @Test
    public void myMeetingsList_addAction_shouldAddItem() {
        int n_item = DI.getMeetingApiService().getMeetings().size();
        onView(withId(R.id.activity_list_meeting_fab)).perform(click());
        onView(withId(R.id.ET_list_attendees)).perform(clearText(), typeText("jean-test@lamoze.com"));
        onView(withId(R.id.ET_meeting_name)).perform(clearText(), typeText("Reunion N"), closeSoftKeyboard());
        onView(withId(R.id.ET_hour_of_meeting)).perform(PickerActions.setTime(14,30));
        // fermer le clavier
        // Clic OK (positive button)
        onView(withText("OK")).inRoot(isDialog())
            .check(matches(isDisplayed()))
            .perform(click());
        onView(allOf(withId(R.id.activity_meeting_rv), isDisplayed())).check(withItemCount(n_item+1));
    }
}
