package com.example.sample.file;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileDAO {

    List<FileEntity> selectFileList(String sortColumn, String sortType, int skip, int size);

    FileEntity selectFile(int id);

    void insertFile(String uuid, String name, String path);

    void updateFile(int id, String uuid, String name, String path);

    void deleteFile(int id);
}
