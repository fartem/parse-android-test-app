package com.smlnskgmail.jaman.randomnotes.utils;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import org.hamcrest.Matcher;

public class ViewChildClick {

    public static ViewAction withChildId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child in a view!";
            }

            @Override
            public void perform(
                    UiController uiController,
                    View view
            ) {
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }

}
