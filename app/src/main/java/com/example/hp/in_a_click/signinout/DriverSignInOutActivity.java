package com.example.hp.in_a_click.signinout;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.hp.in_a_click.R;
import com.example.hp.in_a_click.activities.ActionBarActivity;
import com.example.hp.in_a_click.model.UserDriver;
import com.example.hp.in_a_click.model.UserHomeOwner;
import com.example.hp.in_a_click.model.UserNormal;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.github.javiersantos.bottomdialogs.BottomDialog;
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.hbb20.CountryCodePicker;
import com.irozon.sneaker.Sneaker;
import com.rey.material.widget.Switch;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;

public class DriverSignInOutActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    public static final String WHO = "WHO";

    public final static String TAG_HOME_OWNER = "TAG_HOME_OWNER";
    public final static String TAG_DRIVER = "TAG_DRIVER";
    public final static String TAG_NORMAL_USER = "TAG_NORMAL_USER";

    private static final String TAG = "PhoneAuthActivity";
    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";
    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;
    private static final int RC_SIGN_IN = 7;
    public static final String TAG_WORKERS = "Workers";
    public static final String TAG_HOMEOWNER_HOMES = "Homes";
    LinearLayout llSelectedRole = null;
    TextView tvRegSelectedRole = null;
    FirebaseDatabase firebaseDatabase = null;
    DatabaseReference refUsers = null;
    FirebaseAuth firebaseAuth = null;
    //    @BindView(R.id.btnSignIn)
//    Button btnSIgnIn;
//    @BindView(R.id.btnRegister)
//    Button btnRegister;
    RelativeLayout rlMainView;
    AlertDialog alertDialog = null;
    AlertDialog.Builder builder = null;
    Button btnRegister, btnSIgnIn;
    SpotsDialog waitLogin = null;
    boolean b = true, b2 = true;
    TextView textView1, textView2;
    //    @OnClick(R.id.btnRegister)
//    public void onClickRegister(View view) {
//        showRegister();
//    }
//
//    @OnClick(R.id.btnLogin)
//    public void onClickLogin(View view) {
//        showLogin();
//    }
    View wall1, wall2;
    boolean anim = true;
    LinearLayout linearLayout = null;
    DatabaseReference refWorkers = null;
    String strDriverOrHome = "";
    TextView connectWithSocial = null;
    Switch aSwitchLoginVia = null;
    Context context = null;
    TextView tvLoginVia = null;
    Animation animScaleUp, animScaleDown, animWobble;
    TextView tvErrorLogin = null;
    View viewLogin = null, viewRegister;
    TextView tvErrorRegister = null;
    AlertDialog alertDialogRegister, alertDialogLogin = null;
    CheckBox cbUserLogin, cbWorkerLogin, cbUserReg, cbWorkerReg;
    View view;
    LoginButton loginButton = null;
    EditText etEmailReg, etPassReg, etNameReg, etPhoneReg;
    View viewSocial = null;
    boolean mVerificationInProgress = false;//by default
    SpotsDialog waitDialog = null;
    String countryCode = "";
    CountryCodePicker countryCodePicker = null;
    BottomDialog bottomDialogSocialMedia = null;
    String fbEmail = "", fbFirstName, fbLastName, fbGender;
    FirebaseAuth.AuthStateListener authStateListener = null;
//    GoogleSignInClient mGoogleSignInClient = null;
    GoogleApiClient googleApiClient = null;
    BottomDialog bottomDialogWorkerOptios = null;
    String googleUserEmail = "", googleUserPhone = "", googleUserName = "";
    View.OnClickListener clickListeneerGoogleSignin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            signInWithGooglePlus();
//            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(context);
//            if (acct != null) {
//                String personName = acct.getDisplayName();
//                String personGivenName = acct.getGivenName();
//                String personFamilyName = acct.getFamilyName();
//                String personEmail = acct.getEmail();
//                String personId = acct.getId();
//                Uri personPhoto = acct.getPhotoUrl();
//
//                showRegister();
//                etEmailReg.setText(personEmail);
//                etNameReg.setText(personName);
//
//
//            } else {
//                signInWithGooglePlus();
//            }
        }
    };
    boolean emailExist = false;
    boolean errorType = false;
    String selectedRoleWorkerLogin = "";
    EditText etPhoneNumber = null;
    CountryCodePicker ccpRegister = null;
    EditText etCity = null;
    SpotsDialog waitSendingEmail;
    String from, to, subject, messageBody;
    GoogleApiClient gacPlacePicker = null;
    BottomDialog bottomDialogResendCode = null;
    TextWatcher tw1 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().trim().length() == 1) {
                etDigit2.requestFocus();
            } else {
                //fabEnterDigits.hide();
            }
        }
    };
    TextWatcher tw3 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().trim().length() == 1) {
                etDigit4.requestFocus();
            } else {
                etDigit2.requestFocus();
            }
        }
    };
    TextWatcher tw5 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().trim().length() == 1) {
                etDigit6.requestFocus();
                //fabEnterDigits.setVisibility(View.VISIBLE);
                //fabEnterDigits.show();
            } else {
                etDigit4.requestFocus();
                //fabEnterDigits.hide();
            }
        }
    };
    EditText etDigit1, etDigit2, etDigit3, etDigit4, etDigit5, etDigit6;
    TextWatcher tw2 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().trim().length() == 1) {
                etDigit3.requestFocus();
            } else {
                etDigit1.requestFocus();
            }
        }
    };
    TextView tvTimeResendCode = null;
    TextView tvErrorEnterDigits = null;
    TextWatcher tw6 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().trim().length() == 1) {
                // etDigit3.requestFocus();
//                fabEnterDigits.setVisibility(View.VISIBLE);
//                fabEnterDigits.show();
                tvErrorEnterDigits.setVisibility(View.GONE);
            } else {
                etDigit5.requestFocus();
//                fabEnterDigits.hide();
            }
        }
    };
    FloatingActionButton fabEnterDigits = null;
    TextWatcher tw4 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().trim().length() == 1) {
                etDigit5.requestFocus();
//                fabEnterDigits.setVisibility(View.VISIBLE);
//                fabEnterDigits.show();
            } else {
                etDigit3.requestFocus();
                fabEnterDigits.hide();
            }
        }
    };
    Button btnResndCode = null;
    AlertDialog alertDialogEnterDigits = null;
    Button btnAlertResendCode = null;
    Handler handler;
    Runnable runnable = null;
    boolean stopHandler = true;
    int count = 0;
    private String strWorkerOrUser = "";
    private CallbackManager callbackManager;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private String code = "";
    //    private void setCountryCode() {
//        Spinner spinnerCountries = (Spinner) findViewById(R.id.spinner);
//        // populate country codes
//        final CountryCodesAdapter ccList = new CountryCodesAdapter(this,
//                android.R.layout_select_role.simple_list_item_1,
//                android.R.layout_select_role.simple_spinner_dropdown_item);
//        PhoneNumberUtil util = PhoneNumberUtil.getInstance();
//        Set<String> ccSet = getSupportedRegions(util);
//        for (String cc : ccSet)
//            ccList.add(cc);
//
//        ccList.sort(new Comparator<CountryCodesAdapter.CountryCode>() {
//            public int compare(CountryCodesAdapter.CountryCode lhs, CountryCodesAdapter.CountryCode rhs) {
//                return lhs.regionName.compareTo(rhs.regionName);
//            }
//        });
//        spinnerCountries.setAdapter(ccList);
//        spinnerCountries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                ccList.setSelected(position);
//            }
//
//            public void onNothingSelected(AdapterView<?> parent) {
//                // TODO Auto-generated method stub
//            }
//        });
//
//        //Your country will get automatically selected in the spinner.
//        // FIXME this doesn't consider creation because of configuration change
//        Phonenumber.PhoneNumber myNum = getMyNumber(this);
//        CountryCodesAdapter.CountryCode cc = new CountryCodesAdapter.CountryCode();
//        if (myNum != null) {
//            cc.regionCode = util.getRegionCodeForNumber(myNum);
//            if (cc.regionCode == null)
//                cc.regionCode = util.getRegionCodeForCountryCode(myNum.getCountryCode());
//            spinnerCountries.setSelection(ccList.getPositionForId(cc));
//            //Toast.makeText(this, String.valueOf(myNum.getNationalNumber()), Toast.LENGTH_SHORT).show();
//        } else {
//            final TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//            final String regionCode = tm.getSimCountryIso().toUpperCase(Locale.US);
//            cc.regionCode = regionCode;
//            cc.countryCode = util.getCountryCodeForRegion(regionCode);
//            spinnerCountries.setSelection(ccList.getPositionForId(cc));
//            //Toast.makeText(this, cc.regionCode+"   "+cc.countryCode, Toast.LENGTH_SHORT).show();
//            countryCode = "+" + cc.countryCode;
//        }
//
//
//    }
//     class FragmentModalBottomSheet extends BottomSheetDialogFragment {
//
//        private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                switch (newState) {
//                    case BottomSheetBehavior.STATE_COLLAPSED: {
//                        //Log.d("BSB","collapsed") ;
//
//                    }
//                    case BottomSheetBehavior.STATE_SETTLING: {
//                        //Log.d("BSB","settling") ;
//
//                    }
//                    case BottomSheetBehavior.STATE_EXPANDED: {
//                        //Log.d("BSB","expanded") ;
//
//                    }
//                    case BottomSheetBehavior.STATE_HIDDEN: {
//                        //Log.d("BSB" , "hidden") ;
//                        llSelectedRole.setVisibility(View.VISIBLE);
//                        tvRegSelectedRole.setText(strDriverOrHome);
//
//                        dismiss();
//                    }
//                    case BottomSheetBehavior.STATE_DRAGGING: {
//                        //Log.d("BSB","dragging") ;
//
//                    }
//                }
//            }
//
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//                // Log.d("BSB","sliding " + slideOffset ) ;
//            }
//        };
//
////        public  FragmentModalBottomSheet newInstance(String title) {
////            FragmentModalBottomSheet frag = new FragmentModalBottomSheet();
////            Bundle args = new Bundle();
////            args.putString("title", title);
////            frag.setArguments(args);
////            return frag;
////        }
//
//
//
//        @Override
//        public void setupDialog(Dialog dialog, int style) {
//            super.setupDialog(dialog, style);
//            View contentView = View.inflate(getContext(), R.layout.layout_select_role, null);
//            dialog.setContentView(contentView);
//
//            ///Accessing the select role worker options view
//            //showWorkerOption(contentView);
//
//            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
//            CoordinatorLayout.Behavior behavior = params.getBehavior();
//
//            if (behavior != null && behavior instanceof BottomSheetBehavior) {
//                ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
//            }
//        }
//    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private int PLACE_PICKER_REQUEST = 13;

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }


    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }


        //for session
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
           finish();
            startActivity(new Intent(getApplicationContext(), ActionBarActivity.class));
            return;
        }
        setContentView(R.layout.activity_driver_sign_in_out);
        ButterKnife.bind(this);
        init();
        addBgStartImageView();
        initGoogleLoginButton();
        initPlacPicker();


        ///initPhoneCompRegisteration();
        //new GetComplexCode(getApplicationContext()).getComplexCode();

    }

    EditText etCompPhoneRegFirstName, etCompPhoneRegLastName;

    private void initPhoneCompRegisteration() {

        final View view =
                LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.layout_complete_phone, null);

        final TextView tvError = view.findViewById(R.id.tvErrorRegister);
        tvError.setVisibility(View.GONE);

        etCompPhoneRegFirstName = ((TextInputLayout) view.findViewById(R.id.tilFirstName))
                .getEditText();
        etCompPhoneRegLastName = ((TextInputLayout) view.findViewById(R.id.tilLastName))
                .getEditText();

        final View btnNext1 = view.findViewById(R.id.btnNext1);
        final View btnNext2 = view.findViewById(R.id.btnNext2);

        btnNext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etCompPhoneRegFirstName.getText())) {
                    tvError.setVisibility(View.VISIBLE);
                    tvError.setText("Enter Firstname");
                    tvError.startAnimation(animWobble);
                    return;
                }
                btnNext1.setVisibility(View.GONE);
                btnNext2.setVisibility(View.VISIBLE);
                tvError.setVisibility(View.GONE);
                etCompPhoneRegLastName.setVisibility(View.VISIBLE);


            }
        });
        btnNext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etCompPhoneRegLastName.getText())) {
                    tvError.setVisibility(View.INVISIBLE);
                    tvError.startAnimation(animWobble);
                    tvError.setText("Enter Lastname");

                    return;
                }
                tvError.setVisibility(View.GONE);
                completePhoneRegisterFirebase(userPhone);
            }
        });
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context)
                .setView(view);
        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        if (!alertDialog.isShowing()) {
            alertDialog.show();
        }


    }

    FirebaseUser userPhone = null;

    private void completePhoneRegisterFirebase(FirebaseUser firebaseUser) {
        //saving the phone user data in firebase database
        final SpotsDialog dialogSaving = new SpotsDialog(context, "Saving data");
        dialogSaving.show();

        if (firebaseUser == null)
            return;

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(firebaseUser.getUid());
        UserNormal userNormal = new UserNormal("", "",
                etCompPhoneRegFirstName.getText().toString() + " " +
                        etCompPhoneRegLastName.getText().toString(),
                etPhoneNumber.getText().toString());
        reference.setValue(userNormal, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (dialogSaving.isShowing())
                    dialogSaving.dismiss();
                if (cbUserLogin.isChecked()) {
                    startActivity(new Intent(DriverSignInOutActivity.this, ActionBarActivity.class).putExtra(WHO, TAG_NORMAL_USER));
                } else {
                    if (selectedRoleWorkerLogin == TAG_DRIVER) {
                        startActivity(new Intent(DriverSignInOutActivity.this, ActionBarActivity.class).putExtra(WHO, TAG_DRIVER));
                    } else {
                        startActivity(new Intent(DriverSignInOutActivity.this, ActionBarActivity.class).putExtra(WHO, TAG_HOME_OWNER));
                    }
                }
                finish();
            }
        });


    }

    private void addBgStartImageView() {
        Glide.with(context)
                .load(R.drawable.bg_start)
                //.centerCrop()
                .into((ImageView) findViewById(R.id.ivBgStart));

    }

    private void initPlacPicker() {

        gacPlacePicker = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();


    }

    private boolean checkIfEmailExistForSmrUserTest(String insertedEmailWhileReg) {
        Query query = refUsers.orderByChild("userEmail").equalTo(insertedEmailWhileReg);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //here means the value exist
//here means the value not exist
                emailExist = dataSnapshot.exists();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return emailExist;
    }

    private boolean checkAccountEmailExistInFirebase(String email) {
        FirebaseAuth.getInstance().fetchProvidersForEmail(email).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                emailExist = !task.getResult().getProviders().isEmpty();
            }
        });
        return emailExist;
    }

    private void initGoogleLoginButton() {


        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
//        mGoogleSignInClient = GoogleSignIn.getClient(DriverSignInOutActivity.this, gso);

//        googleApiClient = new GoogleApiClient.Builder(this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API)
//                .enableAutoManage(this, this)
//                .build();


        // Set the dimensions of the sign-in button.
        SignInButton signInButton = viewSocial.findViewById(R.id.loginGooglePlus);
        ///signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(clickListeneerGoogleSignin);


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        //Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();

//                            Log.e("email", user.getEmail());
//                            Log.e("phone", user.getPhoneNumber());
//                            Log.e("Name", user.getDisplayName());
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Toast.makeText(FacebookLoginActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (callbackManager != null) {
            // Pass the activity result back to the Facebook SDK
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
           // Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            //handleSignInResult(task);
            //firebaseAuthWithGoogle(task);
        }
//        if (requestCode == RC_SIGN_IN) {
//            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            handleSignInResult(googleSignInResult);
//
//        }
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                StringBuilder stBuilder = new StringBuilder();
                String placename = String.format("%s", place.getName());
                String latitude = String.valueOf(place.getLatLng().latitude);
                String longitude = String.valueOf(place.getLatLng().longitude);
                String address = String.format("%s", place.getAddress());
                stBuilder.append("Name: ");
                stBuilder.append(placename);
                stBuilder.append("\n");
                stBuilder.append("Latitude: ");
                stBuilder.append(latitude);
                stBuilder.append("\n");
                stBuilder.append("Logitude: ");
                stBuilder.append(longitude);
                stBuilder.append("\n");
                stBuilder.append("Address: ");
                stBuilder.append(address);

                // etCity.setText(stBuilder.toString());
                etCity.setText(place.getAddress().toString());
            }
        }
    }

    private void signInWithGooglePlus() {

        //Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
//        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            //Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
            googleUserEmail = account.getEmail();
            googleUserName = account.getDisplayName();
            account.getFamilyName();
            //googleUserName = account.getGivenName();
            showRegister();
            etEmailReg.setText(googleUserEmail);
            etNameReg.setText(googleUserName);
            //etPhoneReg.setText(getPhoneNumber());
            // Signed in successfully, show authenticated UI.
            //updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
        }
    }

    private void handleSignInResult(GoogleSignInResult googleSignInResult) {

        if (googleSignInResult.isSuccess()) {
            //Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
            googleUserEmail = googleSignInResult.getSignInAccount().getEmail();
            googleUserName = googleSignInResult.getSignInAccount().getDisplayName() + " " + googleSignInResult.getSignInAccount().getFamilyName();
            googleSignInResult.getSignInAccount().getGivenName();
            showRegister();
            etEmailReg.setText(googleUserEmail);
            etNameReg.setText(googleUserName);
            //etPhoneReg.setText(getPhoneNumber());

        } else {

        }
    }

    public String getPhoneNumber() {
        TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();
        return mPhoneNumber;
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        //Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Toast.makeText(GoogleSignInActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void firebaseAuthWithGoogle(Task<GoogleSignInAccount> task) {
        //Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider
                .getCredential(task.getResult().getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Toast.makeText(GoogleSignInActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    private void init() {

        context = this;

        btnRegister = findViewById(R.id.btnRegister);
        btnSIgnIn = findViewById(R.id.btnSignIn);


        btnSIgnIn.setOnClickListener(this);
        btnRegister.setOnClickListener(this);


//        CalligraphyConfig.initDefault(
//                new CalligraphyConfig.Builder()
//                        .setDefaultFontPath("fonts/IndieFlower.ttf")
//                        .setFontAttrId(R.attr.fontPath)
//                        .build()
//        );

        viewSocial = LayoutInflater.from(context).inflate(R.layout.connect_with_social, null);

        rlMainView = findViewById(R.id.rlMainView);
        firebaseAuth = FirebaseAuth.getInstance();
//        firebaseAuth.useAppLanguage();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    // NOTE: this Activity should get onpen only when the user is not signed in, otherwise
                    // the user will receive another verification email.
                    //sendVerificationEmail();
                } else {
                    // User is signed out

                }
            }
        };
        // firebaseAuth.addAuthStateListener(authStateListener);

        firebaseDatabase = FirebaseDatabase.getInstance();
        refUsers = firebaseDatabase.getReference("Users");
        refWorkers = firebaseDatabase.getReference("Workers");


        connectWithSocial = findViewById(R.id.connectWithSocial);
        connectWithSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSocialBottomDialog();

            }
        });

        animScaleDown = AnimationUtils.loadAnimation(context, R.anim.scale_down);
        animScaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_up);
        animWobble = AnimationUtils.loadAnimation(context, R.anim.wobble);

    }

    private void showSocialBottomDialog() {


        // Initialize your instance of callbackManager//
        callbackManager = CallbackManager.Factory.create();
        loginButton = viewSocial.findViewById(R.id.loginFacebook);
        //loginButton.setReadPermissions("email", "public_profile");
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // Log.d(TAG, "facebook:onSuccess:" + loginResult);
                Toast.makeText(context, "loginButton onSuccess", Toast.LENGTH_LONG).show();
//                handleFacebookAccessToken(loginResult.getAccessToken());
//                getDataFromFaceBookLogin(loginResult);
            }

            @Override
            public void onCancel() {
                //Log.d(TAG, "facebook:onCancel");
                // ...
                Toast.makeText(context, "loginButton onCancel", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                //Log.d(TAG, "facebook:onError", error);error
                // ...
                Toast.makeText(context, "loginButton onError", Toast.LENGTH_LONG).show();
            }
        });

        // Register your callback//
        LoginManager.getInstance().registerCallback(callbackManager,

                // If the login attempt is successful, then call onSuccess and pass the LoginResult//
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // Print the user’s ID and the Auth Token to Android Studio’s Logcat Monitor//
//                        Log.d(TAG, "User ID: " +
//                                loginResult.getAccessToken().getUserId() + "\n" +
//                                "Auth Token: " + loginResult.getAccessToken().getToken());
                        // Toast.makeText(context, "LoginManager onSuccess", Toast.LENGTH_LONG).show();

                        //handleFacebookAccessToken(loginResult.getAccessToken());
                        getDataFromFaceBookLogin(loginResult);

                    }

                    // If the user cancels the login, then call onCancel//
                    @Override
                    public void onCancel() {
                        Toast.makeText(context, "LoginManager onCancel", Toast.LENGTH_LONG).show();
                    }

                    // If an error occurs, then call onError//
                    @Override
                    public void onError(FacebookException exception) {

                    }
                });

//        new BottomDialog.Builder(this)
//                .setCustomView(viewSocial)
//                .setCancelable(false)
//                .setTitle("Connect With Social")
//                .setNegativeText("Exit")
//                .setNegativeTextColorResource(R.color.colorAccent)
//                .setIcon(R.drawable.share)
//                .onNegative(new BottomDialog.ButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull BottomDialog dialog) {
//                        dialog.dismiss();
//                    }
//                })
//                .show();

        bottomDialogSocialMedia = new BottomDialog.Builder(DriverSignInOutActivity.this)
                .setCustomView(viewSocial)
                .setCancelable(true)
                .setTitle("Connect With Social")
                .setNegativeText("Exit")
                .setNegativeTextColorResource(R.color.colorAccent)
                .setIcon(R.drawable.share)
                .onNegative(new BottomDialog.ButtonCallback() {
                    @Override
                    public void onClick(@NonNull BottomDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build();

        bottomDialogSocialMedia.show();

    }

    private void getDataFromFaceBookLogin(LoginResult loginResult) {

        waitDialog = new SpotsDialog(context, "Please wait...");
        waitDialog.show();
        Log.e("accessToken", loginResult.getAccessToken().toString());

        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.e("ahmed20130074", response.toString());
                Log.e("ahmed20130074", object.toString());
                //Bundle bFacebookData = getFacebookData(object);
                //bottomDialogSocialMedia.dismiss();


                try {
//                    JSONObject jsonObject = new JSONObject(response.toString());
//                    Log.e("ahmed20130074", jsonObject.toString());
//                    JSONObject jsonObject1 = jsonObject.getJSONObject("graphObject");
//                    Log.e("ahmed20130074", jsonObject1.toString());
//                    fbEmail = jsonObject1.getString("email");
//                    fbFirstName = jsonObject1.getString("first_name");
//                    fbLastName = jsonObject1.getString("last_name");
//                    fbGender = jsonObject1.getString("gender");


                    waitDialog.dismiss();
                    //bottomDialogSocialMedia.dismiss();
                    showRegister();
                    if (object.has("email")) {
                        fbEmail = object.getString("email");
                        etEmailReg.setText(fbEmail);
                    }
                    if (object.has("first_name") && object.has("last_name")) {
                        fbFirstName = object.getString("first_name");
                        fbLastName = object.getString("last_name");
                        etNameReg.setText(fbFirstName + " " + fbLastName);
                    } else {
                        if (object.has("first_name")) {
                            fbFirstName = object.getString("first_name");
                            etNameReg.setText(fbFirstName);
                        } else {
                            if (object.has("last_name")) {
                                fbLastName = object.getString("last_name");
                                etNameReg.setText(fbLastName);
                            }
                        }
                    }
                    if (object.has("gender")) {
                        fbGender = object.getString("gender");
                    }


//                    Profile profile = Profile.getCurrentProfile();
//                    String id = profile.getId();
//                    String link = profile.getLinkUri().toString();
                    ///Log.i("Link",link);
//                    if (Profile.getCurrentProfile() != null) {
//                         Log.i("Login", "ProfilePic" + Profile.getCurrentProfile().getProfilePictureUri(200, 200));
//                    }

//                    Log.i("Login" + "Email", fbEmail);
//                    Log.i("Login" + "FirstName", fbFirstName);
//                    Log.i("Login" + "LastName", fbLastName);
//                    Log.i("Login" + "Gender", fbGender);
//                    Toast.makeText(context, fbEmail, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(context, fbFirstName, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(context, fbLastName, Toast.LENGTH_SHORT).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                    showMessage(context, e.getMessage().toString());
                }

            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,first_name,last_name,gender, location");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));

            return bundle;
        } catch (JSONException e) {
            Log.d(TAG, "Error parsing JSON");
        }
        return null;
    }

    @Override
    public void onClick(View v) {

        if (v.equals(btnRegister)) {
            showRegister();
        }
        if (v.equals(btnSIgnIn)) {
            showLogin();
        }


    }

    private void showLogin() {
        LayoutInflater layoutInflater = getLayoutInflater();
        viewLogin = layoutInflater.inflate(R.layout.layout_signin, null);
        tvErrorLogin = viewLogin.findViewById(R.id.tvErrorLogin);

        cbUserLogin = viewLogin.findViewById(R.id.cbUser);
        cbWorkerLogin = viewLogin.findViewById(R.id.cbWorker);
        cbUserLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbWorkerLogin.setChecked(false);
                } else {
                    cbWorkerLogin.setChecked(true);
                }
            }
        });
        cbWorkerLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //showWorkerOptMenu(cbWorkerLogin);
                    showWorkerOptMenu();
                    cbUserLogin.setChecked(false);
                } else {
                    cbUserLogin.setChecked(true);
                }
            }
        });
        cbWorkerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showWorkerOptMenu();
            }
        });


        final View view1 = viewLogin.findViewById(R.id.llLoginWithEmail);
        final View view2 = viewLogin.findViewById(R.id.llLoginWithPhone);


        tvLoginVia = viewLogin.findViewById(R.id.tvLoginVia);
        aSwitchLoginVia = viewLogin.findViewById(R.id.switchLogin);
        aSwitchLoginVia.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(Switch view, boolean checked) {
                if (checked) {
                    tvLoginVia.setText("Login via Phone Number");

                    view2.startAnimation(animScaleDown);
                    view1.setVisibility(View.GONE);

                    view2.startAnimation(animScaleUp);
                    view2.setVisibility(View.VISIBLE);

                    tvErrorLogin.setVisibility(View.GONE);
                    tvErrorLogin.setText("");


                } else {
                    tvLoginVia.setText("Login vial Email Address");

                    view2.startAnimation(animScaleDown);
                    view2.setVisibility(View.GONE);

                    view1.startAnimation(animScaleUp);
                    view1.setVisibility(View.VISIBLE);


                }
            }
        });

        builder = new AlertDialog.Builder(this)
                //.setTitle("Login")
                ///.setMessage("Login via your email address")
                .setView(viewLogin)
                .setPositiveButton("Login", null)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
        ;
        alertDialogLogin = builder.create();
        alertDialogLogin.setCancelable(false);
        alertDialogLogin.setCanceledOnTouchOutside(false);
        alertDialogLogin.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (aSwitchLoginVia.isChecked()) {
                            loginUserWithPhoneNumber();
                        } else {
                            loginUserWithEmail();
                        }

                    }
                });
            }
        });
        alertDialogLogin.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
        if (!alertDialogLogin.isShowing()) {
            alertDialogLogin.show();
        }


    }

    private void showWorkerOptMenu() {

//        new MaterialDialog.Builder(this)
//                //.title(R.string.title)
//                .items(R.array.options)
//                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
//                    @Override
//                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
//                        //Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
//                        selectedRoleWorkerLogin = (String) text;
//                        if (selectedRoleWorkerLogin.equals("Driver")) {
//                            selectedRoleWorkerLogin = TAG_DRIVER;
//                            //Toast.makeText(context, selectedRoleWorkerLogin, Toast.LENGTH_SHORT).show();
//                        } else {
//                            selectedRoleWorkerLogin = TAG_HOME_OWNER;
//                            //Toast.makeText(context, selectedRoleWorkerLogin, Toast.LENGTH_SHORT).show();
//                        }
//                        return true;
//                    }
//                })
//                .positiveText("Select")
//                .show();


    }

    public void showWorkerOptMenu(View button) {
        PopupMenu popup = new PopupMenu(this, button);
        popup.getMenuInflater().inflate(R.menu.menu_worker_options, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        });

        popup.show();
    }

    public void loginUserWithEmail() {
        final EditText etEmail = viewLogin.findViewById(R.id.etEmail);
        final EditText etPass = viewLogin.findViewById(R.id.etPassword);


        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            //Snackbar.make(rlMainView, "Enter Email", Snackbar.LENGTH_SHORT).show();
            etEmail.requestFocus();
            tvErrorLogin.setVisibility(View.VISIBLE);
            tvErrorLogin.setText("Enter Email");
            tvErrorLogin.startAnimation(AnimationUtils.loadAnimation(context, R.anim.wobble));
            return;
        }
        if (!isValidEmail(etEmail.getText().toString())) {
            //Snackbar.make(rlMainView, "Enter Email", Snackbar.LENGTH_SHORT).show();
            etEmail.requestFocus();
            tvErrorLogin.setVisibility(View.VISIBLE);
            tvErrorLogin.setText("Enter Valid Email");
            tvErrorLogin.startAnimation(AnimationUtils.loadAnimation(context, R.anim.wobble));
            return;
        }
        if (TextUtils.isEmpty(etPass.getText().toString())) {
//            Snackbar.make(rlMainView, "Enter Password", Snackbar.LENGTH_SHORT).show();
            etPass.requestFocus();
            tvErrorLogin.setVisibility(View.VISIBLE);
            tvErrorLogin.setText("Enter Password");
            tvErrorLogin.startAnimation(AnimationUtils.loadAnimation(context, R.anim.wobble));
            return;
        }

        if (etPass.getText().toString().trim().length() < 6) {
            //Snackbar.make(rlMainView, "Password  is too short", Snackbar.LENGTH_SHORT).show();
            etPass.requestFocus();
            tvErrorLogin.setVisibility(View.VISIBLE);
            tvErrorLogin.setText("Password  is too short");
            tvErrorLogin.startAnimation(AnimationUtils.loadAnimation(context, R.anim.wobble));
            return;
        }

        tvErrorLogin.setVisibility(View.GONE);
        waitLogin = new SpotsDialog(DriverSignInOutActivity.this, "Please wait");
        //btnSIgnIn.setEnabled(false);
        waitLogin.show();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(etEmail.getText().toString(), etPass.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                    }
                })
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Log.d("TAG", "signInWithEmail:onComplete:" + task.isSuccessful());
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
//                        if (!task.isSuccessful()) {
//                            //Log.w("TAG", "signInWithEmail:failed", task.getException());
//                            showMessage(context, "Email or Password is invalid\nPlease check them\nThen try again");
//                            Sneaker.with(DriverSignInOutActivity.this)
//                                    .setTitle("Error - Email or Password is invalid")
//                                    .setMessage("Check internet connection !!!")
//                                    .sneakError();
//                        } else {
//                            checkIfEmailVerified(task);
//                        }

                        if (task.isComplete() && task.isSuccessful()) {
                            checkIfThisUserDriverOrNormal(task.getResult().getUser());
                            Log.e("OnComplete", "Success");
                        }

                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (waitLogin.isShowing()) {
                            waitLogin.dismiss();
                        }
                        // btnSIgnIn.setEnabled(true);
                        //Snackbar.make(rlMainView, "Login Failed", Snackbar.LENGTH_SHORT).show();
                        //showMessage(context, "Email or Password is invalid\nPlease check them\nThen try again");
                        Sneaker.with(DriverSignInOutActivity.this)
                                .setTitle("Error!!")
                                .setMessage("Check internet connection !!!")
                                .sneakError();
                    }
                });

    }

    String userType = "";

    private void checkIfThisUserDriverOrNormal(final FirebaseUser user) {

        final DatabaseReference refUsers = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(user.getUid()),
                refDrivers = FirebaseDatabase.getInstance().getReference("Workers")
                        .child(DriverSignInOutActivity.TAG_DRIVER)
                        .child(user.getUid()),
                refHomeOwners = FirebaseDatabase.getInstance().getReference("Workers")
                        .child(DriverSignInOutActivity.TAG_HOME_OWNER)
                        .child(user.getUid());

//        ////Query queryUsers = refUsers.startAt()
//        refUsers.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
//                    if (dialogSession.isShowing())dialogSession.dismiss();
//                    Intent intent = new Intent(DriverSignInOutActivity.this, MenuActivity.class);
//                    intent.putExtra(WHO, TAG_NORMAL_USER);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    userType = "";
//                }
//                //refUsers.removeEventListener(this);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        refDrivers.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
//                    if (dialogSession.isShowing())dialogSession.dismiss();
//                    Intent intent = new Intent(DriverSignInOutActivity.this, MenuActivity.class);
//                    intent.putExtra(WHO, TAG_DRIVER);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    userType = "";
//                }
//                //refDrivers.removeEventListener(this);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        refHomeOwners.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {

                    if (dialogSession.isShowing())dialogSession.dismiss();


                    Intent intent = new Intent(DriverSignInOutActivity.this, ActionBarActivity.class);
                    intent.putExtra(WHO, TAG_HOME_OWNER);
                    startActivity(intent);
                    finish();

                } else {
                    userType = "";
                }
                //refHomeOwners.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void loginUserWithPhoneNumber() {
        etPhoneNumber = viewLogin.findViewById(R.id.etPhoneNumber);

        //assign the country code picker to the phone number edittext
        countryCodePicker = viewLogin.findViewById(R.id.countryCodePicker);
        countryCodePicker.registerCarrierNumberEditText(etPhoneNumber);
        // countryCode = countryCodePicker.getSelectedCountryCode().toString();
        countryCode = countryCodePicker.getSelectedCountryCodeWithPlus();

//        Log.e("ccp20130074",  countryCodePicker.getSelectedCountryCode().toString());
//        Log.e("ccp20130074", countryCodePicker.getSelectedCountryCodeWithPlus().toString());


        if (etPhoneNumber.getText().toString().trim().length() < 6) {
            //Snackbar.make(rlMainView, "Password  is too short", Snackbar.LENGTH_SHORT).show();
            etPhoneNumber.requestFocus();
            tvErrorLogin.setVisibility(View.VISIBLE);
            tvErrorLogin.setText("Enter Phone Number");
            tvErrorLogin.startAnimation(AnimationUtils.loadAnimation(context, R.anim.wobble));
            return;
        }

        tvErrorLogin.setVisibility(View.GONE);
//        waitLogin = new SpotsDialog(DriverSignInOutActivity.this, "Please wait");
        //btnSIgnIn.setEnabled(false);
//        waitLogin.show();

        //ok the , let's go to signin with phone number
        String phone = (countryCode + etPhoneNumber.getText()).toString().trim().replace(" ", "");
        Log.e("phoneNumber", phone);
        startPhoneNumberVerification(phone);


        //loginUserVolley(Urls.URL_LOGIN, new GetComplexCode(getApplicationContext()).getComplexCode(), etPhoneNumber.getText().toString());
    }

    public Phonenumber.PhoneNumber getMyNumber(Context context) {
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            final String regionCode = tm.getSimCountryIso().toUpperCase(Locale.US);
            return PhoneNumberUtil.getInstance().parse(tm.getLine1Number(), regionCode);
        } catch (Exception e) {
            return null;
        }
    }

    private void showRegister() {


        LayoutInflater layoutInflater = getLayoutInflater();
        viewRegister = layoutInflater.inflate(R.layout.layout_register, null);

        etEmailReg = viewRegister.findViewById(R.id.etEmail);
        etPassReg = viewRegister.findViewById(R.id.etPassword);
        etNameReg = viewRegister.findViewById(R.id.etName);
        etPhoneReg = viewRegister.findViewById(R.id.etPhone);
        etCity = viewRegister.findViewById(R.id.etCity);
        etCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    try {
                        startActivityForResult(builder.build(DriverSignInOutActivity.this), PLACE_PICKER_REQUEST);
                    } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        ccpRegister = viewRegister.findViewById(R.id.ccpRegister);
        ccpRegister.registerCarrierNumberEditText(etPhoneReg);

        cbUserReg = viewRegister.findViewById(R.id.cbUser);
        cbWorkerReg = viewRegister.findViewById(R.id.cbWorker);
        cbUserReg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbWorkerReg.setChecked(false);
                    hideRole();
                } else {
                    cbWorkerReg.setChecked(true);
                }
            }
        });
        cbWorkerReg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbUserReg.setChecked(false);
                    showRole(getApplicationContext(), viewRegister);
                    //showWorkerOption(null);


                    //new FragmentModalBottomSheet().show(getSupportFragmentManager(), "BottomSheet Fragment");
//                    FragmentModalBottomSheet fragmentModalBottomSheet = new FragmentModalBottomSheet();
//                    fragmentModalBottomSheet.show(getSupportFragmentManager(), "BottomSheet Fragment");

//                    FragmentManager fm = getSupportFragmentManager();
//                    FragmentModalBottomSheet fragmentModalBottomSheet = FragmentModalBottomSheet.newInstance("Some Title");
//                    fragmentModalBottomSheet.show(fm, "fragment_edit_name");


                } else {
                    cbUserReg.setChecked(true);
                }
            }
        });
        tvErrorRegister = viewRegister.findViewById(R.id.tvErrorRegister);

        llSelectedRole = viewRegister.findViewById(R.id.llSelectedRole);
        tvRegSelectedRole = llSelectedRole.findViewById(R.id.tvRegSelectedRole);


        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                //.setTitle("Register")
                //.setMessage("Please use email to register")
                .setView(viewRegister)
                .setPositiveButton("Register", null)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialogRegister = builder.create();
        alertDialogRegister.setCancelable(false);
        alertDialogRegister.setCanceledOnTouchOutside(false);
        alertDialogRegister.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        registerUser();

                    }
                });
            }
        });
        if (!alertDialogRegister.isShowing()) {
            alertDialogRegister.show();
        }


    }

    public void registerUser() {

        if (TextUtils.isEmpty(etEmailReg.getText().toString())) {
            //Snackbar.make(rlMainView, "Enter Email", Snackbar.LENGTH_SHORT).show();
            //etEmail.setError("Enter Email");
            tvErrorRegister.setVisibility(View.VISIBLE);
            tvErrorRegister.setText("Enter Email");
            tvErrorRegister.startAnimation(AnimationUtils.loadAnimation(context, R.anim.wobble));
            etEmailReg.requestFocus();
            return;
        }
        if (!isValidEmail(etEmailReg.getText().toString())) {
//            Snackbar.make(rlMainView, "Enter Valid Email", Snackbar.LENGTH_SHORT).show();
//            etEmail.setError("Enter Valid Email");
            tvErrorRegister.setVisibility(View.VISIBLE);
            tvErrorRegister.setText("Enter Valid Email");
            tvErrorRegister.startAnimation(AnimationUtils.loadAnimation(context, R.anim.wobble));
            etEmailReg.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(etPassReg.getText().toString())) {
//            Snackbar.make(rlMainView, "Enter Password", Snackbar.LENGTH_SHORT).show();
//            etPass.setError("Enter Password");
            tvErrorRegister.setVisibility(View.VISIBLE);
            tvErrorRegister.setText("Enter Password");
            tvErrorRegister.startAnimation(AnimationUtils.loadAnimation(context, R.anim.wobble));
            etPassReg.requestFocus();
            return;
        }
        if (etPassReg.getText().toString().trim().length() <= 6) {
//            Snackbar.make(rlMainView, "Enter Password", Snackbar.LENGTH_SHORT).show();
//            etPass.setError("Enter Password");
            tvErrorRegister.setVisibility(View.VISIBLE);
            tvErrorRegister.setText("Password is too short");
            tvErrorRegister.startAnimation(AnimationUtils.loadAnimation(context, R.anim.wobble));
            etPassReg.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(etNameReg.getText().toString())) {
//            Snackbar.make(rlMainView, "Enter Name", Snackbar.LENGTH_SHORT).show();
//            etName.setError("Enter Name");
            tvErrorRegister.setVisibility(View.VISIBLE);
            tvErrorRegister.setText("Enter Name");
            tvErrorRegister.startAnimation(AnimationUtils.loadAnimation(context, R.anim.wobble));
            etNameReg.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(etCity.getText().toString())) {
//            Snackbar.make(rlMainView, "Enter Name", Snackbar.LENGTH_SHORT).show();
//            etName.setError("Enter Name");
            tvErrorRegister.setVisibility(View.VISIBLE);
            tvErrorRegister.setText("Enter City");
            tvErrorRegister.startAnimation(AnimationUtils.loadAnimation(context, R.anim.wobble));
            etCity.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(etPhoneReg.getText().toString())) {
//            Snackbar.make(rlMainView, "Enter Phone Number", Snackbar.LENGTH_SHORT).show();
//            etPhone.setError("Enter Phone Number");
            tvErrorRegister.setVisibility(View.VISIBLE);
            tvErrorRegister.setText("Enter Phone Number");
            tvErrorRegister.startAnimation(AnimationUtils.loadAnimation(context, R.anim.wobble));
            etPhoneReg.requestFocus();
            return;
        }
        if (!cbUserReg.isChecked() && !cbWorkerReg.isChecked()) {
//            Snackbar.make(rlMainView, "Please Select Who Are You", Snackbar.LENGTH_SHORT).show();
            tvErrorRegister.setVisibility(View.VISIBLE);
            tvErrorRegister.setText("Please Select Who Are You\nRegister As");
            tvErrorRegister.startAnimation(AnimationUtils.loadAnimation(context, R.anim.wobble));
            return;
        }

//        if (cbUserReg.isChecked()) {
//            //do nothing
//
//        } else {
//            if (strDriverOrHome == "") {
////            Snackbar.make(rlMainView, "Please Select Your Role", Snackbar.LENGTH_SHORT).show();
//                tvErrorRegister.setVisibility(View.VISIBLE);
//                tvErrorRegister.setText("Please Select Your Role");
//                tvErrorRegister.startAnimation(AnimationUtils.loadAnimation(context, R.anim.wobble));
//                return;
//            }
//        }

        //check if email already exists
//        if (checkIfEmailExistForSmrUser(etEmailReg.getText().toString())) {
//            tvErrorRegister.setVisibility(View.VISIBLE);
//            tvErrorRegister.setText("This email already exists \n You can login directly");
//            return;
//        }


        tvErrorRegister.setVisibility(View.GONE);

        waitLogin = new SpotsDialog(DriverSignInOutActivity.this, "Please wait");
        //btnSIgnIn.setEnabled(false);
        waitLogin.show();

//
//        home 2
//        user 4
//        car 3

//        registerUserNow(
//                Urls.URL_REGISTER,
//                new GetComplexCode(getApplicationContext()).getComplexCode(),
//                (cbUserReg.isChecked()) ? "4" : (strDriverOrHome == TAG_DRIVER ? "3" : "2"),
//                etNameReg.getText().toString(),
//                etEmailReg.getText().toString(),
//                etPassReg.getText().toString(),
//                etCity.getText().toString(),
//                etPhoneReg.getText().toString()
//
//        );


        firebaseAuth.createUserWithEmailAndPassword(etEmailReg.getText().toString(), etPassReg.getText().toString())
//                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {

                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
//                    @Override
//                    public void onSuccess(@NonNull AuthResult task)
                        waitLogin.dismiss();
                        checkIfEmailExist(task);
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (!errorType) {
                            //Snackbar.make(rlMainView, "Network Error !!!", Snackbar.LENGTH_SHORT).show();
                            Sneaker.with(DriverSignInOutActivity.this)
                                    .setTitle("Network Error !! - Check internet connection ")
                                    .setMessage("Check internet connection ")
                                    .sneakError();
                        } else
                            return;
                    }
                });


    }

    String verificationCodeEmail, verificationCodePhone;

    private void registerUserNow(String url,
                                 final String key, final String userType,
                                 final String fullName, final String emailAddress, final String password, final String address, final String phoneNumber) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("jsonRegister", response.toString());

                        if (waitLogin.isShowing()) {
                            waitLogin.dismiss();
                        }
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            String code = "";
                            code = jsonObject1.getString("code");
                            if (code.equalsIgnoreCase("0")) {
                                Sneaker.with(DriverSignInOutActivity.this)
                                        .setTitle("Email or phone number already exists")
                                        .sneakWarning();
                            }
                            if (code.equalsIgnoreCase("1")) {
                                Sneaker.with(DriverSignInOutActivity.this)
                                        .setTitle("Registered success")
                                        .sneakSuccess();
                                Sneaker.with(DriverSignInOutActivity.this)
                                        .setTitle("Please, Confirm your phone and email address")
                                        .sneakSuccess();


                                verificationCodeEmail = jsonObject1.getString("verificationCode");
                                verificationCodePhone = jsonObject1.getString("verificationCode2");
                                from = "engahmedali2022@gmail.com";
                                to = etEmailReg.getText().toString();
                                subject = "In A Click";
                                messageBody = "The verification code for email address is " +
                                        verificationCodeEmail
                                ;

                                //sending the email verification code via email only
                                new AsyncSendingEmail(context).execute();
                                //sending the sms code
//                                if (isSMSPermissionGranted()) {
//                                    sendSms(ccpRegister.getSelectedCountryCodeWithPlus() + etPhoneReg.getText().toString(),
//                                            "In A Click \n The verification code is " + verificationCodePhone);
//                                }
//                                sendSms2(/*ccpRegister.getFullNumber()+*/etPhoneReg.getText().toString(),
//                                        "In A Click \n The verification code is " + verificationCodePhone);


                            }
                            if (code.equalsIgnoreCase("2")) {
                                Sneaker.with(DriverSignInOutActivity.this)
                                        .setTitle("Plugin not activated !!")
                                        .sneakError();
                            }
                            if (code.equalsIgnoreCase("3")) {
                                Sneaker.with(DriverSignInOutActivity.this)
                                        .setTitle("Request error !!!")
                                        .sneakError();
                            }
                            if (code.equalsIgnoreCase("4")) {
                                Sneaker.with(DriverSignInOutActivity.this)
                                        .setTitle("Request match error !!!")
                                        .sneakError();

                            }
                            if (code.equalsIgnoreCase("5")) {
                                Sneaker.with(DriverSignInOutActivity.this)
                                        .setTitle("Something wrong !!")
                                        .sneakError();
                            }

//


                        } catch (Exception e) {
//                            Snackbar.make(viewRoot, "Network Error !!!!", Snackbar.LENGTH_SHORT).show();
                        } finally {
//                            pdLogging.dismiss();

                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(LoginActivity.this, "onErrorResponse" + error.getMessage(), Toast.LENGTH_LONG).show();
                        //Log.e("ErrorResponse", error.getMessage().toString());
                        //error.printStackTrace();
                        //Snackbar.make(viewRoot, "Network Error !!!!", Snackbar.LENGTH_SHORT).show();
                        Sneaker.with(DriverSignInOutActivity.this)
                                .setTitle("Check Internet Connection")
                                .sneakError();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("key", key);
                params.put("usertype", userType);
                params.put("name", fullName);
                params.put("password", password);
                params.put("email", emailAddress);
                params.put("address", address);
                params.put("mobile", phoneNumber);

                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);


    }

    private void sendSms(String phoneNo, String message) {
//        try {
//            SmsManager smsManager = SmsManager.getDefault();
//            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
//            Toast.makeText(getApplicationContext(), "Message Sent",
//                    Toast.LENGTH_LONG).show();
//        } catch (Exception ex) {
//            Toast.makeText(getApplicationContext(), ex.getMessage().toString(),
//                    Toast.LENGTH_LONG).show();
//            ex.printStackTrace();
//        }

        PendingIntent pi = PendingIntent.getActivity(this, 0,
                new Intent(this, DriverSignInOutActivity.class), 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNo, null, message, pi, null);


    }

    //    /---sends an SMS message to another device---
    private void sendSms2(String phoneNumber, String message) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 0: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    //send sms here call your method
                    sendSms(ccpRegister.getSelectedCountryCodeWithPlus() + etPhoneReg.getText().toString(),
                            "In A Click \n The verification code is " + verificationCodePhone);
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public boolean isSMSPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.SEND_SMS)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions((Activity) getApplicationContext(), new String[]{Manifest.permission.SEND_SMS}, 0);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }

    private void loginUserVolley(String url,
                                 final String key,
                                 final String phoneNumber) {

        waitDialog = new SpotsDialog(DriverSignInOutActivity.this, "Please wait ...");
        waitDialog.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("jsonLoginUser20130074", response.toString());

                        if (waitDialog.isShowing()) {
                            waitDialog.dismiss();
                        }
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            String code = "";
                            code = jsonObject1.getString("code");
                            if (code.equalsIgnoreCase("0")) {
                                Sneaker.with(DriverSignInOutActivity.this)
                                        .setTitle("User not exits !!!!")
                                        .sneakError();
                            }
                            if (code.equalsIgnoreCase("1")) {
                                Sneaker.with(DriverSignInOutActivity.this)
                                        .setTitle("Logged success")
                                        .sneakSuccess();
                                String userType = "";
                                userType = jsonObject1.getJSONObject("data").getString("usertype");
                                //Toast.makeText(context, userType, Toast.LENGTH_SHORT).show();
                                //finish();
                                //normal user
                                if (userType.equals("4")) {
                                    //Toast.makeText(context, TAG_NORMAL_USER, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(DriverSignInOutActivity.this, ActionBarActivity.class).putExtra(WHO, TAG_NORMAL_USER));

                                } else {
                                    //driver
                                    if (userType.equals("3")) {
                                        startActivity(new Intent(DriverSignInOutActivity.this, ActionBarActivity.class).putExtra(WHO, TAG_DRIVER));
                                    } else {
                                        //home owner
                                        startActivity(new Intent(DriverSignInOutActivity.this, ActionBarActivity.class).putExtra(WHO, TAG_HOME_OWNER));
                                    }
                                }


                            }
                            if (code.equalsIgnoreCase("2")) {
                                Sneaker.with(DriverSignInOutActivity.this)
                                        .setTitle("Plugin not activated !!")
                                        .sneakError();
                            }
                            if (code.equalsIgnoreCase("3")) {
                                Sneaker.with(DriverSignInOutActivity.this)
                                        .setTitle("Request error !!!")
                                        .sneakError();
                            }
                            if (code.equalsIgnoreCase("4")) {
                                Sneaker.with(DriverSignInOutActivity.this)
                                        .setTitle("Request match error !!!")
                                        .sneakError();

                            }

                            if (code.equalsIgnoreCase("6")) {
                                Sneaker.with(DriverSignInOutActivity.this)
                                        .setTitle("User not active !!!")
                                        .sneakError();
                                showActivationView();
                            }
                            if (code.equalsIgnoreCase("7")) {
                                Sneaker.with(DriverSignInOutActivity.this)
                                        .setTitle("Wrong key !!!!")
                                        .sneakError();
                            }
//


                        } catch (Exception e) {
//                            Snackbar.make(viewRoot, "Network Error !!!!", Snackbar.LENGTH_SHORT).show();
                        } finally {
//                            pdLogging.dismiss();

                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(LoginActivity.this, "onErrorResponse" + error.getMessage(), Toast.LENGTH_LONG).show();
                        //Log.e("ErrorResponse", error.getMessage().toString());
                        //error.printStackTrace();
                        //Snackbar.make(viewRoot, "Network Error !!!!", Snackbar.LENGTH_SHORT).show();
                        Sneaker.with(DriverSignInOutActivity.this)
                                .setTitle("Check Internet Connection")
                                .sneakError();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("key", key);
                params.put("mobile", phoneNumber);

                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);


    }

    AlertDialog dialogVerificationView;
    EditText etEmailCode, etPhoneCode;

    private void showActivationView() {

        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_phone_email_activation, null);

        etEmailCode = ((TextInputLayout) view.findViewById(R.id.tilEmailCode)).getEditText();
        etPhoneCode = ((TextInputLayout) view.findViewById(R.id.tilPhoneCode)).getEditText();
        Button btnConfirmCodes = view.findViewById(R.id.btnVerifyNow);
        btnConfirmCodes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etEmailCode.getText())) {
                    etEmailCode.setError("Enter code");
                    return;
                }

                if (TextUtils.isEmpty(etPhoneCode.getText())) {
                    etPhoneCode.setError("Enter code");
                    return;
                }

                Toast.makeText(context, "done", Toast.LENGTH_SHORT).show();


            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(DriverSignInOutActivity.this);
        builder.setView(view);


        dialogVerificationView = builder.create();
        if (!dialogVerificationView.isShowing())
            dialogVerificationView.show();


    }

    public void sendEmail(String from, String to, String subject, String messageText) {
        try {
            String host = "smtp.gmail.com";
            String user = "engahmedali2022@gmail.com";
            String pass = "123456789(*&^%$#@!Aa!@#$";
            //String to = "Ahmedramzy_fcih@yahoo.com";
            //String from = "wowrar1234@gmail.com";
            //String subject = "Congratulations, Ahmed Ramzy";
            //String messageText = "Do not tell any one, I am Ahmed Mohammed Ali Ali";
            boolean sessionDebug = false;

            Properties props = System.getProperties();

            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");

            //java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setText(messageText);

            Transport transport = mailSession.getTransport("smtp");
            transport.connect(host, user, pass);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            System.out.println("message send successfully");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void checkIfEmailExist(@NonNull Task<AuthResult> task) {
        if (!task.isSuccessful()) {
            try {
                throw task.getException();
            }
            // if user enters wrong email.
            catch (FirebaseAuthWeakPasswordException weakPassword) {
                Log.d(TAG, "onComplete: weak_password");
                return;
            }
            // if user enters wrong password.
            catch (FirebaseAuthInvalidCredentialsException malformedEmail) {
                Log.d(TAG, "onComplete: malformed_email");
                return;

            } catch (FirebaseAuthUserCollisionException existEmail) {
                //Log.d(TAG, "onComplete: exist_email");
                tvErrorRegister.setVisibility(View.VISIBLE);
                tvErrorRegister.setText("This email already exists\nYou can login directly\nOr register with a new one");
                tvErrorRegister.startAnimation(AnimationUtils.loadAnimation(context, R.anim.wobble));
                errorType = true;
                return;
            } catch (Exception e) {
                Log.d(TAG, "onComplete: " + e.getMessage());
                return;
            }
        } else {
            proceedRegister();
        }
    }

    private void proceedRegister() {

        alertDialogRegister.dismiss();

        UserHomeOwner userHomeOwner = new UserHomeOwner(
                etEmailReg.getText().toString(),
                etPassReg.getText().toString(),
                etNameReg.getText().toString(),
                etCity.getText().toString(),
                ccpRegister.getSelectedCountryCodeWithPlus() + etPhoneReg.getText().toString(),
                false

        );
        refWorkers.child(strDriverOrHome).child(firebaseAuth.getCurrentUser().getUid()
                //inAClickUser.getUserEmail()
        ).setValue(userHomeOwner).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Snackbar.make(rlMainView, "You are registered as a new " + TAG_HOME_OWNER, Snackbar.LENGTH_SHORT).show();
                Sneaker.with(DriverSignInOutActivity.this)
                        .setTitle("Success - You are registered as a new Worker(Home owner)")
                        .setMessage(" You are registered as a new Worker(Home owner)")
                        .sneakSuccess();

                showLogin();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Snackbar.make(rlMainView, "Network Error, Failed in  Registration", Snackbar.LENGTH_SHORT).show();
                Sneaker.with(DriverSignInOutActivity.this)
                        .setTitle("Network Error !! - Check internet connection ")
                        .setMessage("Check internet connection ")
                        .sneakError();
            }
        });

    }

    public boolean checkIfEmailExistForSmrUser(String insertedEmailWhileReg) {
//        if (firebaseAuth == null)
//            return false;
//
        Query query = null;
        if (cbUserReg.isChecked()) {
            query = refUsers.orderByChild("userEmail").equalTo(insertedEmailWhileReg);
        } else {
            if (strDriverOrHome == TAG_DRIVER) {
                query = refWorkers.child(TAG_DRIVER).orderByChild("userEmail").equalTo(insertedEmailWhileReg);
            } else {
                query = refWorkers.child(TAG_HOME_OWNER).orderByChild("userEmail").equalTo(insertedEmailWhileReg);
            }
        }
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //here means the value exist
//here means the value not exist
                emailExist = dataSnapshot.exists();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return emailExist;
    }

    private void hideRole() {

        if (linearLayout != null) {
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_down);
            linearLayout.startAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    linearLayout.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

        }
    }

    private void showRole(final Context context, View view) {

        linearLayout = view.findViewById(R.id.llRole);

        linearLayout.setVisibility(View.VISIBLE);

        final View view1 = linearLayout.findViewById(R.id.llAsDriver);
        final View view2 = linearLayout.findViewById(R.id.llRentAHome);


        final Animation anim1 = AnimationUtils.loadAnimation(context, R.anim.scale_up);
        anim1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                view2.setVisibility(View.VISIBLE);
                view2.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up));
                anim = false;


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        view1.setVisibility(View.VISIBLE);
        view1.startAnimation(anim1);


        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (b) {

                    //view1
                    textView1 = v.findViewById(R.id.tvAsDriver);
                    wall1 = v.findViewById(R.id.wallDriver);

                    wall1.startAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_up));
                    wall1.setVisibility(View.VISIBLE);

                    textView1.setTextColor(Color.GRAY);
                    textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

                    //view2
                    textView2 = view2.findViewById(R.id.tvHomeAsRent);
                    wall2 = view2.findViewById(R.id.wallHomeRen);

                    wall2.startAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_down));
                    wall2.setVisibility(View.GONE);

                    textView2.setTextColor(getResources().getColor(android.R.color.darker_gray));
                    textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

                    b = false;
                    b2 = true;

                    strDriverOrHome = TAG_DRIVER;


                    Sneaker.with(getParent())
                            .setTitle("Success!!")
                            .setMessage("This is the success message")
                            .sneakSuccess();
                }


            }
        });

        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b2) {
                    //view2
                    textView2 = v.findViewById(R.id.tvHomeAsRent);
                    wall2 = v.findViewById(R.id.wallHomeRen);

                    wall2.startAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_up));
                    wall2.setVisibility(View.VISIBLE);

                    textView2.setTextColor(Color.GRAY);
                    //textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, textView2.getTextSize());
                    textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);


                    //view1
                    textView1 = view1.findViewById(R.id.tvAsDriver);
                    wall1 = view1.findViewById(R.id.wallDriver);

                    wall1.startAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_down));
                    wall1.setVisibility(View.INVISIBLE);

                    textView1.setTextColor(getResources().getColor(android.R.color.darker_gray));
                    textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);


                    b = true;
                    b2 = false;

                    strDriverOrHome = TAG_HOME_OWNER;


                }


            }
        });


    }

    private void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user == null) {
//            Sneaker.with(DriverSignInOutActivity.this)
//                    .setTitle("Please, Register then login")
//                    .setMessage("Email Not Sent")
//                    .sneakWarning();
//            return;
//        }
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent
                            // after email is sent just logout the user and finish this activity
                            FirebaseAuth.getInstance().signOut();
                            //start login view
                            //.....
                            Sneaker.with(DriverSignInOutActivity.this)
                                    .setTitle("Success - The email verification sent")
                                    .setMessage("The email verification sent")
                                    .sneakSuccess();
                            //showMessage(context, "The email verification sent");

                        } else {
                            // email not sent, so display message and restart the activity or do whatever you wish to do
                            Sneaker.with(DriverSignInOutActivity.this)
                                    .setTitle("Error!! - Email Not Sent")
                                    .setMessage("Email Not Sent")
                                    .sneakError();
                            //showMessage(context, "Email Not Sent");

                        }
                    }
                });


    }

    private void checkIfEmailVerified(Task<AuthResult> task) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        assert user != null;
        if (!user.isEmailVerified()) { //-> here clear the not char
            // user is verified, so you can finish this activity or send user to activity which you want.
            //Snackbar.make(rlMainView, "Logged success", Snackbar.LENGTH_SHORT).show();
            //btnSIgnIn.setEnabled(true);
            Intent intent = new Intent(DriverSignInOutActivity.this, ActionBarActivity.class);
            if (cbUserLogin.isChecked()) {
                intent.putExtra(WHO, TAG_NORMAL_USER);
            } else {
                if (selectedRoleWorkerLogin.equals(TAG_DRIVER)) {
                    intent.putExtra(WHO, TAG_DRIVER);
                    //Toast.makeText(context, "Driver", Toast.LENGTH_SHORT).show();
                } else {
                    intent.putExtra(WHO, TAG_HOME_OWNER);
                    //Toast.makeText(context, "Home Owner", Toast.LENGTH_SHORT).show();
                }
            }
            //as a test
            //FirebaseAuth.getInstance().signOut();

            startActivity(intent);
            finish();
        } else {
            // email is not verified, so just prompt the message to the user and restart this activity.
            // NOTE: don't forget to log out the user.

            //restart this activity
            //showSnackbar("Please check email verification\nVerify your email\nThen login");
            tvErrorLogin.setVisibility(View.VISIBLE);
            tvErrorLogin.setText("Please check email verification");
            tvErrorLogin.startAnimation(AnimationUtils.loadAnimation(context, R.anim.wobble));
            //sendVerificationEmail();

            FirebaseAuth.getInstance().signOut();
        }
    }

    public void showMessage(Context context, String message) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

    }

    public void showSnackbar(String message) {
        Snackbar.make(rlMainView, message, Snackbar.LENGTH_LONG).show();
    }

    private void startPhoneNumberVerification(String phoneNumber) {

//        if (TextUtils.isEmpty(etPhoneNumber.getText().toString())) {
//            //Toast.makeText(this, "Please, Enter the phone number, Then start competition.", Toast.LENGTH_SHORT).show();
//            Snackbar.make(findViewById(android.R.id.content),
//                    "Please, Enter the phone number firstly", Snackbar.LENGTH_SHORT).show();
//            return;
//        }
        waitDialog = new SpotsDialog(this, "Sending your SMS code ...");
        waitDialog.show();

        //FirebaseApp.initializeApp(this);
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                //Toast.makeText(context, "onVerificationCompleted", Toast.LENGTH_SHORT).show();
//               // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verificaiton without
                //     user action.
                Log.e(TAG, "onVerificationCompleted:" + credential);

                mVerificationInProgress = false;

                // Update the UI and attempt sign in with the phone credential
//                updateUI(STATE_VERIFY_SUCCESS, credential);
                //signInWithPhoneAuthCredential(credential);


            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                //Toast.makeText(context, "onVerificationFailed", Toast.LENGTH_SHORT).show();
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                //Log.w(TAG, "onVerificationFailed", e);
                mVerificationInProgress = false;

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request

                    Snackbar.make(rlMainView, " Invalid phone number.",
                            Snackbar.LENGTH_SHORT).show();
                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Snackbar.make(rlMainView, "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                } else {
                    Sneaker.with(DriverSignInOutActivity.this)
                            .setTitle("Error!!")
                            .setMessage("Check Internet Connection !!!")
                            .sneakError();

                }
                waitDialog.dismiss();

            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                //Log.d(TAG, "onCodeSent:" + verificationId);

//                Snackbar.make(rlMainView, "Enter code sent to u ",
//                        Snackbar.LENGTH_LONG).show();
                Sneaker.with(DriverSignInOutActivity.this)
                        .setTitle("Success!!")
                        .setMessage("Enter code sent to u ")
                        .sneakSuccess();


                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                waitDialog.dismiss();
                // Update UI
                //updateUI(STATE_CODE_SENT);
                //Toast.makeText(HomeActivity.this, "onCodeSent", Toast.LENGTH_SHORT).show();

                showTheVerificationLayout();

            }

            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {
                super.onCodeAutoRetrievalTimeOut(s);

            }
        };
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                20,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                //this,               // Activity (for callback binding)
                DriverSignInOutActivity.this,
                mCallbacks);        // OnVerificationStateChangedCallbacks

        mVerificationInProgress = true;

    }

    private void showTheVerificationLayout() {


        View viewResendCode = LayoutInflater.from(context).inflate(R.layout.layout_enter_digits, null);


        etDigit1 = viewResendCode.findViewById(R.id.etDigit1);
        etDigit2 = viewResendCode.findViewById(R.id.etDigit2);
        etDigit3 = viewResendCode.findViewById(R.id.etDigit3);
        etDigit4 = viewResendCode.findViewById(R.id.etDigit4);
        etDigit5 = viewResendCode.findViewById(R.id.etDigit5);
        etDigit6 = viewResendCode.findViewById(R.id.etDigit6);

        etDigit1.addTextChangedListener(tw1);
        etDigit2.addTextChangedListener(tw2);
        etDigit3.addTextChangedListener(tw3);
        etDigit4.addTextChangedListener(tw4);
        etDigit5.addTextChangedListener(tw5);
        etDigit6.addTextChangedListener(tw6);

        tvTimeResendCode = viewResendCode.findViewById(R.id.tvTimeResendCode);
        tvErrorEnterDigits = viewResendCode.findViewById(R.id.tvErrorEnterDigits);
        fabEnterDigits = viewResendCode.findViewById(R.id.fabEnterDigits);
        btnResndCode = viewResendCode.findViewById(R.id.btnResendCode);
        btnResndCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearDigits();
                btnResndCode.setVisibility(View.GONE);
                resendVerificationCode(countryCode + etPhoneNumber.getText().toString(), mResendToken);
            }
        });
        fabEnterDigits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                waitDialog = new SpotsDialog(context, "Verifying the code ...");
                waitDialog.show();

                String code =
                        etDigit1.getText().toString() +
                                etDigit2.getText().toString() +
                                etDigit3.getText().toString() +
                                etDigit4.getText().toString();
                verifyPhoneNumberWithCode(mVerificationId, code);


            }
        });


//        bottomDialogResendCode = new BottomDialog.Builder(DriverSignInOutActivity.this)
//                .setCustomView(viewResendCode)
//                .setCancelable(true)
//                .setTitle("Resend code ..")
        //.setNegativeText("Exit")
        // .setNegativeTextColorResource(R.color.colorAccent)
        //.setIcon(R.drawable.share)
//                .onNegative(new BottomDialog.ButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull BottomDialog dialog) {
//                        dialog.dismiss();
//                    }
//                })
//                .build();
//        bottomDialogResendCode.show();

        //starting the counter up timer
        if (stopHandler == false) {
            stopHandler = true;
            count = 0;

        }
        startCounter(1000);

        //countUpTimer(1000, 1000);
//        final CountUpTimer timer = new CountUpTimer(10000) {
//            public void onTick(int second) {
//                tvTimeResendCode.setText(second + "sec");
//                if (second == 10) {
//                    btnResndCode.setVisibility(View.VISIBLE);
//                } else {
//                    if (btnResndCode.getVisibility() != View.GONE) {
//                        btnResndCode.setVisibility(View.GONE);
//                    }
//                }
//            }
//
//            @Override
//            public void onFinish() {
//                super.onFinish();
//            }
//        };
//        timer.start();

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setView(viewResendCode)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Verify Code", null);
        alertDialogEnterDigits = builder.create();
        alertDialogEnterDigits.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                btnAlertResendCode = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                btnAlertResendCode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (
                                TextUtils.isEmpty(etDigit1.getText().toString()) ||
                                        TextUtils.isEmpty(etDigit2.getText().toString()) ||
                                        TextUtils.isEmpty(etDigit3.getText().toString()) ||
                                        TextUtils.isEmpty(etDigit4.getText().toString()) ||
                                        TextUtils.isEmpty(etDigit5.getText().toString()) ||
                                        TextUtils.isEmpty(etDigit6.getText().toString())

                                ) {
                            tvErrorEnterDigits.setVisibility(View.VISIBLE);
                            tvErrorEnterDigits.startAnimation(AnimationUtils.loadAnimation(context, R.anim.wobble));
                            //tvErrorEnterDigits.setText("");
                            return;

                        }


                        waitDialog = new SpotsDialog(context, "Verifying code now ...");
                        waitDialog.show();

                        String code = etDigit1.getText().toString() +
                                etDigit2.getText().toString() +
                                etDigit3.getText().toString() +
                                etDigit4.getText().toString() +
                                etDigit5.getText().toString() +
                                etDigit6.getText().toString();


                        verifyPhoneNumberWithCode(mVerificationId, code);
                    }
                });
            }
        });

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = (int) (displaymetrics.widthPixels * 0.9);
        int height = (int) (displaymetrics.heightPixels * 0.7);

        alertDialogEnterDigits.getWindow().setLayout(width, height);
        alertDialogEnterDigits.setCancelable(false);
        alertDialogEnterDigits.setCanceledOnTouchOutside(false);


        if (!alertDialogEnterDigits.isShowing()) {
            alertDialogEnterDigits.show();
        } else {

        }

    }

    private void clearDigits() {

        etDigit1.setText("");
        etDigit2.setText("");
        etDigit3.setText("");
        etDigit4.setText("");
        etDigit5.setText("");
        etDigit6.setText("");
    }

    private void startCounter(final int delaySec) {

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (stopHandler) {
                    count++;
                    tvTimeResendCode.setText(count + " sec");
                    if (count == 20 && stopHandler) {
                        btnResndCode.setVisibility(View.VISIBLE);
                        handler.removeCallbacks(runnable);
                        stopHandler = false;
                    } else {
                        if (btnResndCode.getVisibility() != View.GONE) {
                            btnResndCode.setVisibility(View.GONE);

                        }
                    }
                    handler.postDelayed(runnable, delaySec);// move this inside the run method
                }

            }
        };
        runnable.run(); // missing
    }

    public void countUpTimer(long delay, long period) {
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
                                      @Override
                                      public void run() {
                                          runOnUiThread(new Runnable() {
                                              @Override
                                              public void run() {
                                                  tvTimeResendCode.setText(count + "sec");
                                                  count++;
                                                  if (count == 10) {
                                                      timer.cancel();
                                                      btnResndCode.setVisibility(View.VISIBLE);
                                                  } else {
                                                      if (btnResndCode.getVisibility() != View.GONE) {
                                                          btnResndCode.setVisibility(View.GONE);
                                                      }
                                                      count = 0;
                                                      countUpTimer(1000, 1000);
                                                  }
                                              }
                                          });
                                      }
                                  }, delay//delay - delay in milliseconds before task is to be executed.
                , period//  period - time in milliseconds between successive task executions.
        );

    }

    public void showCustomSneakar() {
        Sneaker.with(this)
                .setTitle("Title", R.color.white) // Title and title color
                .setMessage("This is the message.", R.color.white) // Message and message color
                .setDuration(4000) // Time duration to show
                .autoHide(true) // Auto hide Sneaker view
                .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT) // Height of the Sneaker layout
                //.setIcon(R.drawable.ic_no_connection, R.color.white, false) // Icon, icon tint color and circular icon view
                //.setTypeface(Typeface.createFromAsset(this.getAssets(), "font/" + fontName)); // Custom font for title and message
                //.setOnSneakerClickListener(this) // Click listener for Sneaker
                .sneak(R.color.colorAccent); // Sneak with background color
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            //Log.d(TAG, "signInWithCredential:success");
//
//                            //adding a unique user id that contains his all own uploaded imaged
//
////                            firebaseDatabase = FirebaseDatabase.getInstance();
////                            databaseReference = firebaseDatabase.getReference().child(etPhoneNumber.getText().toString());
////                            String keyUploadedImages = databaseReference.child("uploadedImages").push().getKey();
////                            String keyFavImages = databaseReference.child("favImages").push().getKey();
//
//                            //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                            sharedPreferences = getSharedPreferences(KEY_PHONE_NUMBER, MODE);
//                            editor = sharedPreferences.edit();
//                            editor.putString(KEY_PHONE_NUMBER, etPhoneNumber.getText().toString());
//                            editor.commit();
////                            editor.putString(KEY_IMAGES_FAV, keyFavImages);
////                            editor.putString(KEY_IMAGES_UPLOADED, keyUploadedImages);
//
//
                            userPhone = task.getResult().getUser();
                            initPhoneCompRegisteration();
                        } else {
//                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                waitDialog.dismiss();
                                Snackbar.make(rlMainView, "Invalid Code !!!", Snackbar.LENGTH_SHORT).show();
                            }
                        }
//                        textInputLayout.setErrorEnabled(false);
//                        if (progressDialog != null) {
//                            if (progressDialog.isShowing())
//                                progressDialog.dismiss();
//                        }
//                        if (pdVerification != null) {
//                            if (pdVerification.isShowing())
//                                pdVerification.dismiss();
//                        }
//                        if (barResendCode != null) {
//                            barResendCode.setVisibility(ProgressBar.GONE);
//                        }

                    }
                });


    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                20,                  // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    private void updateUI(int uiState) {
        updateUI(uiState, firebaseAuth.getCurrentUser(), null);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            updateUI(STATE_SIGNIN_SUCCESS, user);
        } else {
            updateUI(STATE_INITIALIZED);
        }
    }

    private void updateUI(int uiState, FirebaseUser user) {
        updateUI(uiState, user, null);
    }

    private void updateUI(int uiState, PhoneAuthCredential cred) {
        updateUI(uiState, null, cred);
    }

    private void updateUI(int uiState, FirebaseUser user, PhoneAuthCredential cred) {
        switch (uiState) {
            case STATE_INITIALIZED:
                // Initialized state, show only the phone number field and start button
                break;
            case STATE_CODE_SENT:
                // Code sent state, show the verification field, the
                break;
            case STATE_VERIFY_FAILED:

                break;
            case STATE_VERIFY_SUCCESS:
                // Verification has succeeded, proceed to firebase sign in
                // Set the verification text based on the credential
//                showTheVerificationLayout();
//                if (cred != null && mVerificationField != null) {
//                    if (cred.getSmsCode() != null) {
//                        mVerificationField.setText(cred.getSmsCode());
//                        //signInWithPhoneAuthCredential(cred);
//                    } else {
//                        mVerificationField.setText(R.string.instant_validation);
//                        mVerificationField.setTextColor(Color.parseColor("#4bacb8"));
//                    }
//                }

                break;
            case STATE_SIGNIN_FAILED:
                // No-op, handled by sign-in check
                break;
            case STATE_SIGNIN_SUCCESS:
                // Np-op, handled by sign-in check
                //startActivity(new Intent(MainActivity.this, HomeActivity.class));
                finish();
                break;
        }

        if (user == null) {
            // Signed out
//            mPhoneNumberViews.setVisibility(View.VISIBLE);
//            mSignedInViews.setVisibility(View.GONE);
//
//            mStatusText.setText(R.string.signed_out);;
        } else {
            // Signed in
            //mPhoneNumberViews.setVisibility(View.GONE);
            /*
            mSignedInViews.setVisibility(View.VISIBLE);
            enableViews(mPhoneNumberField, mVerificationField);
            mPhoneNumberField.setText(null);
            mVerificationField.setText(null);
            mStatusText.setText(R.string.signed_in);
            mDetailText.setText(getString(R.string.firebase_status_fmt, user.getUid()));
            */
//            Intent intent = new Intent(this, HomeActivity.class);
//            startActivity(intent);
//            finish();

        }
    }

    SpotsDialog dialogSession = null;

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        if (currentUser != null) {
//            checkIfThisUserDriverOrNormal(currentUser);
//        }

//        firebaseAuth.addAuthStateListener(authStateListener);
//        if (mVerificationInProgress && validatePhoneNumber()) {
//            startPhoneNumberVerification(etPhoneNumber.getText().toString());
//        }dialogSession.show();


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Snackbar.make(rlMainView, "Network Error !!, Google Play Services", Snackbar.LENGTH_SHORT).show();
    }

    public void showWorkerOption(View view) {

//        if (view == null)
//            return;
//        else


        //linearLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.layout_select_role, null);
        //linearLayout.setVisibility(View.VISIBLE);
        linearLayout = (LinearLayout) view;

        final View view1 = linearLayout.findViewById(R.id.llAsDriver);
        final View view2 = linearLayout.findViewById(R.id.llRentAHome);


        final Animation anim1 = AnimationUtils.loadAnimation(context, R.anim.scale_up);
        anim1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                view2.setVisibility(View.VISIBLE);
                view2.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_up));
                anim = false;


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        view1.setVisibility(View.VISIBLE);
        view1.startAnimation(anim1);


        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (b) {

                    //view1
                    textView1 = v.findViewById(R.id.tvAsDriver);
                    wall1 = v.findViewById(R.id.wallDriver);

                    wall1.startAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_up));
                    wall1.setVisibility(View.VISIBLE);

                    textView1.setTextColor(Color.GRAY);
                    textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

                    //view2
                    textView2 = view2.findViewById(R.id.tvHomeAsRent);
                    wall2 = view2.findViewById(R.id.wallHomeRen);

                    wall2.startAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_down));
                    wall2.setVisibility(View.GONE);

                    textView2.setTextColor(getResources().getColor(android.R.color.darker_gray));
                    textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

                    b = false;
                    b2 = true;

                    strDriverOrHome = TAG_DRIVER;


                }


            }
        });

        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b2) {
                    //view2
                    textView2 = v.findViewById(R.id.tvHomeAsRent);
                    wall2 = v.findViewById(R.id.wallHomeRen);

                    wall2.startAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_up));
                    wall2.setVisibility(View.VISIBLE);

                    textView2.setTextColor(Color.GRAY);
                    //textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, textView2.getTextSize());
                    textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);


                    //view1
                    textView1 = view1.findViewById(R.id.tvAsDriver);
                    wall1 = view1.findViewById(R.id.wallDriver);

                    wall1.startAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_down));
                    wall1.setVisibility(View.INVISIBLE);

                    textView1.setTextColor(getResources().getColor(android.R.color.darker_gray));
                    textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);


                    b = true;
                    b2 = false;

                    strDriverOrHome = TAG_HOME_OWNER;


                }


            }
        });

        bottomDialogWorkerOptios = new BottomDialog.Builder(DriverSignInOutActivity.this)
                .setCustomView(linearLayout)
                .setCancelable(true)
                .autoDismiss(true)
                .setTitle("Select Your Role")
                //.setNegativeText("Exit")
                //.setNegativeTextColorResource(R.color.colorAccent)
//                .setIcon(R.drawable.share)
//                .onNegative(new BottomDialog.ButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull BottomDialog dialog) {
//                        dialog.dismiss();
//                    }
//                })
                .build();

        bottomDialogWorkerOptios.show();


    }

    class AsyncSendingEmail extends AsyncTask<String, Void, String> {
        Context context;

        public AsyncSendingEmail(Context context) {
            this.context = context;
            waitSendingEmail = new SpotsDialog(context, "Sending email Verification Code ...");
            waitSendingEmail.show();


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            sendEmail(from, to, subject, messageBody);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            waitSendingEmail.dismiss();
//

        }
    }

    public abstract class CountUpTimer extends CountDownTimer {
        private static final long INTERVAL_MS = 1000;
        private final long duration;

        protected CountUpTimer(long durationMs) {
            super(durationMs, INTERVAL_MS);
            this.duration = durationMs;
        }

        public abstract void onTick(int second);

        @Override
        public void onTick(long msUntilFinished) {
            int second = (int) ((duration - msUntilFinished) / 1000);
            tvTimeResendCode.setText(count + "sec");
            count++;
            if (count == 10) {
                btnResndCode.setVisibility(View.VISIBLE);
            } else {
                if (btnResndCode.getVisibility() != View.GONE) {
                    btnResndCode.setVisibility(View.GONE);
                }
                count = 0;
                countUpTimer(1000, 1000);
            }
            onTick(second);
        }

        @Override
        public void onFinish() {
            onTick(duration / 1000);
        }
    }
}
