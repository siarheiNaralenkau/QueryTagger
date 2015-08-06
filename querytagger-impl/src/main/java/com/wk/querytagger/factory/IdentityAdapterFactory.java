package com.wk.querytagger.factory;

import org.springframework.beans.factory.annotation.Autowired;

import com.wk.querytagger.adapter.IdentityAdapter;

public class IdentityAdapterFactory {
	static final IdentityAdapterFactory instance = new IdentityAdapterFactory();

    @Autowired
    private IdentityAdapter identityAdapter;
    

    public IdentityAdapter getIdentityAdapter() {
        return identityAdapter;
    }

    public void setIdentityAdapter(IdentityAdapter identityAdapter) {
        this.identityAdapter = identityAdapter;
    }
    
    public static IdentityAdapterFactory getInstance() {
        return instance;
    }

}
