package com.bookshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseObject {
    private int code;
    private String description;
    private JSONObject result;

    @Override
    public String toString() {
        return "{" +
                "'code'=" + code +
                ", 'description'=" + description  +
                ", 'result'=" + result +
                '}';
    }
}
