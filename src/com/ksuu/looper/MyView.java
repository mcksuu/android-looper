package com.ksuu.looper;

public class MyView {

    private Thread mMainThread;
    private String mText;

    public MyView() {
        super();
        mMainThread = Thread.currentThread();
    }

    private boolean checkThread() {
        if (Thread.currentThread() != mMainThread) {
            throw new RuntimeException("can not change ui in other thread");
        }
        return true;
    }

    public void setText(String text) {
        if (checkThread()) {
            mText = text;
            System.out.println("在view中设置了文字：" + text);
        }
    }
}
