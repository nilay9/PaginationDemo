package com.pagination.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pagination.demo.constant.PageFilter;
import com.pagination.demo.dto.ChildResponse;
import com.pagination.demo.dto.Paginated;
import com.pagination.demo.dto.ParentResponse;
import com.pagination.demo.service.PaginationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Controller Class
 */
@RestController
@RequestMapping("/demoPagination")
@Validated
public class PaginationController {

    PaginationService service;

    public PaginationController(PaginationService service) {
        this.service = service;
    }

    /**
     * Base Url, First page
     */
    @GetMapping("/parent")
    public ResponseEntity<String> getParentDetails(final @Valid Paginated paginated) {
        try {
            ParentResponse parentResponse = service.getParentDetails(paginated, PageFilter.CURRENT);
            ObjectMapper mapper = new ObjectMapper();
            String response = mapper.writeValueAsString(parentResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Something went wrong", HttpStatus.OK);
        }
    }

    /**
     * For Next Page, PageState is passed as argument with order
     */
    @GetMapping("/parent/next")
    public ResponseEntity<String> getNextParentDetails(final @Valid Paginated paginated) {
        try {
            ParentResponse parentResponse = service.getParentDetails(paginated, PageFilter.NEXT);
            ObjectMapper mapper = new ObjectMapper();
            String response = mapper.writeValueAsString(parentResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Something went wrong", HttpStatus.OK);
        }
    }

    /**
     * For Previous Page, PageState is passed as argument with order
     */
    @GetMapping("/parent/previous")
    public ResponseEntity<String> getPreviousParentDetails(final @Valid Paginated paginated) {
        try {
            ParentResponse parentResponse = service.getParentDetails(paginated, PageFilter.PREVIOUS);
            ObjectMapper mapper = new ObjectMapper();
            String response = mapper.writeValueAsString(parentResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Something went wrong", HttpStatus.OK);
        }
    }

    /**
     * Get Child records
     */
    @GetMapping("/child")
    public ResponseEntity<String> getChildDetails(@RequestParam(required = false) String parentId) {
        try {
            ChildResponse childResponse = service.getChildDetails(Integer.valueOf(parentId));
            ObjectMapper mapper = new ObjectMapper();
            String response = mapper.writeValueAsString(childResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Something went wrong", HttpStatus.OK);
        }
    }
}
