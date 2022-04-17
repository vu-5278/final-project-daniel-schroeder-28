package com.vanderbilt.flashcardapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import android.content.Context;
import android.view.View;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import org.hamcrest.Matcher;
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

        onView(withId(R.id.recyclerViewUserSets)).perform(RecyclerViewActions.actionOnItemAtPosition(0, buttonClickViewAction.clickChildViewWithId(R.id.buttonViewSet)));
        onView(withId(R.id.textStudyInstructions)).check(matches(isDisplayed()));

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.sets)).perform(click());
        onView(withId(R.id.recyclerViewUserSets)).check(matches(isDisplayed()));

        onView(withId(R.id.recyclerViewUserSets)).perform(RecyclerViewActions.actionOnItemAtPosition(0, buttonClickViewAction.clickChildViewWithId(R.id.buttonEditSet)));
        onView(withId(R.id.buttonNextCard)).check(matches(isDisplayed()));
    }

    public static class buttonClickViewAction {

        public static ViewAction clickChildViewWithId(final int id) {
            return new ViewAction() {
                @Override
                public Matcher<View> getConstraints() {
                    return null;
                }

                @Override
                public String getDescription() {
                    return "Click on a child view with specified id.";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    View v = view.findViewById(id);
                    v.performClick();
                }
            };
        }

    }
}