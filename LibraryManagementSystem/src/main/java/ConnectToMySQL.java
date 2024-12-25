import java.sql.*;

public class ConnectToMySQL {
    public static void main(String[] args) {
        // 1. 加载数据库驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("找不到数据库驱动！");
            e.printStackTrace();
            return;
        }

        // 2. 连接数据库
        String url = "jdbc:mysql://localhost:3306/library_db";
        String user = "root";
        String password = "123";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("连接数据库成功！");

            // 3. 执行SQL语句
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM books";
            ResultSet rs = stmt.executeQuery(sql);

            // 4. 处理结果集
            while (rs.next()) {
                String name = rs.getString("name");
                int age = rs.getInt("age");
                System.out.println("Name: " + name + ", Age: " + age);
            }
        } catch (SQLException e) {
            System.out.println("连接数据库失败！");
            e.printStackTrace();
        }
    }
}