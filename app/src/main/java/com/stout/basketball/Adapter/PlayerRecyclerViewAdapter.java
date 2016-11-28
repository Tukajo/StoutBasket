package com.stout.basketball.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.tjfri.stoutbasket.R;
import com.stout.basketball.Fragments.PlayerFragment;
import com.stout.basketball.Interfaces.ItemTouchHelperAdapter;
import com.stout.basketball.Models.Player;

import java.util.List;




public class PlayerRecyclerViewAdapter extends RecyclerView.Adapter<PlayerRecyclerViewAdapter.ViewHolder> implements ItemTouchHelperAdapter{

    private final List<Player> mValues;
    private final PlayerFragment.OnListFragmentInteractionListener mListener;

    public PlayerRecyclerViewAdapter(List<Player> items, PlayerFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_player, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mPlayerNumberTV.setText(Integer.toString(mValues.get(position).getPlayerNum()));
        holder.mPlayerPositionTV.setText(mValues.get(position).getPosition());
        holder.mPlayerNameTV.setText(mValues.get(position).getFirstName() + " " + mValues.get(position).getLastName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    public void add(Player newPlayer){
        //Add new player.
        mValues.add(newPlayer);
        //Notify data has changed for UI refresh.
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Player prev = mValues.remove(fromPosition);
        mValues.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
        notifyItemMoved(fromPosition, toPosition);
        notifyDataSetChanged();
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        Log.v("REMOVED", "Item was removed at position: " + position);
        mValues.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mPlayerNameTV;
        public final TextView mPlayerPositionTV;
        public final TextView mPlayerNumberTV;
        public Player mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mPlayerNameTV = (TextView) view.findViewById(R.id.playerFullName);
            mPlayerPositionTV = (TextView) view.findViewById(R.id.playerPosition);
            mPlayerNumberTV = (TextView) view.findViewById(R.id.playerNumber);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mPlayerNameTV.getText() + "'";
        }
    }
}
