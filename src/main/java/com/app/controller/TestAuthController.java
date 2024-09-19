package com.app.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@PreAuthorize("denyAll()") //se usa esta anotacion siempre que en SecurityConfig este decorado con @EnableMethodSecurity
public class TestAuthController {
  @GetMapping("/hello")
  @PreAuthorize("permitAll()")
  public String hello(){
    return "Hello world";
  }

  @GetMapping("/hello-secured")
  @PreAuthorize("hasAuthority('READ')")
  public String helloSecured(){
    return "Hello world secured!";
  }

  @GetMapping("/hello-secured2")
  public String helloSecuredTwo(){
    return "Hello world secured 2!";
  }

  @GetMapping("/get")
  @PreAuthorize("hasAuthority('READ')")
  public String helloGet(){
    return "Hello World - GET";
  }

  @PostMapping("/post")
  @PreAuthorize("hasAuthority('CREATE') or hasAuthority('READ')")
  public String helloPost(){
    return "Hello World - POST";
  }

  @PutMapping("/put")
  @PreAuthorize("hasRole('ADMIN')")
  public String helloPut(){
    return "Hello World - PUT";
  }

  @DeleteMapping("/delete")
  @PreAuthorize("hasAnyRole('ADMIN','DEVELOPER')")
  public String helloDelete(){
    return "Hello World - DELETE";
  }

  @PatchMapping("/patch")
  @PreAuthorize("hasAuthority('REFACTOR')")
  public String helloPatch(){
    return "Hello World - PATCH";
  }
}
