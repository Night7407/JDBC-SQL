import java.util.*;
import java.sql.*;
import java.io.*;

public class Applicatioon extends ConnectionApplication {
    Scanner sc = new Scanner(System.in);
    String tableName ="";
    Map<String,List<List<String>>> map = new LinkedHashMap<>();
    
    void create(){
        System.out.println("Enter the table name");
        String tablename = sc.next();
        System.out.println("Enter the No of Column");
        int n = sc.nextInt();
        sc.nextLine();  

        List<List<String>> col_con = new ArrayList<>();
        tableName = tablename;
        map.put(tableName,col_con);
        
        int cnt = 0;
        for(int i = 0;i<n;i++){
            List<String> Col_con = new ArrayList<>();
            if(cnt == 0){
                System.out.println("Enter the Column name");
                String ColumnName = sc.nextLine();
                Col_con.add(ColumnName);
                System.out.println("Enter the Datatype");
                String data = sc.nextLine();
                Col_con.add(data);
                System.out.println("Enter the Constriant");
                String cont = sc.nextLine();
                Col_con.add(cont);
                col_con.add(Col_con);
            }
            else{
                System.out.println("Enter the Column name");
                String ColumnName = sc.nextLine();
                Col_con.add(ColumnName);
                System.out.println("Enter the Datatype");
                String data = sc.nextLine();
                Col_con.add(data);
                col_con.add(Col_con);
            }
            cnt++;
        }
        
        
        try{
            int count = 0;
            String createQuery = "Create table "+tablename+"(";
                List<List<String>> columns = map.get(tableName);
                for(int i = 0; i < columns.size(); i++) {
                    List<String> col = columns.get(i);
                    createQuery += col.get(0) + " " + col.get(1);
                    if(col.size() == 3) {
                        createQuery += " " + col.get(2);
                    }
                    if(i != columns.size() - 1) {     
                        createQuery += ",";
                    }
                }
                createQuery += ")";
                PreparedStatement pst = con.prepareStatement(createQuery);
                pst.executeUpdate();
                System.out.println(createQuery);
                System.out.println("Table created");

        }



        catch(Exception e){
            System.out.println(e);
        }
    }
    void insert(){
        String tablename = tableName;
        List<List<String>> columns = map.get(tablename);
            StringBuilder insertQuery = new StringBuilder();
            insertQuery.append("INSERT INTO ");
            insertQuery.append(tablename);
            insertQuery.append(" VALUES(");
        
        try{
            if(columns == null){
                System.out.println("Table not created in map.");
                return;
            }

            
            for(int i = 0; i < columns.size(); i++) {

                System.out.println("Enter value for " + columns.get(i).get(0));
                String value = sc.next();
                insertQuery.append("'").append(value).append("'");
                if(i < columns.size() - 1){
                    insertQuery.append(",");
                }
            }

            insertQuery.append(");");
            PreparedStatement pst = con.prepareStatement(insertQuery.toString());
            pst.executeUpdate();
            System.out.println("Data is inserted");
                
        }
            
        catch(Exception e){
            System.out.println(e);
        }
    }
    void Update(){
        System.out.println("Enter the id");
        int n = sc.nextInt();
        List<List<String>> list= map.get(tableName);
        String firstcolumnname = list.get(0).get(0);
        
        try{
            for(int i = 1;i<list.size();i++){
                System.out.println("Press" + i + list.get(i).get(0));
            }
            System.out.println("Enter the Choice");
            int choice = sc.nextInt();
            
            String columnName = list.get(choice).get(0);
            String getDatatype = list.get(choice).get(1);
            System.out.println("You are Updating "+ columnName +" with Datatype" + getDatatype);
            System.out.println("Enter the new Value");
            String updateQuery = " ";
            if(getDatatype.toLowerCase().contains("int")){
                int entrynum = sc.nextInt();
                updateQuery = "UPDATE " + tableName +
                     " SET " + columnName + " = " + entrynum +
                     " WHERE " + firstcolumnname + " = " + n;

            }
            if(getDatatype.toLowerCase().contains("char")){
                String entryStr = sc.next();
                updateQuery = "UPDATE " + tableName +
                            " SET " + columnName + " = '" + entryStr + "'" +
                            " WHERE " + firstcolumnname + " = " + n;

            }
            PreparedStatement pst3 = con.prepareStatement(updateQuery);
            pst3.executeUpdate();
            
            
            System.out.println("Updating is Done");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    void Delete(){
        System.out.println("Enter the id you want delete");
        int nid = sc.nextInt();

        List<List<String>> list = map.get(tableName);
        String firstColumn = list.get(0).get(0);
        try{
            String delQuery = "DELETE FROM " + tableName +
                          " WHERE " + firstColumn + " = " + nid;
            PreparedStatement pst = con.prepareStatement(delQuery);
            int rows = pst.executeUpdate();

        if(rows > 0)
            System.out.println("Delete successful");
        else
            System.out.println("No record found");

    }
    catch(Exception e){
        System.out.println(e);
    }
}
    
    void importfromCSV(){
        System.out.println("Enter the filename or filepath ");
        String filename = sc.next();
        try{
            BufferedReader bf = new BufferedReader(new FileReader(filename));
            String line;
            while((line = bf.readLine()) !=null){
                String[] values = line.split(",");

            // Create placeholders (?, ?, ?, ?)
            StringBuilder placeholders = new StringBuilder();
            for (int i = 0; i < values.length; i++) {
                placeholders.append("?");
                if (i != values.length - 1)
                    placeholders.append(",");
            }

            String insertQuery = "INSERT INTO " + tableName +
                                 " VALUES (" + placeholders + ")";

            PreparedStatement pst = con.prepareStatement(insertQuery);

            for (int i = 0; i < values.length; i++) {
                pst.setString(i + 1, values[i]);
            }

            pst.executeUpdate();
        }
            
            System.out.println("Completed");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    void exportToCSV(){
        try{
        BufferedWriter bw = new BufferedWriter(new FileWriter("Result.csv"));

        String query = "SELECT * FROM " + tableName;
        PreparedStatement pst = con.prepareStatement(query);
        ResultSet rs = pst.executeQuery();

        ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();

 
        for(int i = 1; i <= columnCount; i++){
            bw.write(meta.getColumnName(i));
            if(i != columnCount)
                bw.write(",");
        }
        bw.newLine();

     
        while(rs.next()){
            for(int i = 1; i <= columnCount; i++){
                bw.write(rs.getString(i));
                if(i != columnCount)
                    bw.write(",");
            }
            bw.newLine();
        }

        bw.close();
        System.out.println("Export Completed");
    }
    catch(Exception e){
        System.out.println(e);
    }
    }
    void display(){
        
        try{
        String query = "SELECT * FROM " + tableName;
        PreparedStatement pst = con.prepareStatement(query);
        ResultSet rs = pst.executeQuery();

        ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();

        for(int i = 1; i <= columnCount; i++){
            System.out.print(meta.getColumnName(i) + "\t");
        }
        System.out.println();

        while(rs.next()){
            for(int i = 1; i <= columnCount; i++){
                System.out.print(rs.getString(i) + "\t");
            }
            System.out.println();
        }
    }
    catch(Exception e){
        System.out.println(e);
    }
}
    
    void modifyTable() {
    try {
        System.out.println("Do you want to modify the table? (yes/no)");
        String choice = sc.nextLine();

        if (choice.equalsIgnoreCase("yes")) {

            System.out.println("Enter the table name:");
            String tablename = sc.nextLine();

            System.out.println("Enter new column name:");
            String columnName = sc.nextLine();

            System.out.println("Enter datatype (e.g., varchar(50), int):");
            String datatype = sc.nextLine();

            String alterQuery = "ALTER TABLE " + tablename +
                                " ADD " + columnName + " " + datatype;

            Statement stmt = con.createStatement();
            stmt.executeUpdate(alterQuery);

            // 🔥 Update map structure also
            List<List<String>> list = map.get(tablename);
            if (list != null) {
                List<String> newCol = new ArrayList<>();
                newCol.add(columnName);
                newCol.add(datatype);
                list.add(newCol);
            }

            System.out.println("Column added successfully.");
        }
        else {
            System.out.println("No modification performed.");
        }
    }
    catch (Exception e) {
        System.out.println(e);
    }
}

    
    
}
    
    
    
    
    
    
    
    
    
    
    
    

