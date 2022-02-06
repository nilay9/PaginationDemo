package com.pagination.demo.initializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pagination.demo.model.ChildRoot;
import com.pagination.demo.model.ParentRoot;
import com.pagination.demo.service.PaginationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Responsible for filling json data into InMemory.
 * Will only Run one time
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PaginationInitializer implements CommandLineRunner {

    private static final String PARENT_PATH = "src/main/resources/records/Parent.json";
    private static final String CHILD_PATH = "src/main/resources/records/Child.json";


    private void loadDetails() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            ParentRoot objectParentList = mapper.readValue(new File(PARENT_PATH), ParentRoot.class);
            ChildRoot objectChildList = mapper.readValue(new File(CHILD_PATH), ChildRoot.class);

            objectParentList.data.forEach(parent -> {
                objectChildList.data.forEach(child -> {
                    if (Objects.equals(parent.id, child.parentId)) {
                        child.setSender(parent.sender);
                        child.setReceiver(parent.receiver);
                    }
                });
            });

            PaginationService.childMap = objectChildList.data.stream()
                    .collect( Collectors.groupingBy(child -> child.parentId));

            objectParentList.data.forEach(parent ->
                parent.setTotalPaidAmount(
                        PaginationService.childMap.containsKey(parent.id) ?
                        PaginationService.childMap.get(parent.id).stream().flatMapToInt(val -> IntStream.of(val.paidAmount == null ? 0 : val.paidAmount)).sum() : 0 ));
            PaginationService.parentList.addAll(objectParentList.data);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(String... args) {
        loadDetails();
    }
}
