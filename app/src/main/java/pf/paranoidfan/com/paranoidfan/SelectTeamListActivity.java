package pf.paranoidfan.com.paranoidfan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pf.paranoidfan.com.paranoidfan.Model.SvcError;
import pf.paranoidfan.com.paranoidfan.Model.TeamModel;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiRestCallback;
import pf.paranoidfan.com.paranoidfan.Service.SvcApiService;
import retrofit.client.Response;

public class SelectTeamListActivity extends AppCompatActivity {

    public static String TAG = SelectTeamListActivity.class.getSimpleName();

    ListView listView;
    SelectTeamListAdapter adapter;
    boolean delayLock = false;
    List<TeamModel> teamDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_team_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.mipmap.ic_home);
        setSupportActionBar(toolbar);

        teamDataList = new ArrayList<TeamModel>();

        listView = (ListView) findViewById(R.id.list_team);

        GetAllTeams();

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


    private void GetAllTeams(){

        if(delayLock == true)
            return;

        delayLock = true;

        SvcApiService.getUserIdEndPoint().getAllTeams(new SvcApiRestCallback<TeamModel[]>() {
            @Override
            public void failure(SvcError svcError) {
                Log.d(TAG, "Failed to Login " + svcError.toString());
                delayLock = false;
                showAlertMessage("Error", "Invalid request");
            }

            @Override
            public void success(TeamModel[] result, Response response) {
                Log.d(TAG, "Succeed to Login " + result.toString());
                try {
                    for (int pinIdx = 0; pinIdx < result.length; pinIdx++) {
                        teamDataList.add(result[pinIdx]);
                    }

                    if(teamDataList.size() > 0){
                        adapter = new SelectTeamListAdapter(SelectTeamListActivity.this, teamDataList);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position,
                                                    long id) {
                                String selectedTeam = teamDataList.get(position).getTeam();
                                GroupSelectionActivity.teamSelected = true;
                                GroupSelectionActivity.selectedTeam = selectedTeam;
                                finish();
                            }
                        });
                    }

                    delayLock = false;

                } catch (Exception e) {
                    e.printStackTrace();
                    delayLock = false;
                }
            }
        });
    }

    private void showAlertMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}
