
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
public class CourseQueries {
    private static Connection connection;
    //private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addCourse;
    private static PreparedStatement GetAllCourses;
    private static ResultSet resultSet;
    private static PreparedStatement GetAllCourseCodes;
    private static PreparedStatement GetAllCourseSeats;
    private static PreparedStatement removeCourse;
    
    public static void addCourse(CourseEntry myCourse)
    {
        connection = DBConnection.getConnection();
        try
        {
            addCourse = connection.prepareStatement("insert into app.courseentry (semester, coursecode, description, seats) values (?, ?, ?, ?)");
            addCourse.setString(1, myCourse.getSemester());
            addCourse.setString(2, myCourse.getCourseCode());
            addCourse.setString(3, myCourse.getDescription());
            addCourse.setInt(4, myCourse.getSeats());
            addCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<CourseEntry> GetAllCourses(String semester)
    {
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> courses = new ArrayList<CourseEntry>();
        try
        {
            GetAllCourses = connection.prepareStatement("select semester, coursecode, description, seats from app.courseentry order by semester");
            resultSet = GetAllCourses.executeQuery();
            
            while(resultSet.next())
            {
                if (resultSet.getString(1).equals(semester)){
                
                String code = resultSet.getString(2);
                String desc = resultSet.getString(3);
                int seats = resultSet.getInt(4);
                CourseEntry newCourse = new CourseEntry(semester, code, desc, seats);
                courses.add(newCourse);
                }
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courses;
        
    }
    
    public static ArrayList<String> getAllCourseCodes(String semester)
    {
        connection = DBConnection.getConnection();
        ArrayList<String> codes = new ArrayList<String>();
        try
        {
            GetAllCourseCodes = connection.prepareStatement("select semester, coursecode, description, seats from app.courseentry order by semester");
            resultSet = GetAllCourseCodes.executeQuery();
            
            while(resultSet.next())
            {
                if (resultSet.getString(1).equals(semester)){
                codes.add(resultSet.getString(2));
                }
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return codes;
        
    }
    
    public static int getCourseSeats(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        int seats = 0;
        try
        {
            GetAllCourseSeats = connection.prepareStatement("select semester, coursecode, description, seats from app.courseentry order by semester");
            resultSet = GetAllCourseSeats.executeQuery();
            
            while(resultSet.next())
            {
                if ((resultSet.getString(1).equals(semester)) && ((resultSet.getString(2).equals(courseCode)))){
                seats = (resultSet.getInt(4));
                }
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return seats;
        
    }
    public static void dropCourse(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        try
        {
            removeCourse = connection.prepareStatement("delete from app.courseentry where (semester = ? AND coursecode = ?)");
            removeCourse.setString(1, semester);
            removeCourse.setString(2, courseCode);
            removeCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
}