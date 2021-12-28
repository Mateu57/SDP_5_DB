import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;


class SDPDB {
    public static void main( String args[] ) {
        Connection c = null;
        Statement stmt = null;
        int num = 0;

        Scanner scan = new Scanner(System.in);

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/SDPDB",
                            "postgres", "haslo");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            stmt = c.createStatement();

            do {
                System.out.println("Wybierz co chcesz zrobić:");
                System.out.println("1. Wyswietlic baze danych");
                System.out.println("2. Dodac nowy wiersz");
                System.out.println("3. Usuwanie wierszy");
                System.out.println("9. Wyjście");
                System.out.println("Podaj numer:");
                num = Integer.valueOf(scan.nextLine());

                switch (num) {
                    case 1:
                        System.out.println("[student]");
                        ResultSet rs = stmt.executeQuery("SELECT * FROM student;");
                        while (rs.next()) {
                            int id = rs.getInt("id");
                            System.out.println("ID = " + id);
                        }
                        System.out.println();

                        System.out.println("[nostudent]");
                        ResultSet nrs = stmt.executeQuery("SELECT * FROM nostudent;");
                        while (nrs.next()) {
                            int noid = nrs.getInt("noid");
                            System.out.println("NOID = " + noid);
                        }

                        rs.close();
                        break;

                    case 2:
                        System.out.println("Podaj nowa liczbe do tabeli [student]:");
                        String newid = scan.nextLine();

                        stmt = c.createStatement();
                        String sql = "INSERT INTO student (id) VALUES (" + newid + ");";
                        stmt.executeUpdate(sql);

                        System.out.println("Podaj nowa liczbe do tabeli [nostudent]:");
                        String newnid = scan.nextLine();

                        stmt = c.createStatement();
                        sql = "INSERT INTO nostudent (noid) VALUES (" + newnid + ");";
                        stmt.executeUpdate(sql);
                        break;

                    case 3:
                        System.out.println("Podaj jaka wartosc [id] ma zostac usunieta z tabeli [student]:");
                        String delid = scan.nextLine();

                        sql = "DELETE from student where id = "+ delid +";";
                        stmt.executeUpdate(sql);
                        c.commit();

                        System.out.println("Podaj jaka wartosc [noid] ma zostac usunieta z tabeli [nostudent]:");
                        String delnid = scan.nextLine();

                        sql = "DELETE from nostudent where noid = "+ delnid +";";
                        stmt.executeUpdate(sql);
                        c.commit();
                        break;

                    case 9:
                        System.out.println("Koniec programu.");
                        break;

                    default:
                        System.out.println("Zły wybór. Spróbuj ponownie.");
                }
            } while (num != 9);

            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }
}