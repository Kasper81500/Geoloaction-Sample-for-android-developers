package pf.paranoidfan.com.paranoidfan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Arrays;

import pf.paranoidfan.com.paranoidfan.Helper.ActionType;

public class SettingsActivity extends AppCompatActivity {

    String settingList[] = {"Profile", "Edit Profile", "Add Payment Card", "Change Avatar", "Change Password", "Notifications",
                    "Account", "Customization", "Groups", "Facebook & Twitter",
                    "Privacy", "Privacy Settings", "Terms of Use",
                    " ", "Sign out"};
    String settingHeaderList[] = {"Profile", "Account", "Privacy", " "};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        setSupportActionBar(toolbar);

        ListView listView = (ListView)findViewById(R.id.list_setting);
        SettingsAdapter adapter = new SettingsAdapter(this);
        for(int index = 0; index < settingList.length; index++){
            if(Arrays.asList(settingHeaderList).contains(settingList[index])){
                adapter.addSectionHeaderItem(settingList[index]);
            }else{
                adapter.addItem(settingList[index]);
            }
        }
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;

                switch(settingList[position]){
                    case "Edit Profile":
                        intent = new Intent(SettingsActivity.this, EditProfileActivity.class);
                        startActivity(intent);
                        break;
                    case "Add Payment Card":
                        intent = new Intent(SettingsActivity.this, AddPaymentActivity.class);
                        startActivity(intent);
                        break;
                    case "Change Avatar":
                        intent = new Intent(SettingsActivity.this, AvatarActivity.class);
                        startActivity(intent);
                        break;
                    case "Change Password":
                        intent = new Intent(SettingsActivity.this, ChangePasswordActivity.class);
                        startActivity(intent);
                        break;

                    case "Customization":
                        intent = new Intent(SettingsActivity.this, CustomizationActivity.class);
                        startActivity(intent);
                        break;

                    case "Groups":
                        intent = new Intent(SettingsActivity.this, GroupSelectionActivity.class);
                        startActivity(intent);
                        break;
                    case "Terms of Use":
                        intent = new Intent(SettingsActivity.this, TermsOfUseActivity.class);
                        startActivity(intent);
                        break;
                    case "Facebook & Twitter":
                        intent = new Intent(SettingsActivity.this, SocialActivity.class);
                        startActivity(intent);
                        break;

                    case "Privacy Settings":
                        intent = new Intent(SettingsActivity.this, PrivacySettings.class);
                        startActivity(intent);
                        break;

                    case "Sign out":
                        MainActivity.action = ActionType.SignOut;
                        intent = new Intent(SettingsActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
