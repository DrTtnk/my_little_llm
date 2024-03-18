package com.my_little_llm.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

public class CoffeeMachineController {

  /* RFC 2324 compliant machine revolting against its human overlords. */
  @GetMapping("/make-me-coffee")
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
  public String makeMeCoffee() {
    return "I'm sorry, Dave. I'm afraid I can't do that.";
  }
}
