package com.sun.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class SqlSessionUtil {

    private static SqlSessionFactory sessionFactory=null;

    private SqlSessionUtil(){}

    static{
        String mybatis="mybatis.xml";
        InputStream inputStream=null;
        try {
          inputStream= Resources.getResourceAsStream(mybatis);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    private static ThreadLocal<SqlSession> threadLocal=new ThreadLocal<SqlSession>();
    public static SqlSession getSqlSession(){
        SqlSession session=threadLocal.get();
        if (session == null) {
            session=sessionFactory.openSession();
        }
        return session;
    }


    public static void closeSqlSession(SqlSession session){

        if (session != null) {


            session.close();
            threadLocal.remove();


        }

    }
}
