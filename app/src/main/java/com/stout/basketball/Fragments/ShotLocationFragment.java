package com.stout.basketball.Fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.tjfri.stoutbasket.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class ShotLocationFragment extends Fragment {


    private Button test;
    private Button dismiss;
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    private OnShotLocationInteractionListener mListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.shot_location, container, false);
    }

    public static ShotLocationFragment newInstance(int position){
        Bundle args = new Bundle();
        ShotLocationFragment fragment = new ShotLocationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ShotLocationFragment.OnShotLocationInteractionListener) {
            mListener = (ShotLocationFragment.OnShotLocationInteractionListener) context;
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


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        final Drawable imgShot = getResources().getDrawable(R.drawable.shot);
        final int h = imgShot.getIntrinsicHeight();
        final int w = imgShot.getIntrinsicWidth();

        final RelativeLayout r1 = (RelativeLayout) view.findViewById(R.id.r1);

        r1.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View view, MotionEvent event){
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    layoutInflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);

                    mListener.ShotLocationSelected();

                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    final ImageView iv = new ImageView(getContext());
                    lp.setMargins(x-h/2, y-w/2, 0, 0);
                    iv.setLayoutParams(lp);
                    iv.setImageDrawable(imgShot);
                    ((ViewGroup) view).addView(iv);
                    new CountDownTimer(5000, 1000) {
                        public void onTick(long mSec){

                        }
                        public void onFinish(){
                            iv.setImageDrawable(null);
                        }
                    }.start();

                }
                return false;
            }
        });

    }
    public interface OnShotLocationInteractionListener {
        // TODO: Update argument type and name
        void ShotLocationSelected();
    }
}
