package com.ksuu.looper;

public class LooperTest {
    private static Handler mHandler;
    private static Handler mHandler2;
    private static Handler mMainHandler;
    private static MyView myView;

    public static void main(String[] args) {

        Looper.prepareMainLooper();

        // MainThread
        myView = new MyView();

        doInMainThread();
        doInOtherThread();

        Looper.loop();

    }

    private static void doInOtherThread() {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                mHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        System.out.println("当前线程：" + Thread.currentThread() + "msg.what" + msg.what);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mHandler.sendEmptyMessage(1);
                    }
                };
                mHandler.sendEmptyMessage(1);
                Looper.loop();
                // myView.setText(Thread.currentThread().getName());
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                mHandler2 = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        System.out.println("当前线程：" + Thread.currentThread() + "msg.what" + msg.what);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mHandler.sendEmptyMessage(4);
                        mHandler2.sendEmptyMessage(msg.what);
                    };
                };
                mHandler2.sendEmptyMessage(2);
                Looper.loop();
            };
        }.start();

    }

    private static void doInMainThread() {
        mMainHandler = new Handler() {
            @Override
            public void dispatchMessage(Message msg) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("当前线程：" + Thread.currentThread() + "msg.what" + msg.what);
                mMainHandler.sendEmptyMessage(1);
            }
        };
        mMainHandler.sendEmptyMessage(1);
        myView.setText(Thread.currentThread().getName());
    }

}
