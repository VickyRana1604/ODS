package booking;

import vendor.Vendor;

import java.util.Comparator;

public class CompBasedOnTotalOxygen implements Comparator<Vendor> {
    @Override
    public int compare(Vendor v1, Vendor v2) {
        return -Double.compare(v1.getTotCapacity(),v2.getTotCapacity());
    }
}
