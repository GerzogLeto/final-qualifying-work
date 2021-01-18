package com.example.fqw.repository;

import com.example.fqw.entity.GoToCommand;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface GoToCommandRepository extends PagingAndSortingRepository<GoToCommand, Long> {
@Query(value = "SELECT c FROM GoToCommand c WHERE c.idTruck= :idTruck")
Iterable<GoToCommand> findAllByIdTruck(@Param("idTruck") long idTruck);
}
