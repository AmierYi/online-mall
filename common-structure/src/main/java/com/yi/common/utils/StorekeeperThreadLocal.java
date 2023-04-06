package com.yi.common.utils;

import com.yi.common.entity.Storekeeper;

public class StorekeeperThreadLocal {

    private static final ThreadLocal<Storekeeper> storekeeperThreadLocal = new ThreadLocal<>();

    public static Storekeeper get(){
        return storekeeperThreadLocal.get();
    }

    public static void setStorekeeperThreadLocal(Storekeeper storekeeper) {
        storekeeperThreadLocal.set(storekeeper);
    }

    public static void remove(){
        storekeeperThreadLocal.remove();
    }
}
