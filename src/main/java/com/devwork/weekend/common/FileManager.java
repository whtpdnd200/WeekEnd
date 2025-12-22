package com.devwork.weekend.common;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {
    

    public final static String  FILE_UPLOAD_PATH = "D:\\joseung_Workspace\\springProject\\upload\\WeekEnd";
    // public final static String  FILE_UPLOAD_PATH = "/Users/jose-ung/Downloads/springProject/upload/WeekEnd";
    private final static String[] IMAGE_EXTENSION = {".jpg", ".jpeg", ".png", ".webp"};

    public static String savaFile(long userId, MultipartFile file) {


        // 파일이 전송 되었는지 확인
        if(file == null || !isImage(file)) {
            return null;
        }


        // 원본파일 이름 그대로 저장
        // 디렉터리(폴더)로 구분해서 파일 저장
        // 디렉터리 이름 : 사용자 정보 + 시간 정보 (ex) 3_15431323546
        // UNIX TIME : 1970년 1월 1일 0시 0분 0초 이후로 흐른시간을 표현하는 방식 (millisecond)
        String directoryName = "/" + userId + "_" + System.currentTimeMillis();

        //  디렉터리 만들기

        // 전체 디렉터리 경로
        String directoryPath = FILE_UPLOAD_PATH + directoryName;

        File directory = new File(directoryPath);

        // 디렉터리 생성이 실패하면 null 리턴
        if(!directory.mkdir()) {
            return null;
        }

        // 파일 저장
        String filePath = directoryPath + "/" + file.getOriginalFilename();

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(filePath);
            Files.write(path, bytes);
        } catch (IOException e) {
            return null;
        }

        // 서버 파일 경로 : D:/joseung_Workspace/springProject/upload/WeekEnd/3_15431323546/test.png
        // urlPath : /images/3_15431323546/test.png

        return "/images" + directoryName + "/" + file.getOriginalFilename();
    }

    public static boolean isImage(MultipartFile file) {

        for(String extension : IMAGE_EXTENSION) {

            String fileName = file.getOriginalFilename();

            if(fileName.endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    public static boolean deleteFile(String imagePath) {

        if(imagePath == null) {
            return false;
        }

        String fullFilePath = FILE_UPLOAD_PATH + imagePath.replace("images", "");

        Path path = Paths.get(fullFilePath);
        Path directoryPath = path.getParent();

        try {
            Files.delete(path);
            Files.delete(directoryPath);
        } catch (IOException e) {
            return false;
        }
        return true;

    }
}
