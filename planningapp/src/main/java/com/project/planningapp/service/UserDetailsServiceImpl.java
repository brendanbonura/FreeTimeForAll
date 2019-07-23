package com.project.planningapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.planningapp.dao.UserDao;
import com.project.planningapp.entity.Role;
import com.project.planningapp.entity.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserDao userDao;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userDao.getUserByEmail(email);
		if(user == null) throw new UsernameNotFoundException(email);
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		for(Role role : user.getRoles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
	}

}
