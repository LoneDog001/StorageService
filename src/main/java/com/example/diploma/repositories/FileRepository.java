package com.example.diploma.repositories;

import com.example.diploma.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    @Query(value = "select f from File f where f.holder= :holder")
    Optional<List<File>> findAllByHolder(@Param("holder") String holder);

    File findByFileNameAndHolder(String fileName, String holder);

    void removeByFileNameAndHolder(String fileName, String holder);

    @Modifying
    @Query(value = "update File f set f.fileName = :newName where f.fileName = :fileName and f.holder =:holder")
    void renameFile(@Param("fileName") String fileName, @Param("newName") String newFileName, @Param("holder") String owner);
}
