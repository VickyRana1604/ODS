package customer;

import booking.OxygenBookingStrategy;
import dao.VendorDao;
import lombok.Data;
import vendor.Vendor;
import ODSException.*;

@Data
public abstract class Customer {
    private final VendorDao dao;
    private final CustomerType customerType;
    private String city;
    private final String customerId;
    private String state;
    private double maximumRequirement;

    public Customer(CustomerType customerType,
                    String city,
                    String customerId,
                    String state,
                    double maximumRequirement,
                    OxygenBookingStrategy oxygenBookingStrategy,
                    VendorDao dao) {
        this.customerType = customerType;
        this.city = city;
        this.customerId = customerId;
        this.state = state;
        this.maximumRequirement = maximumRequirement;
        this.dao=dao;
    }

    public abstract Vendor bookOxygen(double oxygenRequired,OxygenBookingStrategy bookingStrategy) throws ODSException ;
}
