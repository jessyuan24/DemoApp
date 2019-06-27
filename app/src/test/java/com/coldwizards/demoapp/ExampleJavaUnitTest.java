package com.coldwizards.demoapp;

import com.coldwizards.demoapp.adapter.Captain;
import com.coldwizards.demoapp.adapter.FishingBoatAdapter;
import org.junit.Test;

/**
 * Created by jess on 19-6-26.
 */
public class ExampleJavaUnitTest {

    @Test
    public void adapterTest() {
        Captain captain = new Captain(new FishingBoatAdapter());

        captain.row();
    }
}
