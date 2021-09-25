package com.yuxy.editorbiz.editor.dto;

import lombok.Data;

@Data
public class WebContentsDto {
    private String html;
    private String css;
    private String js;
    private String[] cssRefs;
    private String[] jsRefs;
}
