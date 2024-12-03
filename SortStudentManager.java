import java.util.ArrayList;
import java.util.Scanner;

class Student {
    private String id;
    private String name;
    private double marks;
    private String rank;

    public Student(String id, String name, double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
        this.rank = assignRank(marks);
    }

    private String assignRank(double marks) {
        if (marks < 5.0) return "Fail";
        if (marks < 6.5) return "Medium";
        if (marks < 7.5) return "Good";
        if (marks < 9.0) return "Very Good";
        return "Excellent";
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getMarks() { return marks; }
    public String getRank() { return rank; }

    public void setName(String name) { this.name = name; }
    public void setMarks(double marks) { 
        this.marks = marks; 
        this.rank = assignRank(marks); 
    }

    public void display() {
        System.out.printf("ID: %s, Name: %s, Marks: %.2f, Rank: %s\n", id, name, marks, rank);
    }
}

public class SortStudentManager {
    private ArrayList<Student> students = new ArrayList<>();
    private boolean useBubbleSort = false; // Default algorithm is Quick Sort

    // Thêm sinh viên
    public void addStudent(String id, String name, double marks) {
        students.add(new Student(id, name, marks));
        System.out.println("Student added successfully.");
    }

    // Sửa sinh viên
    public void editStudent(String id, String newName, double newMarks) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                student.setName(newName);
                student.setMarks(newMarks);
                System.out.println("Student updated successfully.");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    // Xóa sinh viên
    public void deleteStudent(String id) {
        students.removeIf(student -> student.getId().equals(id));
        System.out.println("Student deleted successfully.");
    }

    // Tìm kiếm sinh viên
    public void searchStudent(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                System.out.println("Student found:");
                student.display();
                return;
            }
        }
        System.out.println("Student not found.");
    }

    // Quick Sort Implementation
    private void quickSort(int low, int high, boolean descending) {
        if (low < high) {
            int pi = partition(low, high, descending);
            quickSort(low, pi - 1, descending);
            quickSort(pi + 1, high, descending);
        }
    }

    private int partition(int low, int high, boolean descending) {
        double pivot = students.get(high).getMarks();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if ((descending && students.get(j).getMarks() > pivot) || 
                (!descending && students.get(j).getMarks() < pivot)) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, high);
        return i + 1;
    }

    // Bubble Sort Implementation
    private void bubbleSort(boolean descending) {
        int n = students.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if ((descending && students.get(j).getMarks() < students.get(j + 1).getMarks()) || 
                    (!descending && students.get(j).getMarks() > students.get(j + 1).getMarks())) {
                    swap(j, j + 1);
                }
            }
        }
    }

    private void swap(int i, int j) {
        Student temp = students.get(i);
        students.set(i, students.get(j));
        students.set(j, temp);
    }

    public void sortStudents(boolean descending) {
        if (useBubbleSort) {
            System.out.println("Sorting using Bubble Sort...");
            bubbleSort(descending);
        } else {
            System.out.println("Sorting using Quick Sort...");
            quickSort(0, students.size() - 1, descending);
        }
        System.out.println("Students sorted successfully.");
    }

    public void switchToBubbleSort() {
        useBubbleSort = true;
        System.out.println("Switched to Bubble Sort.");
    }

    public void switchToQuickSort() {
        useBubbleSort = false;
        System.out.println("Switched to Quick Sort.");
    }

    public void displayStudents() {
        for (Student student : students) {
            student.display();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SortStudentManager manager = new SortStudentManager();

        while (true) {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. Edit Student");
            System.out.println("3. Delete Student");
            System.out.println("4. Search Student");
            System.out.println("5. Sort Students by Marks");
            System.out.println("6. Display All Students");
            System.out.println("7. Exit");
            System.out.println("8. Switch Sorting Algorithm");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

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
                    System.out.println("Sort by Marks:");
                    System.out.print("1. Ascending Order\n2. Descending Order\nEnter choice: ");
                    int sortChoice = scanner.nextInt();
                    boolean descending = (sortChoice == 2);
                    manager.sortStudents(descending);
                    break;
                case 6:
                    manager.displayStudents();
                    break;
                case 7:
                    System.out.println("Exiting...");
                    return;
                case 8:
                    System.out.println("1. Use Bubble Sort\n2. Use Quick Sort");
                    int algoChoice = scanner.nextInt();
                    if (algoChoice == 1) manager.switchToBubbleSort();
                    else manager.switchToQuickSort();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
