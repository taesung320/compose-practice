package com.example.sample.err;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.joining;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    /**
     * 400 BadRequest
     * Spring Validation(@RequestBody)
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private Map<String, String> handleMethodValidException(MethodArgumentNotValidException  e) {
        Map<String, String> rtnObj = new HashMap<>();

        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(joining("\n"));
        
        rtnObj.put("error_message", errorMessage);

        return rtnObj;
    }

    /**
     * 400 BadRequest
     * Spring Validation(@ModelAttribute)
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    private Map<String, String> handleBadRequest(BindException e) {
        Map<String, String> rtnObj = new HashMap<>();

        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(joining("\n"));

        rtnObj.put("error_message", errorMessage);

        return rtnObj;
    }

    /**
     * 400 BadRequest
     * RequestParam 필수 필드가 입력되지 않은 경우 발생하는 Exception
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    private Map<String, String> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        Map<String, String> rtnObj = new HashMap<>();

        rtnObj.put("error_message", String.format("필수 파라미터 (%s)를 입력해주세요", e.getParameterName()));

        return rtnObj;
    }

    /**
     * 400 BadRequest
     * RequestPart 필수 필드가 입력되지 않은 경우 발생하는 Exception
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestPartException.class)
    private Map<String, String> handleMissingServletRequestParameterException(MissingServletRequestPartException e) {
        Map<String, String> rtnObj = new HashMap<>();

        rtnObj.put("error_message", String.format("Multipart (%s)를 입력해주세요", e.getRequestPartName()));

        return rtnObj;
    }

    /**
     * 400 BadRequest
     * RequestPart 필수 Path Variable 가 입력되지 않은 경우 발생하는 Exception
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingPathVariableException.class)
    private Map<String, String> handleMissingPathVariableException(MissingPathVariableException e) {
        Map<String, String> rtnObj = new HashMap<>();

        rtnObj.put("error_message", String.format("Path (%s)를 입력해주세요", e.getVariableName()));

        return rtnObj;
    }

    /**
     * 400 BadRequest
     * 잘못된 타입이 입력된 경우 발생하는 Exception
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TypeMismatchException.class)
    private Map<String, String> handleTypeMismatchException(TypeMismatchException e) {
        Map<String, String> rtnObj = new HashMap<>();

        rtnObj.put("error_message", String.format("잘못된 타입이 입력되었습니다. (%s)", e.getValue()));

        return rtnObj;
    }

    /**
     * 400 BadRequest
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            InvalidFormatException.class,
            ServletRequestBindingException.class
    })
    private Map<String, String> handleInvalidFormatException(Exception e) {
        Map<String, String> rtnObj = new HashMap<>();

        rtnObj.put("error_message", "잘못된 요청입니다.");

        return rtnObj;
    }

    /**
     * 405 Method Not Allowed
     * 지원하지 않은 HTTP method 호출 할 경우 발생하는 Exception
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    private Map<String, String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        Map<String, String> rtnObj = new HashMap<>();

        rtnObj.put("error_message", "Not Allowed Method");

        return rtnObj;
    }

    /**
     * 406 Not Acceptable
     */
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    private Map<String, String> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException e) {
        Map<String, String> rtnObj = new HashMap<>();

        rtnObj.put("error_message", "Not Acceptable");

        return rtnObj;
    }

    /**
     * 415 UnSupported Media Type
     * 지원하지 않는 미디어 타입인 경우 발생하는 Exception
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeException.class)
    private Map<String, String> handleHttpMediaTypeException(HttpMediaTypeException e) {
        Map<String, String> rtnObj = new HashMap<>();

        rtnObj.put("error_message", "Unsupported Media Type");

        return rtnObj;
    }

    /**
     * 최대 허용한 이미지 크기를 넘은 경우 발생하는 Exception
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    private Map<String, String> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        Map<String, String> rtnObj = new HashMap<>();

        rtnObj.put("error_message", "업로드 가능한 파일 크기를 초과했습니다.");

        return rtnObj;
    }

    @ExceptionHandler(DataAccessException.class)
    protected Map<String, String> handleDataAccessException(DataAccessException exception) {
        Map<String, String> rtnObj = new HashMap<>();

        rtnObj.put("error_message1", exception.getMessage());

        return rtnObj;
    }

    @ExceptionHandler(SQLException.class)
    protected Map<String, String> handleSQLException(SQLException exception) {
        Map<String, String> rtnObj = new HashMap<>();

        rtnObj.put("error_message2", exception.getMessage());

        return rtnObj;
    }

    @ExceptionHandler(Exception.class)
    protected Map<String, String> handleException(Exception exception) {
        System.out.println("unhandled exception");
        exception.printStackTrace();
        
        Map<String, String> rtnObj = new HashMap<>();

        rtnObj.put("error_message", "일시적인 에러가 발생하였습니다. 잠시 후 다시 시도해주세요! 502 Bad GateWay");

        return rtnObj;
    }
}
