package com.sun.util;

import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TransactionInvocationHandler implements InvocationHandler {

    private Object target = null;

    public TransactionInvocationHandler(Object target){
        this.target=target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) {

         Object o=null;
         SqlSession session=null;

        try {
            session=SqlSessionUtil.getSqlSession();
            o=method.invoke(target,args);
        } catch (IllegalAccessException e) {
            session.rollback();
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        return o;

    }

    public Object getProxy(){
    return Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }
}
