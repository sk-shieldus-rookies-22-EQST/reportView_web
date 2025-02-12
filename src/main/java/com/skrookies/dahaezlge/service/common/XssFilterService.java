package com.skrookies.dahaezlge.service.common;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class XssFilterService {
    // 필터링할 단어 목록
    private static final String[] REPLACE_TOKENS = {
            "input", "context", "iframe", "textarea", "onweb", "alert", "onkeyup",
            "onerror", "title", "xml", "body", "svg", "lowsrc", "onbeforeuload",
            "dynsrc", "url", "marquee", "cookie", "document", "msgbox", "vbscript", "refresh",
            "escape", "string", "expression", "eval", "void", "bind", "create", "confirm",
            "prompt", "location", "fromCharCode", "append", "onbeforeprint", "ondragleave",
            "onmouseenter", "onbeforeeditfocus", "ondatasetchanged", "onbeforepaste",
            "ondragover", "onmouseleave", "onbeforepaste", "ondatasetcomplete", "onbeforeprint",
            "onbeforeunload", "ondragstart", "onmousemove", "onstart", "ondbclick", "embed",
            "onbeforeupdate", "ondrop", "onmouseout", "onstop", "ondblclick", "script",
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
            "onselectstart", "onselectionchange", "onbeforedeactivate", "ondatasetchaged",
            "onresizeend", "onURLFlip", "seekSegmentTime", "onTimeError", "onTrackChange"
    };

    // 필터링할 특수문자 목록
    private static final String[][] SPECIAL_CHARACTERS = {
            {"<", "&lt;"}, {">", "&gt;"},
            {"&lt;", " "}, {"&gt;", " "}
    };

    /** XSS 필터링 */
    public String filter(String input) {
        if (input == null) return null;
        String output = input;

        // 1. 특수문자 변환
        for (String[] special : SPECIAL_CHARACTERS) {
            output = output.replace(special[0], special[1]);
        }

        // 2. 지정된 토큰 치환 (대소문자 무시)
        for (String token : REPLACE_TOKENS) {
            output = output.replaceAll("(?i)" + token, " ");
        }

        return output;
    }

    /** xss 허용 (게시판 내용, 댓글) */
    public String filter1(String input) {
        if (input == null) return null;
        String output = input;

        // `< > ' " ( ) ;`를 제외한 특수문자 필터링
        String[][] limitedSpecialCharacters = {
                {"%3C", "<"}, {"%3E", ">"}
        };
        for (String[] special : limitedSpecialCharacters) {
            output = output.replace(special[0], special[1]);
        }

        // 지정된 토큰 치환 (대소문자 무시)
        List<String> modifiedTokens = new ArrayList<>(Arrays.asList(REPLACE_TOKENS));
//        modifiedTokens.remove("onerror");
//        modifiedTokens.remove("alert");

//        modifiedTokens.remove("alert");

        // onerror, r= 을 공백으로 치환 (정규표현식 사용, 대소문자 무시)
//        output = output.replaceAll("(?i)onerror", "");
//        output = output.replaceAll("(?i)r=", "");



        for (String token : modifiedTokens) {
            output = output.replaceAll("(?i)" + token, "");
        }

        return output;
    }

    /** xss 필터링 (sql 공격 허용) */
    public String filter2(String input) {
        if (input == null) return null;
        String output = input;

        // `< > ' " ( ) ;`를 제외한 특수문자 필터링
        String[][] limitedSpecialCharacters = {
                {"<", "&lt;"}, {">", "&gt;"},
                {"&lt;", " "}, {"&gt;", " "}
        };
        for (String[] special : limitedSpecialCharacters) {
            output = output.replace(special[0], special[1]);
        }

        // 지정된 토큰 치환 (대소문자 무시)
        for (String token : REPLACE_TOKENS) {
            output = output.replaceAll("(?i)" + token, " ");
        }

        return output;
    }

    
}
