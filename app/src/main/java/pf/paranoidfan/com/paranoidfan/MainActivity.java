package pf.paranoidfan.com.paranoidfan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import pf.paranoidfan.com.paranoidfan.Helper.ActionType;

public class MainActivity extends AppCompatActivity {

    public static String action = ActionType.None;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        Button loginBtn = (Button)this.findViewById(R.id.btn_signin);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        Button signupBtn = (Button)this.findViewById(R.id.btn_signup);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisclaimerActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        switch(action){
            case ActionType.None:
                String userid = sharedPreferences.getString("USERID", "DEFAULT");
                if (!userid.isEmpty() && !userid.equals("DEFAULT")) {
                    Intent intent = new Intent(MainActivity.this, MainTabActivity.class);
                    startActivity(intent);
                }
                break;
            case ActionType.ClosingApp:
                action = ActionType.None;
                this.finishAffinity();
                break;
            case ActionType.SignOut:
                action = ActionType.None;
                sharedPreferences.edit().putString("USERID", "").commit();
                sharedPreferences.edit().putString("VERIFIED", "No").commit();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        action = ActionType.None;
        this.finishAffinity();
    }

}
