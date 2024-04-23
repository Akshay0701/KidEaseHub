package com.example.autismproject.Games;

public interface RecyclerViewAction {
    void onViewClicked(int clickedViewId, int clickedItemPosition);
    void onViewLongClicked(int clickedViewId, int clickedItemPosition);
}
