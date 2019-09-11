package com.gsontest.processortest;

import com.google.gson.Gson;
import com.linkkou.gson.processor.GsonAutowired;
import org.junit.Test;

/**
 * @author lk
 * @version 1.0
 *
 */
public class TestDemo {

    @GsonAutowired
    private Gson gson;

    @Test
    public void test() {


    }

}
