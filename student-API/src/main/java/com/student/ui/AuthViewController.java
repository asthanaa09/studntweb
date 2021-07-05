package com.student.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.student.models.User;
import com.student.utils.Utils;

@Controller
public class AuthViewController {
    
    @GetMapping("/login")
    public String login() {
	User user = Utils.getLoggedInUser();
	
	if(user != null)
	    return "redirect:/dashboard/project";
	
	return "login";
    }
}
