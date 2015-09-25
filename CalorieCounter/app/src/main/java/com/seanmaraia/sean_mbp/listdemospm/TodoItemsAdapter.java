package com.seanmaraia.sean_mbp.listdemospm;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Sean-MBP on 9/18/15.
 */


public class TodoItemsAdapter extends RecyclerView.Adapter<TodoItemsAdapter.MealViewHolder> {

    List<TodoItem> mItemList;
    public static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView mealname;
        TextView calorieCount;
        CardView mCardView;

        MealViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView)itemView.findViewById(R.id.cv);
            mealname = (TextView)itemView.findViewById(R.id.label);
            calorieCount = (TextView)itemView.findViewById(R.id.date);
        }
    }

    TodoItemsAdapter(List<TodoItem> todos){
        mItemList = todos;
    }
    @Override
    public int getItemCount(){

        return mItemList.size();
    }

    @Override
    public MealViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.better_list_item, viewGroup, false);
        MealViewHolder mvh = new MealViewHolder(v);

        return mvh;

    }

    @Override
    public void onBindViewHolder(MealViewHolder viewHolder, int i){
        viewHolder.mealname.setText(mItemList.get(i).text);
        viewHolder.calorieCount.setText(mItemList.get(i).formattedDate);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
