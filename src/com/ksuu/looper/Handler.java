package com.ksuu.looper;

public class Handler {

    private Looper mLooper;
    private MessageQueue mQueue;
    private Callback mCallback;

    public Handler(){
        this(null);
    }

    public Handler(Callback callback) {
        mLooper = Looper.myLooper();
        if (mLooper == null) {
            throw new RuntimeException("Can't create handler inside thread that has not called Looper.prepare()");
        }
        mQueue = mLooper.mQueue;
        mCallback = callback;
    }

    public void dispatchMessage(Message msg) {
        if (msg.callback != null) {
            handleCallback(msg);
        } else {
            if (mCallback != null) {
                if (mCallback.handleMessage(msg)) {
                    return;
                }
            }
            handleMessage(msg);
        }
    }

    public void handleMessage(Message msg) {
    }

    private void handleCallback(Message msg) {
        msg.callback.run();
    }

    public void sendEmptyMessage(int what) {
        Message message = new Message();
        message.what = what;
        message.target = this;
        mQueue.enqueMessage(message);
    }

    public interface Callback {
        public boolean handleMessage(Message msg);
    }
}
