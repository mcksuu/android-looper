package com.ksuu.looper;

public class Message {

    public Handler target;
    Message next;
    int what;
    public Runnable callback;
    public void recycleUnchecked() {

    }

}
