package com.bookshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONArray;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseList {
    private int code;
    private String description;
    private JSONArray result;
}
