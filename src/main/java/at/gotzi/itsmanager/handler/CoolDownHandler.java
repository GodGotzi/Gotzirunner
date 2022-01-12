package at.gotzi.itsmanager.handler;

import at.gotzi.itsmanager.api.GotziRunnable;

public class CoolDownHandler {

    public CoolDownHandler() {
    }

    private boolean enterAllowed = true;

    public synchronized void enter() {
        setEnterAllowed(false);
        new GotziRunnable() {
            @Override
            public void run() {
                setEnterAllowed(true);
            }
        }.runAsyncTaskLater(300);
    }

    public synchronized void setEnterAllowed(boolean enterAllowed) {
        this.enterAllowed = enterAllowed;
    }

    public synchronized boolean enterAllowed() {
        return enterAllowed;
    }

}
