package com.paypal.bfs.test.employeeserv.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaveResponse {

    private boolean success;

    private String message;

    public SaveResponse(){

    }
    public SaveResponse( boolean success, String msg){
        this.message = msg;
        this.success = success;

    }
}
