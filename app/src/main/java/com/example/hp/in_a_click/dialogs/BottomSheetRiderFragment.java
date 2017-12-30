package com.example.hp.in_a_click.dialogs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.in_a_click.R;

/**
 * Created by hp on 11/7/2017.
 */

public class BottomSheetRiderFragment extends BottomSheetDialogFragment {

    String mTag;

    public static BottomSheetRiderFragment newInstance(String tag) {

        Bundle args = new Bundle();
        args.putString("TAG", tag);

        BottomSheetRiderFragment fragment = new BottomSheetRiderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTag = getArguments().getString("TAG");


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        //View view = LayoutInflater.from(getActivity()).inflate((R.layout_select_role.about_driver, container, false);
        View view = inflater.inflate(R.layout.about_driver, container, false);
        return view;

    }
}
