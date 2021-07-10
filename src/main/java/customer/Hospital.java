package customer;

import booking.BookingParam;
import booking.CompBasedOnTotalOxygen;
import booking.OxygenBookingStrategy;
import dao.VendorDao;
import lombok.Builder;
import vendor.Vendor;
import ODSException.*;

public class Hospital extends Customer {

    @Builder
    public Hospital(String city,
                    String customerId,
                    String state,
                    double maximumRequirement,
                    OxygenBookingStrategy oxygenBookingStrategy,
                    VendorDao dao) {
        super(CustomerType.HOSPITAL,
                city,
                customerId,
                state,
                maximumRequirement,
                oxygenBookingStrategy,
                dao);
    }

    @Override
    public Vendor bookOxygen(double oxygenRequirement, OxygenBookingStrategy bookingStrategy) throws ODSException {
        if (Double.compare(oxygenRequirement, getMaximumRequirement()) > 0) {
            throw new CustomerLowCapaciityException();
        }

        Vendor vendor = bookingStrategy.book(BookingParam.builder()
                .oxygenRequirement(oxygenRequirement)
                .allVendors(getDao().getVendors())
                .customer(this)
                .bookingPriority(new CompBasedOnTotalOxygen())
                .build());

        setMaximumRequirement(getMaximumRequirement() - oxygenRequirement);
        return vendor;
    }
}
