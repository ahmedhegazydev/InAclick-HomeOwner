package com.example.hp.in_a_click.frg_homeowner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.in_a_click.R;
import com.example.hp.in_a_click.signinout.DriverSignInOutActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.irozon.sneaker.Sneaker;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.paypal.android.sdk.onetouch.core.metadata.ah.v;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class FrgHomeOwnerProfile extends Fragment {

    Context context = null;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvLocation)
    TextView tvLocation;
    @BindView(R.id.tvPass)
    TextView tvPass;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.tvPhoneNumber)
    TextView tvPhoneNumber;
    @BindView(R.id.ivImageProfile)
    ImageView ivImageProfile;
    DatabaseReference ref = null;
    @BindView(R.id.switchStatusOnMap)
    Switch aSwitchStatusOnMap;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    private View parentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.frg_home_owner_profile, container, false);
        return parentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
        initRef();
        getDataFromDataBase();


    }

    private void initRef() {
        ref = FirebaseDatabase.getInstance().getReference(DriverSignInOutActivity.TAG_WORKERS)
                .child(DriverSignInOutActivity.TAG_HOME_OWNER)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());


    }

    private void getDataFromDataBase() {

        progressBar.setVisibility(View.VISIBLE);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    HashMap<String, Object> hashMap = (HashMap<String, Object>) dataSnapshot.getValue();
                    setDataToVariables(hashMap);
                    progressBar.setVisibility(View.GONE);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Sneaker.with(getActivity()).setTitle("Error Network !!!").sneakError();

            }
        });
    }

    private void setDataToVariables(HashMap<String, Object> hashMap) {


        String userCity = (String) hashMap.get("city");
        String userEmail = (String) hashMap.get("userEmail");
        String userPass = (String) hashMap.get("userPass");
        String userPhone = (String) hashMap.get("userPhone");
        boolean userStatusOnMap = (boolean) hashMap.get("homeOwnerStatus");
        String userName = (String) hashMap.get("userName");


        if (userCity != null && userCity != "")
            tvLocation.setText(userCity);

        if (userEmail != null && userEmail != "")
            tvEmail.setText(userEmail);


        if (userPass != null && userPass != "")
            tvPass.setText(userPass);


        if (userPhone != null && userPhone != "")
            tvPhoneNumber.setText(userPhone);

        if (userName != null && userName != "")
            tvName.setText(userName);


        if (userStatusOnMap)
            aSwitchStatusOnMap.setChecked(true);
        else
            aSwitchStatusOnMap.setChecked(false);


    }


    @OnClick(R.id.tvPhoneNumber)
    public void btnChangePhoneNumber(View view) {

        Toast.makeText(context, "Phone Number", Toast.LENGTH_SHORT).show();
        showView((TextView) view, "Enter phone number");


    }

    private void showView(final TextView textView, String hint) {

        CardView cardView = new CardView(getActivity());
        cardView.setElevation(10);
        cardView.setCardElevation(10);
        cardView.setContentPadding(5, 5, 5, 5);
        cardView.setRadius(5);

        final EditText editText = new EditText(getActivity());
        editText.setHint(hint);
        editText.setPadding(5, 5, 5, 5);


        cardView.addView(editText);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(cardView)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textView.setText(editText.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setMessage("Change Data");



        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.setCancelable(true);
        if (!alertDialog.isShowing())
            alertDialog.show();


    }


    @OnClick(R.id.tvLocation)
    public void btnChangeLocation(View view) {

        Toast.makeText(context, "Location", Toast.LENGTH_SHORT).show();



    }

    @OnClick(R.id.tvName)
    public void btnChangeName(View view){

        Toast.makeText(context, "Name", Toast.LENGTH_SHORT).show();


    }

    @OnClick(R.id.ivImageProfile)
    public void btnChangeImageProfile(View view) {

        Toast.makeText(context, "Image Profile", Toast.LENGTH_SHORT).show();







    }


    private void init(View view) {

        context = this.getActivity();
        ButterKnife.bind(FrgHomeOwnerProfile.this, view);
        setMenuVisibility(true);
        setHasOptionsMenu(true);


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_edit, menu);


    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.edit:
                editHomeOwnerInfo();
                break;
            default:
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    private void editHomeOwnerInfo() {


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


}
