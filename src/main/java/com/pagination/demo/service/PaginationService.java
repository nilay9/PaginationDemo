package com.pagination.demo.service;

import com.pagination.demo.constant.PageFilter;
import com.pagination.demo.dto.ChildResponse;
import com.pagination.demo.dto.Paginated;
import com.pagination.demo.dto.ParentResponse;
import com.pagination.demo.model.Child;
import com.pagination.demo.model.Parent;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service class, Maintain Parent and Child details and index state
 */
@Service
public class PaginationService {
    ConcurrentHashMap<String, String> index
            = new ConcurrentHashMap<>();

    public static List<Parent> parentList = new ArrayList<>();
    public static Map<Integer, List<Child>> childMap = new HashMap<>();

    /**
     * This is a common method for all parent details request
     * An inMemory index is maintained for forward and backward page request with
     * ascending or descending order by parent id
     */
    public ParentResponse getParentDetails(Paginated paginated, PageFilter filter) {
        List<Parent> list = new ArrayList<>();
        String token = null;
        try {
            if (parentList.size() > 0) {
                if (paginated.getPagingState() != null) {
                    String currentState = index.get(paginated.getPagingState());
                    if (currentState != null) {
                        String[] cIndex = currentState.split(",");
                        if (filter == PageFilter.NEXT) {
                            indexForNextPage(paginated, list, cIndex, index);
                        } else if (filter == PageFilter.PREVIOUS) {
                            indexForPreviousPage(paginated, list, cIndex, index);
                        } else {
                            indexForCurrentPage(list, cIndex);
                        }
                        token = paginated.getPagingState();
                    } else {
                        return new ParentResponse(list, token, "Token does not exist");
                    }
                } else {
                    token = initializeIndex(list);
                }
                filterList(paginated, list);
            } else {
                return new ParentResponse(list, token, "No Record Exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ParentResponse(list, token, "");
    }

    public ChildResponse getChildDetails(Integer parentId) {
        List<Child> list = new ArrayList<>(childMap.get(parentId));
        list.sort(Child.idAesComparator);
        return new ChildResponse(list);
    }

    private String initializeIndex(List<Parent> list) {
        //First time user request parents details
        String token;
        if (parentList.size() > 1) {
            list.add(parentList.get(0));
            list.add(parentList.get(1));
            token = UUID.randomUUID().toString();
            index.put(token, "0,1");
        } else {
            list.add(parentList.get(0));
            token = UUID.randomUUID().toString();
            index.put(token, "0,");
        }
        return token;
    }

    private static void indexForCurrentPage(List<Parent> list, String[] cIndex) {
        //Get Current page index and set records
        if (cIndex.length == 2) {
            list.add(parentList.get(Integer.parseInt(cIndex[0])));
            list.add(parentList.get(Integer.parseInt(cIndex[1])));
        } else {
            list.add(parentList.get(Integer.parseInt(cIndex[0])));
        }
    }

    private static void indexForPreviousPage(Paginated paginated, List<Parent> list, String[] cIndex, ConcurrentHashMap<String, String> index) {
        //For Back button request
        int row0 = Integer.parseInt(cIndex[0]);
        if (cIndex.length == 2) {
            int row1 = Integer.parseInt(cIndex[1]);
            if (0 != row0 && 1 != (row1)) {
                index.replace(paginated.getPagingState(), (row0 - 2) + "," + (row1 - 2));
                list.add(parentList.get(row0 - 2));
                list.add(parentList.get(row1 - 2));
            } else {
                indexForCurrentPage(list, cIndex);
            }
        } else if (0 != row0) {
            index.replace(paginated.getPagingState(), (row0 - 2) + "," + (row0 - 1));
            list.add(parentList.get(row0 - 2));
            list.add(parentList.get(row0 - 1));
        } else {
            indexForCurrentPage(list, cIndex);
        }
    }

    private static void indexForNextPage(Paginated paginated, List<Parent> list, String[] cIndex, ConcurrentHashMap<String, String> index) {
        //For Next button request
        if (cIndex.length == 2) {
            int row0 = Integer.parseInt(cIndex[0]) + 2;
            int row1 = Integer.parseInt(cIndex[1]) + 2;
            if (parentList.size() - 1 > (row0) && parentList.size() >= (row1)) {
                index.replace(paginated.getPagingState(), row0 + "," + row1);
                list.add(parentList.get(row0));
                list.add(parentList.get(row1));
            } else if (parentList.size() >= (row0)) {
                //single record
                index.replace(paginated.getPagingState(), row0 + ",");
                list.add(parentList.get(row0));
            }
        } else {
            indexForCurrentPage(list, cIndex);
        }
    }

    private static void filterList(Paginated paginated, List<Parent> list) {
        //Sort records based on order request
        if (paginated.getOrder().equals("aes")) {
            list.sort(Parent.idAesComparator);
        } else {
            list.sort(Parent.idDesComparator);
        }
    }
}
