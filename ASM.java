import java.util.ArrayList;
import java.util.Scanner;

// Ngoại lệ cho điểm không hợp lệ
class InvalidMarksException extends Exception {
    public InvalidMarksException(String message) {
        super(message);
    }
}

// Ngoại lệ cho sinh viên không tồn tại
class StudentNotFoundException extends Exception {
    public StudentNotFoundException(String message) {
        super(message);
    }
}

class Student {
    private String id;
    private String name;
    private double marks;
    private String rank;

    public Student(String id, String name, double marks) throws InvalidMarksException {
        if (marks < 0 || marks > 10) {
            throw new InvalidMarksException("Attempted to set invalid marks (" + marks + ")");
        }
        this.id = id;
        this.name = name;
        this.marks = marks;
        this.rank = assignRank(marks);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMarks() {
        return marks;
    }

    public String getRank() {
        return rank;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMarks(double marks) throws InvalidMarksException {
        if (marks < 0 || marks > 10) {
            throw new InvalidMarksException("Attempted to set invalid marks (" + marks + ")");
        }
        this.marks = marks;
        this.rank = assignRank(marks); // Cập nhật lại rank khi điểm thay đổi
    }

    // Phương thức để tính rank dựa trên điểm
    private String assignRank(double marks) {
        if (marks < 5.0) return "Fail";
        if (marks < 6.5) return "Medium";
        if (marks < 7.5) return "Good";
        if (marks < 9.0) return "Very Good";
        return "Excellent";
    }

    public void display() {
        System.out.printf("ID: %s, Name: %s, Marks: %.2f, Rank: %s\n", id, name, marks, rank);
    }
}

public class ASM {
    private ArrayList<Student> students = new ArrayList<>();

    // Kiểm tra trùng mã sinh viên trước khi thêm
    private boolean isStudentIdExists(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return true; // Trùng mã sinh viên
            }
        }
        return false; // Không trùng mã sinh viên
    }

    public void addStudent(String id, String name, double marks) {
        if (isStudentIdExists(id)) {
            System.out.println("Error: Student ID already exists. Please enter a unique ID.");
        } else {
            try {
                students.add(new Student(id, name, marks));
                System.out.println("Student added successfully.");
            } catch (InvalidMarksException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void editStudent(String id, String newName, double newMarks) {
        try {
            boolean studentFound = false;
            for (Student student : students) {
                if (student.getId().equals(id)) {
                    student.setName(newName);
                    student.setMarks(newMarks);
                    System.out.println("Student updated successfully.");
                    studentFound = true;
                    break;
                }
            }
            if (!studentFound) {
                throw new StudentNotFoundException("Attempted to edit a non-existent student (ID " + id + ")");
            }
        } catch (StudentNotFoundException | InvalidMarksException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteStudent(String id) {
        try {
            boolean studentFound = false;
            for (Student student : students) {
                if (student.getId().equals(id)) {
                    students.remove(student);
                    System.out.println("Student deleted successfully.");
                    studentFound = true;
                    break;
                }
            }
            if (!studentFound) {
                throw new StudentNotFoundException("Attempted to delete a non-existent student (ID " + id + ")");
            }
        } catch (StudentNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void searchStudent(String id) {
        try {
            boolean studentFound = false;
            for (Student student : students) {
                if (student.getId().equals(id)) {
                    System.out.println("Student found:");
                    student.display();
                    studentFound = true;
                    break;
                }
            }
            if (!studentFound) {
                throw new StudentNotFoundException("Attempted to find a non-existent student (ID " + id + ")");
            }
        } catch (StudentNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("Student List:");
            for (Student student : students) {
                student.display();
            }
        }
    }

    // Sửa lại phương thức này để bắt ngoại lệ InvalidMarksException
    public void sortStudentsByMarks() {
        students.sort((s1, s2) -> Double.compare(s1.getMarks(), s2.getMarks()));
        System.out.println("Students sorted by marks successfully.");
        
        // Sau khi sắp xếp, cập nhật lại rank cho tất cả sinh viên
        try {
            for (Student student : students) {
                student.setMarks(student.getMarks()); // Cập nhật rank tự động
            }
        } catch (InvalidMarksException e) {
            System.out.println("Error while updating rank: " + e.getMessage());
        }
        displayStudents(); // Hiển thị danh sách sinh viên sau khi sắp xếp
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ASM manager = new ASM(); // Tạo đối tượng từ lớp ASM

        while (true) {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. Edit Student");
            System.out.println("3. Delete Student");
            System.out.println("4. Search Student");
            System.out.println("5. Display Students");
            System.out.println("6. Sort Students by Marks");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Marks: ");
                    double marks = scanner.nextDouble();
                    manager.addStudent(id, name, marks);
                    break;
                case 2:
                    System.out.print("Enter ID: ");
                    String editId = scanner.nextLine();
                    System.out.print("Enter new Name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new Marks: ");
                    double newMarks = scanner.nextDouble();
                    manager.editStudent(editId, newName, newMarks);
                    break;
                case 3:
                    System.out.print("Enter ID: ");
                    String deleteId = scanner.nextLine();
                    manager.deleteStudent(deleteId);
                    break;
                case 4:
                    System.out.print("Enter ID: ");
                    String searchId = scanner.nextLine();
                    manager.searchStudent(searchId);
                    break;
                case 5:
                    manager.displayStudents();
                    break;
                case 6:
                    manager.sortStudentsByMarks(); // Sắp xếp và hiển thị ngay lập tức
                    break;
                case 0:
                    System.out.println("Exiting program...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
