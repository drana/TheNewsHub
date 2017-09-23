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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.db.dipenrana.thenewshub.R;

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
    @BindView(R.id.selectedDate)DatePicker spinnerDateSelected;
    @BindView(R.id.sortOrder)Spinner sortOrder;
    @BindView(R.id.btnSave) Button btnSaveClick;
    @BindView(R.id.btnCancel) Button btnCancelClick;

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

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public void OnSave(){
        Log.d("btn","save clicked");

    }


    public void OnCancel(){
        Log.d("btn","cancel clicked");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        return super.onCreateDialog(savedInstanceState);
    }
}
