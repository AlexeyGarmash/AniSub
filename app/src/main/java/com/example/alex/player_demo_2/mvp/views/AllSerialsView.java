package com.example.alex.player_demo_2.mvp.views;

import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.alex.player_demo_2.models.SerialInfoShort;

import java.util.ArrayList;

public interface AllSerialsView extends BaseLceView {
    @StateStrategyType(AddToEndStrategy.class)
    void addToList(ArrayList<SerialInfoShort> serialInfoShort);
}
