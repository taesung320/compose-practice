package com.example.sample.file;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/files")
    public Map<String, Object> selectAllFile(@RequestParam(defaultValue = "id") String sortColumn,
                                                @RequestParam(defaultValue = "ASC") String sortType,
                                                @RequestParam(defaultValue = "1") int pageNum,
                                                @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> rtnObj = new HashMap<>();

        List<FileEntity> fileList = fileService.selectFileList(sortColumn, sortType, pageNum, size);

        rtnObj.put("files", fileList);
        return rtnObj;
    }

    @GetMapping("/files/{id}")
    public Map<String, Object> selectFile(@PathVariable int id) {
        Map<String, Object> rtnObj = new HashMap<>();

        FileEntity fileEntity = fileService.selectFile(id);

        rtnObj.put("file", fileEntity);
        return rtnObj;
    }

    @PostMapping("/files")
    public Map<String, Object> insertFile(MultipartFile file) throws IOException {
        Map<String, Object> rtnObj = new HashMap<>();

        fileService.insertFile(file);

        rtnObj.put("result", "success");
        return rtnObj;
    }

    @PutMapping("/files/{id}")
    public Map<String, Object> updateFile(@PathVariable int id, @RequestBody MultipartFile file) throws IOException {
        Map<String, Object> rtnObj = new HashMap<>();

        fileService.updateFile(id, file);

        rtnObj.put("result", "success");
        return rtnObj;
    }

    @DeleteMapping("/files/{id}")
    public Map<String, Object> deleteFile(@PathVariable int id) {
        Map<String, Object> rtnObj = new HashMap<>();

        fileService.deleteFile(id);

        rtnObj.put("result", "success");
        return rtnObj;
    }

    @GetMapping("/download/{id}")
    public void downloadFile(@PathVariable int id, HttpServletResponse response) throws IOException {

        FileEntity fileEntity = fileService.selectFile(id);

        byte fileByte[] = FileUtils.readFileToByteArray(new File(fileEntity.getPath()));

        response.setContentType("application/octet-stream");
        response.setContentLength(fileByte.length);
        response.setHeader("Content-Disposition",  "attachment; fileName=\""+ URLEncoder.encode(fileEntity.getName(), "UTF-8")+"\";");
        response.getOutputStream().write(fileByte);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}
