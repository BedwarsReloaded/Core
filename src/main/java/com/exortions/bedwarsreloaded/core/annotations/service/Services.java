package com.exortions.bedwarsreloaded.core.annotations.service;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Services {

    Class<?>[] services();

}
