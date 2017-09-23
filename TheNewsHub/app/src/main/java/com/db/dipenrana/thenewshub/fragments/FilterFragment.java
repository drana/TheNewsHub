package com.db.dipenrana.thenewshub.fragments;


import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.db.dipenrana.thenewshub.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends DialogFragment {
    @BindView(R.id.cbArtsBox) CheckBox cbArts;
    @BindView(R.id.cbSportsBox) CheckBox cbSports;
    @BindView(R.id.cbAutomobiles) CheckBox cbAutomobiles;
    @BindView(R.id.cbFashionBox) CheckBox cbFashion;
    @BindView(R.id.cbBusiness) CheckBox cbBusines;
    @BindView(R.id.selectedDate)DatePicker dateSelected;
    @BindView(R.id.sortOrder)Spinner sortOrderSpinner;
    @BindView(R.id.btnSave) Button btnSaveClick;
    @BindView(R.id.btnCancel) Button btnCancelClick;

    String selectedDate;
    List<String> cbNewsSection = new ArrayList<String>();
    String sortSelection;

    public FilterFragment() {
        // Required empty public constructor
    }

    public static FilterFragment newInstance(String param1, String param2) {
        FilterFragment fragment = new FilterFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter,container,false);
        ButterKnife.bind(this,view);

        //setup btn click listner
        View.OnClickListener btnClicklistner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btnSave:
                        OnSave();
                        break;
                    case R.id.btnCancel:
                        OnCancel();
                        break;
                    default:
                        break;
                }
            }
        };

        btnSaveClick.setOnClickListener(btnClicklistner);
        btnCancelClick.setOnClickListener(btnClicklistner);

        //setup spinner.
        String[] items = new String[]{"Oldest", "Latest"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        sortOrderSpinner.setAdapter(adapter);

        //setup checkbox

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public void OnSave(){
        selectedDate = getSelectedDate();
        sortSelection = sortOrderSpinner.getSelectedItem().toString();
        cbNewsSection = getSubSections();
        Log.d("btn","save clicked");

    }

    private List<String> getSubSections() {

        if(cbArts.isChecked())
            cbNewsSection.add("Arts");
        if(cbAutomobiles.isChecked())
            cbNewsSection.add("Automobiles");
        if(cbBusines.isChecked())
            cbNewsSection.add("Business");
        if(cbFashion.isChecked())
            cbNewsSection.add("Fashion");
        if (cbSports.isChecked())
            cbNewsSection.add("Sports");

        return cbNewsSection;

    }

    private String getSelectedDate() {
        int month = dateSelected.getMonth();
        int day = dateSelected.getDayOfMonth();
        int year = dateSelected.getYear();


        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        SimpleDateFormat sdfformat = new SimpleDateFormat("MM-dd-yyyy");
        String dueDateSelected = sdfformat.format(calendar.getTime());

        return  dueDateSelected;
    }


    public void OnCancel(){
        Log.d("btn","cancel clicked");
    }

}
