package com.generation.model;

import java.util.*;

public class Student extends Person implements Evaluation
{
    private double average;

    private final List<Course> courses = new ArrayList<>();
    private final Map<String, Integer> coursesGrade = new HashMap<>();

    private final Map<String, Course> approvedCourses = new HashMap<>();

    public Student(String id, String name, String email, Date birthDate)
    {
        super(id, name, email, birthDate);
    }

    public void enrollToCourse(Course course) {
        //TODO implement this method
        this.courses.add(course);
    }

    public void setGrade(String courseId, int grade)
    {
        coursesGrade.put(courseId, grade);
        System.out.println(coursesGrade);

    }

    public int getGrade(String courseId)
    {
        //for (String key : coursesGrade.keySet()) {
        if (coursesGrade.containsKey(courseId)){
            //if (key == courseId)
            return coursesGrade.get(courseId);
        }
        return 0;
    }

    public List getCourses()
    {
        return courses;
    }

    //key is course Id (.getCode) and value is course
    public void registerApprovedCourse(Course course)
    {
        approvedCourses.put(course.getCode(), course);
    }

    public boolean isCourseApproved(String courseCode) {
        //TODO implement this method
        return false;
    }


    public List<Course> findPassedCourses(Course course) {
        //TODO implement this method
        return null;
    }

    public boolean isAttendingCourse(String courseCode) {
        //TODO implement this method
        return false;
    }

    @Override
    public double getAverage() {
        return average;
    }

    @Override
    public List<Course> getApprovedCourses() {
        //TODO implement this method
        return null;
    }

    @Override
    public String toString() {
        return "Student {" + super.toString() + "}";

    }
}
