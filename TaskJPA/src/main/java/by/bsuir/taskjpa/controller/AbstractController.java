package by.bsuir.taskjpa.controller;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractController {

    protected List<Sort.Order> getSortOrders(String[] sortParameters) {
        List<Sort.Order> sortOrders = new ArrayList<>();
        if (isMultipleSortsOrders(sortParameters)) {
            for (String sortParameter : sortParameters) {
                String[] sort = sortParameter.split(",");
                sortOrders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
            }
        }
        else {
            sortOrders.add(new Sort.Order(getSortDirection(sortParameters[1]), sortParameters[0]));
        }

        return sortOrders;
    }

    protected boolean isMultipleSortsOrders(String[] sortParameters) {
        return sortParameters[0].contains(",");
    }

    protected Sort.Direction getSortDirection(String directionString) {
        return directionString.contains("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
    }
}
