package com.example.metronome;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sdsmdg.harjot.crollerTest.Croller;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {link Metronome.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Metronome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Metronome extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    /*private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";*/

    // TODO: Rename and change types of parameters
    /*private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;*/

    //circural seek bar
    private Croller croller;
    private TextView bpmText;
    private Spinner accentSpinner;
    private Button buttonPlus;
    private Button minusButton;
    String current;

    public Metronome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * param param1 Parameter 1.
     * param param2 Parameter 2.
     * @return A new instance of fragment Metronome.
     */
    // TODO: Rename and change types and number of parameters
    public static Metronome newInstance() {
        Metronome fragment = new Metronome();
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        //default
        super.onCreate(savedInstanceState);

        //enable options
        setHasOptionsMenu(true);


    }

    //action buttons
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        //menu.clear();
        inflater.inflate(R.menu.main_menu, menu);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final int increase = 5;

        //circural seek bar
        View view =  inflater.inflate(R.layout.fragment_metronome, container, false);
        croller = view.findViewById(R.id.croller);
        bpmText = view.findViewById(R.id.editText2);

        croller.setMax(300); //max number
        croller.setProgress(100); //default number of seek bar
        croller.setMin(1);

        // accent spinner
        /*accentSpinner = view.findViewById(R.id.accent_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.accents_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accentSpinner.setAdapter(adapter);
        accentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(1){
                    case 0:
                        View childLayout = inflater.inflate(R.layout.accents, container, false);
                        view.addView()
                        break;
                    case 1:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        // button +5 change bpm number
        buttonPlus = view.findViewById(R.id.button3);
        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current = bpmText.getText().toString();
                int totalBpm = Integer.parseInt(current) + increase;
                if(totalBpm >= 1 && totalBpm <= 300){
                    bpmText.setText("" + totalBpm);
                    croller.setProgress(totalBpm);
                }
            }
        });

        // button -5 change bpm number
        minusButton = view.findViewById(R.id.button2);
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current = bpmText.getText().toString();
                int totalBpm = Integer.parseInt(current) - increase;
                if(totalBpm >= 1 && totalBpm <= 300){
                    bpmText.setText("" + totalBpm);
                    croller.setProgress(totalBpm);
                }
            }
        });

        //TODO
        // change number of bpm
        /*bpmText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int i = Integer.parseInt(s.toString());
                if (i >= 1 && i <= 300) {
                    croller.setProgress(Integer.parseInt(s.toString()));
                }
            }
        });*/

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //circular seek bar
        croller = getView().findViewById(R.id.croller);
        croller.setOnProgressChangedListener(new Croller.onProgressChangedListener() {
            @Override
            public void onProgressChanged(int progress) {
                bpmText.setText("" + progress);
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    /*public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

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
    /*public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
