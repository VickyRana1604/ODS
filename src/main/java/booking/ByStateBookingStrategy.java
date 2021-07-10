package booking;

import java.util.List;

import vendor.Vendor;
import ODSException.*;

import java.util.Collections;
import java.util.stream.Collectors;


public class ByStateBookingStrategy implements OxygenBookingStrategy {

    @Override
    public Vendor book(BookingParam param) throws ODSException {
        List<Vendor> byStateVendors = param.getAllVendors()
                .stream()
                .filter(vendor -> vendor.getState().equals(param.getCustomer().getState()))
                .collect(Collectors.toList());

        Collections.sort(byStateVendors, param.getBookingPriority());

        Vendor vendor = byStateVendors.get(0);
        if (Double.compare(vendor.getTotCapacity(), param.getOxygenRequirement()) < 0) {
            throw new VendorLowCapacityException();
        }

        vendor.grantOxygen(param.getOxygenRequirement(), param.getCustomer());
        return vendor;
    }
}
