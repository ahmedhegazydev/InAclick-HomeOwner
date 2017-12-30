package com.example.hp.in_a_click.drawerItems;


import com.example.hp.in_a_click.R;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

public class CustomCenteredPrimaryDrawerItem extends PrimaryDrawerItem {

    @Override
    public int getLayoutRes() {
        return R.layout.material_drawer_item_primary_centered;
    }

    @Override
    public int getType() {
        return R.id.material_drawer_item_centered_primary;
    }

}
