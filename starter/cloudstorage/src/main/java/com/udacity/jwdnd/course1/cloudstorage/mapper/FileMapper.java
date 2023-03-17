package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.services.UtilService;
import org.apache.ibatis.annotations.*;
import  com.udacity.jwdnd.course1.cloudstorage.persistence.File;
import org.springframework.ui.Model;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES")
    List<File> findAllFiles();

    @Insert("INSERT INTO FILES (fileName, contenttype, filesize, userid, filedata) " +
            "VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insertFile(File file);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File findFileById(Long fileId);

    @Select("SELECT * FROM FILES WHERE fileName = #{fileName}")
    List<File> findFilesByFileName(String fileName);

    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    List<File> findFilesByUserId(Integer userId);

    @Select("SELECT fileName FROM FILES WHERE userid = #{userId}")
    List<String> getFilesByUserId(Integer userId);

    @Select("SELECT * FROM FILES WHERE fileName = #{fileName}")
    File getFileByName(String fileName);

    @Select("SELECT * FROM FILES")
    List<File> getFiles();

    @Delete("DELETE FROM FILES WHERE fileName = #{fileName}")
    int deleteFileById(Long fileId);


    @Select("SELECT COUNT(*) FROM FILES WHERE fileName = #{fileName}")
    int selectCountByFileName(String fileName);

    @Delete("DELETE FROM FILES WHERE fileName = #{fileName}")
    int deleteFileByFileName(String fileName);

    default void deleteEntrySafely(String fileName, Model model, UtilService utilService) {
        int count = selectCountByFileName(fileName);
        if (count > 0) {
            if (deleteFileByFileName(fileName) < 0) {
                model.addAttribute("message", "File deletion failed");
                utilService.updateModel(model, false);
            } else {
                utilService.updateModel(model, true);
            }
        } else {
            model.addAttribute("message", "File doesn't exist");
            utilService.updateModel(model, false);
        }
    }

}


