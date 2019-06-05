package idu.cs.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import idu.cs.domain.User;
import idu.cs.entity.UserEntity;
import idu.cs.exception.ResourceNotFoundException;
import idu.cs.repository.UserRepository;
import idu.cs.service.UserService;

@Controller //Spring Framework에게 이 클래스로부터 작성된 객체는 Controller역할을 함을 알려줌
//Spring이 이 클래스로부터 Bean객체를 생성해서 등록할 수 있음
public class UserController {
	//@Autowired UserRepository userRepo; // Dependency Injection
	@Autowired UserService userService;
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("test", "인덕 컴소");
		model.addAttribute("egy", "유응구");
		return "index";
	}
	@GetMapping("/user-login-form")
	public String getLoginForm(Model model) {
		return "login";
	}
	@PostMapping("/login")//쓰기
	public String loginUser(@Valid UserEntity user, HttpSession session) {
		System.out.println("login process : " + user.getUserId());
		User sessionUser = userService.getUserByUserId(user.getUserId());
				//userRepo.findByUserId(user.getUserId());
		if(sessionUser == null) {
			System.out.println("id error");
			return "redirect:/user-login-form";
		}
		if(!sessionUser.getUserPw().contentEquals(user.getUserPw())) {
			System.out.println("pw error");
			return "redirect:/user-login-form";
		}
		session.setAttribute("user", sessionUser);
		return "redirect:/";
	}
	@GetMapping("/logout")
	public String logoutUser(HttpSession session) {
		session.removeAttribute("user");//user만 날아감
		//session.invalidate();//세션에 있는 것들이 다 날라감
		return "redirect:/";
	}
	@GetMapping("/users")//읽기
	public String getAllUser(Model model, HttpSession session) {
		model.addAttribute("users", userService.getUsers());
		//model.addAttribute("users", userRepo.findAll());
		return "userlist";
	}
	@GetMapping("/user-register-form")
	public String getRegForm(Model model) {
		return "register";
	}
	@PostMapping("/users")//쓰기
	public String createUser(@Valid User user, Model model) {
		userService.saveUser(user);
		return "redirect:/users";//get방식으로 해당 url로 재지정
	}
	/*@GetMapping("/user-update-form")
	public String getUpdateForm( Model model) {
		return "update";
	}
	@PutMapping("/update-users/{id}")
	public String updateUsers(@PathVariable(value = "id") Long userId, @Valid UserEntity userDetails, Model model, HttpSession session) {
		UserEntity user = userRepo.findById(userId).get();
		UserEntity sessionUser = userRepo.findByUserId(user.getUserId());
		user.setUserPw(userDetails.getUserPw());
		user.setName(userDetails.getName());
		user.setCompany(userDetails.getCompany());
		userRepo.save(user);
		session.setAttribute("user", sessionUser);
		return "redirect:/";
	}
	@GetMapping("/users/{id}")
	public String getUserById(@PathVariable(value = "id") Long userId, Model model)
			throws ResourceNotFoundException {
		UserEntity user = userRepo.findById(userId).get();
		model.addAttribute("user",user);
		return "user";
		//return ResponseEntity.ok().body(user);
	}
	@GetMapping("/users/fn")
	public String getUserByName(@Param(value="name") String name, Model model)
			throws ResourceNotFoundException {
		List<UserEntity> users = userRepo.findByName(name);
		model.addAttribute("users",users);
		return "userlist";
		//return ResponseEntity.ok().body(user);
	}
	@PutMapping("/users/{id}")//@PatchMapping
	public String updateUser(@PathVariable(value = "id") Long userId, @Valid UserEntity userDetails, Model model) {
		UserEntity user = userRepo.findById(userId).get();//user는 DB로부터 읽어온 객체
		user.setName(userDetails.getName());//userDetails는 전송한 객체
		user.setCompany(userDetails.getCompany());
		userRepo.save(user);
		return "redirect:/users";
	}
	@DeleteMapping("/users/{id}")
	public String deleteUser(@PathVariable(value = "id") Long userId, Model model) {
		UserEntity user = userRepo.findById(userId).get();
		userRepo.delete(user);
		model.addAttribute("name", user.getName());
		//return "redirect:/users";
		return "user-deleted";
	}*/
}
