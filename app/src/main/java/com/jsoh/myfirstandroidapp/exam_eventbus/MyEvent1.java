
package com.jsoh.myfirstandroidapp.exam_eventbus;

/**
 * Created by junsuk on 16. 2. 23..
 */
public class MyEvent1 implements MyEvent {
    String name;
    int age;

    public MyEvent1(int age, String name) {
        this.age = age;
        this.name = name;
    }
}
