package riviasoftware.recipesapp;

import android.content.ComponentName;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;


import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import riviasoftware.recipesapp.ui.MainActivity;
import riviasoftware.recipesapp.ui.RecipeDetailActivity;
import riviasoftware.recipesapp.ui.RecipeStepsListActivity;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.getIdlingResources;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;

import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.allOf;


/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest  {

    @Rule public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);



    @Test
    public void testMainActivity(){

        onView(allOf(withId(R.id.loading),isDisplayed()));
        try {
            Thread.sleep(2500);
        }catch(Exception e){
            e.printStackTrace();
        }


        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recipe_list),
                        withParent(allOf(withId(R.id.main_coordinator))),
                        isDisplayed())).check(matches(hasDescendant(withText("Brownies"))));
        recyclerView.perform(actionOnItemAtPosition(1, click()));

        try {
            Thread.sleep(2500);
        }catch(Exception e){
            e.printStackTrace();
        }



        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.steps_list), isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(1, click()));


        try {
            Thread.sleep(2500);
        }catch(Exception e){
            e.printStackTrace();
        }

        onView(allOf(withId(R.id.step_detail), isDisplayed()));
        onView(allOf(withId(R.id.thumbnail_detail), isDisplayed()));
        ViewInteraction buttonBack = onView(allOf(withId(R.id.back_floating_button),isDisplayed()));
        buttonBack.perform(click());

        try {
            Thread.sleep(2500);
        }catch(Exception e){
            e.printStackTrace();
        }

        onView(allOf(withId(R.id.step_detail), isDisplayed()));
        ViewInteraction video = onView(allOf(withId(R.id.playerView), isDisplayed()));

        try {
            Thread.sleep(2500);
        }catch(Exception e){
            e.printStackTrace();
        }

       pressBack();

        try {
            Thread.sleep(2500);
        }catch(Exception e){
            e.printStackTrace();
        }

        onView(allOf(withId(R.id.ingredients),isDisplayed())).perform(click());

        try {
            Thread.sleep(2500);
        }catch(Exception e){
            e.printStackTrace();
        }

        pressBack();


    }




    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = getTargetContext();

        assertEquals("riviasoftware.recipesapp", appContext.getPackageName());
    }



}
