
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Admin
 */
public class StudentQueries {
    private static Connection connection;
    //private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addStudent;
    private static PreparedStatement getAllStudents;
    private static ResultSet resultSet;
    private static ResultSet resultSet2;
    private static PreparedStatement getStudent;
    private static PreparedStatement dropStudent;

    
    public static void addStudent(StudentEntry myStudent)
    {
        connection = DBConnection.getConnection();
        try
        {
            addStudent = connection.prepareStatement("insert into app.studententry (studentid, firstname, lastname) values (?, ?, ?)");
            addStudent.setString(1, myStudent.getStudentID());
            addStudent.setString(2, myStudent.getFirstName());
            addStudent.setString(3, myStudent.getLastName());
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<StudentEntry> getAllStudents()
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<StudentEntry>();
        try
        {
            getAllStudents = connection.prepareStatement("select studentid, firstname, lastname from app.studententry order by studentid");
            resultSet = getAllStudents.executeQuery();
            
            while(resultSet.next())
            {
                String id = resultSet.getString(1);
                String first = resultSet.getString(2);
                String last = resultSet.getString(3);
                StudentEntry newStudent = new StudentEntry(id, first, last);
                
                students.add(newStudent);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return students;
    }
    public static StudentEntry getStudent(String studentID)
    {
        connection = DBConnection.getConnection();
        
        ArrayList<StudentEntry> students = new ArrayList<StudentEntry>();
        
        try
        {
            String[] result = studentID.split(", ");
            getStudent = connection.prepareStatement("select studentid, firstname, lastname from app.studententry where firstname = ? and lastname = ?");
            getStudent.setString(1, result[1]);
            getStudent.setString(2, result[0]);
            
            resultSet2 = getStudent.executeQuery();
            
            if(resultSet2.next()) {
                String id = resultSet2.getString(1);
                String first = resultSet2.getString(2);
                String last = resultSet2.getString(3);
                StudentEntry newStudent = new StudentEntry(id, first, last);
                
                students.add(newStudent);
            }
            
            
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }

        return students.get(0);
    }
    
    public static void dropStudent(String studentID)
    {
        connection = DBConnection.getConnection();
        try
        {
            dropStudent = connection.prepareStatement("delete from app.studententry where studentid = ?");
            dropStudent.setString(1, studentID);
            dropStudent.executeUpdate();
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    
    
}