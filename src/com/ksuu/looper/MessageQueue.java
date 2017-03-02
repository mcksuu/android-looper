package com.ksuu.looper;

public class MessageQueue {

    Message messages;

    private boolean mQuitAllowed;

    public MessageQueue(boolean quitAllowed) {
        mQuitAllowed = quitAllowed;
    }

    public boolean enqueMessage(Message message) {
        synchronized (this) {
            Message message2 = messages;
            if (message2 == null) {
                message.next = messages;
                messages = message;
            } else {
                Message pre;
                while (true) {
                    pre = message2;
                    message2 = message2.next;

                    if (message2 == null) {
                        break;
                    }
                }

                message.next = message2;
                pre.next = message;
            }
        }

        return true;
    }

    public Message next() {

        if (messages == null) {
            try {
                Thread.sleep(3000);
                next();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Message message = new Message();
        message.what = messages.what;
        message.target = messages.target;
        messages = messages.next;
        return message;
    }

}
