package com.seanmaraia.sean_mbp.listdemospm;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by Sean-MBP on 10/9/15.
 */
public abstract class HidingScrollListener extends RecyclerView.OnScrollListener {
    private static final int HIDE_THRESHOLD = 20;
    private int scrolledDist = 0;
    private boolean controlsVisible = true;

    @Override
    public void onScrolled(RecyclerView recyvlerView, int dx, int dy) {
        super.onScrolled(recyvlerView, dx, dy);

        Log.d("ListActivity", "Scrolling: " + scrolledDist  );

        if(scrolledDist > HIDE_THRESHOLD){
            onHide();
            controlsVisible = false;
            scrolledDist = 0;
        } else if (scrolledDist < -HIDE_THRESHOLD && !controlsVisible) {
            onShow();
            controlsVisible = true;
            scrolledDist = 0;
        }

        if((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
            scrolledDist += dy;
        }
    }

    public abstract void onHide();

    public abstract void onShow();
}
