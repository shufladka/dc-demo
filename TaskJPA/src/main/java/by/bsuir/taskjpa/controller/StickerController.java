package by.bsuir.taskjpa.controller;

import by.bsuir.taskjpa.model.dto.request.StickerRequestTo;
import by.bsuir.taskjpa.model.dto.response.StickerResponseTo;
import by.bsuir.taskjpa.service.StickerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stickers")
public class StickerController {

    private final StickerService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StickerResponseTo save(@RequestBody @Valid StickerRequestTo entity) {
        return service.save(entity);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StickerResponseTo> findAll(@RequestParam(defaultValue = "0") Integer pageNumber,
                                           @RequestParam(defaultValue = "5") Integer pageSize,
                                           @RequestParam(defaultValue = "id,desc") String[] sortParameters) {

        List<Order> sortOrders = getSortOrders(sortParameters);
        Pageable restriction = PageRequest.of(pageNumber, pageSize, Sort.by(sortOrders));
        return service.findAll(restriction);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StickerResponseTo findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public StickerResponseTo update(@RequestBody @Valid StickerRequestTo entity) {
        return service.update(entity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Valid Long id) {
        service.delete(id);
    }

    private List<Sort.Order> getSortOrders(String[] sortParameters) {
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

    private boolean isMultipleSortsOrders(String[] sortParameters) {
        return sortParameters[0].contains(",");
    }

    private Sort.Direction getSortDirection(String directionString) {
        return directionString.contains("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
    }
}
