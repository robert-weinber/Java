package Controller;



interface DatabaseConnection {
    String URL = "jdbc:mysql://localhost:3306/cegesmunkak?autoReconnect=true&useSSL=false";
    String USER = "root";
    String PASSWORD = "";
    String DRIVER = "com.mysql.jdbc.Driver";
}
