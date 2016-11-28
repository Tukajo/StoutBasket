package com.stout.basketball.Fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.tjfri.stoutbasket.R;


public class ShotLocationFragment extends Fragment {


    private Button test;
    private Button dismiss;
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        final Drawable imgShot = getResources().getDrawable(R.drawable.shot);
        final int h = imgShot.getIntrinsicHeight();
        final int w = imgShot.getIntrinsicWidth();

        final RelativeLayout r1 = (RelativeLayout) view.findViewById(R.id.r1);

        /*dismiss = (Button) view.findViewById(R.id.dismiss);
        r1.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View view, MotionEvent event){
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    layoutInflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.popup, null);
                    popupWindow = new PopupWindow(container, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
                    popupWindow.showAtLocation(r1, Gravity.NO_GRAVITY, x, y);

                    container.setOnTouchListener(new View.OnTouchListener(){
                        public boolean onTouch(View view, MotionEvent motionEvent){
                           *//*if(motionEvent.getAction() == MotionEvent.ACTION_OUTSIDE){
                               popupWindow.dismiss();
                               return true;
                           }
                           return false;*//*
                            popupWindow.dismiss();
                            return true;
                        }
                    });


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
        });*/
    }
}
