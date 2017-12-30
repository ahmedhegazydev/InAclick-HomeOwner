package com.example.hp.in_a_click.frg_homeowner;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.in_a_click.R;


import butterknife.BindView;
import butterknife.ButterKnife;

public class FrgHomeOwnerHomes extends Fragment {

    private View parentView;

    Context context;
    //@BindView(R.id.srlHomeOwnerHomes)


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_homeowner_homes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initGlobalUseVars();
    }

    private void initGlobalUseVars() {
        context = this.getActivity();
        ButterKnife.bind(FrgHomeOwnerHomes.this, getView());


    }




}
