package cr.ac.ucr.firstclass.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

import cr.ac.ucr.firstclass.R;
import cr.ac.ucr.firstclass.utils.AppPreferences;

public class ToDoListFragment extends Fragment implements View.OnClickListener {

    private AppCompatActivity activity;
    private ArrayList<String> allArr;
    private String allString;
    private ListView lvAll;
    private ArrayAdapter<String> allAdapt;
    private Gson gson;

    public ToDoListFragment() {
    }
    public static ToDoListFragment newInstance() {
        ToDoListFragment fragment = new ToDoListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        allArr = new ArrayList<>();
        gson = new Gson();
        allString = AppPreferences.getInstance(activity).getString(AppPreferences.Keys.ITEMS);
        allAdapt = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, allArr);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_to_do_list, container, false);

        lvAll = view.findViewById(R.id.lv_all);
        FloatingActionButton fabAddTask = view.findViewById(R.id.fab_app_todo);
        fabAddTask.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        if (!allString.equals("")){
            String[] todosArreglo = gson.fromJson(allString, String[].class);
            allArr.addAll(Arrays.asList(todosArreglo));
        }

        lvAll.setAdapter(allAdapt);

        setupListViewListener();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.all_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.clean_all:
                cleanAll();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    private void setupListViewListener(){
        lvAll.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id) {

                androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(activity);

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

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_add_task, null);

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

    private void saveListToPreferences(){
        String allString = gson.toJson(allArr);
        AppPreferences.getInstance(activity).put(AppPreferences.Keys.ITEMS, allString);
    }

    private void cleanAll(){
        allArr.clear();
        allAdapt.notifyDataSetChanged();
        saveListToPreferences();
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (AppCompatActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }


}