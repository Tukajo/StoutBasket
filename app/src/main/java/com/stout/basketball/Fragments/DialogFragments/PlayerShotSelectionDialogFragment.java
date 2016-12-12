package com.stout.basketball.Fragments.DialogFragments;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tjfri.stoutbasket.R;
import com.stout.basketball.Adapter.PlayerRecyclerViewAdapter;
import com.stout.basketball.Callbacks.SimpleItemTouchHelperCallback;
import com.stout.basketball.Fragments.PlayerFragment;
import com.stout.basketball.Models.Player;
import com.stout.basketball.Utilities.GsonUtility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PlayerShotSelectionDialogFragment extends DialogFragment {
    public static final String ARG_PREFERENCE_KEY = "player_data";
    public static final String ARG_COLUMN_COUNT = "column-count";
    private ArrayList<Player> playerList = new ArrayList<Player>();
    private RecyclerView recyclerView;
    private PlayerRecyclerViewAdapter playerAdapter;
    private int mColumnCount = 1;

    private PlayerShotDialogListener mListener;

    public PlayerShotSelectionDialogFragment() {
        // Required empty public constructor
    }


    public static PlayerShotSelectionDialogFragment newInstance(int columnCount) {
        PlayerShotSelectionDialogFragment fragment = new PlayerShotSelectionDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player_list, container, false);
        //Get the preferences.
        playerList = GsonUtility.fetchArrayListFromStorage(ARG_PREFERENCE_KEY,getContext());
        //See if there is any player data.
        setUpList(view,playerList);

        return view;
    }
    private void setUpList(View view,ArrayList<Player> data){

        recyclerView = (RecyclerView) view.findViewById(R.id.playerList);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), mColumnCount));
        }


        if(!data.isEmpty()){
            playerAdapter = new PlayerRecyclerViewAdapter(data,mListener);
            recyclerView.setAdapter(playerAdapter);
            recyclerView.setVisibility(View.VISIBLE);
        }else{
            recyclerView.setVisibility(View.GONE);
            //TODO Hide the list and show a "No data found!" message.
        }

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(playerAdapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Set up view fields.

    }

    @Override
    public void onResume() {
        super.onResume();
        //Set the title.
        //TODO Figure out why the title is not showing up on the dialog fragment.
        getDialog().getWindow().setTitle(getResources().getString(R.string.player_selection_dialog_title));
        //Find the screen size.
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        //Get the height and width.
        int width = size.x;
        int height = size.y;

        //Set the size of the dialog accordingly.
        getDialog().getWindow().setLayout(width, (int)Math.ceil(height/(1.25)));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PlayerShotDialogListener) {
            mListener = (PlayerShotDialogListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface PlayerShotDialogListener {
        // TODO: Update argument type and name
        void onDialogCancelled();
        void onPlayerSelected(Player newPlayer);

    }
}
