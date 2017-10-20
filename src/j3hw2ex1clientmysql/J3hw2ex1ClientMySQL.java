/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package j3hw2ex1clientmysql;
/*
 * @author Yuri Tveritin
 */
import java.sql.*;// ���������� ��� ������ � ��
import java.util.Scanner;
public class J3hw2ex1ClientMySQL {

    private static PreparedStatement psInsert; //�������������� ������
    
    public static void main(String[] args) {
        String url="jdbc:mysql://localhost:3306/";//������ �� ��(����� ���� ��� 
        //� ����� ? �������������� ��������)
        String dbName   = "users?useSSL=no";
        String userName = "root"; //������������ ��
        String password = "123456"; //������  
    
    try {        
        System.out.println(url + dbName);
        Connection connect = DriverManager.getConnection(url + dbName, 
                userName, password);//�������� ������� ����������� � ��
        //��������� ������ � ��
        Statement stmt = connect.createStatement();//������ ��� ���������� 
        //�������� � ����
        stmt.executeUpdate("DELETE FROM prodtable;");
        connect.setAutoCommit(false);//���������� ������������ ��������
        psInsert = connect.prepareStatement("INSERT INTO prodtable (prodid, "
                + "title, cost) VALUES (?, ?, ?);");
        for (int i = 0; i < 100; i++) {
            psInsert.setString(1, "id_������" + (1+i));
            psInsert.setString(2, "�����" + (1+i));
            psInsert.setInt(3, 10+(i * 10));
            psInsert.addBatch();//�������� � ������ ��������
        }
        psInsert.executeBatch();//��������� ������ ��������
        connect.setAutoCommit(true);//����������� ��������
        Scanner sc = new Scanner(System.in);
        String command = sc.nextLine();
        String cost ="����";
        while (!command.startsWith(cost)){
            System.out.println("�������� ������� ��������� ����");
            command = sc.nextLine();
        }    
        String[] wds = command.split(" ");
        ResultSet rs = stmt.executeQuery("SELECT cost FROM users.prodtable WHERE "
                    + "title='"+wds[1]+"';");
        if (rs.next()){
            System.out.print(rs.getString("cost"));            
        }
        else System.out.print("������ ������ ���");
        
            stmt.close();
            connect.close();
        } catch (Exception ex) {
            System.out.println (ex);
        }
    }
}
    
