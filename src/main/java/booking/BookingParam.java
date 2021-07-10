package booking;

import java.util.Comparator;
import java.util.List;

import customer.Customer;
import lombok.Builder;
import lombok.Getter;
import vendor.Vendor;

@Builder
@Getter
public class BookingParam {
    double oxygenRequirement;
    Customer customer;
    Comparator bookingPriority;
    List<Vendor> allVendors;
}
