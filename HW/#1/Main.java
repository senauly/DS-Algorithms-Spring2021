import java.io.IOException;
import java.util.Scanner;
import java.util.*; 
import java.lang.*; 

public class Main{
    public void mainMenu(FurnitureCompany myCompany){
        Scanner input = new Scanner(System.in);
        int option = -1;

        System.out.println("1- Login as Admin\n2- Login as Customer\n3- Login as Branch Employee\n4- Register as Customer\n5- Exit\n\n");
        option = input.nextInt();
        if(option != 1 && option != 2 && option != 3 && option != 4){
            System.err.println("Invalid input");
            mainMenu(myCompany);
        }

        if(option == 4) System.exit(0); 
        
        System.out.print("Enter email: ");
        String email = input.next();
        System.out.print("Enter password: ");
        String pass = input.next();

        Boolean succesLogin = false;
        if(option == 1 && (succesLogin = myCompany.admin().login(email, pass))){
            adminMenu(myCompany.admin());
        }

        switch (option) {
            case 1:
                succesLogin = myCompany.admin().login(email, pass);
                if(succesLogin) adminMenu(myCompany.admin());
                else mainMenu(myCompany);
                break;
            case 2:
                Customer customer = null;
                if((customer = myCompany.findCustomer(email)) != null){
                    succesLogin = customer.login(email, pass);
                    if(succesLogin) customerMenu(customer);
                    else mainMenu(myCompany);
                }

                else{
                    System.err.println("No subscription found, please register.\n");
                    mainMenu(myCompany);
                }
                
                break;
            case 3:
                Employee employee = null;
                if((employee = myCompany.findEmployee(email)) != null){
                    succesLogin = employee.login(email, pass);
                    if(succesLogin) employeeMenu(employee);
                    else mainMenu(myCompany);
                }

                else{
                    System.err.println("There is no employee registered with this email.");
                    mainMenu(myCompany);
                }
                break;
            case 4:
                System.out.print("Enter name: ");
                String name = input.next();
                System.out.print("Enter surname: ");
                String surname = input.next();
                System.out.print("Enter email: ");
                email = input.next();
                System.out.print("Enter password: ");
                pass = input.next();

                Customer newCustomer = new Customer(name, surname, email, myCompany.getAllBranches().at(0));
                try{
                    newCustomer.register(pass);
                    System.out.println("Your subscription is succesfully done!\nCustomer number: " + newCustomer.getCustomerNo());
                    customerMenu(newCustomer);
                }
                catch(IOException e){
                    e.getMessage();
                }
                
                break;
            
                case 5:
                System.exit(0);
                break;
        
            default:
                System.err.println("Invalid input");
                mainMenu(myCompany);
                break;
        }
        
        input.close();
    }
    public void adminMenu(Administrator admin){
        Scanner input = new Scanner(System.in);
        String option = null;
        System.out.println("a) Add a New Branch\nb) Remove Branch\nc)Add Branch Employee\nd)Remove Branch Employee\ne)Query Needs\nf)Return Main Menu\n\n");
        option = input.next();
        
        switch (option) {
            case "a":
                System.out.println("Branch added.\n" + admin.addBranch().toString());
                adminMenu(admin);
                break;
            case "b":
                System.out.println("Which branch do you want to remove?\nEnter branch num ex:1");
                int branch = input.nextInt();
                try{
                    admin.removeBranch(branch);
                    System.out.println("Branch removed.\n");
                }

                catch(NoSuchElementException e){
                    e.getMessage();
                }
                adminMenu(admin);
                break;
            case "c":
                System.out.print("Enter employee name: ");
                String name = input.next();
                System.out.print("Enter employee surname: ");
                String surname = input.next();
                System.out.print("Enter employee's branch no: ");
                branch = input.nextInt();
                System.out.print("Enter employee email: ");
                String email = input.next();
                System.out.print("Enter employee password: ");
                String pass = input.next();
                Branch tempBr = null;
                if((tempBr = admin.getCompany().findBranch(branch)) == null){
                    System.out.println("Invalid branch no.");
                    adminMenu(admin);
                }
                Employee tempEmp = null;
                if((tempEmp = admin.addBranchEmployee(name, surname, tempBr,email,pass)) == null) System.out.println("Employee already exist.\n");
                else System.out.println("Employee added.\n" + tempEmp.toString());
                adminMenu(admin);
                break;
            case "d":
                System.out.print("Enter employee name: ");
                name = input.next();
                System.out.print("Enter employee surname: ");
                surname = input.next();
                System.out.print("Enter employee's branch no: ");
                branch = input.nextInt();
                System.out.print("Enter employee email: ");
                email = input.next();
                System.out.print("Enter employee password: ");
                pass = input.next();
                tempBr = null;
                if((tempBr = admin.getCompany().findBranch(branch)) == null){
                    System.out.println("Invalid branch no.");
                    adminMenu(admin);
                }
        
                try{
                    admin.removeBranchEmployee(name, surname, tempBr, email,pass);
                    System.out.println("Employee removed\n");
                }

                catch(NoSuchElementException e){
                    e.getMessage();
                }
                
                adminMenu(admin);
                break;
            case "e":
                admin.queryNeeds();
                System.out.println("\nProducts supplied and added to center storage.\n");
                adminMenu(admin);
                break;
            case "f":
                mainMenu(admin.getCompany());
                break;
            default:
                System.err.println("Invalid input");
                adminMenu(admin);
                break;
        }

        input.close();
    }
    public void customerMenu(Customer customer){
        Scanner input = new Scanner(System.in);
        String option = null;
        System.out.println("a) Search Product & See Which Branch it is in\nb) See All Products\nc) Shop Online\nd)View Previous Orders\ne)Return Main Menu\n\n");
        option = input.next();

        switch (option) {
            case "a":
                System.out.print("Enter the product type: ");
                String type = input.next();
                System.out.print("Enter the product color: ");
                String color = input.next();
                System.out.print("Enter the product model: ");
                String model = input.next();
                int found = customer.searchProduct(type, color, model);
                if(found == -1){
                    System.out.println("Product is out of stock.\n");
                    customerMenu(customer);
                }

                else{
                    System.out.println("Product is available at Branch " + found);
                }
                customerMenu(customer);
                break;
            case "b":
                customer.seeProducts();
                customerMenu(customer);
                break;
            case "c":
                System.out.println("What do you want to buy?\n");
                System.out.print("Enter the product type: ");
                type = input.next();
                System.out.print("Enter the product color: ");
                color = input.next();
                System.out.print("Enter the product model: ");
                model = input.next();
                System.out.print("Enter your adress: ");
                String adress = input.next();
                System.out.print("Enter your phone number: ");
                String phone = input.next();
                try{
                    customer.shopOnline(adress, phone,new Product(type, color, model));
                    System.out.println("You have succesfully buyed.");
                } 

                catch(NoSuchElementException e){
                    e.getMessage();
                    customerMenu(customer);
                }

                customerMenu(customer);
                break;
            case "d":
                customer.viewPreviousOrders();
                customerMenu(customer);
                break;
            case "e":
                mainMenu(customer.getBranch().getCompany());
                break;
            default:
                System.err.println("Invalid input");
                customerMenu(customer);
                break;
        }

        input.close();
    }

    public void employeeMenu(Employee employee){
        Scanner input = new Scanner(System.in);
        String option = null;
        System.out.println("a) Inquire Stock and Inform Manager\nb) Add Product\nc)Remove Product\nd)Make Sale and Update Theirs Orders\ne)Acces Customer Info\nf)Register Customer\ng)Return Main Menu\n\n");
        option = input.next();

        switch (option) {
            case "a":
                System.out.println("Which product do you want to check?\n");
                System.out.print("Enter the product type: ");
                String type = input.next();
                System.out.print("Enter the product color: ");
                String color = input.next();
                System.out.print("Enter the product model: ");
                String model = input.next();
                if(employee.inquireStock(new Product(type, color, model))) System.out.println("Product is available.\n");
                else System.out.println("Product is out of stock, manager is informed.\n");
                employeeMenu(employee);
                break;
            case "b":
                System.out.println("Which product do you want to add?\n");
                System.out.print("Enter the product type: ");
                type = input.next();
                System.out.print("Enter the product color: ");
                color = input.next();
                System.out.print("Enter the product model: ");
                model = input.next();
                
                try{
                    employee.addProduct(type, color, model);
                    System.out.println("Product added.\n");
                }

                catch(IOException e){
                    e.getMessage();
                }
                employeeMenu(employee);
                break;
            case "c":
                System.out.println("Which product do you want to remove?\n");

                System.out.print("Enter the product type: ");
                type = input.next();
                System.out.print("Enter the product color: ");
                color = input.next();
                System.out.print("Enter the product model: ");
                model = input.next();
                
                try{
                    employee.removeProduct(type, color, model);
                    System.out.println("Product removed.\n");
                }

                catch(NoSuchElementException e){
                    System.out.println("Product does not exist.\n");
                }
                employeeMenu(employee);
                break;
            
            case "d":
                System.out.print("Enter the product type: ");
                type = input.next();
                System.out.print("Enter the product color: ");
                color = input.next();
                System.out.print("Enter the product model: ");
                model = input.next();
                System.out.print("Enter customer email: ");
                String email = input.next();
                Employee tempEmp = null;
                if((tempEmp = employee.getBranch().findEmployee(email)) == null){
                    System.out.println("Please enter customer informations for registration\n");
                    System.out.print("Enter the name: ");
                    String name = input.next();
                    System.out.print("Enter the surname: ");
                    String surname = input.next();
                    System.out.print("Enter the password: ");
                    String password = input.next();
                    
                    try{ 
                        employee.makeSale(type, color, model,new Customer(name, surname, email, employee.getBranch()));
                        System.out.println("Sale is succesfully made.\n");
                    }

                    catch(IOException e){
                        e.getMessage();
                        employeeMenu(employee);
                    }

                    catch(NoSuchElementException e){
                        e.getMessage();
                        employeeMenu(employee);
                    }
                }

                else{
                    try{ 
                        employee.makeSale(type, color, model, employee.getBranch().findCustomer(email));
                        System.out.println("Sale is succesfully made.\n");
                    }

                    catch(IOException e){
                        e.getMessage();
                        employeeMenu(employee);
                    }

                    catch(NoSuchElementException e){
                        e.getMessage();
                        employeeMenu(employee);
                    }
                }
                employeeMenu(employee);
                break;
            case "e":
                System.out.println("Please enter customer number.\n");
                int customerNum = input.nextInt();
                try{
                    employee.accesCustomerInfo(customerNum);
                }

                catch(NoSuchElementException e){
                    e.getMessage();
                    employeeMenu(employee);
                }
                employeeMenu(employee);
                break;
            case "f":
                System.out.println("Please enter customer informations for registration\n");
                System.out.print("Enter the name: ");
                String name = input.next();
                System.out.print("Enter the surname: ");
                String surname = input.next();
                System.out.print("Enter the email: ");
                email = input.next();
                System.out.print("Enter the password: ");
                String password = input.next();

                try{
                    System.out.println("Customer succesfully registered.\n Customer No: " + employee.registerCustomer(name, surname, email, password).getCustomerNo());
                    employeeMenu(employee);
                }

                catch(IllegalArgumentException e){
                    e.getMessage();
                    employeeMenu(employee);
                }

                catch(IOException e){
                    e.getMessage();
                    employeeMenu(employee);
                }
                break;
            case "g":
                mainMenu(employee.getBranch().getCompany());
                break;
            default:
                System.err.println("Invalid input");
                employeeMenu(employee);
                break;
        }

        input.close();
    }
    
    public static void main(String[] args) {
        Main driver = new Main();
        final String password = "12345";
        Company myCompany = new Company("Bloom", "Sena", "Ulukaya");
        System.out.println("Company added.\n" + myCompany.toString());
        myCompany.admin().setEmail("senaulukaya@outlook.com");
        myCompany.admin().setPassword(password);
        Branch branch1 = myCompany.admin().addBranch();
        System.out.println("Branch added.\n" + branch1.toString());
        Branch branch2 = myCompany.admin().addBranch();
        System.out.println("Branch added.\n" + branch2.toString());
        Branch branch3 = myCompany.admin().addBranch();
        System.out.println("Branch added.\n" + branch3.toString());
        Branch branch4 = myCompany.admin().addBranch();
        System.out.println("Branch added.\n" + branch4.toString());
        
        Employee emp1 = myCompany.admin().addBranchEmployee("Melisa","Al",branch1,"melisaal@gmail.com",password);
        System.out.println("Employee added.\n" + emp1.toString());
        Employee emp2 = myCompany.admin().addBranchEmployee("Nesrin","Bal",branch2,"nesrinbal@gmail.com",password);
        System.out.println("Employee added.\n" + emp2.toString());
        Employee emp3 = myCompany.admin().addBranchEmployee("Eylül","Öztürk",branch3,"eyluloz@outlook.com",password);
        System.out.println("Employee added.\n" + emp3.toString());
        Employee emp4 = myCompany.admin().addBranchEmployee("Ali","Yol",branch4,"aliyol@outlook.com",password);
        System.out.println("Employee added.\n" + emp4.toString());
        
        try{
            System.out.println("\nProduct added to Branch " + emp1.getBranch().getBranchNum() + "\n" + emp1.addProduct("OfficeDesk", "Brown", "Model2").toString());
            System.out.println("Product added to Branch " + emp2.getBranch().getBranchNum() + "\n" + emp2.addProduct("OfficeChair", "Brown", "Model3").toString());
            System.out.println("Product added to Branch " + emp3.getBranch().getBranchNum() + "\n" + emp3.addProduct("MeetingTable", "Black", "Model1").toString());
            System.out.println("Product added to Branch " + emp4.getBranch().getBranchNum() + "\n" + emp4.addProduct("Bookcase", "Pink", "Model1").toString());

            Customer cust1 = emp1.registerCustomer("Ayşe", "Yılmaz", "ayseyilmaz@gmail.com", password);
            System.out.println(cust1.toString() + " is registered.\n");
            Customer cust2 = emp2.registerCustomer("Ali", "Veli", "aliveli@outlook.com",password);
            System.out.println(cust2.toString() + " is registered.\n");
            Customer cust3 = new Customer("Selin","Yıldız","syildiz@gtu.edu.tr",branch3);
            cust3.register(password);
            System.out.println(cust3.toString() + " is registered.\n");
            Customer cust4 = emp4.registerCustomer("Beyza", "Güven", "bguven@outlook.com",password);
            System.out.println(cust4.toString() + " is registered.\n");

        }

        catch(IOException exception){
            exception.getMessage();
        }
        
        System.out.println("\n\n -------- " + myCompany.getName() + " Furniture Company Automation System --------\n\n");
        driver.mainMenu(myCompany);
        
    }
}
