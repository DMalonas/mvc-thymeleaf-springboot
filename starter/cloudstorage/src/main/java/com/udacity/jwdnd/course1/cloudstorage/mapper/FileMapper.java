package com.udacity.jwdnd.course1.cloudstorage.mapper;

import org.apache.ibatis.annotations.*;
import  com.udacity.jwdnd.course1.cloudstorage.persistence.File;
import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES")
    List<File> findAllFiles();

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) " +
            "VALUES (#{filename}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    void insertFile(File file);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File findFileById(Long fileId);

    @Select("SELECT * FROM FILES WHERE filename = #{filename}")
    List<File> findFilesByFilename(String filename);

    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    List<File> findFilesByUserId(Long userId);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    void deleteFileById(Long fileId);

}


