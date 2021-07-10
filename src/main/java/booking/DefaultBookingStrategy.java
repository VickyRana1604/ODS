package booking;

import vendor.Vendor;
import ODSException.*;

import java.util.Collections;

public class DefaultBookingStrategy implements OxygenBookingStrategy {

    @Override
    public Vendor book(BookingParam param) throws ODSException {
        Collections.sort(param.getAllVendors(),param.getBookingPriority());
        Vendor vendor=param.getAllVendors().get(0);
        if(Double.compare(vendor.getTotCapacity(),param.getOxygenRequirement())<0){
            throw new VendorLowCapacityException();
        }
        vendor.grantOxygen(param.getOxygenRequirement(),param.getCustomer());
        return vendor;
    }
}
