import java.util.Scanner;
import java.util.ArrayList;
    
class StudentMain {
	public static void main (String[] args) {        
		Scanner input = new Scanner(System.in);  // Create a Scanner object
            
		StudentManagement srms = new StudentManagement();
		int choice = -1;

		do {
			System.out.println("\nWelcome, Administrator!");
			System.out.println("What would you like to do?");
			System.out.println("1 - Add Student");
			System.out.println("2 - Update Student Info");
			System.out.println("3 - View Student Info");
			System.out.println("0 - Exit");
			System.out.print("\nEnter: ");
            
			choice = Integer.parseInt(input.nextLine());

			//Filters to make sure that program skips immediately after receiving 0 as input
			if (choice == 0) {
				break;
			}

			//Checks if the non-zero input is within the range of acceptable choice
			if(choice < 1 || choice > 3) {
				System.out.println("ERROR: Choice is outside range.");
				input.nextLine();
				continue;
			}
			srms = Main.menuAction(input, choice, srms);
		} while(choice != 0);
		
		System.out.println("\nFarewell!");
	
	}

    //dedicated function to perform tasks according to choice made by user/admin
    public static StudentManagement menuAction(Scanner input, int choice, StudentManagement srms) {
        String name, id = "", grade, course;
        int age;
        
        //Asking the user ID first as common info required
        System.out.print("Please enter the ID of the student: ");
        id = input.nextLine();
        
        //Segregation structure according to user's choice
        switch(choice) {
            case 1: if(srms.getTotal() != 0 && srms.getStudentDetails(id).getRecord(2).equals(id) == true) {    //Checks if there's already a record.
                        System.out.println("Record already exists.");
                        input.nextLine();
                        break;
                    }
                    System.out.print("Please enter the name\t: ");
                    name = input.nextLine();
                    System.out.print("Please enter the grade\t: ");
                    grade = input.nextLine();
                    System.out.print("Please enter the course\t: ");
                    course = input.nextLine();
                    System.out.print("Please enter the age\t: "); 
                    age = Integer.parseInt(input.nextLine());
                    
                    //Visual confirmation for successfully adding data. 
                    if(srms.addStudent(name, id, grade, course, age) == 1) {
                        System.out.println("Record added.");
                        input.nextLine();
                    }
                    break;
            case 2: try {   //Checks if there is a record to update or if it will return an exception
                        Student output = srms.getStudentDetails(id);
                        
                        if(output.getRecord(2).equals(id) == true) {
                            System.out.println("Retrieved data...");
                            System.out.println("Name\t: " + output.getRecord(1));
                            System.out.println("ID\t: " + output.getRecord(2));
                            System.out.println("Grade\t: " + output.getRecord(3));
                            System.out.println("Course\t: " + output.getRecord(4));
                            System.out.println("Age\t: " + output.getRecord());
                            
                            int type = -1;
                                    
                            do {
                                System.out.println("\n\nPlease choose which you would like to update...");
                                System.out.println("1 - Name");
                                System.out.println("3 - Grade");
                                System.out.println("4 - Course");
                                System.out.println("5 - Age");
                                System.out.println("0 - No changes");
                                System.out.print("\nEnter: ");
                                type = Integer.parseInt(input.nextLine());
                                
                                //Filters to make sure that program returns to the main menu immediately after receiving 0 as input
                                if(type == 0) {
                                    break;
                                }
                                
                                //Checks if the non-zero input is within the range of acceptable choice
                                if(type < 0 || type > 5) {
                                    System.out.println("ERROR: Choice is outside range.");
                                    input.nextLine();
                                    continue;
                                }
                                        
                                System.out.print("Please enter the new data: ");
                                String data = input.nextLine();
                                if(srms.updateStudent(id, type, data) == 1) {
                                    System.out.println("Updated to " + data);
                                }
                            
                            }while(type != 0);
                        }
                    }
                    catch(Exception e){     //Prompts if there is no record to update
                        System.out.println("\nERROR: Student not found.");
                        input.nextLine();
                        input.nextLine();
                    }
    
            case 3: try {   //Checks if there is a record to display or if it will return an exception
                        Student output = srms.getStudentDetails(id);

                        if(output.getRecord(2).equals(id) == true) {
                            System.out.println("\nRetrieved data...");
                            System.out.println("Name\t: " + output.getRecord(1));
                            System.out.println("ID\t: " + output.getRecord(2));
                            System.out.println("Grade\t: " + output.getRecord(3));
                            System.out.println("Course\t: " + output.getRecord(4));
                            System.out.println("Age\t: " + output.getRecord());
                        }
                    }
                    catch(Exception e){     //Prompts if there is no record to display
                        System.out.println("\nERROR: Student not found.");
                        input.nextLine();
                        input.nextLine();
                    }
                    break;
        }
            
        return srms;
    }
}
    
//Structure for Student information
class Student {
    private String name, id, grade, course;
    private int age;
        
    //public void Student() {}
        
        
    //Sets information for a specific class instance
    public void setRecord(String sName, String sId, String sGrade, String sCourse, int sAge) {
        name = sName;
        id = sId;
        grade = sGrade;
        course = sCourse;
        age = sAge;
    }
        
    //Retrieves String information for the class instance
    public String getRecord(int type) {
        //System.out.println("thank u");
        switch(type) {
            case 1: return name;
            case 2: return id;
            case 3: return grade;
            case 4: return course;
            default: return "404";
        }
    }
        
    //Retrieves age information for the class instance
    public int getRecord() {
        return age;
    }
        
    //Updates String information for the class instance
    public void updateRecord(int type, String data) {
        switch(type) {
            case 1: name = data;
                    break;
            case 3: grade = data;
                    break;
            case 4: course = data;
                    break;
            //default: return "404";
        }
    }
        
    //Updates age information for the class instance
    public void updateRecord(int age) {
        age = age;
    }
}
    
//Class to hold the student's information.
class StudentManagement {
    private static ArrayList<Student> students;
    private static int studentTotal;
        
    //Constructor to create the new student list and set the total to zero. 
    public StudentManagement() {
        students = new ArrayList<Student>();
        studentTotal = 0;
    }
        
    public static int getTotal() {
        return StudentManagement.studentTotal;
    }
        
        
    //Method to add a new student to the list
    public static int addStudent(String name, String id, String grade, String course, int age) {
            
        //Checks first if the list is empty before invoking the for-loop
        if(studentTotal == 0) {
			Student newStudent = new Student();
			newStudent.setRecord(name, id, grade, course, age);
			students.add(newStudent);
			studentTotal++;
            return 1;
		}
            
		//Searches for the record to see if there's already a student with the information 
		for(int i = 0; i < studentTotal; i++) {
			//Checks if the name of the current iteration does not match
			//to continue with the loop
			if(students.get(i).getRecord(2).equals(id) == false) {
				//Checks if it reached the end of the list before adding a new entry
				if(i == studentTotal - 1) {
					Student newStudent = new Student();
					newStudent.setRecord(name, id, grade, course, age);
					students.add(newStudent);
					studentTotal++;
					return 1;
				}
				continue;
            }
			break;
		}
		return 0;
	}
        
        
	//Method to update a student information in the list
	public static int updateStudent(String id, int num, String data) {
		int index;
		
		//Checks if the list is empty
		if(studentTotal == 0) {
			return 0;
		}
            
		//Searches the id throughout the list, and exits the loop once found
		for(index = 0; index < studentTotal; index++) {
			if (students.get(index).getRecord(2).equals(id) == false) {
				continue;
			}
                
                 //Conditional call based on the information being handled. Case 5 is for age since it has a different method due to data type.
                switch(num) {
                    case 5: students.get(index).updateRecord(Integer.valueOf(data));
                            break;
                    default:students.get(index).updateRecord(num, data);
                            break;
                }
                return 1;
            }
            
            return 0;
        }
        
        //Method to retrieve student information 
        public static Student getStudentDetails(String id) {
            int index;
            
            //Checks if the list is empty
            if(students.size() == 0) {
                return new Student();
            }
            
            //Searches the id throughout the list, and exits the loop once found
            for(index = 0; index < students.size(); index++) {
                if (students.get(index).getRecord(2).equals(id) == false) {
                    
                    if(index == students.size() - 1){
                        return new Student();
                    }
                    continue;
                }
                
                break;
            }
            
            return students.get(index);
        }
        
        
    }

