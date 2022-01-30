package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

//template을 통해 viewResolver에 던져줌
@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data", "hello!!");
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value = "name") String name, Model model){
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody // http에서 body part를 직접 넣어주겠다.
    public String helloString(@RequestParam("name") String name, Model model){
        return "hello "+ name; //string converter
    }

    //Data를 가져와! 주로 이럴때 api방식을 씀
    @GetMapping("hello-api")
    @ResponseBody //json으로 반환하는게 default
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello(); // json converter
        hello.setName(name);
        return hello;
     }

    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
