package at.gotzi.itsmanager.api;

public abstract class GotziRunnable {

    private Thread thread;
    private boolean stop;


    public GotziRunnable() {
    }

    public abstract void run();

    public GotziRunnable runAsyncTaskLater(int milliseconds) {
        this.thread = new Thread(() -> {
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (stop)
                return;
            this.run();
        });
        this.thread.start();
        return this;
    }

    public GotziRunnable runTaskAsync() {
        this.thread = new Thread(() -> {
            while(true) {
                if (stop)
                    return;
                this.run();
            }
        });
        this.thread.start();
        return this;
    }

    public void cancel() {
        this.stop = true;
    }

    public Thread getThread() {
        return this.thread;
    }

    public GotziRunnable runRepeatingTaskAsync(int milliseconds) {
        this.thread = new Thread(() -> {
            while(true) {
                if (stop)
                    return;
                this.run();
                try {
                    Thread.sleep(milliseconds);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        this.thread.start();
        return this;
    }
}
