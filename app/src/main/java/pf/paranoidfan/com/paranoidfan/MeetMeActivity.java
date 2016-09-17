package pf.paranoidfan.com.paranoidfan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MeetMeActivity extends AppCompatActivity {
    public static String TAG = MeetMeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetme);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        setSupportActionBar(toolbar);

        double latitude = MainTabActivity.myPosition.latitude;
        double longitude = MainTabActivity.myPosition.longitude;

        EditText meetmeText = (EditText)findViewById(R.id.edittxt_meetme);
        String defaultMeetmeText = getText(R.string.text_default_meetme).toString();
        final String webLinkText = defaultMeetmeText + " http://paranoidfan.com/meetme.php?latitude=" + latitude + "&longitude=" + longitude;
        meetmeText.setText(webLinkText);

        TextView sendRequest = (TextView)findViewById(R.id.txt_sendrequest);
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeetMeActivity.this, MeetMeListActivity.class);
                intent.putExtra("meetMeText", webLinkText);
                startActivity(intent);

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
