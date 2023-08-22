package com.example.d3.exercise.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lfy
 * @Description
 * @create 2023-04-24 18:52
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEntity implements Serializable {

    private String username;
    private String passwd;
    private Map<String,String> remarkMap=new HashMap<>();
}
