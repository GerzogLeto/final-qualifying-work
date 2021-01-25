package com.example.fqw.repository;

import com.example.fqw.entity.RefuelCommand;
import com.example.fqw.entity.RepairCommand;
import com.example.fqw.logic.StatusCommand;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RepairCommandRepository extends
        PagingAndSortingRepository<RepairCommand, Long> {

    @Query(value = "SELECT c FROM RepairCommand c WHERE (c.idTruck= :idTruck AND c.statusCommand= :statusCommand)")
    Optional<RepairCommand> findByIdTruckAndStatusCurrent(@Param("idTruck") long idTruck,
                                                          @Param("statusCommand") StatusCommand statusCommand);

    @Query(value = "SELECT c FROM RepairCommand c WHERE (c.idTruck= :idTruck AND c.statusCommand= :statusCommand) ORDER BY c.timeStart")
    Iterable<RepairCommand> findByIdTruckAndStatusFutureBySortedByTimeStart(
            @Param("idTruck") long idTruck, @Param("statusCommand") StatusCommand statusCommand);
}
