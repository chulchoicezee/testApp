package com.example.chulchoice.testapp.http;

/**
 * Created by chulchoice on 2016-05-05.
 */
public class HttpUtilConst {

    public enum ConnType{
        GET("GET"),
        POST("POST"),
        PUT("PUT"),
        DELETE("DELETE");

        private String name;
        ConnType(String name){
            this.name = name;
        }
        String getName(){
            return this.name;
        }
    }
    public enum ResponseType{
        WBXML("dd"),
        XML("d"),
        JSON("application/json");

        private String name;
        ResponseType(String name){
            this.name = name;
        }
        String getName(){
            return this.name;
        }
    }
}
