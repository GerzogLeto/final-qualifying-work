package com.example.fqw.repository;

import com.example.fqw.entity.Position;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PositionRepository extends PagingAndSortingRepository<Position, Long> {
}
