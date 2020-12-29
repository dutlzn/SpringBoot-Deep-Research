package com.example.sb2.pro;

import org.springframework.beans.factory.Aware;

public interface MyAware extends Aware {
    void setFlag(Flag flag);
}
