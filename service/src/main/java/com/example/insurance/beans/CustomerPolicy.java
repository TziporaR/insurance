package com.example.insurance.beans;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CustomerPolicy {
    private long policyNumber;
    private long productNumber;
    private String insuredId;
    private String insuredFirstName;
    private String insuredLastName;
}
