package com.seanmaraia.sean_mbp.listdemospm;

import android.content.Context;
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
public class TodoItemsAdapter extends ArrayAdapter<TodoItem> {
    public TodoItemsAdapter(Context context, int resource, List<TodoItem> objects) {
        super (context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final TodoItem item = getItem(position);

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.better_list_item, parent, false);
        }
        TextView label = (TextView) convertView.findViewById(R.id.label);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        CheckBox complete = (CheckBox) convertView.findViewById(R.id.checkbox);

        label.setText(item.text);
        date.setText(item.formattedDate);
        complete.setChecked(item.complete);

        complete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.complete = isChecked;
            }
        });

        return convertView;

    }

}
