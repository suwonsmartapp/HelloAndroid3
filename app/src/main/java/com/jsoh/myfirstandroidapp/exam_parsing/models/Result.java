package com.jsoh.myfirstandroidapp.exam_parsing.models;

/**
 * Created by junsuk on 16. 4. 27..
 */
public class Result {
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Result{");
        sb.append("result='").append(result).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
