package customer;

import booking.BookingParam;
import booking.CompBasedOnIndustryOxygen;
import booking.CompBasedOnTotalOxygen;
import booking.OxygenBookingStrategy;
import dao.VendorDao;
import lombok.Builder;
import lombok.Getter;
import vendor.Vendor;
import ODSException.*;

@Getter
public class Industry extends Customer {

    @Builder
    public Industry(String city,
                    String customerId,
                    String state,
                    double maximumRequirement,
                    OxygenBookingStrategy oxygenBookingStrategy,
                    VendorDao dao) {
        super(CustomerType.INDUSTRY,
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
                .bookingPriority(new CompBasedOnIndustryOxygen())
                .build());

        setMaximumRequirement(getMaximumRequirement()-oxygenRequirement);
        return vendor;
    }
}
