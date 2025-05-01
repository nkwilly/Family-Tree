package com.gi.ro.repository;

import com.gi.ro.entity.Male;
import com.gi.ro.entity.Union;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UnionRepository extends JpaRepository<Union, UUID> {
    @Query(value = "INSERT INTO \"union\" (id, husband_id, wife_id, date, place, church) VALUES (:id, :husband_id, :wife_id, :date, :place, :church)", nativeQuery = true)
    @Modifying
    @Transactional
    void insertUnion(@Param("id") UUID id,
                     @Param("husband_id") UUID husbandId,
                     @Param("wife_id") UUID wifeId,
                     @Param("date") String date,
                     @Param("place") String place,
                     @Param("church") boolean church);

    @Query(value = "SELECT * FROM \"union\" WHERE id = :id", nativeQuery = true)
    Optional<Union> findUnionById(@Param("id") UUID id);

    @Query(value = "SELECT * FROM \"union\"", nativeQuery = true)
    List<Union> findAllUnions();

    @Query(value = "UPDATE \"union\" SET husband_id = :husband_id, wife_id = :wife_id, date = :date, place = :place, church = :church WHERE id = :id", nativeQuery = true)
    @Modifying
    @Transactional
    void updateUnion(@Param("id") UUID id,
                     @Param("husband_id") UUID husbandId,
                     @Param("wife_id") UUID wifeId,
                     @Param("date") String date,
                     @Param("place") String place,
                     @Param("church") boolean church);

    @Query(value = "DELETE FROM \"union\" WHERE id = :id", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteUnionById(@Param("id") UUID id);

    // Requête pour récupérer les unions avec leurs relations (jointure)
    @Query(value = "SELECT u.* FROM \"union\" u " +
            "LEFT JOIN male m ON u.husband_id = m.id " +
            "LEFT JOIN female f ON u.wife_id = f.id", nativeQuery = true)
    List<Union> findAllUnionsWithRelations();

    // Requête pour récupérer une union spécifique avec ses relations
    @Query(value = "SELECT u.* FROM \"union\" u " +
            "LEFT JOIN male m ON u.husband_id = m.id " +
            "LEFT JOIN female f ON u.wife_id = f.id " +
            "WHERE u.id = :id", nativeQuery = true)
    Optional<Union> findUnionWithRelationsById(@Param("id") UUID id);
}
