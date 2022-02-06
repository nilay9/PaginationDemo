package com.pagination.demo.dto;

import com.pagination.demo.model.Child;

import java.util.List;

/**
 * Set Response Parameters for Child Url
 */
public class ChildResponse {

    public List<Child> childDetails;

    public ChildResponse(List<Child> childDetails) {
        this.childDetails = childDetails;
    }
}
