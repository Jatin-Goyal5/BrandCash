package com.demo.brandbacks.auth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.brandbacks.dto.BrandRequestDto;
import com.demo.brandbacks.dto.GenericResponseBuilder;
import com.demo.brandbacks.dto.JwtResponse;
import com.demo.brandbacks.dto.LoginRequestDto;
import com.demo.brandbacks.dto.VerifyOtpDto;
import com.demo.brandbacks.repository.UserRepository;
import com.demo.brandbacks.security.JwtUtil;
import com.demo.brandbacks.security.UserPrincipal;
import com.demo.brandbacks.service.CustomDetailService;
import com.demo.brandbacks.service.OtplessService;
import com.demo.brandbacks.service.UserPrincipalService;
import com.demo.brandbacks.service.UserService;
import com.demo.brandbacks.util.MessageUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	UserRepository userRepository;
	
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomDetailService userDetailsService;
    
    @Autowired
    OtplessService otplessService;
    
    @Autowired
    UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
	@Autowired
	UserPrincipalService userPrincipalService;

//    @PostMapping("/login")
//    public ResponseEntity<GenericResponseBuilder> login(@RequestBody LoginRequestDto authRequest, HttpServletResponse response ) throws Exception {
//        try {
//        	
//            authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
//            );
//        } catch (Exception e) {
//        	log.info("Exception occured while logging {}", e);
//            return new ResponseEntity<>(GenericResponseBuilder.builder().message("Invalid Username").build(), HttpStatus.OK);
//        }
//
//        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
//        String jwt = jwtUtil.generateToken(userDetails.getUsername());
//        
//        return new ResponseEntity<>(GenericResponseBuilder.builder().message(MessageUtil.getMessage("app.auth.success.auth")).data(jwt).build(), HttpStatus.OK);
//    }
    
    @PostMapping("/otp/send")
    public ResponseEntity<?> sendOtp(@RequestBody String phoneNumber) {
        // Call Otpless or other service to send OTP
    	try {
        	otplessService.sendOtp(phoneNumber);
        	return new ResponseEntity<>(GenericResponseBuilder.builder().message(MessageUtil.getMessage("OTP sent successfully")).build(), HttpStatus.OK);
    	}catch(Exception exp) {
    		return new ResponseEntity<>(GenericResponseBuilder.builder().message(MessageUtil.getMessage(exp.getMessage())).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    
    }

    @PostMapping("/otp/verify")
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpDto verifyOtpDto, HttpServletResponse response) {
        // Verify OTP with the service if user doesn't exist create new user 
    	String otp = verifyOtpDto.getOtp();
	    String phoneNumber = verifyOtpDto.getPhoneNumber();
    	    
        boolean isVerified = otplessService.verifyOtp(phoneNumber, otp);
        if (isVerified) {
        	userService.saveCustomer(phoneNumber);
            final String jwt = jwtUtil.generateToken(phoneNumber);
            Cookie cookie = createCookie(jwt);
            response.addCookie(cookie);
            return new ResponseEntity<>(GenericResponseBuilder.builder().message(MessageUtil.getMessage("app.auth.success.auth")).data(jwt).build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(GenericResponseBuilder.builder().message("Invalid OTP").build(), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
    }
	
	private Cookie createCookie(String jwt) {
		// TODO Auto-generated method stub
		Cookie cookie = new Cookie("token", jwt);
		cookie.setSecure(false);
		cookie.setPath("/");
		cookie.setHttpOnly(false);
		cookie.setMaxAge(-1);
		return cookie;
	}

//	@GetMapping("/new")
//	public ResponseEntity<?> createuser(){
//		User user = new User();
//		user.setUsername("new_user");
//		 user.setPassword(passwordEncoder.encode("set_password"));
//		 
//		 Set<String> set = new HashSet<>();
//		 set.add("BRAND");
//         user.setRoles(set);
//
//		userRepository.save(user);
//		return null;
//	}
	
	@RequestMapping(method= RequestMethod.POST, value ="/brand/register")
	public ResponseEntity<?> registerUser(@RequestBody BrandRequestDto brandRequestDto){
		try {
			try {
				userService.validateBrandRegistration(brandRequestDto);
			}catch(Exception exp) {
				return new ResponseEntity<>(GenericResponseBuilder.builder().message(exp.getMessage()).data(brandRequestDto).build(), HttpStatus.NOT_ACCEPTABLE);

			}
			userService.registerBrand(brandRequestDto);
			return new ResponseEntity<>(GenericResponseBuilder.builder().message("Brand boarded successfully!!").data(brandRequestDto).build(), HttpStatus.OK);
		}catch(Exception exp) {
			return new ResponseEntity<>(GenericResponseBuilder.builder().message(exp.getMessage()).data(brandRequestDto).build(), HttpStatus.EXPECTATION_FAILED);

		}
	}
	
	@PostMapping("/brand/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {
	    try {
	        authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
	        );

	        String jwt = jwtUtil.generateToken(loginRequest.getEmail());
			UserPrincipal userDetails =(UserPrincipal) userPrincipalService.fetchUserPrincipal(jwt);

	        JwtResponse jwtResponse = new JwtResponse();
	        jwtResponse.setAccessToken(jwt);
	        jwtResponse.setEmail(userDetails.getEmail());
	        jwtResponse.setFirstName(userDetails.getFirstName());
	        jwtResponse.setLastName(userDetails.getLastName());
	        jwtResponse.setId(userDetails.getId());
	        
	        return new ResponseEntity<>(GenericResponseBuilder.builder().message(MessageUtil.getMessage("app.auth.success.auth")).data(jwtResponse).build(), HttpStatus.OK);

	    } catch (Exception e) {
	    	e.printStackTrace();
	        return new ResponseEntity<>(GenericResponseBuilder.builder().message(e.getMessage()).build(), HttpStatus.EXPECTATION_FAILED);
	    }
	}
   
}
