package booking;

import vendor.Vendor;

import java.util.Comparator;

public class CompBasedOnIndustryOxygen implements Comparator<Vendor> {
    @Override
    public int compare(Vendor v1, Vendor v2) {
        return -Double.compare(v1.getIndustryOxygenCapacity(),v2.getIndustryOxygenCapacity());
    }
}
