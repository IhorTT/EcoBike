package net.ukr.tigor.service;

import net.ukr.tigor.domain.Bike;
import net.ukr.tigor.domain.Catalog;

import java.lang.reflect.Field;
import java.util.List;

public class FindBikeThread implements Runnable {

    private Bike usersBike;
    private Catalog catalog = Catalog.getInstance();

    public FindBikeThread(Bike usersBike) {
        this.usersBike = usersBike;
    }

    /**
     *
     * @param userBike - bike for search that the user entered
     * @param catalogBike - bike from catalog
     * @return true if the fields match
     */
    private static boolean equalFields(Object userBike, Object catalogBike) {
        Field[] fields = userBike.getClass().getDeclaredFields();
        for (Field userfield : fields) {

            userfield.setAccessible(true);
            try {
                Field fieldCatalogBikes = catalogBike.getClass().getDeclaredField(userfield.getName());
                fieldCatalogBikes.setAccessible(true);
                Object dataUserField = userfield.get(userBike);
                // if the fields are empty, they are not searched
                if (dataUserField == null || dataUserField.equals(0) || dataUserField.equals(false)) {
                    continue;
                }
                if (!userfield.get(userBike).equals(fieldCatalogBikes.get(catalogBike))) {
                    return false;
                }
            } catch (IllegalAccessException | NoSuchFieldException e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void run() {
        findBike();
    }

    /**
     * match search method
     */
    private  void findBike() {
        // set marker to prohibit changing the list during the search
        catalog.setLockForChange(true);
        List<Bike> bikes = catalog.getCatalogOfBikes();
//        synchronized(catalog) {
            Bike bikeFromCatalog = null;
            for (Bike bike : bikes) {
//                for test
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                if (usersBike.equalsByParams(bike) && equalFields(usersBike, bike)) {
                    bikeFromCatalog = bike;
                    break;
                }
            }

            if (bikeFromCatalog == null) {
                System.out.println("You looking for: " + usersBike +" Result: no matching item");
            } else {
                System.out.println("You looking for: " + usersBike +" Result: " + bikeFromCatalog);
            }
//        }
        // marker reset
        catalog.setLockForChange(false);
    }

}
