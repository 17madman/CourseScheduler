/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Admin
 */
public class CourseEntry {
    
    //Properties
    private String Semester;
    private String CourseCode;
    private String Description;
    private int Seats;

    //Getter Methods
    public String getSemester() {
        return Semester;
    }
    public String getCourseCode() {
        return CourseCode;
    }
    public String getDescription() {
        return Description;
    }
    public int getSeats() {
        return Seats;
    }

    //Constructors
    public CourseEntry(String Semester, String CourseCode, String Description, int Seats) {
        this.Semester = Semester;
        this.CourseCode = CourseCode;
        this.Description = Description;
        this.Seats = Seats;
    }
    
    
    

}
