package com.example.fqw.repository;

import com.example.fqw.entity.LoadCommand;
import com.example.fqw.entity.RepairCommand;
import com.example.fqw.logic.StatusCommand;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LoadCommandRepository extends PagingAndSortingRepository<LoadCommand, Long> {

    @Query(value = "SELECT c FROM LoadCommand c WHERE (c.idTruck= :idTruck AND c.statusCommand= :statusCommand)")
    Optional<LoadCommand> findByIdTruckAndStatusCurrent(@Param("idTruck") long idTruck,
                                                          @Param("statusCommand") StatusCommand statusCommand);

    @Query(value = "SELECT c FROM LoadCommand c WHERE (c.idTruck= :idTruck AND c.statusCommand= :statusCommand) ORDER BY c.timeStart")
    Iterable<LoadCommand> findByIdTruckAndStatusFutureBySortedByTimeStart(
            @Param("idTruck") long idTruck, @Param("statusCommand") StatusCommand statusCommand);
}
