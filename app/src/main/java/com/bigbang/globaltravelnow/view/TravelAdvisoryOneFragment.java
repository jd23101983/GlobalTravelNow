package com.bigbang.globaltravelnow.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigbang.globaltravelnow.R;
import com.bigbang.globaltravelnow.model.Result;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TravelAdvisoryOneFragment extends Fragment {

    Result travelAdvisoryResult;

    @BindView(R.id.country_name_textview)
    TextView countryNameTextView;

    @BindView(R.id.advisory_score_textview)
    TextView advisoryScoreTextView;

    @BindView(R.id.advisory_message_textView)
    TextView advisoryMessageTextView;

    @BindView(R.id.advisory_updated_date_textView)
    TextView advisoryUpdatedDateTextView;

    //@BindView()

    public TravelAdvisoryOneFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_travel_advisory_one, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        countryNameTextView.setText(travelAdvisoryResult.getData().getLang().getEn().getCountry());
        advisoryScoreTextView.setText(travelAdvisoryResult.getData().getSituation().getRating()+getText(R.string.five_pt_zero));
        advisoryMessageTextView.setText(travelAdvisoryResult.getData().getLang().getEn().getAdvice());
        advisoryUpdatedDateTextView.setText(travelAdvisoryResult.getData().getSituation().getUpdated());
    }

    public void setTravelAdvisoryResult(Result travelAdvisoryResult) {
        this.travelAdvisoryResult = travelAdvisoryResult;
    }

    @OnClick(R.id.return_button)
    public void returnFromAdvisory() {
        ((MainActivity)getContext()).returnFromAdvisory();
    }
}
