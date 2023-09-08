package com.example.d3.exercise.config.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author wangchao
 * @Description:
 * @date 2023/9/7 15:29
 * @vVersion 1.0
 */
@Data
@NoArgsConstructor
@ToString
public class User {
    private Long id;
    private String name;
    private Integer age;
    private Integer version; // 版本号

    public User(Long id, String name, Integer age) {
        this.id=id;
        this.name = name;
        this.age = age;
    }
}
