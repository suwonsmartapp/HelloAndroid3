package com.jsoh.myfirstandroidapp.test;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by junsuk on 16. 3. 25..
 */
public class CalcTest {

    private Calc mCalc;

    @Before
    public void setUp() throws Exception {
        // 초기화
        mCalc = new Calc();
    }

    @After
    public void tearDown() throws Exception {
        // 끝나 후 처리
    }

    @Test
    /**
     * 1.
     */
    public void 합계() throws Exception {
        int result = mCalc.sum(1, 10);
        Assert.assertEquals(11, result);

        result = mCalc.sum(-10, -20);
        Assert.assertEquals(-30, result);
    }

    @Test
    public void 곱셈() throws Exception {
        int result = mCalc.product(1, 10);
        Assert.assertEquals(10, result);

        result = mCalc.product(-10, -20);
        Assert.assertEquals(200, result);
    }
}