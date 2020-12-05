package com.mindorks.framework.androidloginandregisterwithsqlitedatabase.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mindorks.framework.androidloginandregisterwithsqlitedatabase.R;
import com.mindorks.framework.androidloginandregisterwithsqlitedatabase.helpers.InputValidation;
import com.mindorks.framework.androidloginandregisterwithsqlitedatabase.models.User;
import com.mindorks.framework.androidloginandregisterwithsqlitedatabase.network.ApiClient;
import com.mindorks.framework.androidloginandregisterwithsqlitedatabase.network.ApiInterface;
import com.mindorks.framework.androidloginandregisterwithsqlitedatabase.request.SignUpRequest;
import com.mindorks.framework.androidloginandregisterwithsqlitedatabase.response.SignUpResponse;
import com.mindorks.framework.androidloginandregisterwithsqlitedatabase.utils.Constants;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private final AppCompatActivity activity = RegisterActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutFirstName;
    private TextInputLayout textInputLayoutLastName;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;

    private TextInputEditText textInputEditTextFirstName;
    private TextInputEditText textInputEditTextLastName;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextConfirmPassword;

    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewLoginLink;

    private InputValidation inputValidation;
//    private DatabaseHelper databaseHelper;
    private User user;
    private SignUpRequest signUpRequest;
    private String role;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutFirstName = (TextInputLayout) findViewById(R.id.textInputLayoutFirstName);
        textInputLayoutLastName = (TextInputLayout) findViewById(R.id.textInputLayoutLastName);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        textInputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.textInputLayoutConfirmPassword);

        textInputEditTextFirstName = (TextInputEditText) findViewById(R.id.textInputEditTextFirstName);
        textInputEditTextLastName = (TextInputEditText) findViewById(R.id.textInputEditTextLastName);
        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);
        textInputEditTextConfirmPassword = (TextInputEditText) findViewById(R.id.textInputEditTextConfirmPassword);

        appCompatButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRegister);

        appCompatTextViewLoginLink = (AppCompatTextView) findViewById(R.id.appCompatTextViewLoginLink);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonRegister.setOnClickListener(this);
        appCompatTextViewLoginLink.setOnClickListener(this);

    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);
//        databaseHelper = new DatabaseHelper(activity);
        user = new User();
        signUpRequest = new SignUpRequest();
        role = getIntent().getExtras().getString("role");
        System.out.println(role);

    }


    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.appCompatButtonRegister:
                postDataToSQLite();
                break;

            case R.id.appCompatTextViewLoginLink:
                finish();
                break;
        }
    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextFirstName, textInputLayoutFirstName, getString(R.string.error_message_first_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextLastName, textInputLayoutLastName, getString(R.string.error_message_last_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword,
                textInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
            return;
        }

//        if (!databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim())) {
//
//            user.setName(textInputEditTextName.getText().toString().trim());
//            user.setEmail(textInputEditTextEmail.getText().toString().trim());
//            user.setPassword(textInputEditTextPassword.getText().toString().trim());
//
//            databaseHelper.addUser(user);
//
//            // Snack Bar to show success message that record saved successfully
//            Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
//            emptyInputEditText();
//
//
//        }
        else {
            // Snack Bar to show error message that record already exists
//            Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();

            registerUser(textInputEditTextFirstName.getText().toString().trim(), textInputEditTextLastName.getText().toString().trim(), textInputEditTextEmail.getText().toString().trim(), textInputEditTextPassword.getText().toString().trim());
        }


    }

    private void registerUser(String firstName, String lastName, String email,String password){

        ApiInterface apiService = ApiClient.getRetrofitClient();

        signUpRequest.setFirst_name(firstName);
        signUpRequest.setLast_name(lastName);
        signUpRequest.setEmail(email);
        signUpRequest.setPassword(password);
        signUpRequest.setType("tutor");
//        signUpRequest.setType(role);


        Call<JsonObject> call = apiService.postRegister(signUpRequest);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try{
                    Gson gson = new Gson();
                    String userData = gson.toJson(response.body());
                    SignUpResponse user = gson.fromJson(response.body(), SignUpResponse.class);
                    System.out.println(user.getCode());
                    if(user.getCode() == Constants.AUTH_SUCCESS_CODE){
//                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Constants.PHARMACY_PREFS, Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor= sharedPreferences.edit();
//                        editor.putString(Constants.USER_DATA,userData);
//                        editor.putString(Constants.API_TOKEN,user.getToken());
//                        editor.apply();
                        System.out.println("registered!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");

//                        Objects.requireNonNull(getActivity())
//                                .getSupportFragmentManager()
//                                .beginTransaction()
//                                .replace(R.id.home_fragment_container,new UserProfileFragment())
//                                .commit();
                        Toast.makeText(getApplicationContext(), user.getMessage(),Toast.LENGTH_LONG).show();
                    }
//                    else if(user.getCode() == Constants.NO_VALID_SUBSCRIPTION_PLAN){
//                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                        builder.setTitle(Objects.requireNonNull(getActivity()).getResources().getString(R.string.fail));
//                        builder.setMessage(getActivity().getResources().getString(R.string.err_no_valid_subscription_plan));
//                        builder.setCancelable(true);
//
//                        AlertDialog dialog = builder.create();
//                        dialog.show();
//                    }
                    else {
//                        Toast.makeText(getApplicationContext(), Objects.requireNonNull(getApplicationContext()).getResources().getString(R.string.err_login_fail),Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), user.getMessage(),Toast.LENGTH_LONG).show();
                    }

                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), Objects.requireNonNull(getApplicationContext()).getResources().getString(R.string.err_unable_to_login),Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), Objects.requireNonNull(getApplicationContext()).getResources().getString(R.string.err_unable_to_login),Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextFirstName.setText(null);
        textInputEditTextLastName.setText(null);
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
    }
}