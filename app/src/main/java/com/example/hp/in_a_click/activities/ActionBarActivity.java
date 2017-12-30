package com.example.hp.in_a_click.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.hp.in_a_click.R;
import com.example.hp.in_a_click.frg_homeowner.FrgHomeOwnerDeals;
import com.example.hp.in_a_click.frg_homeowner.FrgHomeOwnerHomes;
import com.example.hp.in_a_click.frg_homeowner.FrgHomeOwnerMap;
import com.example.hp.in_a_click.frg_homeowner.FrgHomeOwnerProfile;
import com.example.hp.in_a_click.frg_homeowner.FrgHomeOwnerSettings;
import com.example.hp.in_a_click.signinout.DriverSignInOutActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ActionBarActivity extends AppCompatActivity {

    private Drawer result = null;

    @BindView(R.id.frame_container)
    FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_actionbar);
        setTitle("InAclick HomeOwner");

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);


        // Handle Toolbar
        result = new DrawerBuilder()
                .withActivity(this)
                .withSavedInstance(savedInstanceState)
                .withDisplayBelowStatusBar(false)
                .withTranslucentStatusBar(false)
                .withToolbar(toolbar)
                .withDrawerLayout(R.layout.material_drawer_fits_not)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Map").withIcon(FontAwesome.Icon.faw_map),
                        new PrimaryDrawerItem().withName("My Homes").withIcon(FontAwesome.Icon.faw_home),
                        new PrimaryDrawerItem().withName("Profile").withIcon(FontAwesome.Icon.faw_user),
                        new PrimaryDrawerItem().withName("Settings").withIcon(FontAwesome.Icon.faw_home),
                        new PrimaryDrawerItem().withName("Deals").withIcon(FontAwesome.Icon.faw_home)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem instanceof Nameable) {
                            toolbar.setTitle(((Nameable) drawerItem).getName().getText(ActionBarActivity.this));

                        }
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        Fragment fragment = null;

                        switch (position) {
                            case 0:
                                fragment = new FrgHomeOwnerMap();
                                break;
                            case 1:
                                fragment = new FrgHomeOwnerHomes();
                                break;
                            case 2:
                                fragment = new FrgHomeOwnerProfile();
                                break;
                            case 3:
                                fragment = new FrgHomeOwnerSettings();
                                break;
                            case 4:
                                fragment = new FrgHomeOwnerDeals();
                                break;
                            default:
                                fragment = new FrgHomeOwnerMap();
                                break;


                        }

                        transaction.replace(R.id.frame_container, fragment);
                        transaction.commit();


                        return false;
                    }
                }).build();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frame_container, new FrgHomeOwnerMap());
        transaction.commit();


    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_home_owner, menu);
//        return super.onCreateOptionsMenu(menu);
//    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
//            case R.id.homeOwnerSignOut:
//                signOut();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}
