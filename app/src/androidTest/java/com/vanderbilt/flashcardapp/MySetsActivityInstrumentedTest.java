package com.vanderbilt.flashcardapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import android.content.Context;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MySetsActivityInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.vanderbilt.flashcardapp", appContext.getPackageName());
    }

    @Rule
    public ActivityScenarioRule<MySetsActivity> activityScenarioRule = new ActivityScenarioRule<>(MySetsActivity.class);

    @Test
    public void test_MySetsActivity() {
        AppGlobals.setUserSets(MapConverter.convertStringToMap("{test=[{testFront1=testBack1}, {testFront2=testBack2}, {testFront3=testBack3}], Food=[{Bread=Grain}, {Milk=Dairy}]}"));

        onView(withId(R.id.study)).perform(click());
        onView(withId(R.id.textStudyInstructions)).check(matches(isDisplayed()));
        onView(withId(R.id.study)).perform(click());
        onView(withId(R.id.textStudyInstructions)).check(matches(isDisplayed()));
        onView(withId(R.id.add)).perform(click());
        onView(withId(R.id.buttonNextCard)).check(matches(isDisplayed()));
        onView(withId(R.id.add)).perform(click());
        onView(withId(R.id.buttonNextCard)).check(matches(isDisplayed()));
        onView(withId(R.id.sets)).perform(click());
        onView(withId(R.id.textViewWelcomeUser)).check(matches(isDisplayed()));
        onView(withId(R.id.sets)).perform(click());
        onView(withId(R.id.textViewWelcomeUser)).check(matches(isDisplayed()));
        onView(withId(R.id.add)).perform(click());
        onView(withId(R.id.buttonNextCard)).check(matches(isDisplayed()));
        onView(withId(R.id.study)).perform(click());
        onView(withId(R.id.textStudyInstructions)).check(matches(isDisplayed()));
        onView(withId(R.id.sets)).perform(click());
        onView(withId(R.id.textViewWelcomeUser)).check(matches(isDisplayed()));

        //onView(withId(R.id.buttonViewSet)).perform(click());
        //onView(withId(R.id.textStudyInstructions)).check(matches(isDisplayed()));
        //onView(withId(R.id.buttonEditSet)).perform(click());
        //(withId(R.id.buttonEditSet)).check(matches(isDisplayed()));
    }
}