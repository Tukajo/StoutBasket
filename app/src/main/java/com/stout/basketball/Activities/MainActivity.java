package com.stout.basketball.Activities;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.content.Context;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.content.res.Resources.Theme;

import android.widget.TextView;

import com.example.tjfri.stoutbasket.R;
import com.stout.basketball.Fragments.DialogFragments.PlayerAdditionDialogFragment;
import com.stout.basketball.Fragments.DialogFragments.PlayerShotSelectionDialogFragment;
import com.stout.basketball.Fragments.PlayerFragment;
import com.stout.basketball.Fragments.ShotLocationFragment;
import com.stout.basketball.Models.Player;
import com.stout.basketball.Utilities.GsonUtility;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ShotLocationFragment.OnShotLocationInteractionListener,
        PlayerFragment.OnListFragmentInteractionListener, PlayerAdditionDialogFragment.PlayerAdditionDialogListener,
        PlayerShotSelectionDialogFragment.PlayerShotDialogListener{
    private PlayerAdditionDialogFragment additionDialogFragment;
    private PlayerShotSelectionDialogFragment playerShotSelectionDialogFragment;
    private PlayerFragment playerFragment;
    private ShotLocationFragment shotFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Setup spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new MyAdapter(toolbar.getContext(),getResources().getStringArray(R.array.dropdown_items)));

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // When the given dropdown item is selected, show its contents in the
                // container view.
                switch(position){
                    case 0:
                        playerFragment = PlayerFragment.newInstance(1);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container,playerFragment )
                                .commit();
                        break;
                    case 1:
                        shotFragment = ShotLocationFragment.newInstance(0);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container,shotFragment)
                                .commit();
                        break;
                    case 2:
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAdditionDialog();
            }
        });

    }

    private void showAdditionDialog() {
        FragmentManager fm = getSupportFragmentManager();
        additionDialogFragment = PlayerAdditionDialogFragment.newInstance();
        additionDialogFragment.show(fm, "addition_dialog");
    }
    private void showShotSelectionDialog() {
        FragmentManager fm = getSupportFragmentManager();
        playerShotSelectionDialogFragment = PlayerShotSelectionDialogFragment.newInstance(1);
        playerShotSelectionDialogFragment.show(fm, "shot_selection_dialog");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(Player item) {
        Log.v("Item Selected","Item was selected");
    }

    @Override
    public void onDialogCancelled() {
        additionDialogFragment.dismiss();
    }

    @Override
    public void onPlayerSelected(Player newPlayer) {
     //TODO Do something
    }

    @Override
    public void onDialogSubmitted(Player newPlayer) {
        //Retrieve the list.
        ArrayList<Player> playerList = GsonUtility.fetchArrayListFromStorage(PlayerFragment.ARG_PREFERENCE_KEY,getApplication());
        //Update the list.
        playerList.add(newPlayer);
        //Store the list.
        GsonUtility.saveArrayListToStorage(playerList,PlayerFragment.ARG_PREFERENCE_KEY,getApplication());
        //Notify the fragment to update the UI.
        if(playerFragment!=null){
            playerFragment.addNewPlayer(newPlayer);
        }
    }

    @Override
    public void ShotLocationSelected() {
        showShotSelectionDialog();
    }


    private static class MyAdapter extends ArrayAdapter<String> implements ThemedSpinnerAdapter {
        private final ThemedSpinnerAdapter.Helper mDropDownHelper;

        public MyAdapter(Context context, String[] objects) {
            super(context,android.R.layout.simple_list_item_1, objects);
            mDropDownHelper = new ThemedSpinnerAdapter.Helper(context);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                // Inflate the drop down using the helper's LayoutInflater
                LayoutInflater inflater = mDropDownHelper.getDropDownViewInflater();
                view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            } else {
                view = convertView;
            }

            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            textView.setText(getItem(position));

            return view;
        }

        @Override
        public Theme getDropDownViewTheme() {
            return mDropDownHelper.getDropDownViewTheme();
        }

        @Override
        public void setDropDownViewTheme(Theme theme) {
            mDropDownHelper.setDropDownViewTheme(theme);
        }
    }


}
