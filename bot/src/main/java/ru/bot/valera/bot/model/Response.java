package ru.bot.valera.bot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {

    boolean ok;
    String description;
    @JsonProperty("error_code" )
    int errorCode;
}
