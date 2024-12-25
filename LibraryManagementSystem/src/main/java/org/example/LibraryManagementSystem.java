package org.example;

import java.sql.*;
import java.util.Scanner;


public class LibraryManagementSystem {

    // 数据库连接信息
    private static final String URL = "jdbc:mysql://localhost:3306/library_db";
    private static final String USER = "root";
    private static final String PASSWORD = "123";

    // 获取数据库连接
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // 添加书籍
    private static void addBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();
        System.out.print("Enter book price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter book quantity: ");
        int quantity = scanner.nextInt();

        String sql = "INSERT INTO books (title, author, price, quantity) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setDouble(3, price);
            stmt.setInt(4, quantity);
            int rowsInserted = stmt.executeUpdate();
            System.out.println(rowsInserted + " book(s) added.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 查看所有书籍
    private static void viewBooks() {
        String sql = "SELECT * FROM books";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("ID | Title | Author | Price | Quantity");
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                System.out.println(id + " | " + title + " | " + author + " | " + price + " | " + quantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 更新书籍信息
    private static void updateBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the ID of the book to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume the newline character
        System.out.print("Enter new title: ");
        String title = scanner.nextLine();
        System.out.print("Enter new author: ");
        String author = scanner.nextLine();
        System.out.print("Enter new price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter new quantity: ");
        int quantity = scanner.nextInt();

        String sql = "UPDATE books SET title = ?, author = ?, price = ?, quantity = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setDouble(3, price);
            stmt.setInt(4, quantity);
            stmt.setInt(5, id);
            int rowsUpdated = stmt.executeUpdate();
            System.out.println(rowsUpdated + " book(s) updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除书籍
    private static void deleteBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the ID of the book to delete: ");
        int id = scanner.nextInt();

        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            System.out.println(rowsDeleted + " book(s) deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 主菜单
    private static void menu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Library Management System ===");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Update Book");
            System.out.println("4. Delete Book");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    viewBooks();
                    break;
                case 3:
                    updateBook();
                    break;
                case 4:
                    deleteBook();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        menu();
    }
}
