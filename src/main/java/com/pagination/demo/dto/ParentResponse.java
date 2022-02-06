package com.pagination.demo.dto;

import com.pagination.demo.model.Parent;
import java.util.List;

/**
 * Set Response Parameters for Parent Url
 */
public class ParentResponse {

    public List<Parent> parentDetails;

    public String pagingState;

    public String message;

    public ParentResponse(List<Parent> parentDetails, String pagingState, String message) {
        this.parentDetails = parentDetails;
        this.pagingState = pagingState;
        this.message = message;
    }
}
