import java.util.*;

class MainOperation {
    public static void main(String[] args) {

        Applicatioon app = new Applicatioon();
        Scanner sc = new Scanner(System.in);

        while(true){

            System.out.println("\n===== MENU =====");
            System.out.println("1. Create Table");
            System.out.println("2. Insert");
            System.out.println("3. Update");
            System.out.println("4. Delete");
            System.out.println("5. Display");
            System.out.println("6. Import from CSV");
            System.out.println("7. Export to CSV");
            System.out.println("8. Modify Table");   
            System.out.println("9. Exit");          
            System.out.println("Enter your choice:");

            int choice = sc.nextInt();
            sc.nextLine();  

            switch(choice){

                case 1:
                    app.create();
                    break;

                case 2:
                    app.insert();
                    break;

                case 3:
                    app.Update();
                    break;

                case 4:
                    app.Delete();
                    break;

                case 5:
                    app.display();
                    break;

                case 6:
                    app.importfromCSV();
                    break;

                case 7:
                    app.exportToCSV();
                    break;

                case 8:
                    app.modifyTable();  
                    break;

                case 9:
                    System.out.println("Exiting...");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
