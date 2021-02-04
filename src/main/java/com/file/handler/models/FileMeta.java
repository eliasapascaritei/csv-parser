package com.file.handler.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "files_meta")
@Data
public class FileMeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fileName;
    private Long fileSize;
    private LocalDateTime creationDate;
    private LocalDateTime uploadDate;
}
