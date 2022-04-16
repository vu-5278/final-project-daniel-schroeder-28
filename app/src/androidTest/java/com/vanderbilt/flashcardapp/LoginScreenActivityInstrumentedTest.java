package com.vanderbilt.flashcardapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import android.content.Context;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class LoginScreenActivityInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.vanderbilt.flashcardapp", appContext.getPackageName());
    }

    public static final String STRING_TO_BE_TYPED = "Test Text";

    @Rule
    public ActivityScenarioRule<LoginScreenActivity> activityScenarioRule = new ActivityScenarioRule<>(LoginScreenActivity.class);

    @Test
    public void changeText_LoginScreenActivity() {
        onView(withId(R.id.editTextEmail)).perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard());
        onView(withId(R.id.editTextEmail)).check(matches(withText(STRING_TO_BE_TYPED)));

        onView(withId(R.id.buttonSignIn)).perform(click());
        onView(withId(R.id.textViewRandomError)).check(matches(isDisplayed()));

        onView(withId(R.id.editTextTextPassword)).perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword)).check(matches(withText(STRING_TO_BE_TYPED)));

        onView(withId(R.id.buttonSignIn)).perform(click());
        onView(withId(R.id.textViewRandomError)).check(matches(isDisplayed()));

        onView(withId(R.id.newUserTextView)).perform(click());
        onView(withId(R.id.signUpTextView)).check(matches(isDisplayed()));
    }
}