import java.util.Scanner;

class GradeViewer {
    int noOfStudents ;
    String[] StudentNames;
    int[] StudentGrades;


    public void StudentDetailsInput(){
        Scanner input = new Scanner(System.in);

        System.out.println("Enter the No of Students : ");
        noOfStudents = input.nextInt();

        StudentNames = new String[noOfStudents];
        StudentGrades = new int[noOfStudents];


        int i = 0, j = 0;
        while (i < noOfStudents){
            System.out.print("Enter the Student name  " +(i + 1) +" : " );
            StudentNames[i] = input.next();
            i++;
        }
        while (j < noOfStudents){
            System.out.print("Enter the Grade of the student  " + (j+1) + " : ");
            StudentGrades[j] = input.nextInt();
            j++;

        }


    }

    public void displayDetails(){
        int totalGrades = 0;
        int highestGrade = StudentGrades[0];
        int lowestGrade = StudentGrades[0];

        int i = 1 , j = 0;
        while(i < noOfStudents){
//            totalGrades += StudentGrades[i];
            if(highestGrade < StudentGrades[i]){
                highestGrade = StudentGrades[i];
            }
            i++;
        }
        while (j < noOfStudents){
            if(lowestGrade > StudentGrades[j]){
                lowestGrade = StudentGrades[j];
            }
            j++;
        }
        for(int k = 0 ;k < noOfStudents;k++){
            totalGrades += StudentGrades[k];

        }
        double avg = (double) totalGrades / noOfStudents;

//        System.out.println(totalGrades);


        System.out.println("<<< Student details >>>");
        System.out.printf("%-15s %-10s\n","Student Name "," Grades ");

        for (int k = 0; k < noOfStudents; k++){
            System.out.printf("%-15s  %-10d\n",StudentNames[k],StudentGrades[k] );
        }
        System.out.println();
        System.out.println();
        System.out.println("Highest grade of the Students : " + highestGrade);
        System.out.println("Lowest Grade of the Student : " + lowestGrade);
        System.out.println("Average : " + avg);

    }
    public static void main(String[] args){
        System.out.println("Welcome to the grade checker :>>>");
        GradeViewer std = new GradeViewer();
        std.StudentDetailsInput();
        std.displayDetails();
    }
}
