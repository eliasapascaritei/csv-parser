package com.file.handler.services;

import com.file.handler.dtos.OpportunityDto;
import com.file.handler.models.FileMeta;
import com.file.handler.models.Opportunity;
import com.file.handler.repositories.FileRepository;
import com.file.handler.repositories.OpportunityRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Service
public class FileHandlerService {

    private final OpportunityRepository opportunityRepository;
    private final FileRepository fileRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public FileHandlerService(OpportunityRepository opportunityRepository, FileRepository fileRepository, ModelMapper modelMapper) {
        this.opportunityRepository = opportunityRepository;
        this.fileRepository = fileRepository;
        this.modelMapper = modelMapper;
    }

    public void uploadCsv(MultipartFile multipartFile) throws IOException {

        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(multipartFile.getBytes());

        try {
            BasicFileAttributes attributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            uploadFileMetaData(attributes, file.getName());

            List<OpportunityDto> opportunities = getOpportunitiesFromCSV(file);
            opportunities
                    .forEach(opportunityDto -> {
                        Opportunity opportunity = modelMapper.map(opportunityDto, Opportunity.class);
                        String opportunityId = opportunityDto.getOpportunityId();
                        boolean isDuplicate = opportunityRepository
                                .findById(opportunityId)
                                .isPresent();
                        if (!isDuplicate)
                            opportunityRepository
                                    .save(opportunity);
                    });
        } finally {
            Files.delete(file.toPath());
        }
    }

    private List<OpportunityDto> getOpportunitiesFromCSV(File file) {
        List<OpportunityDto> opportunities = new ArrayList<>();

        try (Reader reader = Files.newBufferedReader(Paths.get(String.valueOf(file.toPath())))) {

            CsvToBean<OpportunityDto> csvToBean = new CsvToBeanBuilder<OpportunityDto>(reader)
                    .withType(OpportunityDto.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withSkipLines(1) //skip header
                    .withFilter(strings ->
                            Arrays
                                    .stream(strings)
                                    .anyMatch(s -> !s.isEmpty()))
                    .build();

            opportunities = csvToBean.parse();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return opportunities;
    }

    private void uploadFileMetaData(BasicFileAttributes attr, String fileName) {
        Long fileSize = attr.size();
        LocalDateTime creationFileDate = LocalDateTime.ofInstant(
                attr.creationTime().toInstant(),
                ZoneId.systemDefault());

        FileMeta fileMeta = new FileMeta();

        fileMeta.setFileName(fileName);
        fileMeta.setFileSize(fileSize);
        fileMeta.setCreationDate(creationFileDate);
        fileMeta.setUploadDate(LocalDateTime.now());

        fileRepository.save(fileMeta);
    }
}
