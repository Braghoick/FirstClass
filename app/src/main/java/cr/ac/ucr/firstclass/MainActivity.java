package cr.ac.ucr.firstclass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cr.ac.ucr.firstclass.utils.AppPreferences;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private ArrayList<String> allArr;
    private ArrayAdapter<String> allAdapt;
    private ListView lvAll;

    private Gson gson;
    private String allString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gson = new Gson();

        lvAll = findViewById(R.id.lv_all);
        allArr = new ArrayList<>();

        String allString = AppPreferences.getInstance(this).getString(AppPreferences.Keys.ITEMS);

        if (!allString.equals("")){
            String[] todosArreglo = gson.fromJson(allString, String[].class);
            allArr.addAll(Arrays.asList(todosArreglo));
        }

        allAdapt = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allArr);

        lvAll.setAdapter(allAdapt);

        setupListViewListener();


    }

    private void setupListViewListener(){

        final AppCompatActivity activity = this;
        lvAll.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                builder.setMessage(R.string.delete)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                allArr.remove(position);
                                allAdapt.notifyDataSetChanged();
                                saveListToPreferences();
                            }
                        })
                        .setNegativeButton(R.string.cancel, null)
                        .create()
                        .show();



                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                logout();
                return true;
            case R.id.clean_all:
                cleanAll();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void cleanAll(){
        allArr.clear();
        allAdapt.notifyDataSetChanged();
        saveListToPreferences();
    }

    private void saveListToPreferences(){
        String allString = gson.toJson(allArr);
        AppPreferences.getInstance(this).put(AppPreferences.Keys.ITEMS, allString);
    }

    private void logout() {

        AppPreferences.getInstance(this).clear();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_app_todo:
                showAlert();
                break;
            default:
                break;
        }
    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View view = inflater.inflate(R.layout.dialog_add_task, null);
        final AppCompatActivity activity = this;


        builder.setView(view)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TextInputEditText etTaskName = view.findViewById(R.id.et_task_name);
                        String taskName = etTaskName.getText().toString();

                        if (!taskName.isEmpty()){
                            allArr.add(taskName);
                            allAdapt.notifyDataSetChanged();
                            saveListToPreferences();

                            dialogInterface.dismiss();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .create()
                .show();
    }
}