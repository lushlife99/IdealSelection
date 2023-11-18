//package com.example.idealselect.controller;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.dao.*;
//import org.springframework.jdbc.BadSqlGrammarException;
//import org.springframework.jdbc.InvalidResultSetAccessException;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.Map;
//
//@Slf4j
//@Controller
//@ControllerAdvice
//public class ErrorController {
//
//    @ExceptionHandler(DataAccessException.class)
//    public @ResponseBody Map keyError(DataAccessException ex, HttpServletRequest request, HttpServletResponse response)
//            throws Exception {
//
//        SQLException se = (SQLException) ex.getRootCause();
//        log.debug("****** DataAccessException : {} // {}", se.getErrorCode(), se.getMessage());
//
//        String errCode = "";
//        String errMsg = "";
//
//        if (ex instanceof BadSqlGrammarException) {
//
//            se = ((BadSqlGrammarException) ex).getSQLException();
//
//            log.debug("**BadSqlGrammarException {} ", se.getErrorCode());
//
//        } else if (ex instanceof InvalidResultSetAccessException) {
//
//            se = ((InvalidResultSetAccessException) ex).getSQLException();
//
//            log.debug("**InvalidResultSetAccessException {} ", se.getErrorCode());
//
//        } else if (ex instanceof DuplicateKeyException) {
//
//            log.debug("**DuplicateKeyException {} ", ex.getMessage());
//
//        } else if (ex instanceof DataIntegrityViolationException) {
//            log.debug("**DataIntegrityViolationException {} ", ex.getMessage());
//            errCode = "1";
//            errMsg = "데이터 중복오류";
//        } else if (ex instanceof DataAccessResourceFailureException) {
//            log.debug("**DataAccessResourceFailureException {} ", ex.getMessage());
//            errCode = "1";
//            errMsg = "데이터베이스 연결오류";
//        } else if (ex instanceof CannotAcquireLockException) {
//
//            log.debug("**CannotAcquireLockException {} ", ex.getMessage());
//
//        } else if (ex instanceof DeadlockLoserDataAccessException) {
//            log.debug("**DeadlockLoserDataAccessException {} ", ex.getMessage());
//            errCode = "1";
//            errMsg = "교착 상태로 인한 현재 작업 실패";
//        } else if (ex instanceof CannotSerializeTransactionException) {
//
//            log.debug("**CannotSerializeTransactionException {} ", ex.getMessage());
//
//        } else {
//            errMsg = ex.getMessage();
//            log.error("[DataAccessException] getMessage {}", ex.getMessage());
//        }
//
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("errCode", errCode);
//        map.put("errMsg", errMsg);
//
//        return map;
//    }
//
////    @ExceptionHandler(Exception.class)
////    public @ResponseBody void keyError(Exception ex, HttpServletRequest request, HttpServletResponse response)
////            throws Exception {
////        log.error("[Exception] {}", ex.getMessage());
////        response.sendError(500);
////    }
//}