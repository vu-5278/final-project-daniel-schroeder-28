package com.vanderbilt.flashcardapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
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
public class NewUserActivityInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.vanderbilt.flashcardapp", appContext.getPackageName());
    }

    public static final String STRING_TO_BE_TYPED = "Test Text";
    public static final String STRING_TO_BE_TYPED_TWO = "Test";

    @Rule
    public ActivityScenarioRule<NewUserActivity> activityScenarioRule = new ActivityScenarioRule<>(NewUserActivity.class);

    @Test
    public void changeText_NewUserActivity() {
        onView(withId(R.id.editTextEmail)).perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard());
        onView(withId(R.id.editTextEmail)).check(matches(withText(STRING_TO_BE_TYPED)));

        onView(withId(R.id.editTextPassword)).perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard());
        onView(withId(R.id.editTextPassword)).check(matches(withText(STRING_TO_BE_TYPED)));

        onView(withId(R.id.buttonCreateAccount)).perform(click());
        onView(withId(R.id.textViewRandomError)).check(matches(isDisplayed()));

        onView(withId(R.id.editTextReenterPassword)).perform(typeText(STRING_TO_BE_TYPED_TWO), closeSoftKeyboard());
        onView(withId(R.id.editTextReenterPassword)).check(matches(withText(STRING_TO_BE_TYPED_TWO)));

        onView(withId(R.id.buttonCreateAccount)).perform(click());
        onView(withId(R.id.textViewRandomError)).check(matches(isDisplayed()));

        onView(withId(R.id.editTextReenterPassword)).perform(clearText());
        onView(withId(R.id.editTextReenterPassword)).perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard());
        onView(withId(R.id.editTextReenterPassword)).check(matches(withText(STRING_TO_BE_TYPED)));

        onView(withId(R.id.buttonCreateAccount)).perform(click());
        onView(withId(R.id.textViewRandomError)).check(matches(isDisplayed()));

        onView(withId(R.id.textViewAlreadyHaveAccount)).perform(click());
        onView(withId(R.id.welcomeTextView)).check(matches(isDisplayed()));
    }
}