package com.example.sample.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    List<FileEntity> selectFileList(String sortColumn, String sortType, int pageNum, int size);

    FileEntity selectFile(int id);

    void insertFile(MultipartFile file) throws IOException;

    void updateFile(int id, MultipartFile file) throws IOException;

    void deleteFile(int id);
}
