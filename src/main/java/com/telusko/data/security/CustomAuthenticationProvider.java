package com.telusko.data.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.telusko.data.domain.AppUser;
import com.telusko.data.repo.UserRepo;



@Component("customAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	

	/*@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)authentication;
		AppUser user = userRepo.findByUserName(token.getName());
		if(user==null || !bCryptPasswordEncoder.matches(token.getCredentials().toString(), user.getPassword())) {
			throw new BadCredentialsException("The credentials are invalid");
		}
		
		return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());		
		
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		
		return UsernamePasswordAuthenticationToken.class.equals(authentication);
	}*/
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		CustomAuthenticationToken token = (CustomAuthenticationToken) authentication;
		AppUser user = userRepo.findByUserName(token.getName());
		//System.out.println(token.getCredentials().toString());
		//System.out.println(user.getPassword());
		//System.out.println(bCryptPasswordEncoder.matches(token.getCredentials().toString(),user.getPassword()));
		if(user == null ) {
			throw new UsernameNotFoundException(token.getName());
		}
		if(!bCryptPasswordEncoder.matches(token.getCredentials().toString(),user.getPassword())
				|| !token.getMake().equalsIgnoreCase("subaru")) {
			throw new BadCredentialsException("bad credentials");
		}
		return new CustomAuthenticationToken(user, user.getPassword(), user.getAuthorities(),token.getMake());		
		
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		
		return CustomAuthenticationToken.class.equals(authentication);
	}

}
