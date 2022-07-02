package ar.com.mercadito.mysql;

import java.sql.*;

/*
Pasos para usar JDBC:
1. Crear una instancia del JDBC Driver. (Solo si estamos en eclipse o notepad)
2. Especificar la url y credenciales de la BDD.
3. Establecer una conexión usando el driver que crea el objeto Connection.
4. Crear un objeto Statement, usando Connection.
5. Armar el postulado SQL y enviarlo a ejecución usando el Statement.
6. Recibir los resultados en el objeto ResultSet.
*/

public class Conector {
    private String JDBC_DRIVER;
    private String DB_URL;
    private String USER;
    private String PASSWORD;

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    private boolean estaConectado;

    public Conector(){
        connection = null;
        statement = null;
        resultSet = null;
        estaConectado = false;

        // Datos para coneccion
        JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        DB_URL = "jdbc:mysql://localhost:3306/supermark";
        USER = "root";
        PASSWORD = "1234567890f";
    }

    /* Métodos
    //PASO 1: Abrir una Conexion
    //PASO 2: Hacer la consulta
    //PASO 3: Estraer los datos
    //PASO 4: Realizar la Limpieza
    */

    private void abrirConeccion(){
        //PASO 1: Abrir una Conexion
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            estaConectado = true;
        } catch (SQLException evento) {
            evento.printStackTrace();
            estaConectado = false;
        }
    }

    //---------------------------------------------------------------------------------------------
    public String consultar(String etiqueta,String etiqueta2){
        String cadena = "";

        abrirConeccion();
        if(estaConectado){
            try {
                statement = connection.createStatement();
                String sql = "";
                sql = "SELECT * FROM supermark.users_db;";
                resultSet = statement.executeQuery(sql);
                if (resultSet.next()) cadena = extraeDatos(etiqueta2);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        limpiar();
        return cadena;
    }
    //---------------------------------------------------------------------------------------------
    public void guardarUsuario(String user, String password){
        abrirConeccion();
        if(estaConectado){
            try {
                statement = connection.createStatement();
                String sql = "";
                sql = "INSERT INTO `supermark`.`users_db` (id,user,password,type) VALUES(0,'" + user + "', '" + password + "',0)";
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //---------------------------------------------------------------------------------------------
    private String extraeDatos(String label){
        //PASO 3: Extraer datos de un ResulSet
        String cadena,a,b,c,j;
        cadena = "";
        try {
            resultSet.next();
            j = resultSet.getString("id");
            a = resultSet.getString("user");
            b = resultSet.getString("password");
            c = resultSet.getString("type");
            cadena = j+"/"+a+"/"+b+"/"+c;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cadena;
    }
    //---------------------------------------------------------------------------------------------

    private void limpiar(){
    //PASO4: Entorno de Limpieza
        try {
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            // Bloque finalmente utilizado para cerrar recursos
            try{
                if(statement!=null) statement.close();
            }catch(SQLException se2) {
                se2.printStackTrace();
            }
            try{
                if(connection!=null) connection.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        //System.gc();
    }
}
