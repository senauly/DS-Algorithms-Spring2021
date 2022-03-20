import java.io.IOException;
import java.util.Scanner;
import java.util.*; 
import java.lang.*; 

public class Main{
    public static void mainMenu(FurnitureCompany myCompany){
        Scanner input = new Scanner(System.in);
        int option = -1;

        System.out.println("1- Login as Admin\n2- Login as Customer\n3- Login as Branch Employee\n4- Register as Customer\n5- Exit\n\n");
        option = input.nextInt();
        if(option != 1 && option != 2 && option != 3 && option != 4 && option != 5){
            System.err.println("Invalid input");
            mainMenu(myCompany);
        }

        String email = null;
        String pass = null;
        Boolean succesLogin = false;

        if(option != 4 && option != 5){
            System.out.print("Enter email: ");
            email = input.next();
            System.out.print("Enter password: ");
            pass = input.next();
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

                Customer newCustomer = new Customer(name, surname, email, myCompany.getAllBranches().getFirst());
                    if(newCustomer.register(email, pass)){
                        System.out.println("Your subscription is succesfully done!\nCustomer number: " + newCustomer.getCustomerNo());
                        customerMenu(newCustomer);
                    }

                    else System.err.println("E-mail has been used before. Please login.");
                
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
    public static void adminMenu(Administrator admin){
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
                if(admin.removeBranch(branch)) System.out.println("Branch removed.\n");
                else System.err.println("There is no such branch with no: " + branch);
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
                tempBr = admin.getCompany().findBranch(branch);
                
                if(tempBr == null){
                    System.out.println("Invalid branch no.");
                    adminMenu(admin);
                }
                if(admin.removeBranchEmployee(name, surname, tempBr, email,pass)) System.out.println("Employee removed\n");
                else System.err.println("There is no such employee.");
                adminMenu(admin);
                break;
            case "e":
                if(admin.queryNeeds()) System.out.println("\nProducts supplied and added to center storage.\n");
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
    public static void customerMenu(Customer customer){
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
                if(customer.shopOnline(adress, phone,new Product(type, color, model))) System.out.println("You have succesfully buyed.");
                else System.err.println("Product is out of stock.\n");
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

    public static void employeeMenu(Employee employee){
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
                employee.addProduct(type, color, model);
                System.out.println("Product added.\n");
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
                if(employee.removeProduct(type, color, model)) System.out.println("Product removed.\n");
                else System.out.println("Product does not exist.\n");
            
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
                if(employee.getBranch().findCustomer(email) == null){
                    System.out.println("Please enter customer informations for registration\n");
                    System.out.print("Enter the name: ");
                    String name = input.next();
                    System.out.print("Enter the surname: ");
                    String surname = input.next();
                    System.out.print("Enter the password: ");
                    String password = input.next();
                    
                    try{ 
                        Customer newCustomer = new Customer(name, surname, email, employee.getBranch());
                        newCustomer.setPassword(password);
                        employee.makeSale(type, color, model,newCustomer);
                        System.out.println("Sale is succesfully made.\n");
                    }

                    catch(NoSuchElementException e){
                        System.err.println("Product is out of stock. Manager informed.\n");
                        employeeMenu(employee);
                    }
                }

                else{
                    try{ 
                        employee.makeSale(type, color, model, employee.getBranch().findCustomer(email));
                        System.out.println("Sale is succesfully made.\n");
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
                    System.out.println("There is no such customer with that customer number.\n");
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
                    System.out.println("Customer is already registered.\n");
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

    public static void driverCode(Company myCompany){
        final String password = "12345";
        System.out.println("\n\n\n DRIVER CODE \n\n\n");
        System.out.println("------ Admin Methods ------ ");
        System.out.println("Add branch: ");
        for (int i = 0; i < 10; i++) {
            System.out.println("Branch added.\n" + myCompany.admin().addBranch().toString());
        }

        System.out.println("\nRemove branch: ");
        System.out.println("Removing branch no 12 is \n" + myCompany.admin().removeBranch(12));
        System.out.println("Removing branch no 8 is \n" + myCompany.admin().removeBranch(8));
        System.out.println("\nAfter removing: ");
        for (int i = 0; i < myCompany.getAllBranches().size(); i++) {
            System.out.println(myCompany.getAllBranches().get(i).toString());
        }

        System.out.println("\nAdd Branch Employee:");
        Employee temp = null;

        for (int i = 0; i < 2; i++) {
            temp = null;
            System.out.println("Trying to add Melisa Al.");
            if((temp = myCompany.admin().addBranchEmployee("Melisa","Al",myCompany.getAllBranches().get(0),"melisaal@gmail.com",password)) != null){
                System.out.println("Branch employe " + temp.toString() + " is added to branch " + myCompany.getAllBranches().get(0).getBranchNum());
            }
            else System.out.println("Employee already exist.");
        }

        Employee emp1 = myCompany.admin().addBranchEmployee("Eylül","Öztürk",myCompany.getAllBranches().get(0),"eyluloz@outlook.com",password);
        System.out.println("Branch employee " +emp1.toString()  + "is added to the branch 1.\n");

        System.out.println("\nRemove Branch Employee:");
        for (int i = 0; i < 2; i++) {
            temp = null;
            System.out.println("Trying to remove Melisa Al.");
            if(myCompany.admin().removeBranchEmployee("Melisa","Al",myCompany.getAllBranches().get(0),"melisaal@gmail.com",password)){
                System.out.println("Melisa Al is removed.");
            }
            else System.out.println("Employee does not exist.");
        }

        System.out.println("\nQuery Needs:");
        myCompany.admin().queryNeeds();

        System.out.println("\n\n------ Employee Methods ------ \n\n");
        System.out.println("Inquire Stock and Inform Manager: ");
        Product tempPr = new Product("Bookcase", "Blue", "Model4");
        if(emp1.inquireStock(tempPr)) System.out.println(tempPr.toString() + " is in stocks.");
        else System.out.println(tempPr.toString() + " is out of stock, admin informed.\n");
        System.out.println("\nChecking if admin is informed by calling Query needs...");
        if(myCompany.admin().queryNeeds()) System.out.println("should be supplied.");

        System.out.println("\nAdding product: ");
        for (int i = 0; i < 20; i++){
            System.out.println(emp1.addProduct("Bookcase", "Blue", "Model4").toString() + "is added.");
        }
        System.out.println("Checking for blue bookcase again.");
        if(emp1.inquireStock(tempPr)) System.out.println(tempPr.toString() + " is in stocks.");
        else System.out.println(tempPr.toString() + " is out of stock, admin informed.");

        System.out.println("\nRemoving product: ");
        System.out.println("Purple Office desk in model2 removed: " + emp1.removeProduct("OfficeDesk", "Purple", "Model2"));
        System.out.println("Blue bookcase in model4 removed: " + emp1.removeProduct("Bookcase", "Blue", "Model4"));
        System.out.println();
        
        Customer cust = null;
        for (int i = 0; i < 2; i++) {
            cust = null;
            System.out.println("Registering customer Ayşe Bal\n");
            try{
                cust = emp1.registerCustomer("Ayşe", "Bal", "aysebal@gtu.com", password);
                System.out.println("Registered. Here's the customer info: " + cust.toString());
            }
            catch(IllegalArgumentException e){
                System.err.println("There's a account with the same email, registering did not complete.");
            }
            
        }

        System.out.println("\n\nMake sale: \n");
        System.out.println("Try to sale Blue Bookcase in Model4...\n");
        Customer newCust = null;
        try{
            newCust = new Customer("Ali", "Rüzgar", "ali@gtu.com",emp1.getBranch());
            newCust.setPassword(password);
            emp1.makeSale("Bookcase", "Blue", "Model4",newCust);
            System.out.print("Blue Bookcase in Model4 is sold to Ali Rüzgar. Here's Ali's previous orders: ");
            newCust.viewPreviousOrders();

        }

        catch(NoSuchElementException e){
            System.out.println("Sale is unsuccesfull. Product is out of stock.");
        }

        System.out.println("\nTry to sale Yellow Bookcase in Model4.");
        try{
            newCust.setPassword(password);
            emp1.makeSale("Bookcase", "Yellow", "Model4",newCust);
            System.out.print("Yellow Bookcase in Model4 is sold to Ali Rüzgar. Here's Ali's previous orders: ");
            newCust.viewPreviousOrders();

        }

        catch(NoSuchElementException e){
            System.out.println("Sale is unsuccesfull. Product is out of stock.");
        }

        System.out.println("Admin is suppliying the yellow bookcase in model4...");
        myCompany.admin().supplyProduct(new Product("Bookcase", "Yellow", "Model4"), 5);
        System.out.println("\nTry to sale Yellow Bookcase in Model4.");
        try{
            newCust.setPassword(password);
            emp1.makeSale("Bookcase", "Yellow", "Model4",newCust);
            System.out.print("Yellow Bookcase in Model4 is sold to Ali Rüzgar. Here's Ali's previous orders: ");
            newCust.viewPreviousOrders();

        }
        catch(NoSuchElementException e){
            System.out.println("Sale is unsuccesfull. Product is out of stock.");
        }

        System.out.println("Access customer info: \n");
        System.out.println("Trying to access customer info of 101001...");
        try{
            emp1.accesCustomerInfo(emp1.getBranch().getAllCustomers().get(0).getCustomerNo());
        }

        catch(NoSuchElementException e){
            System.err.println("There is no such customer with that custumer no.\n");
        }

        System.out.println("\nTrying to access customer info of customer 1010101010...");
        try{
            emp1.accesCustomerInfo(1010101010);
        }

        catch(NoSuchElementException e){
            System.err.println("There is no such customer with that custumer no.\n");
        }

        
        System.out.println("\n\n------ Customer Methods ------ \n\n");
        System.out.println("See All Products: ");
        newCust.seeProducts();

        System.out.println("Employee adding new products...\nSee All Products: ");
        for (int i = 0; i < 10; i++) {
            emp1.addProduct("OfficeChair", "Brown", "Model1");
        }

        newCust.seeProducts();

        System.out.println("Search Product: ");
        System.out.println("Searching Brown Office Chair in Model1...");
        int br = -1;
        if((br = newCust.searchProduct("OfficeChair", "Brown", "Model1")) != -1) System.out.println("Product is in the branch no " + br);
        else System.out.println("Product is out of stock");
        
        System.out.println("Searching Black Office Chair in Model1...");
        br = -1;
        if((br = newCust.searchProduct("OfficeChair", "Black", "Model1")) != -1) System.out.println("Product is in the branch no " + br);
        else System.out.println("Product is out of stock");

        System.out.println("\nOnlineShop: ");
        System.out.println("Buying Brown Office Chair in Model1...");
        if(newCust.shopOnline("İstanbul", "05678900987", new Product("OfficeChair", "Brown", "Model1"))){
            System.out.println("Successfully buyed. Here's previous orders:");
            newCust.viewPreviousOrders();
        }
        else{
            System.out.println("Product is out of stock.");
        }

        System.out.println("Buying Black Office Chair in Model1...");
        if(newCust.shopOnline("İstanbul", "05678900987", new Product("OfficeChair", "Black", "Model1"))){
            System.out.println("Successfully buyed. Here's previous orders:");
            newCust.viewPreviousOrders();
        }
        else{
            System.out.println("Product is out of stock.");
        }
    
    }
    
    public static void main(String[] args){
        System.out.println("\n1- Try the Methods for me\n2- I will try them with menu.\n\n");
        Scanner input = new Scanner(System.in);
        int option = input.nextInt();

        final String password = "12345";
        Company myCompany = new Company("Bloom", "Sena", "Ulukaya");
        System.out.println("Company added.\n" + myCompany.toString());
        myCompany.admin().setEmail("senaulukaya@outlook.com");
        myCompany.admin().setPassword(password);

        if(option == 1) driverCode(myCompany);
        if(option == 2){
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
                cust3.register("syildiz@gtu.edu.tr",password);
                System.out.println(cust3.toString() + " is registered.\n");
                Customer cust4 = emp4.registerCustomer("Beyza", "Güven", "bguven@outlook.com",password);
                System.out.println(cust4.toString() + " is registered.\n");

            }

            catch(IllegalArgumentException exception){
                exception.getMessage();
            }
            
            System.out.println("\n\n -------- " + myCompany.getName() + " Furniture Company Automation System --------\n\n");
            mainMenu(myCompany);
        }
        
    }
}
