package com.skrookies.dahaezlge.controller.qna;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class FileUploadExceptionAdvice {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handelMaxSizeException(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "파일 크기가 20MB를 초과할 수 없습니다.");
        return "redirect:/qnaWrite";
    }
}
