package com.memo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component // 일반적은 spring bean
public class FileManagerService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	// 파일 업로드 후 저장
	
	
	// 상수값으로 만듬, 상수라서 대문자로 함
	// 실제 업로드가 된 이미지가 저장될 경로(서버)
	public static final String FILE_UPLOAD_PATH = "D:\\sys\\6_spring_project\\memo\\workspace\\images/";
	
	// BO가 요청하는 파일을 업로드 하고 웹주로 리턴
	// input: MultipartFile(이미지파일), loginId
	// output: web image path(String)
	public String saveFile(String loginId, MultipartFile file) {
		
		//파일 디렉토리 예) 로그인아이디 + "_" + 현재시간/sun.png
		String directoryName = loginId + "_" + System.currentTimeMillis() + "/";
		
		//D:\\sys\\6_spring_project\\memo\\workspace\\images/로그인아이디 + "_" + 현재시간
		String filePath = FILE_UPLOAD_PATH + directoryName; 
		
		// 폴더생성
		File directory = new File(filePath);
		
		if (directory.mkdir() == false) {
			//실패시 이미지 경로 null로 리턴
			return null;
		}
		
		//파일 업로드 : byte단위 업로드
		try {
			byte[] bytes = file.getBytes();
			//★한글이름 이미지는 올릴수 없으므로 나중에 영문자로 바꿔서 올리기
			//getOriginalFilename 사용자가 올린파일명 그대로 가져옴
			Path path = Paths.get(filePath + file.getOriginalFilename()); //디렉토리경로+사용자가 올린 파일명
			Files.write(path, bytes); //파일업로드
		} catch (IOException e) {
			e.printStackTrace();
			return null;// 이미지 업로드 실패시 null리턴
		}
		
		// 파일업로드가 성공했으면 웹이미지 URL PATH를 리턴한다
		//http://localhost:88\images\shing27_1689839335005\capybara-1732020_960_720.jpg
		// 도메인 빼고 \images\shing27_1689839335005\capybara-1732020_960_720.jpg 리턴함		
		
		return "\\images\\" + directoryName + file.getOriginalFilename();
	}
	
	//file 삭제 메소드
	//input : image path
	//ouput : void
	public void deleteFile(String imagePath) {
		//'\images\shing27_1689925074389/testsetset.jpg'
		// D:\sys\6_spring_project\memo\workspace\images
		// 이미지 삭제 후 폴더도 삭제
		// FILE_UPLOAD_PATH = "D:\\sys\\6_spring_project\\memo\\workspace\\images/";
		// 주소에 겹치는 /image/ 를 제거한다
		Path path = Paths.get(FILE_UPLOAD_PATH + imagePath.replace("/image/",""));
		if (Files.exists(path)) {
			// 이미지 존재하면 이미지 삭제
			try {
				Files.delete(path);
			} catch (IOException e) {
				// e.printStackTrace();//에러를 로그로 찍는다
				logger.info("[FileManagerService 이미지 삭제 실패] imagePath : {}" , path);
			}
			// 이미지 삭제후 디렉토리도 삭제
			path = path.getParent();//파일의 바로위 폴더
			if (Files.exists(path)) {
				try {
					Files.delete(path);
				} catch (IOException e) {
					// e.printStackTrace();//에러를 로그로 찍는다
					logger.info("[FileManagerService 이미지 폴더 삭제 실패] imagePath : {}" , path);
				}
			}
		}
	}

}
