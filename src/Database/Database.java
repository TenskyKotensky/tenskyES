package Database; /**
 * Created by l on 27.09.2016.
 */

import info.*;
import org.sqlite.SQLiteConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Database
{
    public static Connection connection;
    public static Statement statement;
    public static String databasePath = "src\\Database\\";
    public static String databaseName = "Food.s3db";


    /**
     * Подключение к базе данных
     *
     * @return true - успешно; false - не удалось подключиться
     */
    public static boolean connect()
    {
        connection = null;
        try
        {
            Class.forName("org.sqlite.JDBC");
            System.out.println("[Database]: Set encoding to UTF8");
            SQLiteConfig config = new SQLiteConfig();
            config.setEncoding(SQLiteConfig.Encoding.UTF8);
            System.out.println("[Database]: Connecting to " + databasePath + databaseName);
            connection = DriverManager.getConnection("jdbc:sqlite:" + databasePath + databaseName);
            System.out.println("[Database]: Connected successfully.");
            return true;
        } catch (ClassNotFoundException e)
        {
//            e.printStackTrace();
            System.err.println("[Database.connect()]: -- Class [org.sqlite.JDBC] not found!");
        } catch (SQLException e)
        {
//            e.printStackTrace();
            System.out.println("[Database.connect()]: Can't get stop with [" + databasePath + databaseName + "]." +
                    "\n\t Make sure that DB is exists.");

        }
        return false;
    }

    public static void deleteAllRecrords()
    {
        try
        {
            statement = connection.createStatement();
            String query = "DELETE FROM 'Food';";
            statement.execute(query);
            System.out.println("[Database.deleteAllRecords]: All Records Deleted");
            statement.close();
        } catch (SQLException e)
        {
            System.err.println("[Database.deleteAllRecords.L:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + "]: Can't create statement on connection");
        }

    }

    /**
     * Создает запись в базе данных
     *
     * @param название  столбец <code>название</code>, отвечающий за {@link FoodInfo#название название}<br>
     * @param категория столбец <code>категория</code> {@link FoodInfo#категория категорию}<br>
     * @param размер    столбец <code>размер</code> {@link FoodInfo#размер размер}<br>
     * @param форма     столбец <code>форма</code> {@link FoodInfo#форма форму}<br>
     * @param цвет      столбец <code>цвет</code> {@link FoodInfo#цвет цвет}
     */
    public static void writeRecord(String название, Категория категория, Размер размер, Форма форма, Цвет цвет)
    {
        try
        {
            statement = connection.createStatement();
            System.out.println("[Database.writeRecord]: Statement created on connection");
            String query = "INSERT INTO 'Food' (Name,Category,Size,Form,Color) VALUES (\"" +
                    название +
                    "\",\"" + категория.toString() +
                    "\",\"" + размер.toString() +
                    "\",\"" + форма.toString() +
                    "\",\"" + цвет.toString() + "\");";
            statement.execute(query);
            statement.close();
            statement = connection.createStatement();
            query = "SELECT * FROM 'Food' WHERE Name='" + название + "';";
            ResultSet resultSet = statement.executeQuery(query);
            System.out.println("[Database.writeRecord]: --------------------");
            System.out.println("[Database.writeRecord]: New row added!");
            System.out.println("[Database.writeRecord]: Name: " + resultSet.getString("Name"));
            System.out.println("[Database.writeRecord]: Category: " + resultSet.getString("Category"));
            System.out.println("[Database.writeRecord]: Size: " + resultSet.getString("Size"));
            System.out.println("[Database.writeRecord]: Form: " + resultSet.getString("Form"));
            System.out.println("[Database.writeRecord]: Color: " + resultSet.getString("Color"));
            System.out.println("[Database.writeRecord]: --------------------");
            System.out.println("");
            statement.close();
            System.out.println("[Database.writeRecord]: Statement closed.");
        } catch (SQLException e)
        {
            System.err.println("[writeRecord.L:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + "]: Can't create statement on connection");
        }
    }
   public static List<String> getList(Категория категория, Размер размер, Форма форма, Цвет цвет)
    {
        List<String> listOfVariants = new ArrayList<>();
        try
        {
            statement = connection.createStatement();
        String category = getCategoryQuery(категория);
            String size = getSizeQuery(размер);
            String form = getFormQuery(форма);
            String color = getColorQuery(цвет);

            String query = "SELECT Name FROM 'Food' WHERE " + category +  size +form + color + ";";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next())
            {
                listOfVariants.add(resultSet.getString("Name"));
            }
            statement.close();
            resultSet.close();
            return listOfVariants;
        } catch (SQLException e)
        {
            System.err.println("[getList.L:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + "]: Can't create statement on connection.");
            try
            {
                statement.close();
            } catch (SQLException e1)
            {
                System.err.println("[getList.L:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + "]: Can't close connection");
            }
            return listOfVariants;
        }
    }

    /**
     * Возвращает часть запроса, которая сгенерирована на основе переданной <code>категории</code>
     *
     * @param категория {@link FoodInfo#категория категория вида}
     * @return часть запроса (например: <code>'Category'="Фрукт" AND 'Category'="Овощ" AND 'Category'="Ягода"</code>)
     * в случае, если пользователь указал, что категория неизвестна
     */
    private static String getCategoryQuery(Категория категория)
    {
        String query;
        String category = "Category=\"";
        String categoryend = "\"";
        String and = " OR ";
        if (категория.toString().equals(Категория.Неизвестно.toString()))
        {
            query="";
        }
        else
        {
            query = category + категория.toString() + categoryend;
        }
        return query;
    }

    /**
     * Возвращает часть запроса, которая сгенерирована на основе переданного <code>размера</code>
     *
     * @param размер {@link FoodInfo#размер размер вида}
     * @return часть запроса (например: <code>'Size'="Большой" AND 'Size'="Средний" AND 'Size'="Малый"</code>)
     * в случае, если пользователь указал, что размер неизвестен
     */
    private static String getSizeQuery(Размер размер)
    {
        String query;
        String size = "Size=\"";
        String sizeEnd = "\"";
        String and = " OR ";
        if ((размер.toString().equals(Размер.Неизвестно.toString())))
        {
            query="";
        }
        else
        {
            query = " AND " +size + размер + sizeEnd;
        }
        return query;
    }

    /**
     * Возвращает часть запроса, которая сгенерирована на основе переданной <code>формы</code>
     *
     * @param форма {@link FoodInfo#форма форма вида}
     * @return часть запроса (например: <code>'Form'="Круглая" AND 'Form'="Продолговатая" AND 'Form'="Другая"</code>)
     * в случае, если пользователь указал, что форма другая или неизвестна
     */
    private static String getFormQuery(Форма форма)
    {
        String query;
        String form = "Form=\"";
        String formend = "\"";
        String and = " OR ";
        if (форма.toString().equals(Форма.Неизвестно.toString()) || форма.toString().equals(Форма.Другая.toString()))
        {
            query="";
        }
        else
        {
            query = " AND " + form + форма.toString() + formend;
        }
        return query;
    }

    /**
     * Возвращает часть запроса, которая сгенерирована на основе переданного <code>цвета</code>
     *
     * @param цвет {@link FoodInfo#цвет цвет вида}
     * @return часть запроса (например: <code>'Color'="Белый" AND 'Color'="Желтый" AND 'Color'="Зеленый" AND
     * 'Color'="Красный" AND 'Color'="Оранжевый" AND 'Color'="Синий" AND 'Color'="Черный" AND 'Color'="Другой"</code>)
     * в случае, если пользователь указал, что цвет другой или неизвестен
     */
    private static String getColorQuery(Цвет цвет)
    {
        String query;
        String color = "Color=\"";
        String colorend = "\"";
        String and = " OR ";
        if (цвет.toString().equals(Цвет.Неизвестно.toString()) || цвет.toString().equals(Цвет.Другой.toString()))
        {
            query="";
        }
        else
        {
            query = " AND " +color + цвет.toString() + colorend;
        }
        return query;
    }
}
