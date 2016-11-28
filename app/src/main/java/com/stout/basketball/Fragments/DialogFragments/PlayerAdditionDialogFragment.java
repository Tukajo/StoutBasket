package com.stout.basketball.Fragments.DialogFragments;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.example.tjfri.stoutbasket.R;
import com.stout.basketball.Models.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PlayerAdditionDialogFragment extends DialogFragment {

    private EditText firstNameET, lastNameET;
    private Spinner positionSpinner;
    private NumberPicker playerNumberPicker;
    private Button submitBtn, cancelBtn;

    private PlayerAdditionDialogListener mListener;

    public PlayerAdditionDialogFragment() {
        // Required empty public constructor
    }


    public static PlayerAdditionDialogFragment newInstance() {
        PlayerAdditionDialogFragment fragment = new PlayerAdditionDialogFragment();
        fragment.setStyle(DialogFragment.STYLE_NORMAL, 0);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player_addition_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Set up view fields.
        firstNameET = (EditText) view.findViewById(R.id.playerAdditionFirstNameEditText);
        lastNameET = (EditText) view.findViewById(R.id.playerAdditionLastNameEditText);
        playerNumberPicker = (NumberPicker) view.findViewById(R.id.playerAdditionNumberPicker);
        positionSpinner = (Spinner) view.findViewById(R.id.playerAdditionPositionSpinner);
        submitBtn = (Button) view.findViewById(R.id.dialogBtnSubmit);
        cancelBtn = (Button) view.findViewById(R.id.dialogBtnCancel);


        //Program functionality for spinner.
        final List<String> positionList = Arrays.asList(getResources().getStringArray(R.array.basketball_positions));
        ArrayAdapter<String> positionAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,positionList);
        positionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        positionSpinner.setAdapter(positionAdapter);

        //Program functionality for the number picker.
        playerNumberPicker.setMaxValue(getResources().getInteger(R.integer.player_number_max));
        playerNumberPicker.setMinValue(getResources().getInteger(R.integer.player_number_min));

        //Functionality for cancellation.
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dismiss the dialog.
                getDialog().dismiss();
            }
        });

        //Functionality for submission.
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Construct a new player and close the dialog.
                mListener.onDialogSubmitted(new Player(firstNameET.getEditableText().toString(),
                                                        lastNameET.getEditableText().toString(),
                                                        playerNumberPicker.getValue(),
                                                        positionSpinner.getSelectedItem().toString()));

                getDialog().dismiss();
            }
        });

        // Show soft keyboard automatically and request focus to field
        firstNameET.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        //Set the title.
        //TODO Figure out why the title is not showing up on the dialog fragment.
        getDialog().getWindow().setTitle(getResources().getString(R.string.player_addition_dialog_title));
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
        if (context instanceof PlayerAdditionDialogListener) {
            mListener = (PlayerAdditionDialogListener) context;
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
    public interface PlayerAdditionDialogListener {
        // TODO: Update argument type and name
        void onDialogCancelled();
        void onDialogSubmitted(Player newPlayer);

    }
}
