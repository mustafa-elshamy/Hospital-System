package com.example.hospital;

import android.content.Context;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
    private static String ip = "192.168.1.4";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String dataBase = "TheHospital";
    private static String userName = "mustafa";
    private static String passWord = "123456";
    private static String url = "jdbc:jtds:sqlserver://" + ip + ":" + port + "/" + dataBase;

    public static Connection connection = null;

    public DataBase(Context context) {
        setConnection(context);
    }

    public static void setConnection(Context context)
    {
        try {
            Class.forName(Classes);
            connection = DriverManager.getConnection(url,userName,passWord);
            Toast.makeText(context,"Connected to DB",Toast.LENGTH_LONG);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context,"Error",Toast.LENGTH_LONG);
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(context,"Failed connect to DB",Toast.LENGTH_LONG);
        }
    }

    public static ResultSet excutQuery(String query ,Context context)
    {
        setConnection(context);
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static void setIp(String ip,Context context) {
        DataBase.ip = ip;
        setUrl(ip,port,dataBase,context);
    }

    public static void setPort(String port,Context context) {
        DataBase.port = port;
        setUrl(ip,port,dataBase,context);
    }

    public static void setDataBase(String dataBase,Context context) {
        DataBase.dataBase = dataBase;
        setUrl(ip,port,dataBase,context);
    }

    public static void setUserName(String userName,Context context) {
        DataBase.userName = userName;
        setConnection(context);
    }

    public static void setPassword(String passWord,Context context) {
        DataBase.passWord = passWord;
        setConnection(context);
    }

    public static void setUrl(String ip , String port , String dataBase ,Context  context) {
        DataBase.url = "jdbc:jtds:sqlserver://" + ip + ":" + port + "/" + dataBase;
        setConnection(context);
    }

    public static int resultSize(String query, Context context)
    {
        ResultSet resultSet = DataBase.excutQuery(query,context);
        int size = 0;
        try {
            while (resultSet != null && resultSet.next())
                size++;
        }catch (SQLException e)
        {
        }
        return size;
    }
}
