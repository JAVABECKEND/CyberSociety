package com.example.cybersociety.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class ResponseUploadFile<T> {

    private int status;
    private T resultSuccess;
    private T resultError;
}
