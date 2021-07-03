package com.student.utils;

import java.util.Calendar;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.student.models.User;
import com.student.security.UserPrincipal;

public class Utils {
	
	public static Date now() {
		return new Date();
	}
	
	public static Date addMiliSec(Date time, int miliSec) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.setTimeInMillis(System.currentTimeMillis() + miliSec);
		return cal.getTime();
	}
	
	public static User getLoggedInUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = ((UserPrincipal) auth.getPrincipal()).getUser();
		return user;
	}
	
	public static String getTimestamp() {
		Date now = now();
		return Long.toHexString(now.getTime());
	}
	
	public static byte[] decodeBase64(String base64data) {
		// Decoding Base64 data
		String base64Image = base64data.split(",")[1];
		byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
		return imageBytes;
	}
	
	public static String getExtension(String base64) {
		String extension = null;
		String[] strings = base64.split(",");
		switch (strings[0]) {
		case "data:image/jpeg;base64":
			extension = "jpeg";
            break;
        case "data:image/png;base64":
            extension = "png";
            break;
        default://should write cases for more images types
            extension = "jpg";
            break;
		}
		
		return extension;
	}
}
