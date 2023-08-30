
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Admin
 */
public class ScheduleQueries {
    private static Connection connection;
    //private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addSchedule;
    private static PreparedStatement getScheduleByStudent;
    private static PreparedStatement getCount;
    private static ResultSet resultSet;
    private static PreparedStatement getScheduledStudentsByCourse;
    private static PreparedStatement getWaitlistedStudentsByCourse;
    private static PreparedStatement dropStudentScheduleByCourse;  
    private static PreparedStatement dropScheduleByCourse;
    private static PreparedStatement dropStudentSchedule;

    
    public static void addScheduleEntry(ScheduleEntry myEntry)
    {
        connection = DBConnection.getConnection();
        try
        {
            addSchedule = connection.prepareStatement("insert into app.scheduleentry (semester, coursecode, studentid, status, timestamp) values (?, ?, ?, ?, ?)");
            addSchedule.setString(1, myEntry.getSemester());
            addSchedule.setString(2, myEntry.getCourseCode());
            addSchedule.setString(3, myEntry.getStudentID());
            addSchedule.setString(4, myEntry.getStatus());
            addSchedule.setTimestamp(5, myEntry.getTimestamp());
            addSchedule.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID)
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedule = new ArrayList<ScheduleEntry>();
        try
        {
            getScheduleByStudent = connection.prepareStatement("select semester, studentid, coursecode, status, timestamp from app.scheduleentry order by studentid");
            resultSet = getScheduleByStudent.executeQuery();
            
            while(resultSet.next())
            {

                if (semester.equals(resultSet.getString(1)) && (studentID.equals(resultSet.getString(2)))) {
                
                String currentSemester = resultSet.getString(1);
                String courseCode = resultSet.getString(3);
                String ID = resultSet.getString(2);
                String status = resultSet.getString(4);
                Timestamp timestamp = resultSet.getTimestamp(5);

                
                ScheduleEntry newEntry = new ScheduleEntry(currentSemester, courseCode, ID, status, timestamp);
                
                schedule.add(newEntry);
            }
        }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return schedule;
    }
    
    public static int getScheduledStudentCount(String semester, String courseCode){
        connection = DBConnection.getConnection();
        int count = 0;
        try
        {
            getCount = connection.prepareStatement("select semester, studentid, coursecode, status, timestamp from app.scheduleentry order by studentid");
            resultSet = getCount.executeQuery();
            while(resultSet.next())
            {
                if (semester.equals(resultSet.getString(1)) && (courseCode.equals(resultSet.getString(3)))){
                    
                    count = count + 1;
                }
            }

        }
        
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return count;
    }
    
    public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedule = new ArrayList<ScheduleEntry>();
        try
        {
            getScheduledStudentsByCourse = connection.prepareStatement("select semester, studentid, coursecode, status, timestamp from app.scheduleentry order by studentid");
            resultSet = getScheduledStudentsByCourse.executeQuery();
            
            while(resultSet.next())
            {

                if (semester.equals(resultSet.getString(1)) && (courseCode.equals(resultSet.getString(3))) && "S".equals(resultSet.getString(4))) {
                
                String currentSemester = resultSet.getString(1);

                String ID = resultSet.getString(2);
                String status = resultSet.getString(4);
                Timestamp timestamp = resultSet.getTimestamp(5);

                
                ScheduleEntry newEntry = new ScheduleEntry(currentSemester, courseCode, ID, status, timestamp);
                
                schedule.add(newEntry);
            }
        }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }


        return schedule;
    }
    
    
    
    
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByCourse(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedule = new ArrayList<ScheduleEntry>();
        try
        {
            getWaitlistedStudentsByCourse = connection.prepareStatement("select semester, studentid, coursecode, status, timestamp from app.scheduleentry order by studentid");
            resultSet = getWaitlistedStudentsByCourse.executeQuery();
            
            while(resultSet.next())
            {

                if (semester.equals(resultSet.getString(1)) && (courseCode.equals(resultSet.getString(3))) && "W".equals(resultSet.getString(4))) {
                
                String currentSemester = resultSet.getString(1);

                String ID = resultSet.getString(2);
                String status = resultSet.getString(4);
                Timestamp timestamp = resultSet.getTimestamp(5);

                
                ScheduleEntry newEntry = new ScheduleEntry(currentSemester, courseCode, ID, status, timestamp);
                
                schedule.add(newEntry);
            }
        }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return schedule;
        
    }
    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode){
        connection = DBConnection.getConnection();

        try
        {
            dropStudentScheduleByCourse = connection.prepareStatement("delete from app.scheduleentry where semester = ? and studentid = ? and coursecode = ?");
            
            
            dropStudentScheduleByCourse.setString(1, semester);
            dropStudentScheduleByCourse.setString(2, studentID);
            dropStudentScheduleByCourse.setString(3, courseCode);
            dropStudentScheduleByCourse.executeUpdate();
            
            ArrayList<ScheduleEntry> ScheduledStudents = new ArrayList<ScheduleEntry>();
            ScheduledStudents = ScheduleQueries.getWaitlistedStudentsByCourse(semester, courseCode);
            
            if (ScheduledStudents.size() > 0){
            ScheduleEntry newEntry = ScheduledStudents.get(0);
            
            ScheduleQueries.updateScheduleEntry(semester, newEntry);
            }
        }
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    public static void dropStudentSchedule(String semester, String studentID){
        connection = DBConnection.getConnection();

        try
        {
            dropStudentSchedule = connection.prepareStatement("delete from app.scheduleentry where semester = ? and studentid = ?");
            
            
            dropStudentSchedule.setString(1, semester);
            dropStudentSchedule.setString(2, studentID);

            dropStudentSchedule.executeUpdate();
            
            //ArrayList<ScheduleEntry> ScheduledStudents = new ArrayList<ScheduleEntry>();
            //ScheduledStudents = ScheduleQueries.getWaitlistedStudentsByCourse(semester, courseCode);
            
            //ScheduleEntry newEntry = ScheduledStudents.get(0);
            
            //ScheduleQueries.updateScheduleEntry(semester, newEntry);
        }
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static void dropScheduleByCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();

        try
        {
            dropScheduleByCourse = connection.prepareStatement("delete from app.scheduleentry where semester = ? and coursecode = ?");
            
            
            dropScheduleByCourse.setString(1, semester);
            dropScheduleByCourse.setString(2, courseCode);
            dropScheduleByCourse.executeUpdate();
        }
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static void updateScheduleEntry(String semester, ScheduleEntry entry){
        connection = DBConnection.getConnection();
        String courseName = entry.getCourseCode();
        String id = entry.getStudentID();
        
        try
        {
            dropScheduleByCourse = connection.prepareStatement("UPDATE app.scheduleentry SET status = ? where semester = ? and studentid = ? and coursecode = ?");
            
            dropScheduleByCourse.setString(1, "S");
            dropScheduleByCourse.setString(2, semester);
            dropScheduleByCourse.setString(3, id);
            dropScheduleByCourse.setString(4, courseName);
            dropScheduleByCourse.executeUpdate();
        }
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
        
        
        
        
        
        /*
        String courseName = entry.getCourseCode();
        int seats = CourseQueries.getCourseSeats(semester, courseName);
        
        String id = entry.getStudentID();
        
        ArrayList<ScheduleEntry> ScheduledStudents = new ArrayList<ScheduleEntry>();
        ScheduledStudents = getScheduledStudentsByCourse(semester, courseName);
        int howManyScheduled = ScheduledStudents.size();
        
        ScheduledStudents = getWaitlistedStudentsByCourse(semester, courseName);
        if (seats > howManyScheduled){
            ScheduleEntry newEntry = ScheduledStudents.get(0);
            
            String newid = newEntry.getStudentID();
            ScheduleQueries.dropStudentScheduleByCourse(semester, newid, courseName);
            ScheduleQueries.addScheduleEntry(newEntry);
        }
        
        
    }delete this
    */

}