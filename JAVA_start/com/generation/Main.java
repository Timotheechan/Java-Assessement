package com.generation;

import com.generation.model.*;
import com.generation.service.CourseService;
import com.generation.service.StudentService;
import com.generation.utils.PrinterHelper;

import java.sql.SQLOutput;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main
{

    public static void main( String[] args )
        throws ParseException
    {
        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService();
        Scanner scanner = new Scanner( System.in );
        int option = 0;
        do
        {
            PrinterHelper.showMainMenu();
            option = scanner.nextInt();
            switch ( option )
            {
                case 1:
                    registerStudent( studentService, scanner );
                    break;
                case 2:
                    findStudent( studentService, scanner );
                    break;
                case 3:
                    gradeStudent( studentService, scanner );
                    break;
                case 4:
                    enrollStudentToCourse( studentService, courseService, scanner );
                    break;
                case 5:
                    showStudentsSummary( studentService, scanner );
                    break;
                case 6:
                    showCoursesSummary( courseService, scanner );
                    break;

                case 7:
                    showPassCourses( studentService, scanner );
                    break;

                case 8:
                    ShowAverageGrade( courseService, studentService, scanner );
                    break;
            }
        }
        while ( option != 9 );
    }

    private static void enrollStudentToCourse( StudentService studentService, CourseService courseService,
                                               Scanner scanner )
    {
        System.out.println( "Insert student ID" );
        String studentId = scanner.next();
        Student student = studentService.findStudent( studentId );
        if ( student == null )
        {
            System.out.println( "Invalid Student ID" );
            return;
        }
        System.out.println( student );
        System.out.println( "Insert course ID" );
        String courseId = scanner.next();
        Course course = courseService.getCourse( courseId );
        if ( course == null )
        {
            System.out.println( "Invalid Course ID" );
            return;
        }
        System.out.println( course );
        courseService.enrollStudent( courseId, student );
        studentService.enrollToCourse( studentId, course );
        System.out.println( "Student with ID: " + studentId + " enrolled successfully to " + courseId );

    }

    private static void showCoursesSummary( CourseService courseService, Scanner scanner )
    {
        courseService.showSummary();
    }

    private static void showStudentsSummary( StudentService studentService, Scanner scanner )
    {
        studentService.showSummary();
    }

    private static void gradeStudent( StudentService studentService, Scanner scanner )
    {
        List <Course> studentCourse;
        System.out.println( "Enter student ID: " );
        String studentId = scanner.next();

        System.out.println("Enrolled course:");
        studentCourse = studentService.findStudent(studentId).getCourses();

        for (int i = 0; i<studentCourse.size(); i++) {
            System.out.println(studentCourse.get(i));
        }

        System.out.println("Insert course grade to be graded" );
        String courseToGrade = scanner.next();
        int index =0;
        for (int i = 0; i<studentCourse.size(); i++) {

            if(studentCourse.get(i).getCode().equals(courseToGrade)){
                index = i;
                System.out.println("Insert this course grade for: " + studentCourse.get(i).getName());
                break;
            }
        }

        int grade = scanner.nextInt();
        if (grade < 0 || grade > 6){
            System.out.println("Please enter a valid grade of 0 - 6");
        }
        else {
            studentService.findStudent(studentId).setGrade(courseToGrade, grade);
        }


    }
    private static void showPassCourses ( StudentService studentService, Scanner scanner ) {
        List<Course> studentCourse;
        System.out.println("Enter student ID: ");
        String studentId = scanner.next();

        studentCourse = studentService.findStudent(studentId).getCourses();

        for (int i = 0; i < studentCourse.size(); i++) {
            if (studentService.findStudent(studentId).getGrade(studentCourse.get(i).getCode())>= 3) {
                System.out.println(studentCourse.get(i));
            }

        }


    }

    private static void ShowAverageGrade( CourseService courseService, StudentService studentService, Scanner scanner )
    {
        //find those courses with students inside
         Map<String, List<Student>> allEnrolledStudents = courseService.getAllEnrolledStudents();
        System.out.println(allEnrolledStudents);
        //find how many student are inside that course

        for ( String courseId : allEnrolledStudents.keySet() )
        {
            double sum =0;
            List<Student> studentThatHasGrade = allEnrolledStudents.get( courseId );
             int studentCount = studentThatHasGrade.size();

            for (int i =0; i<studentThatHasGrade.size(); i++){
                sum += studentThatHasGrade.get(i).getGrade(courseId);
            }
            double averageGrade = sum / studentCount;
            System.out.println("Course " + courseId + " :");
            System.out.println( "Average Grade : " + averageGrade );
        }
    }

    private static void findStudent( StudentService studentService, Scanner scanner )
    {
        System.out.println( "Enter Course ID: " );
        String studentId = scanner.next();
        Student student = studentService.findStudent( studentId );
        if ( student != null )
        {
            System.out.println( "Student Found: " );
            System.out.println( student );
        }
        else
        {
            System.out.println( "Student with Id = " + studentId + " not found" );
        }
    }

    private static void registerStudent( StudentService studentService, Scanner scanner )
        throws ParseException
    {
        Student student = PrinterHelper.createStudentMenu( scanner );
        studentService.subscribeStudent( student );
    }
}
