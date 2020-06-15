package net.ukr.tigor.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Catalog {

    private static Catalog instance = null;
    private List<Bike> catalogOfBikes;
    private boolean lockForChange;
    private boolean changed;

    private Catalog() {
         catalogOfBikes = Collections.synchronizedList(new ArrayList<>());
    }

    public static synchronized Catalog getInstance() {
        if (instance == null)
            instance = new Catalog();
        return instance;
    }

    public List<Bike> getCatalogOfBikes() {
        return catalogOfBikes;
    }

    public boolean isLockForChange() {
        return lockForChange;
    }

    public void setLockForChange(boolean lockForChange) {
        this.lockForChange = lockForChange;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public void setCatalogOfBikes(List<Bike> catalogOfBikes) {
        this.catalogOfBikes = catalogOfBikes;
    }
}
