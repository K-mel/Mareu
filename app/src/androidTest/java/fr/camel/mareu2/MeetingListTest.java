package fr.camel.mareu2;

import android.content.Context;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import fr.camel.mareu2.ui.ListMeetingActivity;
import fr.camel.mareu2.utils.DeleteViewAction;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;
import fr.camel.mareu2.di.DI;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MeetingListTest {

    @Rule
    public ActivityTestRule<ListMeetingActivity> mActivityRule =
            new ActivityTestRule(ListMeetingActivity.class);

    @Before
    public void setUp() {
        ListMeetingActivity activity = mActivityRule.getActivity();
        assertThat(activity, notNullValue());
    }

    @Test
    public void myMeetingList_shouldNotBeEmpty() {
        onView(ViewMatchers.withId(R.id.meeting_list))
                .check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void myMeetingList_deleteAction_shouldRemoveItem() {
        int ITEMS_COUNT = 4;
        onView(ViewMatchers.withId(R.id.meeting_list)).check(matches(hasChildCount(ITEMS_COUNT)));

        onView(ViewMatchers.withId(R.id.meeting_list))
                .perform(actionOnItemAtPosition(1, new DeleteViewAction()));

        onView(ViewMatchers.withId(R.id.meeting_list)).check(matches(hasChildCount(ITEMS_COUNT - 1)));

    }

    @Test
    public void myAddingMeetingButton_workingSucess() {
        int meetingCount = DI.getMeetingApiService().getMeeting().size();

        onView(ViewMatchers.withId(R.id.meeting_list)).check(matches(hasChildCount(meetingCount)));

        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        String dayOfMonthStr = String.valueOf(dayOfMonth);

        onView(ViewMatchers.withId(R.id.add_meeting))
                .perform(click());

        onView(ViewMatchers.withId(R.id.color_meeting))
                .check(matches(isDisplayed()));

        onView(allOf(withClassName(is("android.widget.NumberPicker$CustomEditText")), withText(dayOfMonthStr), isDisplayed())).perform(replaceText(String.valueOf(dayOfMonth + 7)));

        onView(withId(R.id.sujet_meeting)).perform(scrollTo(), replaceText("testInstrumented"), closeSoftKeyboard());

        onView(allOf(withId(R.id.save_meeting), withText("Save"))).perform(scrollTo(), click());

        onView(ViewMatchers.withId(R.id.meeting_list)).check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.meeting_list)).check(matches(hasChildCount(meetingCount + 1)));
    }


}