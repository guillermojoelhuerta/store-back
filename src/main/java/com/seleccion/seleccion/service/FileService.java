package com.seleccion.seleccion.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface FileService {

    public String save(MultipartFile file) throws Exception;

    public Resource load(String name) throws Exception;

    public void save(List<MultipartFile> files) throws Exception;

    public Stream<Path> loadAll() throws Exception;

    public boolean deleteArchivo(String name) throws Exception;
}
