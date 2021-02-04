package com.file.handler.repositories;

import com.file.handler.models.FileMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileMeta, Integer> {
}
