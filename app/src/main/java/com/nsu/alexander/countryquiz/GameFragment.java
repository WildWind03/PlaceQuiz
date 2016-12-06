package com.nsu.alexander.countryquiz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.OnClick;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.Random;
import java.util.logging.Logger;

public class GameFragment extends BaseFragment implements OnStreetViewPanoramaReadyCallback {
    private static final Logger logger = Logger.getLogger(GameFragment.class.getName());

    @BindView(R.id.firstCountry)
    protected Button firstCountry;

    @BindView(R.id.secondCountry)
    protected Button secondCountry;

    private StreetViewPanorama streetViewPanorama;
    private String currentPlaceDescription;

    public static GameFragment newInstance() {
        return new GameFragment();
    }

    @Override
    protected void onPostViewCrated(@Nullable Bundle savedInstanceState) {
        StreetViewPanoramaFragment streetViewPanoramaFragment1 = StreetViewPanoramaFragment.newInstance();
        FragmentUtil.replaceFragment(streetViewPanoramaFragment1, R.id.map_layout, getFragmentManager(), "STREET");
        streetViewPanoramaFragment1.getStreetViewPanoramaAsync(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.game_fragment_layout;
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        streetViewPanorama.setUserNavigationEnabled(false);
        this.streetViewPanorama = streetViewPanorama;
        setNextPlace(streetViewPanorama);
    }

    private void setNextPlace(StreetViewPanorama streetViewPanorama) {
        Random random = new Random();

        DatabaseController databaseController = new DatabaseController(getActivity());
        long maxId = databaseController.getMaxId();
        int id = random.nextInt((int) maxId);
        Place place = databaseController.getPlaceById(id + 1);
        streetViewPanorama.setPosition(new LatLng(place.getLatitude(), place.getGratitude()));

        firstCountry.setText(place.getRightPlace());
        secondCountry.setText(place.getWrongPlace());
        currentPlaceDescription = place.getPlaceDescription();

    }

    @OnClick(R.id.firstCountry)
    protected void onFirstCountryButtonClick(View v) {
        String prevPlaceDescription = currentPlaceDescription;

        setNextPlace(streetViewPanorama);
        showMessage(getActivity().getString(R.string.yoy_are_right), prevPlaceDescription, null);

        if (getActivity() instanceof OnStatisticsUpdate) {
            ((OnStatisticsUpdate) getActivity()).onStatisticUpdate(true);
        }
    }

    @OnClick(R.id.secondCountry)
    protected void setSecondCountryClick(View v) {
        String prevPlaceDescription = currentPlaceDescription;

        setNextPlace(streetViewPanorama);

        if (getActivity() instanceof OnStatisticsUpdate) {
            ((OnStatisticsUpdate) getActivity()).onStatisticUpdate(false);
        }

        showMessage(getActivity().getString(R.string.not_right_text), prevPlaceDescription, null);
    }
}
