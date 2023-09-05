package com.github.alexeysol.app.service;

import com.github.alexeysol.app.model.entity.City;
import com.github.alexeysol.app.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;

    public List<City> findAllCities() {
        return cityRepository.findAll();
    }

    public Optional<City> findCityById(long id) {
        return cityRepository.findById(id);
    }
}
