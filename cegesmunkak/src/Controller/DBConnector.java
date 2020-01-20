package Controller;

import static Controller.DatabaseConnection.DRIVER;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

public class DBConnector implements DatabaseConnection {

    public static List DBQuery(String sql) {
        List rows = new ArrayList();
        boolean retry = true;
        while (retry) {
            try {
                Class.forName(DRIVER);
                try (Connection kapcsolat = DriverManager.getConnection(
                        URL, USER,
                        PASSWORD)) {
                    Statement s = kapcsolat.createStatement();
                    ResultSet rs = s.executeQuery(sql);
                    while (rs.next()) {;
                        ResultSetMetaData md = rs.getMetaData();
                        String[] rowValues = new String[md.getColumnCount()];
                        for (int i = 0; i < rowValues.length; i++) {
                            rowValues[i] = rs.getString(i + 1);
                        }
                        rows.add(rowValues);
                    }

                    if (!kapcsolat.isClosed()) {
                        kapcsolat.close();
                    }
                    retry = false;
                }
            } catch (ClassNotFoundException | SQLException e2) {
                System.out.println("A kapcsolódás sikertelen.");
                JFrame errorframe = new JFrame();
                Object[] options = {"Újra",
                    "Kilépés"};
                int result = JOptionPane.showOptionDialog(errorframe,
                        "A kapcsolódás sikertelen.",
                        "Adatbázis hiba",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        options,
                        options[1]);

                switch (result) {
                    case 0:
                        //DBQuery(sql);
                        break;
                    case 1:
                        System.exit(0);
                        break;
                }
                e2.printStackTrace();

            }
        }

        return rows;
    }

    public static String DBUpdate(String sql) {
        String error = "Sikeres update";
        boolean retry = true;
        while (retry) {
            try {
                Class.forName(DRIVER);
                try (Connection kapcsolat = DriverManager.getConnection(
                        URL, USER,
                        PASSWORD)) {
                    Statement s = kapcsolat.createStatement();

                    s.executeUpdate(sql);

                    if (!kapcsolat.isClosed()) {
                        kapcsolat.close();
                    }
                }
            } catch (ClassNotFoundException | SQLException e2) {
                System.out.println("A kapcsolódás sikertelen.");
                JFrame errorframe = new JFrame();
                Object[] options = {"Újra",
                    "Kilépés"};
                int result = JOptionPane.showOptionDialog(errorframe,
                        "A kapcsolódás sikertelen.",
                        "Adatbázis hiba",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        options,
                        options[1]);

                switch (result) {
                    case 0:
                        //DBQuery(sql);
                        break;
                    case 1:
                        System.exit(0);
                        break;
                }
                e2.printStackTrace();
                error = e2.getMessage();
            }
        }
        return error;
    }

}
