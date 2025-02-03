package com.skrookies.dahaezlge.service.common;

import org.springframework.stereotype.Service;

@Service
public class XssFilterService {
    // 필터링 대상 문자열 목록 (대소문자 구분 없이 치환)
    private static final String[] REPLACE_TOKENS = {
            "title", "xml", "body", "svg", "lowsrc", "dynsrc", "url", "marquee",
            "cookie", "document", "msgbox", "vbscript", "refresh", "escape", "string",
            "expression", "eval", "void", "bind", "create", "confirm", "prompt", "location",
            "fromCharCode", "append", "onbeforeprint", "ondragleave", "onmouseenter",
            "onbeforeeditfocus", "ondatasetchanged", "onbeforepaste", "onbeforeuload",
            "ondragover", "onmouseleave", "onbeforepaste", "ondatasetcomplete", "onbeforeprint",
            "onbeforeunload", "ondragstart", "onmousemove", "onstart", "ondbclick", "embed",
            "onbeforeupdate", "ondrop", "onmouseout", "onstop", "ondblclick", "onerror",
            "onblur", "onmouseover", "onsubmit", "ondeactivate", "onabort", "onbounce",
            "onerrorupdate", "onmouseup", "onundo", "ondrag", "onactivate", "oncellchange",
            "onfilterchage", "onmousewheel", "onunload", "ondragend", "onactive", "onchange",
            "onfilterchange", "onmove", "onlayoutcomplete", "onresizestart", "onafteripudate",
            "onclick", "onfinish", "onmoveend", "onload", "onrowenter", "onafterprint",
            "oncontextmenu", "onfocus", "onmovestart", "onlosecapture", "onrowexit",
            "onafterupdate", "oncontrolselect", "onfocusin", "onpaste", "onmouse", "onrowsdelete",
            "onbefore", "oncontrolselected", "onfocusout", "onpropertychange", "onmousedown",
            "onrowsinserted", "onbeforeactivate", "oncopy", "onhelp", "onreadystatechange",
            "onmouseend", "onscroll", "onbeforecopy", "oncut", "onkeydown", "onreset",
            "ondragenter", "onselect", "onbeforecut", "ondataavailable", "onkeypress", "onresize",
            "onselectstart", "onselectionchange", "onbeforedeactivate", "ondatasetchaged", "onkeyup",
            "onresizeend", "onURLFlip", "seekSegmentTime", "onTimeError", "onTrackChange"
    };

    /**
     * 입력 문자열에 대해 XSS 필터링을 수행합니다.
     * - <script>, <s, script, </ 를 모두 공백으로 치환
     * - 그 외 지정된 문자열은 모두 token + "-1"로 치환하여 우회 공격을 방지
     *
     * @param input 원본 문자열
     * @return 필터링된 문자열
     */
    public String filter(String input) {
        if (input == null) {
            return null;
        }
        String output = input;

        // 1. <script>, <s, script, </ 를 공백으로 치환 (정규표현식 사용, 대소문자 무시)
        //output = output.replaceAll("(?i)<script>", "");
        //output = output.replaceAll("(?i)<s", "");
        output = output.replaceAll("(?i)script", "");
        //output = output.replaceAll("(?i)</", "");

        // 2. 지정된 토큰들을 token + "-1"로 치환 (대소문자 구분 없이)
        for (String token : REPLACE_TOKENS) {
            // (?i) 옵션을 사용하여 대소문자 무시
            // 단순 치환: 토큰이 나타나는 모든 곳을 token + "-1"로 대체합니다.
            output = output.replaceAll("(?i)" + token, token + "-1");
        }

        return output;
    }
}
