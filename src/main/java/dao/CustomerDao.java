package dao;

import customer.Customer;
import customer.CustomerType;
import customer.Hospital;
import customer.Industry;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CustomerDao {
    HashMap<String, Customer> customers=new HashMap<>();

    public List<Hospital> getHospitalsByCity(String city) {
        return getCustomers()
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().getCustomerType().equals(CustomerType.HOSPITAL) &&
                        entry.getValue().getCity().equals(city))
                .map(entry -> (Hospital)entry.getValue())
                .collect(Collectors.toList());
    }

    public List<Industry> getAllIndustries(){
        return getCustomers()
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().getCustomerType().equals(CustomerType.INDUSTRY))
                .map(entry -> (Industry)entry.getValue())
                .collect(Collectors.toList());
    }
}
