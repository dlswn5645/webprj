package com.myapp.webprj.common;

import com.myapp.webprj.util.FileUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;


@Controller
@Log4j2
public class UploadController {

    //업로드 파일 저장 경로
    private static final String uploadPath = "D:\\developing_sij\\upload";

    //업로드 form jsp파일을 포워딩하는 화면 처리
    @GetMapping("/uploadForm")
    public String uploadForm(){
        return "upload/upload-form";
    }

    //업로드된 파일을 처리
    //MultipartFile : 클라이언트가 전송한 파일데이터
    @PostMapping("/upload")
    public String upload(@RequestParam("file") List<MultipartFile> fileList){


        for (MultipartFile file : fileList) {
            log.info("파일명: " + file.getOriginalFilename());
            log.info("용량: " + file.getSize());
            log.info("파일타입: " + file.getContentType());
            System.out.println("-----------------------------------------------");

            //세이브파일 객체 생성
            //생성자의 첫번째 파라미터로 저장경로를, 두번째 파라미터를 파일멸을 넣어주세요.
//            File saveFile = new File(uploadPath, file.getOriginalFilename());
//
//            try {
//                //서버에 파일을 저장하는 메서드
//                file.transferTo(saveFile);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            FileUtils.uploadFile(file, uploadPath);
        }
        return "";
    }

    //비동기로 전송된 파일 업로드 처리
    //@RestController가 아닌 @Controller에서 비동기를 처리하려면
    //@ResponseBody를 메서드에 붙여주세요
    @PostMapping("/ajaxUpload")
    @ResponseBody
    public ResponseEntity<String[]> ajaxUpload(List<MultipartFile> files){

        //업로드된 파일 수 만큼 배열의 길이를 설정
        int len = (files == null) ? 0: files.size();

        //업로드 완료된 파일명을 저장할 배열
        String[] fileNameList = new String[len];
        for (int i = 0; i < len; i++) {
            MultipartFile file = files.get(i);
            log.info(file.getOriginalFilename());
            log.info(file.getSize());
            System.out.println("--------------------------------------");

            fileNameList[i] = FileUtils.uploadFile(file, uploadPath);
        }
        return new ResponseEntity<>(fileNameList, HttpStatus.CREATED);
    }

    //파일 로딩 요청 처리
    //요청 URI: /loadFile?fileName=/upload/2021/04/22/dufidfuidf_cat_jpg
    @GetMapping("/loadFile")
    @ResponseBody
    public ResponseEntity<byte[]> loadFile(String fileName){

        log.info("request file name: " + fileName);
        log.info("request file full path: " + (uploadPath + fileName));

        //클라이언트가 요청한 파일명을 이용하여 파일의 풀 경로를 만들고
        //파일 객체 생성
        File file = new File(uploadPath + fileName);
        if(!file.exists()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //서버에 해당 파일이 저장되어 있었다면 InputStream객체를 통해 파일을 불러옴
        try {
            InputStream in = new FileInputStream(file);

            //클라이언트로 파일을 보내줄때는 응답 헤더에 파일의 컨텐츠 타입을 알려줘야 함
            String ext = FileUtils.getFileExtension(fileName);

            //응답헤더에 컨텐츠타입을 설정
            HttpHeaders headers = new HttpHeaders();
            switch (ext.toLowerCase()){
                case "jpg":
                    headers.setContentType(MediaType.IMAGE_JPEG);
                    break;
                case "gif":
                    headers.setContentType(MediaType.IMAGE_GIF);
                    break;
                case "png":
                    headers.setContentType(MediaType.IMAGE_PNG);
                    break;
            }
            return new ResponseEntity<>(IOUtils.toByteArray(in),headers,HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
