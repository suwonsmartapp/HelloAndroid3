
package com.jsoh.myfirstandroidapp.exam_listview;

/**
 * 모델 클래스
 */
public class MyItem {
    private int imageRes;
    private String title;
    private String description;

    public MyItem(int imageRes, String title, String description) {
        this.description = description;
        this.imageRes = imageRes;
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
