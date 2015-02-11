package com.rooney.dealbrowserpoc.nameyourprice.test;

import android.os.Bundle;
import android.test.InstrumentationTestCase;

import com.rooney.dealbrowserpoc.nameyourprice.fragments.DealDetailFragment;

/**
 * Class to test
 */
public class DealDetailTest extends InstrumentationTestCase {
    public void test() throws Exception {
        final int expected = 10;

        DealDetailFragment fragment = DealDetailFragment.newInstance(expected);

        Bundle bundle = fragment.getArguments();

        //null check
        assertNotNull(bundle);

        int reality = bundle.getInt("index");

        assertEquals(expected, reality);
    }

}
