package com.pagination.demo.dto;

import lombok.Data;

/**
 * Get Request Parameters for Parent Url
 */
@Data
public class Paginated {

    private String order;
    private String pagingState;

    public String getPagingState() {
        return pagingState;
    }

    public String getOrder() {
        return order != null && order.equalsIgnoreCase("des") ? "des" : "aes";
    }

}
