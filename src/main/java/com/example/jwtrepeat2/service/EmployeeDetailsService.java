package com.example.jwtrepeat2.service;

import com.example.jwtrepeat2.model.Employee;
import com.example.jwtrepeat2.repository.EmployeeJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeDetailsService implements UserDetailsService {
    private EmployeeJpaRepository employeeJpaRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeJpaRepository.findEmployeeByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No such employee!"));
        return User.builder()
                .username(employee.getUsername())
                .password(employee.getPassword())
                .authorities(employee.getAuthorities())
                .build();
    }
}
