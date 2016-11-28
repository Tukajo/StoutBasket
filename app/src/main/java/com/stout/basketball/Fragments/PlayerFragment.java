package com.stout.basketball.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stout.basketball.Adapter.PlayerRecyclerViewAdapter;
import com.stout.basketball.Callbacks.SimpleItemTouchHelperCallback;
import com.stout.basketball.Models.Player;
import com.stout.basketball.Utilities.GsonUtility;

import com.example.tjfri.stoutbasket.R;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PlayerFragment extends Fragment {
    public static final String ARG_PREFERENCE_KEY = "player_data";
    public static final String ARG_COLUMN_COUNT = "column-count";
    private ArrayList<Player> playerList = new ArrayList<Player>();
    private RecyclerView recyclerView;
    private PlayerRecyclerViewAdapter playerAdapter;
    private TextView emptyView;
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private SharedPreferences prefs;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PlayerFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PlayerFragment newInstance(int columnCount) {
        PlayerFragment fragment = new PlayerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

        emptyView = (TextView) view.findViewById(R.id.empty_view);
        // Set the adapter
        if(!data.isEmpty()){
            playerAdapter = new PlayerRecyclerViewAdapter(data,mListener);
            recyclerView.setAdapter(playerAdapter);
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }else{
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            //TODO Hide the list and show a "No data found!" message.
        }

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(playerAdapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void addNewPlayer(Player newPlayer){
        //Pass on the new player from the activity to the adapter.
        if(playerAdapter!=null){
            playerAdapter.add(newPlayer);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Player item);
    }
}
