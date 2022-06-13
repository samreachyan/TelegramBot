package com.viettel;

public abstract class CustomTimerTask {
    private String taskName;
    private int times = 1;

    /**
     * Constructor
     *
     * @param taskName Name of the task
     */
    public CustomTimerTask(String taskName, int times) {
        this.taskName = taskName;
        this.times = times;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public void reduceTimes() {
        if (this.times > 0) {
            this.times -= 1;
        }
    }

    /**
     * @abstract Should contain the functionality of the task
     */
    public abstract void execute();
}
