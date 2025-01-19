import com.rbi.credit.management.*;

import java.util.ArrayList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

public class Main {

    public static void adminInteraction(){
        while(true){
            System.out.println("Select the Bank : ");
            System.out.println("1.ABC");
            System.out.println("2.XYZ");
            System.out.println("Press any key to go back to the previous menu");
            Scanner scanner = new Scanner(System.in);
            int answer = scanner.nextInt();
            Bank result = switch (answer) {
                case 1 -> {
                    yield abcBank;
                }
                case 2 -> {
                    yield xyzBank;
                }
                default -> {
                    yield null;
                }
            };
            if(result == null){
                break;
            }else{
                while(true){
                    System.out.println("Select the option : ");
                    System.out.println("1.Login as admin");
                    System.out.println("2.Add admin");
                    System.out.println("3.List Admins");
                    System.out.println("Press any key to go back to the previous menu");
                    int adminAnswer = scanner.nextInt();
                    int adminResult = switch (adminAnswer){
                        case 1 -> {
                            System.out.println("Enter the name: ");
                            String name = scanner.next();
                            System.out.println("Enter the password: ");
                            int password = scanner.nextInt();

                            boolean auth =  result.loginAdmin(name,password,globalCustomers);
                            if(!auth){
                                System.out.println("Incorrect user or password");
                                yield 0;
                            }else {
                                yield 1;
                            }
                        }
                        case 2 -> {
                            System.out.println("Enter root password : ");
                            int pass = scanner.nextInt();
                            if(pass == 1234){
                                System.out.println("logged in as root");
                                result.addAdmin();
                                yield 1;
                            }else {
                                System.out.println("password incorrect");
                                yield 0;
                            }
                        }
                        case 3 -> {
                            System.out.println("Enter root password : ");
                            int pass = scanner.nextInt();
                            if(pass == 1234) {
                                System.out.println("List of all bank admins : ");
                                result.getAllAdmins();
                            }else{
                                System.out.println("UnAuthorized");
                            }
                            yield 1;
                        }
                        default -> {
                            yield 0;
                        }
                    };
                    if(adminResult == 0){
                        break;
                    }
                }
            }
        }
    }


    public static ArrayList<CustomerIdentification> globalCustomers = new ArrayList<>();
    public static ABCBank abcBank = new ABCBank();
    public static XYZBank xyzBank = new XYZBank();


    public static void customerInteraction() {
        while (true) {
            System.out.println("Select the options : ");
            System.out.println("1.I have global ID");
            System.out.println("2.I don't have a global id");
            System.out.println("Press any number to go back to previous menu : ");
            Scanner scanner = new Scanner(System.in);
            int answer = scanner.nextInt();
            int exitCode =  switch (answer) {
                case 1 -> {
                    System.out.println("Select the bank : ");
                    System.out.println("1.ABC");
                    System.out.println("2.XYZ");
                    int result = scanner.nextInt();
                    Bank bank;
                    if(result == 1){
                        bank = abcBank;
                    }else if(result == 2){
                        bank = xyzBank;
                    }else{
                        System.out.println("Invalid Option");
                        yield 0;
                    }
                    Customer.login(bank,globalCustomers);
                    yield 1;
                }
                case 2 -> {
                    System.out.println("Press any key to register for global id");
                    scanner.nextInt();
                    CustomerIdentification customerIdentification = new CustomerIdentification();
                    globalCustomers.add(customerIdentification);
                    yield 1;
                }
                default -> {
                    yield 0;
                }
            };
            if(exitCode == 0){
                break;
            }
        }
    }

    public static void main(String[] args) {
        while(true){
            System.out.println("Which type of user you are?");
            System.out.println("1.Bank Admin");
            System.out.println("2.Customer");
            System.out.println("3.Press any key to exit");
            Scanner scanner = new Scanner(System.in);
            int answer = scanner.nextInt();
            int exitCode =  switch (answer) {
                case 1 -> {
                    adminInteraction();
                    yield 1;
                }
                case 2 -> {
                    customerInteraction();
                    yield 1;
                }
                default -> {
                    yield 0;
                }
            };
            if(exitCode == 0){
                break;
            }
        }
    }
}