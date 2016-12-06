package com.nsu.alexander.countryquiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.BindView;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.logging.Logger;

public class MainActivity extends BaseActivity implements OnStatisticsUpdate, OnClearStatisticsIntent {
    private static final Logger logger = Logger.getLogger(MainActivity.class.getName());

    private static final int STATISTICS_ID = 0;
    private static final int GAME_ID = 1;

    private static String MAP_FRAGMENT_TAG = "MAP_FRAGMENT_TAG";
    private static String STATISTICS_FRAGMENT_TAG = "STATISTICS_FRAGMENT_TAG";

    private static final String CURRENT_ITEM_STATE = "CURRENT_ITEM_STATE";

    private static final String APP_PREFERENCES = "MY_SETTINGS";
    private static final String IS_FIRST_START= "IS FIRST_START";

    private static final String WIN_COUNT = "WIN_COUNT";
    private static final String LOSE_COUNT = "LOSE_COUNT";

    private Drawer drawer;

    private PrimaryDrawerItem statisticsDrawerItem;
    private PrimaryDrawerItem gameDrawerItem;

    private AccountHeader accountHeader;
    private int currentItemState = GAME_ID;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        boolean isFirstStart = sharedPreferences.getBoolean(IS_FIRST_START, true);

        if (isFirstStart) {
            DatabaseController databaseController = new DatabaseController(this);

            databaseController.addNewPlace(new Place(37.743106, -119.5929, "Yosemite National Park", "Altai", "Yosemite National Park is a national park in California. It's very famous because of mule deers which inhabit park areas"));
            databaseController.addNewPlace(new Place(45.8325855, 6.8650383, "France", "Switzerland", "It is Mont Blanc, the highest mountain in western Europe which resides on the border between France and Italy"));
            databaseController.addNewPlace(new Place(-33.857394, 151.2154351, "Sydney Opera House", "Novosibirsk Opera and Ballet", "It's the Syndey Opera House, one of the 20th century's most famous buildings"));

            sharedPreferences.edit()
                    .putBoolean(IS_FIRST_START, false)
                    .apply();

            databaseController.close();
        }

        if (null == savedInstanceState) {
            currentItemState = GAME_ID;
            toolbar.setTitle(R.string.game_drawer_item);
        } else {
            currentItemState = savedInstanceState.getInt(CURRENT_ITEM_STATE);
            toolbar.setTitle(R.string.statistics_drawer_item);
            logger.info("Saved Instance State is not null: " + currentItemState);
        }

        accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.theatre)
                .build();

        gameDrawerItem = new PrimaryDrawerItem();
        gameDrawerItem
                .withIdentifier(GAME_ID)
                .withName(R.string.game_drawer_item)
                .withIcon(R.drawable.ic_games_black_24dp);

        statisticsDrawerItem = new PrimaryDrawerItem();
        statisticsDrawerItem
                .withIdentifier(STATISTICS_ID)
                .withName(R.string.statistics_drawer_item)
                .withIcon(R.drawable.ic_account_box_black_24dp);

        setSupportActionBar(toolbar);

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(accountHeader)
                .addDrawerItems(
                        statisticsDrawerItem,
                        gameDrawerItem
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        int itemId = (int) drawerItem.getIdentifier();

                        onItemSelected(itemId);

                        return true;
                    }
                })
                .build();

        drawer.setSelection(currentItemState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_ITEM_STATE, currentItemState);
    }

    private void onItemSelected(int id) {
        switch (id) {
            case GAME_ID :
                onGameItemSelected();
                break;
            case STATISTICS_ID :
                onStatisticsItemSelected();
                break;
        }
    }

    private void onStatisticsItemSelected() {
        logger.info("On statistics item selected");
        toolbar.setTitle(R.string.statistics_drawer_item);
        currentItemState = STATISTICS_ID;

        SharedPreferences sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        int winCount = sharedPreferences.getInt(WIN_COUNT, 0);
        int loseCount = sharedPreferences.getInt(LOSE_COUNT, 0);

        FragmentUtil.replaceFragment(StatisticsFragment.newInstance(winCount, loseCount), R.id.layout_for_showing_fragment, getFragmentManager(), STATISTICS_FRAGMENT_TAG);
    }

    private void onGameItemSelected() {
        logger.info("On game item selected");
        toolbar.setTitle(R.string.game_drawer_item);
        currentItemState = GAME_ID;

        FragmentUtil.replaceFragment(GameFragment.newInstance(), R.id.layout_for_showing_fragment, getFragmentManager(), MAP_FRAGMENT_TAG);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onStatisticUpdate(boolean isWin) {

        if (isWin) {
            SharedPreferences sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
            int winCount = sharedPreferences.getInt(WIN_COUNT, 0);
            sharedPreferences
                    .edit()
                    .putInt(WIN_COUNT, winCount + 1)
                    .apply();
        } else {
            SharedPreferences sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
            int loseCount = sharedPreferences.getInt(LOSE_COUNT, 0);
            sharedPreferences
                    .edit()
                    .putInt(LOSE_COUNT, loseCount + 1)
                    .apply();
        }
    }

    @Override
    public void clearStatistics() {
        SharedPreferences sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(LOSE_COUNT).remove(WIN_COUNT).apply();
    }
}
