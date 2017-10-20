/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package j3hw2ex1clientmysql;
/*
 * @author Yuri Tveritin
 */
import java.sql.*;// библиотека для работы с БД
import java.util.Scanner;
public class J3hw2ex1ClientMySQL {

    private static PreparedStatement psInsert; //подготовленный запрос
    
    public static void main(String[] args) {
        String url="jdbc:mysql://localhost:3306/";//ссылка на БД(адрес порт имя 
        //и после ? дополнительные параметы)
        String dbName   = "users?useSSL=no";
        String userName = "root"; //пользователь БД
        String password = "123456"; //пароль  
    
    try {        
        System.out.println(url + dbName);
        Connection connect = DriverManager.getConnection(url + dbName, 
                userName, password);//создание объекта подключения к БД
        //формируем запрос к БД
        Statement stmt = connect.createStatement();//объект для выполнения 
        //запросов к базе
        stmt.executeUpdate("DELETE FROM prodtable;");
        connect.setAutoCommit(false);//отключение подтвердения операции
        psInsert = connect.prepareStatement("INSERT INTO prodtable (prodid, "
                + "title, cost) VALUES (?, ?, ?);");
        for (int i = 0; i < 100; i++) {
            psInsert.setString(1, "id_товара" + (1+i));
            psInsert.setString(2, "товар" + (1+i));
            psInsert.setInt(3, 10+(i * 10));
            psInsert.addBatch();//добавить в список запросов
        }
        psInsert.executeBatch();//выполнить список запросов
        connect.setAutoCommit(true);//подтвердить операции
        Scanner sc = new Scanner(System.in);
        String command = sc.nextLine();
        String cost ="цена";
        while (!command.startsWith(cost)){
            System.out.println("неверная команда повторите ввод");
            command = sc.nextLine();
        }    
        String[] wds = command.split(" ");
        ResultSet rs = stmt.executeQuery("SELECT cost FROM users.prodtable WHERE "
                    + "title='"+wds[1]+"';");
        if (rs.next()){
            System.out.print(rs.getString("cost"));            
        }
        else System.out.print("Такого товара нет");
        
            stmt.close();
            connect.close();
        } catch (Exception ex) {
            System.out.println (ex);
        }
    }
}
    
