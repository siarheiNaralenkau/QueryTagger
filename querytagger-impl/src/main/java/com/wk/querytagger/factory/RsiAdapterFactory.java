package com.wk.querytagger.factory;

import org.springframework.beans.factory.annotation.Autowired;

import com.wk.querytagger.adapter.RsiAdapter;

public class RsiAdapterFactory {
static final RsiAdapterFactory instance = new RsiAdapterFactory();
    
    @Autowired
    private RsiAdapter rsiAdapter;
    

    public void setRsiAdapter(RsiAdapter rsiAdapter) {
        this.rsiAdapter = rsiAdapter;
    }

    public RsiAdapter getRsiAdapter() {
        return rsiAdapter;
    }
    
    public static RsiAdapterFactory getInstance() {
        return instance;
    }

}
