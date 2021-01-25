package com.example.fqw.repository;

import com.example.fqw.entity.GoToCommand;
import com.example.fqw.entity.RefuelCommand;
import com.example.fqw.logic.StatusCommand;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefuelCommandRepository extends
        PagingAndSortingRepository<RefuelCommand, Long> {

    @Query(value = "SELECT c FROM RefuelCommand c WHERE (c.idTruck= :idTruck AND c.statusCommand= :statusCommand) ORDER BY c.timeStart")
    Iterable<RefuelCommand> findByIdTruckAndStatusFutureBySortedByTimeStart(
            @Param("idTruck") long idTruck, @Param("statusCommand") StatusCommand statusCommand);

    @Query(value = "SELECT c FROM RefuelCommand c WHERE (c.idTruck= :idTruck AND c.statusCommand= :statusCommand)")
    Optional<RefuelCommand> findByIdTruckAndStatusCurrent(@Param("idTruck") long idTruck,
                                                        @Param("statusCommand") StatusCommand statusCommand);
}

