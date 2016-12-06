package com.nsu.alexander.countryquiz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;

import java.util.logging.Logger;

public class StatisticsFragment extends BaseFragment {
    private static final Logger logger = Logger.getLogger(StatisticsFragment.class.getName());

    private static final String RIGHT_TAG = "RIGHT_TAG";
    private static final String WRONG_TAG = "WRONG_TAG";

    @BindView(R.id.guessedTitle)
    protected TextView guessed;

    @BindView(R.id.notGuessedTitle)
    protected TextView notGuessedTitle;

    public static StatisticsFragment newInstance(int countOfRight, int countOfWrong) {
        StatisticsFragment statisticsFragment = new StatisticsFragment();
        Bundle args = new Bundle();
        args.putInt(RIGHT_TAG, countOfRight);
        args.putInt(WRONG_TAG, countOfWrong);

        statisticsFragment.setArguments(args);
        return statisticsFragment;
    }

    @Override
    protected void onPostViewCrated(@Nullable Bundle savedInstanceState) {
        int countOfRight = getArguments().getInt(RIGHT_TAG);
        int countOfWrong = getArguments().getInt(WRONG_TAG);
        guessed.setText(getString(R.string.count_of_guessed_places) + ": " +  countOfRight);
        notGuessedTitle.setText(getString(R.string.count_of_not_guessed_places) + ": " + countOfWrong);
    }

    @OnClick(R.id.clearStatistics)
    protected void onClearStatisticsClicked(View v) {
        guessed.setText(getString(R.string.count_of_guessed_places) + ": " +  0);
        notGuessedTitle.setText(getString(R.string.count_of_not_guessed_places) + ": " + 0);

        if (getActivity() instanceof OnClearStatisticsIntent) {
            ((OnClearStatisticsIntent) getActivity()).clearStatistics();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.statistics_fragment_layout;
    }
}
