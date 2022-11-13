package com.example.sample.file;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileDAO fileDAO;

    private final String FILE_UPLOAD_DIR_PATH = System.getProperty("user.dir") + File.separator +
        "workdir" + File.separator + "server" + File.separator + "src" + File.separator + "main" + File.separator + 
        "resources" + File.separator + "upload";

    @Override
    public List<FileEntity> selectFileList(String sortColumn, String sortType, int pageNum, int size) {
        return fileDAO.selectFileList(sortColumn, sortType, (pageNum - 1) * size, size);
    }

    @Override
    public FileEntity selectFile(int id) {
        return fileDAO.selectFile(id);
    }

    @Override
    public void insertFile(MultipartFile file) throws IOException {

        String uuid = UUID.randomUUID().toString();
        String fileName = file.getOriginalFilename();
        String filePath = FILE_UPLOAD_DIR_PATH + File.separator + uuid + '-' + fileName;

        file.transferTo(new File(filePath));

        fileDAO.insertFile(uuid, fileName, filePath);
    }

    @Override
    public void updateFile(int id, MultipartFile file) throws IOException {

        String uuid = UUID.randomUUID().toString();
        String fileName = file.getOriginalFilename();
        String filePath = FILE_UPLOAD_DIR_PATH + File.separator + uuid + '-' + fileName;

        file.transferTo(new File(filePath));

        fileDAO.updateFile(id, uuid, fileName, filePath);
    }

    @Override
    public void deleteFile(int id) {
        fileDAO.deleteFile(id);
    }
}
