package com.growthx.assignmentportal.entity;
/*
 * @author : Mausam Raj
 * @Date : 11-10-2024
 * This class implements Spring Security's UserDetails interface to provide
 * user authentication details and authorities.
 */


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Custom implementation of UserDetails for Spring Security.
 */
public class UserPrincipal implements UserDetails {
    private String id;
    private String username;

    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    /**
     * Constructor for UserPrincipal.
     *
     * @param id          The user's unique ID.
     * @param username    The user's username.
     * @param password    The user's password.
     * @param authorities The user's granted authorities.
     */
    public UserPrincipal(String id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    /**
     * Static factory method to create a UserPrincipal from a User entity.
     *
     * @param user The user entity to convert.
     * @return A UserPrincipal instance.
     */
    public static UserPrincipal create(User user) {
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().name());
        return new UserPrincipal(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                List.of(authority)
        );
    }

    public String getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
