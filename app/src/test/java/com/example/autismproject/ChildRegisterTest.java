package com.example.autismproject;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.robolectric.Shadows.shadowOf;

import com.example.autismproject.Child.ChildRegister;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

@RunWith(RobolectricTestRunner.class)
public class ChildRegisterTest {

    @Test
    public void activityShouldNotBeNull() {
        // Create an instance of the activity
        ActivityController<ChildRegister> controller = Robolectric.buildActivity(ChildRegister.class);
        ChildRegister activity = controller.create().get();

        // Check that the activity is not null
        assertNotNull(activity);

        // Verify that the correct layout is set
        assertEquals(R.layout.activity_child_register, shadowOf(activity).getContentView().getId());
    }
}
