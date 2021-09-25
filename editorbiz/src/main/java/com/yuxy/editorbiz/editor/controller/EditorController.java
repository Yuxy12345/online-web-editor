package com.yuxy.editorbiz.editor.controller;

import com.yuxy.editorbiz.editor.dto.WebContentsDto;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@RequestMapping("editor")
@RestController
public class EditorController {

    @PostMapping(value = "create", produces = "application/json")
    public String create(@RequestBody WebContentsDto dto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        StringBuilder sb = new StringBuilder("<html><head><title>test</title>");
        sb.append("<style>").append(dto.getCss()).append("</style>");
        if (dto.getCssRefs() != null) {
            for (String cssRef : dto.getCssRefs()) {
                cssRef = cssRef.replaceAll("\"", "%22");
                sb.append("<link rel=\"stylesheet\" href=\"").append(cssRef).append("\">");
            }
        }
        sb.append("</head><body>").append(dto.getHtml());
        if (dto.getJsRefs() != null) {
            for (String jsRef : dto.getJsRefs()) {
                jsRef = jsRef.replaceAll("\"", "%22");
                sb.append("<script type=\"text/javascript\" src=\"").append(jsRef).append("\"></script>");
            }
        }

        sb.append("<script>").append(dto.getJs()).append("</script>");
        sb.append("</body>");
        String id = UUID.randomUUID().toString();
        session.setAttribute(id, sb.toString());
        return "\""+ id + "\"";
    }

    @GetMapping("show")
    public String show(String id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String content = (String) session.getAttribute(id);
        session.removeAttribute(id);
        return content;
    }
}
