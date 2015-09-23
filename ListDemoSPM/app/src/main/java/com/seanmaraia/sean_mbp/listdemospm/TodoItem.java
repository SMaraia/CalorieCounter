package com.seanmaraia.sean_mbp.listdemospm;

/**
 * Created by Sean-MBP on 9/18/15.
 */
public class TodoItem {
    public String text;
    public String formattedDate;
    public boolean complete;

    public TodoItem(String text, String formattedDate) {
        this.text = text;
        this.formattedDate = formattedDate;
        complete = false;
    }

    public String toString() {
        return "TodoItem: text=" + text + ", formattedDate=" + formattedDate + ", complete=" + complete;
    }
}
