package com.myhotel.paymentms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private String email;
    private String message;
    private Map<String, Object> data = new HashMap<>();

    public void addData(String key, Object value) {
        this.data.put(key, value);
    }

}
