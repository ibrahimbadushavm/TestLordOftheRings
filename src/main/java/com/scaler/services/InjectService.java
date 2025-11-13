package com.scaler.services;


import javax.inject.Inject;

public class InjectService {

    @Inject
    String s;

    @Inject
    public InjectService() {
    }

    @Inject
    public InjectService(String s) {
        this.s = s;
    }
}