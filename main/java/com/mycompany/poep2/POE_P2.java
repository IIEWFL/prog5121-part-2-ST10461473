/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.poep2;

import javax.swing.JOptionPane;

/**
 * POEP2 Application
 * 
 * @author moloto
 */
public class POEP2 {

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Welcome to EasyKanban");

        User user = login();

        if (user != null) {
            int choice;
            do {
                choice = showMenu();

                switch (choice) {
                    case 0 -> addTask(user);  // Adjusted index for options
                    case 1 -> showReport(user);
                    case 2 -> JOptionPane.showMessageDialog(null, "Exiting EasyKanban. Goodbye!");
                    default -> JOptionPane.showMessageDialog(null, "Invalid choice. Please try again.");
                }
            } while (choice != 2);
        }
    }

    public static User login() {
        String username = JOptionPane.showInputDialog("Enter your username:");
        String password = JOptionPane.showInputDialog("Enter your password:");

        if ("admin".equals(username) && "password".equals(password)) {
            return new User(username);
        } else {
            JOptionPane.showMessageDialog(null, "Login failed. Please try again.");
            return null;
        }
    }

    public static int showMenu() {
        String[] options = {"Add tasks", "Show report", "Quit"};
        return JOptionPane.showOptionDialog(null, "Choose an option:", "Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    }

    public static void addTask(User user) {
        int numberOfTasks = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of tasks to add:"));

        for (int i = 0; i < numberOfTasks; i++) {
            String taskName = JOptionPane.showInputDialog("Enter task name:");
            String taskDescription;
            do {
                taskDescription = JOptionPane.showInputDialog("Enter task description (max 50 characters):");
                if (!checkTaskDescription(taskDescription)) {
                    JOptionPane.showMessageDialog(null, "Please enter a task description of less than 50 characters.");
                }
            } while (!checkTaskDescription(taskDescription));

            String developerDetails = JOptionPane.showInputDialog("Enter developer details (first name & last name):");
            double taskDuration = Double.parseDouble(JOptionPane.showInputDialog("Enter task duration in hours:"));
            String taskID = createTaskID(taskName, developerDetails, i);

            String[] statusOptions = {"To Do", "Doing", "Done"};
            int statusChoice = JOptionPane.showOptionDialog(null, "Choose task status:", "Task Status", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, statusOptions, statusOptions[0]);
            String taskStatus = statusOptions[statusChoice];

            // Display task details
            String taskDetails = printTaskDetails(taskStatus, developerDetails, i, taskName, taskDescription, taskID, taskDuration);
            JOptionPane.showMessageDialog(null, taskDetails);

            user.incrementTaskCount();
            user.addTaskDuration(taskDuration);
        }

        JOptionPane.showMessageDialog(null, "Total number of tasks: " + user.getTaskCount() + "\nTotal hours: " + user.getTotalHours());
    }

    public static boolean checkTaskDescription(String description) {
        return description.length() <= 50;
    }

    public static String createTaskID(String taskName, String developerName, int taskNumber) {
        return taskName.substring(0, 2).toUpperCase() + ":" + taskNumber + ":" + developerName.substring(developerName.length() - 3).toUpperCase();
    }

    public static String printTaskDetails(String taskStatus, String developerDetails, int taskNumber, String taskName, String taskDescription, String taskID, double taskDuration) {
        return "Task Status: " + taskStatus +
               "\nDeveloper Details: " + developerDetails +
               "\nTask Number: " + taskNumber +
               "\nTask Name: " + taskName +
               "\nTask Description: " + taskDescription +
               "\nTask ID: " + taskID +
               "\nTask Duration: " + taskDuration + " hours";
    }

    public static void showReport(User user) {
        JOptionPane.showMessageDialog(null, "Total number of tasks: " + user.getTaskCount() + "\nTotal hours: " + user.getTotalHours());
    }
}

class User {
    private String name;
    private int taskCount;
    private double totalHours;

    public User(String name) {
        this.name = name;
        this.taskCount = 0;
        this.totalHours = 0.0;
    }

    public String getName() {
        return name;
    }

    public void incrementTaskCount() {
        taskCount++;
    }

    public int getTaskCount() {
        return taskCount;
    }

    public void addTaskDuration(double hours) {
        totalHours += hours;
    }

    public double getTotalHours() {
        return totalHours;
    }
}
