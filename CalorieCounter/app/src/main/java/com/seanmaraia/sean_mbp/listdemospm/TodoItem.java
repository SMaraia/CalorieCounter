package com.seanmaraia.sean_mbp.listdemospm;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Sean-MBP on 9/18/15.
 */
public class TodoItem {
    public String text;
    public String calText;
    public String formattedDate;
    public boolean complete;

    public TodoItem(String text, String calText, String formattedDate) {
        this.text = text;
        this.calText = calText;
        this.formattedDate = formattedDate;
        complete = false;
    }

    public String toString() {
        return "TodoItem: text=" + text + ", calText=" + calText + ", formattedDate=" + formattedDate + ", complete=" + complete;
    }
}
