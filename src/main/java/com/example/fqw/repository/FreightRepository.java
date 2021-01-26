package com.example.fqw.repository;

import com.example.fqw.entity.Freight;
import com.example.fqw.entity.LoadCommand;
import com.example.fqw.logic.StatusCommand;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface FreightRepository extends PagingAndSortingRepository<Freight, Long> {

    @Query(value = "SELECT f FROM Freight f WHERE" +
"((f.open= true) AND (f.placeOfLoadingCargo.start= :start OR f.placeOfLoadingCargo.finish= :start))" +
            " ORDER BY f.cost desc")
    Iterable<Freight> findByLoadPlace(
            @Param("start") String start);
}
