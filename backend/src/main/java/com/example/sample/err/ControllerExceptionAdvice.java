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
     * RequestParam ?????? ????????? ???????????? ?????? ?????? ???????????? Exception
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    private Map<String, String> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        Map<String, String> rtnObj = new HashMap<>();

        rtnObj.put("error_message", String.format("?????? ???????????? (%s)??? ??????????????????", e.getParameterName()));

        return rtnObj;
    }

    /**
     * 400 BadRequest
     * RequestPart ?????? ????????? ???????????? ?????? ?????? ???????????? Exception
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestPartException.class)
    private Map<String, String> handleMissingServletRequestParameterException(MissingServletRequestPartException e) {
        Map<String, String> rtnObj = new HashMap<>();

        rtnObj.put("error_message", String.format("Multipart (%s)??? ??????????????????", e.getRequestPartName()));

        return rtnObj;
    }

    /**
     * 400 BadRequest
     * RequestPart ?????? Path Variable ??? ???????????? ?????? ?????? ???????????? Exception
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingPathVariableException.class)
    private Map<String, String> handleMissingPathVariableException(MissingPathVariableException e) {
        Map<String, String> rtnObj = new HashMap<>();

        rtnObj.put("error_message", String.format("Path (%s)??? ??????????????????", e.getVariableName()));

        return rtnObj;
    }

    /**
     * 400 BadRequest
     * ????????? ????????? ????????? ?????? ???????????? Exception
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TypeMismatchException.class)
    private Map<String, String> handleTypeMismatchException(TypeMismatchException e) {
        Map<String, String> rtnObj = new HashMap<>();

        rtnObj.put("error_message", String.format("????????? ????????? ?????????????????????. (%s)", e.getValue()));

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

        rtnObj.put("error_message", "????????? ???????????????.");

        return rtnObj;
    }

    /**
     * 405 Method Not Allowed
     * ???????????? ?????? HTTP method ?????? ??? ?????? ???????????? Exception
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
     * ???????????? ?????? ????????? ????????? ?????? ???????????? Exception
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeException.class)
    private Map<String, String> handleHttpMediaTypeException(HttpMediaTypeException e) {
        Map<String, String> rtnObj = new HashMap<>();

        rtnObj.put("error_message", "Unsupported Media Type");

        return rtnObj;
    }

    /**
     * ?????? ????????? ????????? ????????? ?????? ?????? ???????????? Exception
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    private Map<String, String> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        Map<String, String> rtnObj = new HashMap<>();

        rtnObj.put("error_message", "????????? ????????? ?????? ????????? ??????????????????.");

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

        rtnObj.put("error_message", "???????????? ????????? ?????????????????????. ?????? ??? ?????? ??????????????????! 502 Bad GateWay");

        return rtnObj;
    }
}
