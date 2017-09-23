package com.db.dipenrana.thenewshub.fragments;


import android.support.annotation.Nullable;
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
import com.db.dipenrana.thenewshub.models.ArticleFilter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends DialogFragment implements View.OnClickListener{

    //region butterknife
    @BindView(R.id.cbArtsBox) CheckBox cbArts;
    @BindView(R.id.cbSportsBox) CheckBox cbSports;
    @BindView(R.id.cbAutomobiles) CheckBox cbAutomobiles;
    @BindView(R.id.cbFashionBox) CheckBox cbFashion;
    @BindView(R.id.cbBusiness) CheckBox cbBusines;
    @BindView(R.id.selectedDate)DatePicker dateSelected;
    @BindView(R.id.sortOrder)Spinner sortOrderSpinner;
    @BindView(R.id.btnSave) Button btnSaveClick;
    @BindView(R.id.btnCancel) Button btnCancelClick;
    //endregion
    String selectedDate;
    List<String> cbNewsSection = new ArrayList<String>();
    String sortSelection;
    ArticleFilter articleFilters;

    public FilterFragment() {
        // Required empty public constructor
    }

    public static FilterFragment newInstance(String param1, String param2) {
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

        //setup spinner.
        String[] items = new String[]{"Latest","Oldest"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
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
        selectedDate = getSelectedDate();
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
        dismiss();
        Log.d("btn","cancel clicked");
    }

}
