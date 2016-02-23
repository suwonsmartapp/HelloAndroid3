
package com.jsoh.myfirstandroidapp.exam_eventbus;

/**
 * Created by junsuk on 16. 2. 23..
 */
public class MyEvent2 implements MyEvent {
    boolean isMarried;
    int age;

    public MyEvent2(int age, boolean isMarried) {
        this.age = age;
        this.isMarried = isMarried;
    }
}
