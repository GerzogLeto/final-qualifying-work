package com.example.fqw.repository;

import com.example.fqw.entity.GoToCommand;
import com.example.fqw.logic.StatusCommand;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GoToCommandRepository extends PagingAndSortingRepository<GoToCommand, Long> {
@Query(value = "SELECT c FROM GoToCommand c WHERE c.idTruck= :idTruck")
Iterable<GoToCommand> findAllByIdTruck(@Param("idTruck") long idTruck);

@Query(value = "SELECT c FROM GoToCommand c WHERE (c.idTruck= :idTruck AND c.statusCommand= :statusCommand)")
Optional<GoToCommand> findByIdTruckAndStatusCurrent(@Param("idTruck") long idTruck,
                                                        @Param("statusCommand") StatusCommand statusCommand);

@Query(value = "SELECT c FROM GoToCommand c WHERE (c.idTruck= :idTruck AND c.statusCommand= :statusCommand) ORDER BY c.timeStart")
Iterable<GoToCommand> findByIdTruckAndStatusFutureBySortedByTimeStart(
        @Param("idTruck") long idTruck, @Param("statusCommand")StatusCommand statusCommand);
}

