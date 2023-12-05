package com.work.easystep2.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class StringValidator {

	private String acctRegex = "^(?=.*\\d)(?=.*[A-Za-z])[A-Za-z\\d]{6,15}$";
	private String nameRegex = "^\\S+$";
	private String telRegex = "^(\\d{2,3}-?|\\(\\d{2,3}\\))\\d{3,4}-?\\d{4}$";
	private String mobileRegex = "^0[9]\\d{8}$";
	private String emailRegex = "^([a-zA-Z0-9_\\.\\-\\+])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
	private Pattern acctPattern = Pattern.compile(acctRegex);
	private Pattern pwdPattern = Pattern.compile(acctRegex);
	private Pattern namePattern = Pattern.compile(nameRegex);
	private Pattern sexPattern = Pattern.compile(nameRegex);
	private Pattern telPattern = Pattern.compile(telRegex);
	private Pattern mobilePattern = Pattern.compile(mobileRegex);
	private Pattern emailPattern = Pattern.compile(emailRegex);
	
	 public boolean acctIsValid(String acct) {
	        Matcher matcher = acctPattern.matcher(acct);
	        return matcher.matches();
	 }
	  
	 public boolean pwdIsValid(String pwd) {
		 Matcher matcher = pwdPattern.matcher(pwd);
		 return matcher.matches();
	 }
	 
	 public boolean sexIsValid(String sexabbr) {
		 Matcher matcher = sexPattern.matcher(sexabbr);
		 return matcher.matches();
	 }
	 
	 public boolean nameIsValid(String name) {
		 Matcher matcher = namePattern.matcher(name);
		 return matcher.matches();
	 }
	 
	 public boolean telIsValid(String tel) {
		 Matcher matcher = telPattern.matcher(tel);
		 return matcher.matches();
	 }
	 
	 public boolean mobileIsValid(String mobile) {
		 Matcher matcher = mobilePattern.matcher(mobile);
		 return matcher.matches();
	 }
	 public boolean emailIsValid(String email) {
		 Matcher matcher = emailPattern.matcher(email);
		 return matcher.matches();
	 }

	public String getNameRegex() {
		return nameRegex;
	}

	public void setNameRegex(String nameRegex) {
		this.nameRegex = nameRegex;
	}

}
