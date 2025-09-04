package com.akash.moviebooking.api.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FieldErrorStructure<T> {
    private int statusCode;
    @JsonProperty(namespace = "error_message")
    private String errorMessage;
    T data;
}
/*JsonProperty comes from Jackson library, which is used in Spring Boot to convert Java objects to JSON (and JSON to Java).


This will make the JSON output look like this:
{
  "error_message": "User not found"
}


Without @JsonProperty, the JSON would use the Java field name:
{
  "errorMessage": "User not found"
}
* */