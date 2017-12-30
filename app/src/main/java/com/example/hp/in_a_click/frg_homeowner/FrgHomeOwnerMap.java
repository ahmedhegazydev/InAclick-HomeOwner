package com.example.hp.in_a_click.frg_homeowner;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ToggleButton;
import android.support.v7.widget.ToggleGroup;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.in_a_click.R;
import com.example.hp.in_a_click.activities.ActionBarActivity;
import com.example.hp.in_a_click.activities.HomeItemSettings;
import com.example.hp.in_a_click.adapters.RecyclerViewAdapter;
import com.example.hp.in_a_click.model.HomeItem;

import com.example.hp.in_a_click.signinout.DriverSignInOutActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.irozon.sneaker.Sneaker;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class FrgHomeOwnerMap extends Fragment implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,
        LocationListener,
        GoogleMap.OnCameraMoveCanceledListener,
        GoogleMap.OnCameraIdleListener,
        GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnMapLoadedCallback,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMarkerClickListener,
//        GoogleMap.InfoWindowAdapter,
        OnMapReadyCallback, GoogleMap.OnInfoWindowCloseListener, GoogleMap.OnInfoWindowLongClickListener {
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    final static int MY_PERMISSIONS_REQUEST_LOCATION = 1233;
    final static int PLAY_SERVICES_REF_REQUEST = 12345;
    final static int DISTANCE = 1000;
    final static String MARKER_CURRENT_PLACE = "MARKER_CURRENT_PLACE";
    private static final int REQUEST_CHECK_SETTINGS = 10;
    Context context = null;
    @BindView(R.id.mapView)
    MapView mMapView;
    GoogleMap googleMap = null;
    @BindView(R.id.btnGetMyLocationNow)
    ImageButton btnGetMyLocation;
    LocationRequest locationRequest = null;
    Marker markerCurrentLocation = null, markerRegisteredPlace = null;
    Animation animFadeIn, animFadeOut = null;
    @BindView(R.id.btnRegisterThisPlace)
    Button btnRegisterThisPlace;
    boolean b = true;
    @BindView(R.id.cardWhatToDo)
    CardView cardViewWhatToDo;
    int PLACE_PICKER_REQUEST = 1;
    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
    boolean b2 = false;
    boolean boPlacePickerBtn = false, boGetCurrentLocBtn = false, boMapClick = false;
    LatLng latLngFromPlacePicker = null, latLngFromMapClicking = null;
    View viewPopUpForMarker;
    SpotsDialog dialogPlacePickerLoading = null;
    String checkedItem = "";
    List<HomeItem> homeItemList = new ArrayList<>();
    AlertDialog alertDialogListHomes = null;
    Marker markerActive = null;
    private View parentView;
    private Location lastLocation = null;
    private GoogleApiClient googleApiClient = null;
    private int mWidth;
    private int mHeight;
    private PopupWindow mPopupWindow;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView rvListAllHomes = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frg_home_map, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initVars(view);
        checkLocationPermission();
        initMap(view, savedInstanceState);
        initAnimations();
        initViewAppearOnMarker(getActivity());
        initClickListenerActionBarButton();


    }

    private void initClickListenerActionBarButton() {
        ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        assert mActionBar != null;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_list_all_homes, menu);
    }

    private void disableEnableViews(ViewGroup viewGroup, boolean flag) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            child.setEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.homeOwnerSignOut:
                signOut();
                break;
            case R.id.listAllHomes:
                listAllHomes();
                break;
            case android.R.id.home:

                Toast.makeText(context, "Action", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        new AlertDialog.Builder(getActivity())
                .setMessage("Do yuo want to signout ?")
                .setPositiveButton("SignOut", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getActivity(), DriverSignInOutActivity.class));
                        getActivity().finish();
                        FirebaseAuth.getInstance().signOut();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show()
        ;
    }

    private void listAllHomes() {

        final View layout_list_all_homes = LayoutInflater.from(getActivity()).inflate(R.layout.layout_list_all_homes, null);


        rvListAllHomes = layout_list_all_homes.findViewById(R.id.rvListAllHomes);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rvListAllHomes.setLayoutManager(linearLayoutManager);
        rvListAllHomes.setItemAnimator(new DefaultItemAnimator());
        rvListAllHomes.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));


        rvListAllHomes.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                rvListAllHomes, new ClickListener() {
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


        final ToggleGroup toggleGroup = layout_list_all_homes.findViewById(R.id.groupHomeCatName);

        final MaterialProgressBar materialProgressBar = layout_list_all_homes.findViewById(R.id.progressLoadAllHomes);
        materialProgressBar.setVisibility(View.VISIBLE);
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference(DriverSignInOutActivity.TAG_WORKERS)
                .child(DriverSignInOutActivity.TAG_HOME_OWNER)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(DriverSignInOutActivity.TAG_HOMEOWNER_HOMES);


        toggleGroup.setOnCheckedChangeListener(new ToggleGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ToggleGroup group, @IdRes int[] checkedId) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //loadAllHomes(dataSnapshot);
                        String query = ((ToggleButton) toggleGroup.findViewById(toggleGroup.getCheckedId())).getText().toString();
                        recyclerViewAdapter.getFilter().filter(query);
                        //materialProgressBar.setVisibility(View.GONE);
                        Log.e("QueryString", query);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Sneaker.with(getActivity()).setTitle("Network Error !!!").sneakError();

                    }
                });

            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loadAllHomes(dataSnapshot);
                String query = ((ToggleButton) toggleGroup.findViewById(toggleGroup.getCheckedId())).getText().toString();
                recyclerViewAdapter.getFilter().filter(query);
                materialProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Sneaker.with(getActivity()).setTitle("Network Error !!!").sneakError();

            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(layout_list_all_homes)
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialogListHomes = builder.create();
        alertDialogListHomes.setCancelable(false);
        alertDialogListHomes.setCanceledOnTouchOutside(false);
        if (!alertDialogListHomes.isShowing()) {
            alertDialogListHomes.show();
        }


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
                    case R.id.previewOnMap:
                        alertDialogListHomes.dismiss();
                        goToClickedPoint(new LatLng(Double.parseDouble(homeItem.getLat()), Double.parseDouble(homeItem.getLon())));
                        break;
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

    private void loadAllHomes(DataSnapshot dataSnapshot) {
        homeItemList.clear();
        if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                HomeItem homeItem = dataSnapshot1.getValue(HomeItem.class);
                homeItemList.add(homeItem);
            }
            //recyclerViewAdapter.notifyDataSetChanged();
            //recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), homeItemList, rvListAllHomes);

        }

    }

    private void initViewAppearOnMarker(Activity activity) {
        // inflate our view here
        viewPopUpForMarker = LayoutInflater.from(activity).inflate(R.layout.custominfowindow, null);
        PopupWindow popupWindow = new PopupWindow(viewPopUpForMarker, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        viewPopUpForMarker.measure(size.x, size.y);
        mWidth = viewPopUpForMarker.getMeasuredWidth();
        mHeight = viewPopUpForMarker.getMeasuredHeight();
        mPopupWindow = popupWindow;


        //access the view
        final Button btnRegisterNext = viewPopUpForMarker.findViewById(R.id.btnRegisterNext);
        ToggleGroup groupHomeCatName = viewPopUpForMarker.findViewById(R.id.groupHomeCatName);
        groupHomeCatName.setOnCheckedChangeListener(new ToggleGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ToggleGroup group, @IdRes int[] checkedId) {

                ToggleButton toggleButton = group.findViewById(group.getCheckedId());
                checkedItem = toggleButton.getText().toString();
//                Log.e("getText201300", toggleButton.getText().toString());
//                Log.e("getCheckedId", group.getCheckedId()+"");

            }
        });
        checkedItem = ((ToggleButton) groupHomeCatName.
                findViewById(groupHomeCatName.getCheckedId())).getText().toString();
        ///Log.e("checkedItem201300", checkedItem);


        btnRegisterNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPopUpWindowForRegistwrThisPlace();


            }
        });

    }

    private void showPopUpWindowForRegistwrThisPlace() {


        LatLng latLngFinal = null;
        if (boPlacePickerBtn) {
            latLngFinal = latLngFromPlacePicker;
        }

        if (boGetCurrentLocBtn) {
            if (lastLocation != null) {
                latLngFinal = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            }
        }

        if (boMapClick) {
            latLngFinal = latLngFromMapClicking;
        }

        View viewPopUpForRegisterPlace = LayoutInflater.from(getActivity()).inflate(R.layout.pop_up_place_info_confirm, null);
        final MaterialProgressBar materialProgressBar = viewPopUpForRegisterPlace.findViewById(R.id.progressViewAddingHome);

        TextView textView = viewPopUpForRegisterPlace.findViewById(R.id.tvPlaceInfo);
        Button btnRegisterThisPlace = viewPopUpForRegisterPlace.findViewById(R.id.btnRegisterThis);
        final LatLng finalLatLngFinal = latLngFinal;
        final String strCompleteAddress = getAddress(getActivity(), finalLatLngFinal.latitude, finalLatLngFinal.longitude);
        textView.setText(strCompleteAddress);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(viewPopUpForRegisterPlace)
                .setCancelable(false)
                .setPositiveButton("Register", null)
                .setNegativeButton("Back", null);

        final AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                final Button btnPos = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                btnPos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialProgressBar.setVisibility(View.VISIBLE);
                        materialProgressBar.setIndeterminate(true);
                        btnPos.setEnabled(false);
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(DriverSignInOutActivity.TAG_WORKERS)
                                .child(DriverSignInOutActivity.TAG_HOME_OWNER)
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child(DriverSignInOutActivity.TAG_HOMEOWNER_HOMES);
                        String key = reference.push().getKey();
                        reference.child(key).setValue(new HomeItem(finalLatLngFinal.latitude + "", finalLatLngFinal.longitude + "", checkedItem,
                                strCompleteAddress, "" + new Date().toString(), key))
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful() && task.isComplete()) {
                                            btnPos.setEnabled(true);
                                            alertDialog.dismiss();
                                            Sneaker.with(getActivity()).setTitle("Registered Success, Go to your homes").sneakSuccess();
                                        }
                                    }
                                });
                    }
                });
                ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        if (!alertDialog.isShowing())
            alertDialog.show();


    }

    public String getAddress(Context context, double lat, double lng) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        String add = "";
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            add = obj.getAddressLine(0);
            add = add + "\n" + obj.getCountryName();
            add = add + "\n" + obj.getCountryCode();
//            add = add + "\n" + obj.getAdminArea();
//            add = add + "\n" + obj.getPostalCode();
//            add = add + "\n" + obj.getSubAdminArea();
//            add = add + "\n" + obj.getLocality();
//            add = add + "\n" + obj.getSubThoroughfare();

//            Log.v("IGA", "Address" + add);
            // Toast.makeText(this, "Address=>" + add,
            // Toast.LENGTH_SHORT).show();

            // TennisAppActivity.showDialog(add);
        } catch (IOException e) {
            e.printStackTrace();
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return add;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initAnimations() {
        animFadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out);
        animFadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);

    }

    @OnClick(R.id.btnGetMyLocationNow)
    public void btnGetMyLocation(View view) {
        setCurrentLocation(null);
        if (mPopupWindow != null) {
            if (mPopupWindow.isShowing()) mPopupWindow.dismiss();
        }
        boGetCurrentLocBtn = true;
        boPlacePickerBtn = boMapClick = false;
        //the latlng will be lastLocation


    }

    @OnClick(R.id.btnRegisterThisPlace)
    public void btnRegisterThisPlaceAsHome(View view) {

    }

    @OnClick(R.id.btnSelectPlacePicker)
    public void btnPickPlace(View view) {
        try {

            startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);

        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    private void initVars(View view) {
        //ButterKnife.bind(getActivity(), view);
        //ButterKnife.bind(FrgHomeOwnerMap.this, view);
        //ButterKnife.bind(getContext(), getView());
        //ButterKnife.bind(this.getActivity(), getView());
        ButterKnife.bind(this, view);
        setMenuVisibility(true);
        setHasOptionsMenu(true);
        context = getActivity();
        dialogPlacePickerLoading = new SpotsDialog(context, ".....");


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();
        getLastLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (googleApiClient != null)
            googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void startLocationUpdates() {

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }


        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);


    }

    private void updatePopup(GoogleMap mMap, Marker currentMarker, View viewAppearOnIt) {
        if (currentMarker != null && mPopupWindow != null) {
            // currentMarker is visible
            if (mMap.getProjection().getVisibleRegion().latLngBounds.contains(currentMarker.getPosition())) {
                Point p = mMap.getProjection().toScreenLocation(currentMarker.getPosition());
                if (!mPopupWindow.isShowing()) {
                    mPopupWindow.showAtLocation(viewAppearOnIt, Gravity.NO_GRAVITY, 0, 0);
                }
//                mPopupWindow.update(p.x - mWidth / 2, p.y - mHeight + 100, -1, -1);
                //make the popup window above currentMarker
                mPopupWindow.update(p.x - mWidth / 2, p.y - (mHeight + 10), -1, -1);
            } else { // currentMarker outside screen
                mPopupWindow.dismiss();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (googleApiClient != null)
            stopLocationUpdates();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (googleApiClient != null)
            stopLocationUpdates();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (googleApiClient != null)
            stopLocationUpdates();
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }

    protected void stopLocationUpdates() {
        //Log.i(TAG, "stopLocationUpdates");
        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).


        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ) {
            return;
        }
        if (googleApiClient != null) {
            if (googleApiClient.isConnected())
                LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }

    }

    @Override
    public void onLocationChanged(Location location) {

        this.lastLocation = location;
        setCurrentLocation(new LatLng(location.getLatitude(), location.getLongitude()));

    }

    private void initMap(View rootView, Bundle savedInstanceState) {

        //mMapView = getView().findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        mMapView.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMap mMap) {
//                googleMap = mMap;
//                decoreMyMapView();
//                checkLocationPermission();
//
//            }
//        });
        mMapView.getMapAsync(this);

    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (lastLocation != null) {
            setCurrentLocation(null);
        }
    }

    private void setCurrentLocation(LatLng latLngCurrentLoc) {

        if (latLngCurrentLoc == null) {
            if (lastLocation != null) {
                latLngCurrentLoc = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            }
        }
        if (markerCurrentLocation != null) {
            markerCurrentLocation.remove();
        }
        if (markerRegisteredPlace != null) {
            markerRegisteredPlace.remove();
        }
        markerCurrentLocation = googleMap.addMarker(new MarkerOptions()
                        //.icon(BitmapDescriptorFactory.fromResource(R.drawable.car))
                        //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))//by default
                        //.icon(createAppropIconForThisDriver(userDriver.getCarCatName()))
                        //.icon(BitmapDescriptorFactory.fromResource(R.drawable.user))
                        .title("My Location")
                        //.snippet(setCarCatName(userDriver.getCarCatName()))
                        //.snippet(userDriver.getCarModelNumber() + "  " + userDriver.getCarCatName())
                        .position(latLngCurrentLoc)
                        .flat(false)
                //.draggable(false)
        );
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngCurrentLoc, 15.0f));
    }

    private void goToClickedPoint(LatLng latLngCurrentLoc) {

        if (latLngCurrentLoc == null) {
            latLngCurrentLoc = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
        }
        if (markerCurrentLocation != null) {
            markerCurrentLocation.remove();
        }
        if (markerRegisteredPlace != null) {
            markerRegisteredPlace.remove();
        }
        markerRegisteredPlace = googleMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.company))
                        .title("Put this place")
                        //.snippet(setCarCatName(userDriver.getCarCatName()))
                        //.snippet(userDriver.getCarModelNumber() + "  " + userDriver.getCarCatName())
                        .position(latLngCurrentLoc)
                        .flat(false)
                //.draggable(false)
        );
        //markerRegisteredPlace.setTag(MARKER_CURRENT_PLACE);
        //markerRegisteredPlace.showInfoWindow();
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngCurrentLoc, 15.0f));


        //showing the PopupWindow
        markerActive = markerRegisteredPlace;
        updatePopup(googleMap, markerActive, viewPopUpForMarker);


    }

    @Override
    public void onCameraIdle() {

        if (!b) {
            show(btnGetMyLocation);
            show(cardViewWhatToDo);
            //show(btnRegisterThisPlace);
            b = !b;
        }

        if (b2) {
            hide(btnGetMyLocation);
            b2 = false;
        }

    }

    private void show(final View view) {

        animFadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animFadeIn);

    }

    @Override
    public void onCameraMoveCanceled() {
    }

    @Override
    public void onCameraMove() {
        if (markerActive != null) {
            updatePopup(googleMap, markerActive, viewPopUpForMarker);
        }
    }

    @Override
    public void onCameraMoveStarted(int i) {
        if (b) {
            hide(btnGetMyLocation);
            hide(cardViewWhatToDo);
            //hide(btnRegisterThisPlace);
            b = !b;
        }


    }

    private void hide(final View view) {

        animFadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animFadeOut);


    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onInfoWindowClose(Marker marker) {


    }

    @Override
    public void onInfoWindowLongClick(Marker marker) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
        goToClickedPoint(latLng);
        boMapClick = true;
        boGetCurrentLocBtn = boPlacePickerBtn = false;
        latLngFromMapClicking = latLng;


    }

    @Override
    public void onMapLoaded() {


    }

    private void decoreMyMapView() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(false);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.getUiSettings().setCompassEnabled(false);
        googleMap.setTrafficEnabled(true);

        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        googleMap.getUiSettings().setTiltGesturesEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        //or myMap.getUiSettings().setAllGesturesEnabled(true);


        googleMap.setOnCameraMoveListener(this);
        googleMap.setOnCameraMoveStartedListener(this);
        googleMap.setOnCameraMoveCanceledListener(this);
        googleMap.setOnCameraIdleListener(this);
        googleMap.setOnMapClickListener(this);
        googleMap.setOnInfoWindowClickListener(this);
        googleMap.setOnInfoWindowCloseListener(this);
        googleMap.setOnInfoWindowLongClickListener(this);
        googleMap.setOnMarkerClickListener(this);

        //CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(this.getActivity());
        // googleMap.setInfoWindowAdapter(adapter);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (checkPlayServices()) {
                        buildGoogleApiClient();
                        createLocationRequest();
                        getLastLocation();

                    }
                }
            }
        }
    }

    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();


    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    public void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);

        } else {
            if (checkPlayServices()) {
                buildGoogleApiClient();
                createLocationRequest();
                getLastLocation();
            }
        }

    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        if (resultCode != ConnectionResult.SUCCESS) {

            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(), PLAY_SERVICES_REF_REQUEST).show();
            } else {
                Toast.makeText(getActivity(), "Google Play Services are not available", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }

            return false;
        }

        return true;

    }

    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setSmallestDisplacement(DISTANCE);
    }

    private synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        googleApiClient.connect();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                }
                break;
        }

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == ActionBarActivity.RESULT_OK) {
                Place place = PlacePicker.getPlace(data, getActivity());
                //String toastMsg = String.format("Place: %s", place.getName());

                latLngFromPlacePicker = place.getLatLng();
                boPlacePickerBtn = true;
                boGetCurrentLocBtn = boMapClick = false;

                goToClickedPoint(place.getLatLng());

            } else {

            }


        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        decoreMyMapView();
        //checkLocationPermission();
        //getLastLocation();

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        //String position = (String) marker.getTag();

        if (marker.equals(markerCurrentLocation)) {
            markerActive = markerCurrentLocation;
            updatePopup(googleMap, markerActive, viewPopUpForMarker);
        }
//        if (position == MARKER_CURRENT_PLACE) {
//            updatePopup(googleMap, markerCurrentLocation, viewPopUpForMarker);
//            Log.e("ahmed20130074", "clicked");
//        } else {
//            Log.e("ahmed20130074", "click me please");
//        }


        return true;
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        public GestureDetector gestureDetector;
        public ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private Activity context;

        public CustomInfoWindowAdapter(Activity context) {
            this.context = context;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            View view = context.getLayoutInflater().inflate(R.layout.custominfowindow, null);
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            View view = context.getLayoutInflater().inflate(R.layout.custominfowindow, null);

            return view;
        }
    }
}
