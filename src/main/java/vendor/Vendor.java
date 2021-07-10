package vendor;

import customer.Customer;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Getter
@Builder
public class Vendor {
    String city;
    String state;
    double medicalOxygenCapacity;
    double industryOxygenCapacity;

    public double getTotCapacity() {
        return medicalOxygenCapacity + industryOxygenCapacity;
    }

    public void grantOxygen(double oxygenRequired, Customer customer) {
        switch (customer.getCustomerType()) {
            case HOSPITAL:
                double remaining = medicalOxygenCapacity - oxygenRequired;
                medicalOxygenCapacity = Math.max(0.0, remaining);
                industryOxygenCapacity = industryOxygenCapacity + Math.min(0.0, remaining);
                break;
            case INDUSTRY:
                industryOxygenCapacity = industryOxygenCapacity - oxygenRequired;
                break;
        }
    }
}
