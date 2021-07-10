package dao;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import vendor.Vendor;


@Getter
public class VendorDao {
    List<Vendor> vendors=new ArrayList<>();
    public List<Vendor> getVendorByState(String state) {
        return getVendors()
                .stream()
                .filter(vendor -> vendor.getState().equals(state))
                .collect(Collectors.toList());
    }
}
