package com.example.hp.in_a_click.drawerItems;

import android.annotation.SuppressLint;

import com.mikepenz.materialdrawer.holder.ColorHolder;
import com.mikepenz.materialdrawer.model.AbstractBadgeableDrawerItem;

import java.util.List;

public class CustomPrimaryDrawerItem extends AbstractBadgeableDrawerItem<CustomPrimaryDrawerItem> {

    private ColorHolder background;

    public CustomPrimaryDrawerItem withBackgroundColor(int backgroundColor) {
        this.background = ColorHolder.fromColor(backgroundColor);
        return this;
    }

    public CustomPrimaryDrawerItem withBackgroundRes(int backgroundRes) {
        this.background = ColorHolder.fromColorRes(backgroundRes);
        return this;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void bindView(ViewHolder holder, List payloads) {
        //super.bindView(holder, payloads);

        if (background != null) {
            background.applyToBackground(holder.itemView);
        }
    }
}
