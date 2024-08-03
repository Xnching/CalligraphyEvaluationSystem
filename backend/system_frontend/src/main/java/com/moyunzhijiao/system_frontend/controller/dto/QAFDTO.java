package com.moyunzhijiao.system_frontend.controller.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "1为常见问题，2为教师自己的建议与反馈,type为字符串")
public class QAFDTO {
    private String type;
    @Schema(description = "内容字符串，常见问题则为问题和答案，建议与反馈则为，反馈内容以及回复内容")
    private List<Item> list;

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Item> getList() {
        return list;
    }

    public void setList(List<Item> list) {
        this.list = list;
    }

    public static class Item {
        private String title;
        private String content;

        // Getters and Setters
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}

