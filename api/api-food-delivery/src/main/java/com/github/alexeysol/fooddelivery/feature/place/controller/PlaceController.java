package com.github.alexeysol.fooddelivery.feature.place.controller;

import com.github.alexeysol.common.feature.place.model.dto.PlaceDto;
import com.github.alexeysol.common.feature.place.model.dto.PlacePreviewDto;
import com.github.alexeysol.common.feature.place.model.dto.UpdatePlaceDto;
import com.github.alexeysol.common.shared.constant.ErrorMessageConstant;
import com.github.alexeysol.fooddelivery.feature.place.mapper.PlaceMapper;
import com.github.alexeysol.fooddelivery.feature.place.mapper.PlacePreviewMapper;
import com.github.alexeysol.fooddelivery.feature.place.model.entity.Place;
import com.github.alexeysol.fooddelivery.feature.place.service.PlaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/place", produces = "application/json")
@Validated
@RequiredArgsConstructor
public class PlaceController {
    private static final String PLACE = "Place";

    private final PlaceService placeService;
    private final PlaceMapper placeMapper;
    private final PlacePreviewMapper placePreviewMapper;

    @GetMapping
    public Page<PlacePreviewDto> getPlaces(
        @RequestParam(value = "page", defaultValue = "0", required = false) int page,
        @RequestParam(value = "size", defaultValue = "20", required = false) int size
    ) {
        var pageable = PageRequest.of(page, size);
        Page<Place> placePage = placeService.findAllPlaces(pageable);
        return placePreviewMapper.map(placePage, pageable);
    }

    @GetMapping("/{id}")
    public PlaceDto getPlaceById(@PathVariable long id) {
        var place = placeService.findPlaceById(id).orElseThrow(() -> {
            var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, PLACE, id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        });

        return placeMapper.map(place);
    }

    @PatchMapping("/{id}")
    public PlaceDto updatePlaceById(@PathVariable long id, @RequestBody @Valid UpdatePlaceDto dto) {
        var place = placeService.findPlaceById(id).orElseThrow(() -> {
            var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, PLACE, id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        });

        Place updatedPlace = placeMapper.map(dto, place);
        placeService.savePlace(updatedPlace);
        return placeMapper.map(updatedPlace);
    }
}
