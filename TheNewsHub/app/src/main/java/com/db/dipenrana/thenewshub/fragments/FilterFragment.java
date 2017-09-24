package com.db.dipenrana.thenewshub.fragments;


import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.db.dipenrana.thenewshub.R;
import com.db.dipenrana.thenewshub.models.ArticleFilter;
import com.db.dipenrana.thenewshub.utils.CommonUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends DialogFragment implements View.OnClickListener,DatePickerFragment.BeginDateListener {

    //region butterknife
    @BindView(R.id.cbArtsBox) CheckBox cbArts;
    @BindView(R.id.cbSportsBox) CheckBox cbSports;
    @BindView(R.id.cbAutomobiles) CheckBox cbAutomobiles;
    @BindView(R.id.cbFashionBox) CheckBox cbFashion;
    @BindView(R.id.cbBusiness) CheckBox cbBusines;
    @BindView(R.id.cbPolitics)CheckBox cbPolitics;
    @BindView(R.id.etBeginDate)EditText etBeginDate;
    //@BindView(R.id.selectedDate)DatePicker dateSelected;
    @BindView(R.id.sortOrder)Spinner sortOrderSpinner;
    @BindView(R.id.btnSave) Button btnSaveClick;
    @BindView(R.id.btnCancel) Button btnCancelClick;
    @BindView(R.id.btnDatePicker)ImageButton btnDatePicker;

    //endregion
    String selectedDate;
    List<String> cbNewsSection = new ArrayList<String>();
    String sortSelection;
    ArticleFilter articleFilters;

    public FilterFragment() {
        // Required empty public constructor
    }

    public static FilterFragment newInstance() {
        FilterFragment fragment = new FilterFragment();

//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }

    // Inflate the layout for this fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter,container,false);
        ButterKnife.bind(this,view);

        return view;
    }

    //attach listners and view lookups
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        //setup btn click listner
//        View.OnClickListener btnClicklistner = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (v.getId()){
//                    case R.id.btnSave:
//                        OnApplyFilters();
//                        break;
//                    case R.id.btnCancel:
//                        OnCancel();
//                        break;
//                    default:
//                        break;
//                }
//            }
//        };

//        btnSaveClick.setOnClickListener(btnClicklistner);
//        btnCancelClick.setOnClickListener(btnClicklistner);

        btnSaveClick.setOnClickListener(this);
        btnCancelClick.setOnClickListener(this);
        btnDatePicker.setOnClickListener(this);
        //setup spinner.

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, CommonUtils.SORT_ORDER);
        sortOrderSpinner.setAdapter(adapter);


        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSave:
                OnApplyFilters();
                break;
            case R.id.btnCancel:
                OnCancel();
                break;
            case R.id.btnDatePicker:
                OnDatePickerClick();
            default:
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    //interface for passing filters back to activity
    public interface FiltersDialogListener{
        void onApplyArticleFilters(ArticleFilter articleFilter);
    }

    //get filter values and store it in pojo
    public void OnApplyFilters(){
        selectedDate =  etBeginDate.getText().toString();
        sortSelection = sortOrderSpinner.getSelectedItem().toString();
        cbNewsSection = getSubSections();
        articleFilters = new ArticleFilter(selectedDate,sortSelection,cbNewsSection);
        FiltersDialogListener mListener = (FiltersDialogListener) getActivity();
        mListener.onApplyArticleFilters(articleFilters);
        dismiss();
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
        if(cbPolitics.isChecked())
            cbNewsSection.add("Politics");

        return cbNewsSection;

    }

//    private String getSelectedDate() {
//        int month = dateSelected.getMonth();
//        int day = dateSelected.getDayOfMonth();
//        int year = dateSelected.getYear();
//
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(year, month, day);
//
//        SimpleDateFormat sdfformat = new SimpleDateFormat("MM-dd-yyyy");
//        String dueDateSelected = sdfformat.format(calendar.getTime());
//
//        return  dueDateSelected;
//    }

    public void OnCancel(){
        dismiss();
        Log.d("btn","cancel clicked");
    }

    private void OnDatePickerClick() {

        //DialogFragment dateFragment = new DatePickerFragment();
        DatePickerFragment dateFragment = DatePickerFragment.newInstance();
        // SETS the target fragment for use later when sending results
        dateFragment.setTargetFragment(FilterFragment.this, 300);
        dateFragment.show(getFragmentManager(), "datePicker");

    }

    @Override
    public void OnFinishPickingBeginDate(String dateSelected){
        //beginDate = dateSelected;
        etBeginDate.setText(dateSelected);
    }

}
