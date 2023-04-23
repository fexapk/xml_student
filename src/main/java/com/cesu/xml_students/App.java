package com.cesu.xml_students;

import java.util.Scanner;

import com.cesu.xml_students.data_acess.*;
import com.cesu.xml_students.pojo.*;

/**
 * Hello world!
 */
public class App 
{
    public static void main( String[] args ) {

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character
            switch (choice) {
                case 1:
                    // Read student data from file
                    DomReader reader = new DomReader("..\\data_test\\alumnos.xml");
                    AlumnoDao dao = null;
                    if (reader.read()) {
                        System.out.println("Succesfully read file!");
                        dao = new AlumnoDao(reader.getDocument());
                    }
                    else
                        System.out.println("Something went wrong");
                        running = false;
                    break;
                case 2:
                    // Add a new student
                    
                    break;
                case 3:
                    // Delete a student
                    break;
                case 4:
                    // Update student information
                    break;
                case 5:
                    // Display student information
                    break;
                case 6:
                    // Save changes to file
                    break;
                case 0:
                    // Exit program
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        }
        scanner.close();
    }

    private static final String MENU = 
        "--------------------------------------\n"
        + "STUDENT ADMINISTRATION PROGRAM MENU\n"
        + "--------------------------------------\n"
        + "1. Read student data from file\n"
        + "2. Add a new student\n"
        + "3. Delete a student\n"
        + "4. Update student information\n"
        + "5. Display student information\n"
        + "6. Save changes to file\n"
        + "0. Exit program\n"
        + "--------------------------------------\n"
        + "Enter the number of the option you want: ";
        
}
