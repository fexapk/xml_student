package com.cesu.xml_students;

import java.util.Scanner;

import com.cesu.xml_students.data_acess.*;
import com.cesu.xml_students.pojo.*;

import java.util.List;

/**
 * Hello world!
 */
public class App 
{
    public static void main( String[] args ) {

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("File to be read (./example.xml): ");
        System.out.print("> ");
        final String FILE_PATH = scanner.next();
        
        DomReader reader = new DomReader(FILE_PATH);
        boolean read = reader.read();

        if (!read)
            System.exit(1);  
        AlumnoDao dao = new AlumnoDao(reader.getDocument());

        while (running) {
            System.out.print(MENU);
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character
            switch (choice) {
                case 1:
                    // Read student data from file
                    List<Alumno> data = dao.getAll();
                    data.forEach(d -> printYellow(d.toString()));
                    break;
                case 2:
                    // Add a new student
                    System.out.println("-----ADD-STUDENT------");
                    dao.save(getNewAlumno(scanner));
                    break;
                case 3:
                    // Delete a student
                    System.out.println("ID to be deleted: ");
                    int rmId = scanner.nextInt();
                    dao.delete(rmId);
                    break;
                case 4:
                    // Update student information
                    System.out.println("update Id:");
                    int updateId = scanner.nextInt();
                    printYellow(String.valueOf(dao.get(updateId)));
                    dao.update(updateId, getNewAlumno(scanner));
                    break;
                case 5:
                    // Display student information
                    System.out.println("Id:");
                    int id = scanner.nextInt();
                    Alumno alumno = dao.get(id);
                    if (alumno == null)
                        printYellow("No studetn with id " + id);
                    else 
                        printYellow(alumno.toString());
                    break;
                case 6:
                    DomWriter writer = new DomWriter(FILE_PATH, reader.getDocument());
                    if (writer.save())
                        printYellow("File saved succesfully!");
                    else 
                        printYellow("Something went wrong");
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
        + "1. Print student data from file\n"
        + "2. Add a new student\n"
        + "3. Delete a student\n"
        + "4. Update student information\n"
        + "5. Display student information\n"
        + "6. Save changes to file\n"
        + "0. Exit program\n"
        + "--------------------------------------\n"
        + "Enter the number of the option you want: ";

    private static Alumno getNewAlumno(Scanner scanner) {
        System.out.print("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Enter nombre: ");
        String nombre = scanner.next();
        
        System.out.print("Enter apellido: ");
        String apellido = scanner.next();
        
        System.out.print("Enter grado: ");
        String grado = scanner.next();
        
        System.out.print("Enter fechaFin(yyyymmdd): ");
        String fechaFin = scanner.next();
        
        return new Alumno(id, nombre, apellido, grado, fechaFin);
    }

    public static void printYellow(String message) {
        System.out.print("\u001B[33m"); // ANSI escape code for yellow color
        System.out.print(message);
        System.out.print("\u001B[0m\n"); // ANSI escape code to reset color
    }
    
   
}
