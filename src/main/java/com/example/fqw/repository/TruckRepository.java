package com.example.fqw.repository;

import com.example.fqw.entity.Truck;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TruckRepository extends PagingAndSortingRepository<Truck, Long> {
}
