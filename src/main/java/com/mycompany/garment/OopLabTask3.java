
package com.mycompany.garment;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class Garment {

    public String id;
    public String name;
    public String description;
    public String size;
    public String color;
    public double price;
    public int stockQuantity;

    
    public Garment(String id, String name, String description, String size, String color, double price, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.size = size;
        this.color = color;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    void updateStock(int quantity) {
        this.stockQuantity = quantity;
    }

    double calculateDiscountPrice(double discountPercentage) {
        double discount = price * (discountPercentage / 100);
        return price - discount;
    }
}

class Fabric {

    public String id;
    public String type;
    public String color;
    public double pricePerMeter;


    public Fabric(String id, String type, String color, double pricePerMeter) {
        this.id = id;
        this.type = type;
        this.color = color;
        this.pricePerMeter = pricePerMeter;
    }

    double calculateCost(double meters) {
        return pricePerMeter * meters;
    }
}


class Order {

    public String orderId;
    public Date orderDate;
    public List<Garment> garments = new ArrayList<>();
    private double totalAmount;

    public Order(String orderId, Date orderDate) {
        this.orderId = orderId;
        this.orderDate = orderDate;
    }

    void addGarment(Garment garment) {
        garments.add(garment);
    }

    double calculateTotalAmount() {
        totalAmount = 0;
        for (Garment g : garments) {
            totalAmount += g.price;
        }
        return totalAmount;
    }

    void printOrderDetails() {
        System.out.println("------------------------");
        System.out.println(" your Order Details");
        System.out.println("------------------------");
        for (Garment g : garments) {
            System.out.println("your Name: " + g.name);
            System.out.println("product Price: " + g.price);
            System.out.println("Description: " + g.description);
            System.out.println("------------------------");
        }
        System.out.println("Total Amount: " + calculateTotalAmount());
    }
}

class Customer {

    public String customerId;
    public String name;
    public String email;
    public String phone;
    List<Order> orders = new ArrayList<>();

    public Customer(String customerId, String name, String email, String phone) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    void placeOrder(Order order) {
        orders.add(order);
        order.printOrderDetails();
        System.out.println("your Order Placed Successfully.");
    }

    List<Order> viewOrders() {
        return orders;
    }
}

class Inventory {

    List<Garment> garments = new ArrayList<>();

    void addGarment(Garment garment) {
        garments.add(garment);
    }

    void removeGarment(String id) {
        garments.removeIf(g -> g.id.equals(id));
    }

    Garment findGarment(String id) {
        for (Garment g : garments) {
            if (g.id.equals(id))
                return g;
        }
        return null;
    }

    void displayGarments() {
        System.out.println("Inventory:");
        for (Garment g : garments) {
            System.out.println("ID: " + g.id + ", Name: " + g.name + ", Price: " + g.price + ", Stock: " + g.stockQuantity);
        }
    }
}

public class OopLabTask3 {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Inventory inventory = new Inventory();
    private static final List<Customer> customers = new ArrayList<>();
    
    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("\n----- Garment Management System -----");
            System.out.println("1. Add Garment");
            System.out.println("2. View Garments");
            System.out.println("3. Place Order");
            System.out.println("4. View Orders by Customer");
            System.out.println("5. Update Garment Stock");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); 
            
            switch (choice) {
                case 1 -> addGarment();
                case 2 -> inventory.displayGarments();
                case 3 -> placeOrder();
                case 4 -> viewCustomerOrders();
                case 5 -> updateGarmentStock();
                case 6 -> running = false;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
        System.out.println("Thank you for using the Garment Management System!");
    }
    
    private static void addGarment() {
        System.out.print("Enter Garment ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Garment Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Description: ");
        String description = scanner.nextLine();
        System.out.print("Enter the Size: ");
        String size = scanner.nextLine();
        System.out.print("Enter Color: ");
        String color = scanner.nextLine();
        System.out.print("Enter the Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter the Stock Quantity: ");
        int stockQuantity = scanner.nextInt();
        
        Garment garment = new Garment(id, name, description, size, color, price, stockQuantity);
        inventory.addGarment(garment);
        System.out.println("The Garment added successfully!");
    }
    
    private static void placeOrder() {
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine();
        Customer customer = findCustomer(customerId);
        
        if (customer == null) {
            System.out.print("Enter Customer Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter your Email: ");
            String email = scanner.nextLine();
            System.out.print("Enter your Phone: ");
            String phone = scanner.nextLine();
            customer = new Customer(customerId, name, email, phone);
            customers.add(customer);
        }
        
        System.out.print("Enter Order ID: ");
        String orderId = scanner.nextLine();
        Order order = new Order(orderId, new Date());
        
        System.out.print("Enter Garment ID to add to Order (or 'done' to finish): ");
        while (true) {
            String garmentId = scanner.nextLine();
            if (garmentId.equalsIgnoreCase("done")) break;
            Garment garment = inventory.findGarment(garmentId);
            if (garment != null) {
                order.addGarment(garment);
                System.out.println("Garment added to order.");
            } else {
                System.out.println(" The Garment not found.");
            }
            System.out.print("Enter another Garment ID (or 'done' to finish): ");
        }
        
        customer.placeOrder(order);
    }
    
    private static void viewCustomerOrders() {
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine();
        Customer customer = findCustomer(customerId);
        
        if (customer != null) {
            List<Order> orders = customer.viewOrders();
            if (!orders.isEmpty()) {
                for (Order order : orders) {
                    order.printOrderDetails();
                }
            } else {
                System.out.println("No orders found for this customer.");
            }
        } else {
            System.out.println("Customer not found.");
        }
    }
    
    private static void updateGarmentStock() {
        System.out.print("Enter Garment ID: ");
        String id = scanner.nextLine();
        Garment garment = inventory.findGarment(id);
        
        if (garment != null) {
            System.out.print("Enter new Stock Quantity: ");
            int quantity = scanner.nextInt();
            garment.updateStock(quantity);
            System.out.println("Stock updated successfully!");
        } else {
            System.out.println("Garment not found.");
        }
    }
    
    private static Customer findCustomer(String customerId) {
        for (Customer customer : customers) {
            if (customer.customerId.equals(customerId)) {
                return customer;
            }
        }
        return null;
    }
}
