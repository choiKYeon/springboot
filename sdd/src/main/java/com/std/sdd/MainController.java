package com.std.sdd;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @GetMapping("/main")
    @ResponseBody
    public String main(){
        return "안녕하세요";
    }
    @GetMapping("/")
    public String root(){
        return "redirect:/question/list";
    }
}
