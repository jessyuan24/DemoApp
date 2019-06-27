package com.coldwizards.demoapp.adapter;

/**
 * Created by jess on 19-6-26.
 */
public class Captain implements RowingBoat {

    private RowingBoat rowingBoat;

    public Captain(RowingBoat rowingBoat) {
        this.rowingBoat = rowingBoat;
    }

    @Override
    public void row() {
        rowingBoat.row();
    }
}
