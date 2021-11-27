package com.example.demo.src.test;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

	private final TestDAO testDAO;

	public TestController(TestDAO testDAO) {
		this.testDAO = testDAO;
	}

	/**
     * 로그 테스트 API
     * [GET] /test/log
     * @return String
     */
    @ResponseBody
    @GetMapping("/log")
    public String getAll() {
        System.out.println("테스트");
//        trace, debug 레벨은 Console X, 파일 로깅 X
//        logger.trace("TRACE Level 테스트");
//        logger.debug("DEBUG Level 테스트");

//        info 레벨은 Console 로깅 O, 파일 로깅 X
        log.info("INFO Level 테스트");
//        warn 레벨은 Console 로깅 O, 파일 로깅 O
        log.warn("Warn Level 테스트");
//        error 레벨은 Console 로깅 O, 파일 로깅 O (app.log 뿐만 아니라 error.log 에도 로깅 됨)
//        app.log 와 error.log 는 날짜가 바뀌면 자동으로 *.gz 으로 압축 백업됨
        log.error("ERROR Level 테스트");

        return "Success Test";
    }

	@ResponseBody
	@GetMapping("/test")
	public String test() throws Exception {
    	try {
			return testDAO.getUsers();
		} catch(Exception exception) {
    		exception.printStackTrace();
			return exception.getMessage();
		}
	}


}
