package com.mindorks.framework.androidloginandregisterwithsqlitedatabase.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
import com.mindorks.framework.androidloginandregisterwithsqlitedatabase.network.ApiClient;
import com.mindorks.framework.androidloginandregisterwithsqlitedatabase.network.ApiInterface;
import com.mindorks.framework.androidloginandregisterwithsqlitedatabase.request.AuthRequest;
import com.mindorks.framework.androidloginandregisterwithsqlitedatabase.response.UserResponse;
import com.mindorks.framework.androidloginandregisterwithsqlitedatabase.utils.Constants;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;

    private AppCompatButton appCompatButtonLogin;

    private AppCompatTextView textViewLinkRegister;

    private AppCompatTextView getTextViewLinkForgotPassword;

    private InputValidation inputValidation;
//    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);

        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);

        textViewLinkRegister = (AppCompatTextView) findViewById(R.id.textViewLinkRegister);

        getTextViewLinkForgotPassword = (AppCompatTextView) findViewById(R.id.textViewLinkForgotPassword);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
        getTextViewLinkForgotPassword.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
//        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);

    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonLogin:
                verifyFromSQLite();
                break;
            case R.id.textViewLinkRegister:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
            case R.id.textViewLinkForgotPassword:
                Intent intentForgotPassword = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(intentForgotPassword);
                break;
        }
    }

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }

//        if (databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim()
//                , textInputEditTextPassword.getText().toString().trim())) {
//
//
//            Intent accountsIntent = new Intent(activity, UsersListActivity.class);
//            accountsIntent.putExtra("EMAIL", textInputEditTextEmail.getText().toString().trim());
//            emptyInputEditText();
//            startActivity(accountsIntent);
//
//
//        } else {
//            // Snack Bar to show success message that record is wrong
//            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
//        }
//        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Constants.PHARMACY_PREFS, Context.MODE_PRIVATE);
//        String deviceBroadcastToken = sharedPreferences.getString(Constants.DEVICE_UNIQUE_TOKEN,"");
        loginUser(textInputEditTextEmail.getText().toString().trim(), textInputEditTextPassword.getText().toString().trim());
    }

    private void loginUser(String email,String password){

        ApiInterface apiService = ApiClient.getRetrofitClient();
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername(email);
        authRequest.setPassword(password);

//        Call<List<JsonObject>> call = apiService.getAllPharmacies();
//        call.enqueue(
//                new Callback<List<JsonObject>>() {
//                    @Override
//                    public void onResponse(Call<List<JsonObject>> call, Response<List<JsonObject>> response) {
//                        Gson gson = new Gson();
//                    String userData = gson.toJson(response.body());
////                    UserResponse user = gson.fromJson(response.body(), UserResponse.class);
//                        Toast.makeText(getApplicationContext(), userData, Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<JsonObject>> call, Throwable t) {
//
//                    }
//                }
//        );

//

        Call<JsonObject> call = apiService.postLogin(authRequest);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try{
                    Gson gson = new Gson();
                    String userData = gson.toJson(response.body());
                    UserResponse user = gson.fromJson(response.body(), UserResponse.class);
                    System.out.println(user.getCode());
                    if(user.getCode() == Constants.AUTH_SUCCESS_CODE){
//                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Constants.PHARMACY_PREFS, Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor= sharedPreferences.edit();
//                        editor.putString(Constants.USER_DATA,userData);
//                        editor.putString(Constants.API_TOKEN,user.getToken());
//                        editor.apply();
                        System.out.println("ooooooooooooooooooooooooooooooooooooooooo");

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
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }
}