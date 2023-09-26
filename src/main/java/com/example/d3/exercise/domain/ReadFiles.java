package com.example.d3.exercise.domain;

import java.io.File;

/**
 * @author wangchao
 * @Description:
 * @date 2023/9/22 8:41
 * @vVersion 1.0
 */
public class ReadFiles {
    public static void main(String[] args) {
        String fileName="D:\\vscode_workspace\\front-code\\cms";
        File file=new File(fileName);
        for(File f:file.listFiles()){
            if(f.isDirectory())System.out.println(f.getName());
        }
    }

}
