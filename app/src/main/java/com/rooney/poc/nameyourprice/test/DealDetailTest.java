package com.rooney.poc.nameyourprice.test;

import android.os.Bundle;
import android.test.InstrumentationTestCase;

import com.rooney.poc.nameyourprice.fragments.ItemDetailFragment;

/**
 * Class to test
 */
public class DealDetailTest extends InstrumentationTestCase {
    public void test() throws Exception {
        final int expected = 10;

        ItemDetailFragment fragment = ItemDetailFragment.newInstance(expected);

        Bundle bundle = fragment.getArguments();

        //null check
        assertNotNull(bundle);

        int reality = bundle.getInt("index");

        assertEquals(expected, reality);
    }

}
