package com.amitabh.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;

    private EditText editPassword, editName;
    private Button btnSignIn, btnRegister;
    String finalResult ;
    String HttpURL = "https://192.168.43.146/phpMyLogin/UserLogin.php";
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Spinner spin = (Spinner) findViewById(R.id.spinnerType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.SelectUserType,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);

        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnSignIn = (Button)findViewById(R.id.btnNext);
        editName = (EditText)findViewById(R.id.editName);
        editPassword = (EditText)findViewById(R.id.editPassword);
        spinner = (Spinner)findViewById(R.id.spinnerType);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = editName.getText().toString();
                String password = editPassword.getText().toString();
                String userType = spinner.getSelectedItem().toString();

                if(userName.equals("") || password.equals("")|| userType.equals("")){

                    Toast.makeText(LoginActivity.this, "Username or password must be filled", Toast.LENGTH_LONG).show();

                    return;

                }

                if(userName.length()<=1 || password.length()<=1){

                    Toast.makeText(LoginActivity.this, "Username or password length must be greater than one", Toast.LENGTH_LONG).show();

                    return;
                }else {


                    userLoginFunction(userName,password,userType);



                }



            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String text = adapterView.getItemAtPosition(position).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void userLoginFunction(final String userName, final String password, final String userType){

        class UserLoginClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(LoginActivity.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                if(httpResponseMsg.equalsIgnoreCase("Data Matched")){

                    finish();
                    Intent intent;

                    switch (userType)
                    {
                        case "Type_A":
                            intent = new Intent(LoginActivity.this, Type_A_Activity.class);
                            LoginActivity.this.startActivity(intent);
                            break;
                        case "Type_B":
                            intent = new Intent(LoginActivity.this, Type_B_Activity.class);
                            LoginActivity.this.startActivity(intent);
                            break;
                        case "Type_C":
                            intent = new Intent(LoginActivity.this, Type_C_Activity.class);
                            LoginActivity.this.startActivity(intent);
                            break;
                    }

                }
                else{

                    Toast.makeText(LoginActivity.this,httpResponseMsg,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("userName",params[0]);

                hashMap.put("password",params[1]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(userName,password);
    }
}