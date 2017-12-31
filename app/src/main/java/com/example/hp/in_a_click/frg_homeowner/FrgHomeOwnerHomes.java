package com.example.hp.in_a_click.frg_homeowner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ToggleButton;
import android.support.v7.widget.ToggleGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.hp.in_a_click.R;
import com.example.hp.in_a_click.activities.HomeItemSettings;
import com.example.hp.in_a_click.adapters.RecyclerViewAdapter;
import com.example.hp.in_a_click.interfaces.RecyclerTouchListener;
import com.example.hp.in_a_click.model.HomeItem;
import com.example.hp.in_a_click.signinout.DriverSignInOutActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.irozon.sneaker.Sneaker;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class FrgHomeOwnerHomes extends Fragment {

    Context context;
    //@BindView(R.id.srlHomeOwnerHomes)
    @BindView(R.id.groupHomeCatName)
    ToggleGroup toggleGroup;
    @BindView(R.id.rvListAllHomes)
    RecyclerView rvListAllHomes;
    @BindView(R.id.progressLoadAllHomes)
    MaterialProgressBar progressLoadAllHomes;
    @BindView(R.id.tvNoHomes)
    TextView tvNoHomes;
    String strCheckedCatName = "";
    RecyclerViewAdapter recyclerViewAdapter = null;
    List<HomeItem> homeItemList = new ArrayList<>();
    DatabaseReference refForListAllHomes = null;
    ToggleButton currentToggleButton = null;
    SpotsDialog dialogDisabling = null, dialogDeleting = null;
    private View parentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_list_all_homes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initGlobalUseVars();
        initVars();
        initRef();
        getDataFromDatabase();


    }

    private void initRef() {
        refForListAllHomes = FirebaseDatabase.getInstance().getReference(DriverSignInOutActivity.TAG_WORKERS)
                .child(DriverSignInOutActivity.TAG_HOME_OWNER)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(DriverSignInOutActivity.TAG_HOMEOWNER_HOMES);


    }

    private void initVars() {

        currentToggleButton = ((ToggleButton) toggleGroup.findViewById(toggleGroup.getCheckedId()));
        toggleGroup.setOnCheckedChangeListener(new ToggleGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ToggleGroup group, @IdRes int[] checkedId) {
                currentToggleButton = toggleGroup.findViewById(toggleGroup.getCheckedId());
                strCheckedCatName = currentToggleButton.getText().toString();
                recyclerViewAdapter.getFilter().filter(strCheckedCatName);


            }
        });
        strCheckedCatName = currentToggleButton.getText().toString();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvListAllHomes.setLayoutManager(linearLayoutManager);
        rvListAllHomes.setItemAnimator(new DefaultItemAnimator());
        rvListAllHomes.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        rvListAllHomes.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                rvListAllHomes, new FrgHomeOwnerMap.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                HomeItem homeItem = homeItemList.get(position);
                showPopUpMenu(view, homeItem);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), homeItemList, rvListAllHomes);
        rvListAllHomes.setAdapter(recyclerViewAdapter);


        //by default
        progressLoadAllHomes.setVisibility(View.INVISIBLE);

        //by default
        tvNoHomes.setVisibility(View.GONE);


        recyclerViewAdapter.getFilter().filter(strCheckedCatName);





    }

    private void getDataFromDatabase() {


        //clear the list firstly
        homeItemList.clear();
        //recharge the liat again
        refForListAllHomes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {

                    //clear the list firstly
                    homeItemList.clear();

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        HomeItem homeItem = dataSnapshot1.getValue(HomeItem.class);
                        homeItemList.add(homeItem);
                    }
                    //recyclerViewAdapter.notifyDataSetChanged();
                    //recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), homeItemList, rvListAllHomes);

                    recyclerViewAdapter.getFilter().filter(strCheckedCatName);
                    progressLoadAllHomes.setVisibility(View.GONE);
                    if (homeItemList.isEmpty())
                        tvNoHomes.setVisibility(View.VISIBLE);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Sneaker.with(getActivity()).setTitle("Network Error !!!").sneakError();

            }
        });


    }

    private void showPopUpMenu(View v, final HomeItem homeItem) {
        PopupMenu popup = new PopupMenu(context, v);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.menu_options, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
//                    case R.id.previewOnMap:
//                        alertDialogListHomes.dismiss();
//                        goToClickedPoint(new LatLng(Double.parseDouble(homeItem.getLat()), Double.parseDouble(homeItem.getLon())));
//                        break;
                    case R.id.homeItemSettings:
                        Intent i = new Intent(context, HomeItemSettings.class);
                        i.putExtra("HomeItem", homeItem);
                        context.startActivity(i);
                        break;

                }

                return true;
            }
        });

        popup.show(); //showing popup menu

    }

    private void initGlobalUseVars() {
        context = this.getActivity();
        ButterKnife.bind(FrgHomeOwnerHomes.this, getView());


        dialogDeleting = new SpotsDialog(getActivity(), "Deleting ...");
        dialogDisabling = new SpotsDialog(getActivity(), "Disabling ...");


        setHasOptionsMenu(true);
        setMenuVisibility(true);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_frg_list_all_homes, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteAllHomes:
                deleteAllHomes();
                break;
            case R.id.disableAllHomes:
                disableAllHomes();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void disableAllHomes() {


        dialogDeleting.show();

        Query query = refForListAllHomes.equalTo(true);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().child("enabled").setValue(false);
                if (dialogDeleting.isShowing()) dialogDeleting.dismiss();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Sneaker.with(getActivity()).setTitle("Error Occurred !!!!").sneakError();


            }
        });


//        refForListAllHomes.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
//
//                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                        HomeItem homeItem = dataSnapshot1.getValue(HomeItem.class);
//                        //update the home item
//                        refForListAllHomes.child(homeItem.getRefId())
//                                .setValue(
//                                        new HomeItem(homeItem.getLat(),
//                                                homeItem.getLon(),
//                                                homeItem.getLocationName(),
//                                                homeItem.getCatName(),
//                                                homeItem.getInsertDate(),
//                                                homeItem.getSalaryFromTo(),
//                                                homeItem.getSalaryOrRent(),
//                                                false,
//                                                homeItem.getRefId()
//                                        )
//                                );
//
//                    }
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//


    }

    private void deleteAllHomes() {


    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }


}
