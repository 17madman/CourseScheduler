/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Admin
 */
public class StudentEntry {
    
    //Properties
    private String StudentID;
    private String FirstName;
    private String LastName;
    
    //Getter Methods
    public String getStudentID() {
        return StudentID;
    }
    public String getFirstName() {
        return FirstName;
    }
    public String getLastName() {    
        return LastName;
    }

    //Constructors
    public StudentEntry(String StudentID, String FirstName, String LastName) {
        this.StudentID = StudentID;
        this.FirstName = FirstName;
        this.LastName = LastName;
    }
    
    
    

}
