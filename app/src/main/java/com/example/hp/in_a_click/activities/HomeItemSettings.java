package com.example.hp.in_a_click.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ToggleButton;
import android.support.v7.widget.ToggleGroup;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.in_a_click.R;
import com.example.hp.in_a_click.model.HomeItem;
import com.example.hp.in_a_click.signinout.DriverSignInOutActivity;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.irozon.sneaker.Sneaker;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeItemSettings extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    HomeItem homeItem = null;
    @BindView(R.id.groupHomeCatName)
    ToggleGroup toggleGroup;
    @BindView(R.id.switchEnabling)
    Switch switchEnabling;
    @BindView(R.id.cbForRent)
    CheckBox cbForRent;
    @BindView(R.id.cbForSalary)
    CheckBox cbForSalary;
    @BindView(R.id.tvStatus)
    TextView textViewStatus;
    @BindView(R.id.llDatePicker)
    LinearLayout llDatePicker;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.llLocation)
    LinearLayout llLocation;
    @BindView(R.id.tvLocation)
    TextView tvLocation;
    @BindView(R.id.tvExpectedSalary)
    TextView tvExpectedSalary;
    @BindView(R.id.llExpectedSalary)
    LinearLayout llExpectedSalary;
    String strCheckedItem = "";
    int PLACE_PICKER_REQUEST = 1;
    Context context = null;
    @BindView(R.id.toolBar)
    Toolbar toolbar;
    DatabaseReference reference = null;
    boolean isEditted = false;
    boolean isPlaceChanged = false;
    Place place = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_item_seetings);
        ButterKnife.bind(this);
        //Log.e("HomeCatName", homeItem.getCatName());
        init();
        initToolBar();
        //getDataFromFireBaseDatabase();
        setDataFromPassedData();
        initRef();

    }

    private void setDataFromPassedData() {


        //get the passed data
        homeItem = (HomeItem) getIntent().getSerializableExtra("HomeItem");


        if (homeItem != null) {
            //set values
            String catName = homeItem.getCatName();
            for (int i = 0; i < toggleGroup.getChildCount(); i++) {
                ToggleButton toggleButton = (ToggleButton) toggleGroup.getChildAt(i);
                if (toggleButton.getText() == catName) {
                    toggleButton.setChecked(true);
                    break;
                }
            }
            //toggleGroup.findViewById(toggleGroup.getCheckedId());




            if (homeItem.getInsertDate() != null && homeItem.getInsertDate() != "")
                tvDate.setText(homeItem.getInsertDate());


            if (homeItem.isEnabled()) {
                switchEnabling.setChecked(true);
                textViewStatus.setText("Enabled");
            } else {
                switchEnabling.setChecked(false);
                textViewStatus.setText("Disabled");
            }


            cbForRent.setChecked(false);
            cbForSalary.setChecked(false);
            if (homeItem.getSalaryOrRent() == 0)
                cbForSalary.setChecked(true);
            else
                cbForRent.setChecked(true);


            if (homeItem.getSalaryFromTo() != null && homeItem.getSalaryFromTo() != "")
                tvExpectedSalary.setText(homeItem.getSalaryFromTo());


            if (homeItem.getLocationName() != null && homeItem.getLocationName() != "")
                tvLocation.setText(homeItem.getLocationName());


        }


    }

    private void initRef() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference(DriverSignInOutActivity.TAG_WORKERS).child(DriverSignInOutActivity.TAG_HOME_OWNER)
                .child(user.getUid())
                .child(DriverSignInOutActivity.TAG_HOMEOWNER_HOMES)
        ;


    }

    private void initToolBar() {

        toolbar.setTitle("HomeItem Settings");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(context, "Back Arrow", Toast.LENGTH_SHORT).show();
                        if (isEditted) {
                            saveDataBeforeExite();
                        } else {
                            finish();

                        }

                    }
                }

        );

    }

    private void init() {


        context = this;


        toggleGroup.setOnCheckedChangeListener(new ToggleGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ToggleGroup group, @IdRes int[] checkedId) {
                ToggleButton toggleButton = ((ToggleButton) group.findViewById(group.getCheckedId()));
                strCheckedItem = toggleButton.getText().toString();
                isEditted = true;
            }
        });
        strCheckedItem = ((ToggleButton) toggleGroup.findViewById(toggleGroup.getCheckedId())).getText().toString();


        llExpectedSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final MaterialNumberPicker numberPicker = new MaterialNumberPicker.Builder(context)
                        .minValue(1000)
                        .maxValue(100000)
                        .defaultValue(1000)
                        .backgroundColor(Color.WHITE)
                        .separatorColor(Color.TRANSPARENT)
                        .textColor(Color.BLACK)
                        .textSize(20)
                        .enableFocusability(false)
                        .wrapSelectorWheel(true)
                        .build();


                new AlertDialog.Builder(context)
                        .setTitle("Select Number")
                        .setView(numberPicker)
                        .setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Snackbar.make(findViewById(R.id.your_container), "You picked : " + numberPicker.getValue(), Snackbar.LENGTH_LONG).show();
                                tvExpectedSalary.setText(numberPicker.getValue() + "");
                                isEditted = true;

                            }
                        })
                        .show();


            }
        });

        llDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        HomeItemSettings.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");


            }
        });

        llLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(HomeItemSettings.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });


        switchEnabling.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    textViewStatus.setText("Enabled");
                    isEditted = true;
                } else {
                    textViewStatus.setText("Disabled");
                    isEditted = true;
                }
            }
        });


        cbForRent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    if (cbForSalary.isChecked()) {
                        cbForSalary.setChecked(false);
                    }
                    isEditted = true;
                }


            }
        });

        cbForSalary.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (cbForRent.isChecked())
                        cbForRent.setChecked(false);
                    isEditted = true;
                }


            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == HomeItemSettings.RESULT_OK) {
                place = PlacePicker.getPlace(data, this);
                tvLocation.setText(place.getAddress().toString());
                isEditted = isPlaceChanged = true;
            }

        }

    }

    @Override
    public void onBackPressed() {

        if (true) {

            confirmSaving();

        } else {
            super.onBackPressed();
        }
    }

    private void confirmSaving() {

        if (isEditted) {

//            new AlertDialog.Builder(this)
//                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            saveDataBeforeExite();
//                        }
//                    })
//                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            finish();
//                        }
//                    })
//                    .setMessage("Do u want to save before exit ")
//                    .show();


            saveDataBeforeExite();


        } else


            finish();


    }

    private void saveDataBeforeExite() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setView(LayoutInflater.from(getApplicationContext()).inflate(R.layout.progress_view, null))
                .setMessage("")
                .setTitle("Saving");


        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        if (!alertDialog.isShowing())
            // alertDialog.show();


            reference.child(homeItem.getRefId()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                        HomeItem homeItem = dataSnapshot.getValue(HomeItem.class);
//                        String key = homeItem.getRefId();
//
//                        HashMap<String, Object> hashMap = new HashMap<String, Object>();
//                        hashMap.put("locationName", tvLocation.getText().toString());
//                        hashMap.put("lat", "");
//                        hashMap.put("lon", "");
//                        hashMap.put("catName", strCheckedItem);
//                        hashMap.put("salaryOrRent", (cbForSalary.isChecked() ? 0 : 1));
//                        hashMap.put("insertDate", tvDate.getText().toString());
//                        hashMap.put("salaryFromTo", tvExpectedSalary.getText().toString());
//                        hashMap.put("enabled", (switchEnabling.isEnabled()) ? true : false);
//                        hashMap.put("refId", homeItem.getRefId());

                        HomeItem homeItem1 = null;

                        if (isPlaceChanged && place != null) {
                            LatLng latLng = place.getLatLng();
                            homeItem1 = new HomeItem(latLng.latitude + "", latLng.longitude + "",
                                    strCheckedItem, tvLocation.getText().toString(),
                                    tvDate.getText().toString(), tvExpectedSalary.getText().toString(),
                                    (cbForSalary.isChecked() ? 0 : 1),
                                    (switchEnabling.isEnabled()), homeItem.getRefId());
                        } else {
                            homeItem1 = new HomeItem(homeItem.getLat(), homeItem.getLon(),
                                    strCheckedItem, tvLocation.getText().toString(),
                                    tvDate.getText().toString(), tvExpectedSalary.getText().toString(),
                                    (cbForSalary.isChecked() ? 0 : 1),
                                    (switchEnabling.isEnabled()), homeItem.getRefId());

                        }


                        reference.child(HomeItemSettings.this.homeItem.getRefId())
                                //.updateChildren(hashMap)
                                .setValue(homeItem1)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful() && task.isComplete()) {
                                            if (alertDialog.isShowing()) alertDialog.dismiss();
                                            //finish();
                                            //Toast.makeText(HomeItemSettings.this, "Saved", Toast.LENGTH_SHORT).show();
                                            isEditted = false;


                                        }
                                    }
                                });



                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.homeItemDelete:
                deleteThisHomeItem();
                break;
            case R.id.homeItemEdit:
                confirmSaving();
                break;
            default:
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    private void deleteThisHomeItem() {

        new AlertDialog.Builder(this)
                .setMessage("Do u want to delete this item ???? ")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reference.child(homeItem.getRefId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                //Sneaker.with((Activity) getApplicationContext()).setTitle("Deleted Success").sneakSuccess();
                                finish();
                                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();


    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        tvDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
        isEditted = true;
    }


}
