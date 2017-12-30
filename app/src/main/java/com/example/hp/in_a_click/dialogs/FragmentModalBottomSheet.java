package com.example.hp.in_a_click.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.hp.in_a_click.R;
import com.example.hp.in_a_click.adapters.CoverFlowAdapter;
import com.example.hp.in_a_click.model.GameEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;


public class FragmentModalBottomSheet extends BottomSheetDialogFragment {

    public final static String KIND_CAR_OR_HOME = "CAR_OR_HOME";
    View parentView;
    Context context = null;
    String carOrHoemConst = "";
    BottomSheetBehavior bottomSheetBehavior;
    ImageView ivCloseDialog;
    Button btnHideDialog = null;
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            switch (newState) {

                case BottomSheetBehavior.STATE_COLLAPSED: {
                    Log.d("BSB", "collapsed");
                    ivCloseDialog.setVisibility(View.GONE);
                    btnHideDialog.setVisibility(View.VISIBLE);

                }
                case BottomSheetBehavior.STATE_SETTLING: {

                    Log.d("BSB", "settling");
                }
                case BottomSheetBehavior.STATE_EXPANDED: {
                    Log.d("BSB", "expanded");
                    ivCloseDialog.setVisibility(View.VISIBLE);
                    btnHideDialog.setVisibility(View.GONE);
                }
                case BottomSheetBehavior.STATE_HIDDEN: {

                    Log.d("BSB", "hidden");
                    //dismiss();
                }
                case BottomSheetBehavior.STATE_DRAGGING: {

                    Log.d("BSB", "dragging");
                }
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            Log.d("BSB", "sliding " + slideOffset);

        }
    };
    private ArrayList<GameEntity> listCarsItems = new ArrayList<>(0),
            listHomesItems = new ArrayList<>();
    private TextSwitcher mTitle;

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        parentView = View.inflate(getContext(), R.layout.activity_coverflow, null);
        dialog.setContentView(parentView);
        context = getContext();
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) parentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
            bottomSheetBehavior = (BottomSheetBehavior) behavior;

        }


        Bundle bundle = getArguments();
        carOrHoemConst = bundle.getString(KIND_CAR_OR_HOME);

        if (carOrHoemConst == "cars") {
            initCoverFlowCars();
            //Toast.makeText(context, carOrHoemConst, Toast.LENGTH_SHORT).show();
        } else {
            initCoverFlowHomes();
            //Toast.makeText(context, carOrHoemConst, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Bundle bundle = getArguments();
//        carOrHoemConst = bundle.getString(KIND_CAR_OR_HOME);


        //carOrHoemConst = getArguments().getString(FrgUserMap.KIND_CAR_OR_HOME);

//        assert savedInstanceState != null;
//        carOrHoemConst = savedInstanceState.getString(FrgUserMap.KIND_CAR_OR_HOME);

        // Toast.makeText(getContext(), carOrHoemConst, Toast.LENGTH_SHORT).show();

    }

    private void initCoverFlowCars() {

        List<String> titlesCars = Arrays.asList(getResources().getStringArray(R.array.titles_cars));
        @SuppressLint("Recycle") TypedArray iconsCars = getResources().obtainTypedArray(R.array.arr_cars_icons);

        for (int i = 0; i < titlesCars.size(); i++) {
            listCarsItems.add(new GameEntity(iconsCars.getResourceId(i, -1), titlesCars.get(i)));
        }


        final TextSwitcher mTitle = parentView.findViewById(R.id.title);
        mTitle.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                LayoutInflater inflater = LayoutInflater.from(context);
                return inflater.inflate(R.layout.item_title, null);
            }
        });
        Animation in = AnimationUtils.loadAnimation(context, R.anim.slide_in_top);
        Animation out = AnimationUtils.loadAnimation(context, R.anim.slide_out_bottom);
        mTitle.setInAnimation(in);
        mTitle.setOutAnimation(out);

        CoverFlowAdapter mAdapter = new CoverFlowAdapter(context);
        mAdapter.setData(listCarsItems);
        FeatureCoverFlow mCoverFlow = parentView.findViewById(R.id.coverflow);
        mCoverFlow.setAdapter(mAdapter);

        mCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(context,
//                        getResources().getString(listCarsItems.get(position).titleResId),
//                        Toast.LENGTH_SHORT).show();
                // Toast.makeText(context, listCarsItems.get(position).title, Toast.LENGTH_SHORT).show();

            }
        });
        mCoverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
            @Override
            public void onScrolledToPosition(int position) {
                //mTitle.setText(getResources().getString(listCarsItems.get(position).titleResId));
                mTitle.setText(listCarsItems.get(position).title);
            }

            @Override
            public void onScrolling() {
                mTitle.setText("");
            }
        });


        TextView tvTypeHomeOrCar = parentView.findViewById(R.id.tvType);
        tvTypeHomeOrCar.setText("فئة السيارات");
        ivCloseDialog = parentView.findViewById(R.id.ivCloseDialog);


        ivCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dismiss();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                bottomSheetBehavior.setPeekHeight(100);
                //bottomSheetBehavior.setHideable(false);
//                ivCloseDialog.setVisibility(View.GONE);
//                btnHideDialog.setVisibility(View.VISIBLE);


            }
        });
        btnHideDialog = parentView.findViewById(R.id.btnHideDialog);
        btnHideDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


    }

    private void initCoverFlowHomes() {

        List<String> titlesHomes = Arrays.asList(getResources().getStringArray(R.array.titles_homes));
        @SuppressLint("Recycle") TypedArray iconsHomes = getResources().obtainTypedArray(R.array.arr_homes_icons);

        for (int i = 0; i < titlesHomes.size(); i++) {
            listHomesItems.add(new GameEntity(iconsHomes.getResourceId(i, -1), titlesHomes.get(i)));
        }


        final TextSwitcher mTitle = parentView.findViewById(R.id.title);
        mTitle.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                LayoutInflater inflater = LayoutInflater.from(context);
                return inflater.inflate(R.layout.item_title, null);
            }
        });
        final Animation in = AnimationUtils.loadAnimation(context, R.anim.slide_in_top);
        Animation out = AnimationUtils.loadAnimation(context, R.anim.slide_out_bottom);
        mTitle.setInAnimation(in);
        mTitle.setOutAnimation(out);

        CoverFlowAdapter mAdapter = new CoverFlowAdapter(context);
        mAdapter.setData(listHomesItems);
        FeatureCoverFlow mCoverFlow = parentView.findViewById(R.id.coverflow);
        mCoverFlow.setAdapter(mAdapter);

        mCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(context,
//                        getResources().getString(listCarsItems.get(position).titleResId),
//                        Toast.LENGTH_SHORT).show();
                Toast.makeText(context, listHomesItems.get(position).title, Toast.LENGTH_SHORT).show();
            }
        });

        mCoverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
            @Override
            public void onScrolledToPosition(int position) {
                //mTitle.setText(getResources().getString(listCarsItems.get(position).titleResId));
                mTitle.setText(listHomesItems.get(position).title);
            }

            @Override
            public void onScrolling() {
                mTitle.setText("");
            }
        });


        TextView tvTypeHomeOrCar = parentView.findViewById(R.id.tvType);
        tvTypeHomeOrCar.setText("فئة البيوت");
        ImageView ivCloseDialog = parentView.findViewById(R.id.ivCloseDialog);
        ivCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dismiss();


            }
        });


    }

}
