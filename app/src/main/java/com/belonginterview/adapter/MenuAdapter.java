package com.belonginterview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.belonginterview.R;
import com.belonginterview.enums.MenuEnumTagMap;
import com.belonginterview.enums.MenuOptionsEnum;

import java.util.ArrayList;
import java.util.List;

/*List Adapter for items in the side navigation drawer*/
public class MenuAdapter extends ArrayAdapter {

    private List<String> menuOptions;

    public MenuAdapter(Context context, int resource, List<String> menuOptions) {
        super(context, resource, menuOptions);
        this.menuOptions = menuOptions;
    }

    @Override
    public int getCount() {
        return menuOptions.size();
    }

    @Override
    public String getItem(int position) {
        return menuOptions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_menu, null);
        }

        String menuItem = menuOptions.get(position);
        if(menuItem != null) {
            TextView nameTextView = (TextView) view.findViewById(R.id.name);
            TextView tagTextView = (TextView) view.findViewById(R.id.tag);

            nameTextView.setText(menuItem);
            tagTextView.setText(MenuEnumTagMap.getTagString(MenuOptionsEnum.getMenuOption(position)));
        }

        return view;
    }
}
