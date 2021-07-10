package booking;

import vendor.Vendor;
import ODSException.*;

public interface OxygenBookingStrategy {

    Vendor book(BookingParam param) throws ODSException;
}
