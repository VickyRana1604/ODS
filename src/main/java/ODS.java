import ODSException.ODSException;
import booking.ByStateBookingStrategy;
import booking.DefaultBookingStrategy;
import customer.CustomerType;
import customer.Hospital;
import customer.Industry;
import dao.CustomerDao;
import dao.VendorDao;
import vendor.Vendor;


public class ODS {

    VendorDao vendorDao;
    CustomerDao customerDao;

    public ODS(VendorDao vendorDao, CustomerDao customerDao) {
        this.vendorDao = vendorDao;
        this.customerDao = customerDao;
    }

    String registerVendor(String city,
                          String state,
                          double medicalOxygenCapacity,
                          double industryOxygenCapacity) {
        vendorDao.getVendors().add(Vendor.builder()
                .city(city)
                .state(state)
                .medicalOxygenCapacity(medicalOxygenCapacity)
                .industryOxygenCapacity(industryOxygenCapacity)
                .build());
        return "Vendor Added";
    }

    String registerCustomer(String customerId,
                            CustomerType customerType,
                            String city,
                            String state,
                            double maximumRequirement) {

        switch (customerType) {
            case HOSPITAL:
                customerDao.getCustomers().put(customerId, Hospital.builder()
                        .customerId(customerId)
                        .city(city)
                        .dao(vendorDao)
                        .state(state)
                        .maximumRequirement(maximumRequirement)
                        .build());
                break;
            case INDUSTRY:
                customerDao.getCustomers().put(customerId, Industry.builder()
                        .customerId(customerId)
                        .city(city)
                        .dao(vendorDao)
                        .state(state)
                        .maximumRequirement(maximumRequirement)
                        .build());
                break;
        }
        return "Customer added";
    }

    String bookOxygen(String customerId, double oxygenRequirement) throws ODSException {

        Vendor vendor = customerDao.getCustomers().get(customerId).bookOxygen(oxygenRequirement, new DefaultBookingStrategy());
        return String.format("Vendor found in state: %s and city: %s", vendor.getState(), vendor.getCity());
    }

    String showAllVendors(String state) {
        String response = "";
        int i = 0;
        for (Vendor v : vendorDao.getVendorByState(state)) {
            response += String.format("\nVendor%d in state: %s and city: %s medCap: %sd indCap: %s",
                    i++,
                    v.getState(),
                    v.getCity(),
                    v.getMedicalOxygenCapacity(),
                    v.getIndustryOxygenCapacity());
        }

        return response;
    }

    String showHospitals(String city) {
        String response = "";
        for (Hospital h : customerDao.getHospitalsByCity(city)) {
            response += String.format("\nHospital with id: %s in state: %s and city: %s",
                    h.getCustomerId(),
                    h.getState(),
                    h.getCity());
        }
        return response;
    }

    String showAllIndustries() {
        String response = "";
        for (Industry i : customerDao.getAllIndustries()) {
            response += String.format("\nHospital with id: %s in state: %s and city: %s",
                    i.getCustomerId(),
                    i.getState(),
                    i.getCity());
        }
        return response;
    }

    String bookOxyggenPreferState(String customerId, double oxygenRequirement) throws ODSException {

        Vendor vendor;
        try {
            vendor = customerDao.getCustomers().get(customerId).bookOxygen(oxygenRequirement, new ByStateBookingStrategy());
        } catch (ODSException e) {
            vendor = customerDao.getCustomers().get(customerId).bookOxygen(oxygenRequirement, new DefaultBookingStrategy());
        }

        return String.format("Vendor found in state: %s and city: %s", vendor.getState(), vendor.getCity());
    }

    interface Run{
        String run()throws ODSException;
    }
    private static void execute(Run run){
        try{
            System.out.println(run.run());
        }catch (ODSException e){
            System.out.println("ERROR: "+ e.toString());
        }
        System.out.println("=================================");
    }
    public static void main(String args[]) {

        ODS service = new ODS(new VendorDao(),
                new CustomerDao());

        execute(() -> service.registerVendor("Kolkata","West Bengal",10,100));
        execute(() -> service.registerVendor("Bengaluru","Karnataka",100,100));
        execute(() -> service.registerVendor("Mysuru","Karnataka",50,100));
        execute(() -> service.registerCustomer("H2",CustomerType.HOSPITAL,"Kolkata","West Bengal",1500));
        execute(() -> service.registerCustomer("H1",CustomerType.HOSPITAL,"Bengaluru","Karnataka",1500));
        execute(() ->service.registerCustomer("I1",CustomerType.INDUSTRY,"Bengaluru","Karnataka",150));
        execute(() ->service.bookOxyggenPreferState("H2",100));
        execute(() ->service.bookOxygen("H1",400));
        execute(() ->service.showAllVendors("Karnataka"));
        execute(() ->service.bookOxygen("I1",100));
        execute(() ->service.showAllVendors("Karnataka"));
        execute(() ->service.showHospitals("Bengaluru"));
        execute(() ->service.showAllIndustries());

    }
}
