package com.student.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    
    @GetMapping("/project")
    public String dashboard() {
	System.out.println("Hitting");
	return "project";
    }
}
