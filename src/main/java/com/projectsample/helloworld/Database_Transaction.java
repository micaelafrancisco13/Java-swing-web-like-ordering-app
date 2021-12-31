/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectsample.helloworld;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Ela
 */
public class Database_Transaction {
    private static String currentEmailAddress;
    
    public static void insertRecord(My_SQL_Connection connection, String[] personalInfo, 
                                    String[] addressInfo) {
        try {
            String firstName = personalInfo[0];
            String lastName = personalInfo[1];
            String emailAddress = personalInfo[2];
            String phone = personalInfo[3];
            String password = personalInfo[4];
            String completeAddress = addressInfo[0];
            String barangay = addressInfo[1];
            String query = "INSERT INTO customers(first_name, last_name, email_address, "
                         +                       "password, phone, complete_address, "
                         +                       "barangay) "
                         + "VALUES(?, ?, ?, MD5(?), ?, ?, ?)";
            var preparedStatement = connection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, emailAddress);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, phone);
            preparedStatement.setString(6, completeAddress);
            preparedStatement.setString(7, barangay);
            preparedStatement.executeUpdate();
        }
        catch (Exception e) {
            System.out.println("error creating an account");
        }
    }
    
    public static boolean doesEmailExist(My_SQL_Connection connection, JTextField emailField) {
        boolean isEmailFound = false;
        try{
            int id = 0;
            String emailAddress = emailField.getText().trim();
            String query = "SELECT customer_id "
                         + "FROM customers " 
                         + "WHERE email_address = ?";
            var preparedStatement = connection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, emailAddress);
            ResultSet resultSetID = preparedStatement.executeQuery();

            if (resultSetID.next())
                id = resultSetID.getInt("customer_id");
            if (id == 0)
                return isEmailFound;
            else
                isEmailFound = true;
        }
        catch (Exception e) {
            System.out.println("error finding email");
        }
        return isEmailFound;
    }
    
    public static boolean confirmPassword(My_SQL_Connection connection, JPasswordField passwordField) {
        boolean isPasswordCorrect = false;
        try {
            String currentPassword = new String(passwordField.getPassword());
            if (currentPassword.isEmpty())
                return isPasswordCorrect;
            String query = "SELECT email_address, password "
                         + "FROM customers "
                         + "WHERE email_address = ? AND password = MD5(?)";
            var preparedStatement = connection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, currentEmailAddress);
            preparedStatement.setString(2, currentPassword);
            ResultSet resultCombination = preparedStatement.executeQuery();
            if (resultCombination.next() == true)
                isPasswordCorrect = true;
        }
        catch (Exception exception) {
            System.out.println("error");
        }
        return isPasswordCorrect;
    }
    
    public static void addOrderToCart(My_SQL_Connection connection, int[] tracker) {
        try {
            int row = tracker[0];
            int column = tracker[1];
            int customerID = getCurrentCustomerID(connection);
            int quantity = Quantity.getArrayOfQuantities()[row][column];
            String dishName = Quantity.getArrayOfItems()[row][column].getDishName()[row][column];
            String whiteSpace = Quantity.getArrayOfWhiteSpaces()[row][column];
            BigDecimal unitPrice = Quantity.getArrayOfItems()[row][column].getPriceArray()[row][column];
            String query = "INSERT INTO Cart "
                         + "VALUES(?, ?, ?, ?, ?, ?, ?)";
            var preparedStatement = connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, customerID);
            preparedStatement.setInt(2, row);
            preparedStatement.setInt(3, column);
            preparedStatement.setInt(4, quantity);
            preparedStatement.setString(5, dishName);
            preparedStatement.setString(6, whiteSpace);
            preparedStatement.setBigDecimal(7, unitPrice);
            preparedStatement.executeUpdate();
        }
        catch(Exception exception) {
            System.out.println("error adding order to cart");
        }
    }
    
    public static void updateQuantity(My_SQL_Connection connection, int[] tracker) {
        try {
            int row = tracker[0];
            int column = tracker[1];
            int customerID = getCurrentCustomerID(connection);
            int quantity = Quantity.getArrayOfQuantities()[row][column];
            String dishName = Quantity.getArrayOfItems()[row][column].getDishName()[row][column];
            String query = "UPDATE Cart "
                         + "SET quantity = ? "
                         + "WHERE (customer_id = ?) AND (product_name = ?)";
            var preparedStatement = connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, customerID);
            preparedStatement.setString(3, dishName);
            preparedStatement.executeUpdate();
        }
        catch(Exception exception) {
            System.out.println("error updating order quantity");
        }
    }
    
    public static void removeOrderFromCart(My_SQL_Connection connection, int[] tracker) {
        try {
            int row = tracker[0];
            int column = tracker[1];
            int customerID = getCurrentCustomerID(connection);
            String dishName = Quantity.getArrayOfItems()[row][column].getDishName()[row][column];
            String query = "DELETE FROM Cart "
                         + "WHERE product_name = ? AND customer_id = ?";
            var preparedStatement = connection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, dishName);
            preparedStatement.setInt(2, customerID);
            preparedStatement.executeUpdate();
        }
        catch(Exception exception) {
            System.out.println("error retrieving orders from cart");
        }
    }
    
    public static void retrieveOrderInCart(My_SQL_Connection connection, JPanel cartBGPanel, JLabel[][] arrayOfQtyLabels,
                                           JLabel[] arrayOfItemLabels, String blankText){
        try {
            int customerID = getCurrentCustomerID(connection);
            String query = "SELECT * "
                         + "FROM Cart "
                         + "WHERE customer_id = ?";
            var preparedStatement = connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, customerID);
            ResultSet ordersInCart = preparedStatement.executeQuery();
            while(ordersInCart.next() == true) {
                int row = ordersInCart.getInt("row_x");
                int column = ordersInCart.getInt("column_y");
                var tracker = new int[]{row, column};
                int quantity = ordersInCart.getInt("quantity");
                String dishName = ordersInCart.getString("product_name").trim();
                String whiteSpace = ordersInCart.getString("white_space");
                String priceString = ordersInCart.getBigDecimal("unit_price").toString().trim();
                var labelFormat = new String[]{dishName, whiteSpace, priceString};
                BigDecimal priceInt = ordersInCart.getBigDecimal("unit_price");
                Quantity.setSavedQuantity(tracker, quantity, "from cart");
                Cart.modifyItemDetails(tracker, labelFormat, priceInt, cartBGPanel, 
                                       arrayOfQtyLabels, arrayOfItemLabels, blankText);
            }
        }
        catch(Exception exception) {
            System.out.println("error retrieving in-cart orders");
        }
    }
    
    public static void confirmOrder(My_SQL_Connection connection, int[] tracker) {
        try{
            int row = tracker[0];
            int column = tracker[1];
            String dishName = Quantity.getArrayOfItems()[row][column].getDishName()[row][column];
            int customerID = getCurrentCustomerID(connection);
            var currentDateAndTime = new java.sql.Timestamp(new java.util.Date().getTime());
            int orderStatus = 1; // added to cart
            int productID = getProductID(connection, dishName);
            int quantity = Quantity.getArrayOfQuantities()[row][column];
            BigDecimal unitPrice = Quantity.getArrayOfItems()[row][column].getPriceArray()[row][column];         
            
            String query1 = "INSERT INTO orders(customer_id, order_date, status) "
                          + "VALUES(?, ?, ?);";
            var preparedStatement1 = connection.getConnection().prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
            preparedStatement1.setInt(1, customerID);
            preparedStatement1.setTimestamp(2, currentDateAndTime);
            preparedStatement1.setInt(3, orderStatus);
            preparedStatement1.executeUpdate();   
            int lastInsertID = getLastInsertedID(preparedStatement1);
            String query2 = "INSERT INTO order_items "
                          + "VALUES(?, ?, ?, ?)";
            var preparedStatement2 = connection.getConnection().prepareStatement(query2);
            preparedStatement2.setInt(1, lastInsertID);
            preparedStatement2.setInt(2, productID);
            preparedStatement2.setInt(3, quantity);
            preparedStatement2.setBigDecimal(4, unitPrice);
            preparedStatement2.executeUpdate();
        }
        catch (Exception e) {
            System.out.println("error inserting order");
        }
    }
    
    public static void updateQtyStock(My_SQL_Connection connection, String dishName, String state) {
        try {
            int orderedQty = getOrderedQuantity(connection, dishName);
            int qtyInStock = getQuantityInStock(connection, dishName);
            int updatedQtyInStock;
            if (state == "confirmed order")
                updatedQtyInStock = (qtyInStock - orderedQty);
            else
                updatedQtyInStock = (qtyInStock + orderedQty);
            String query = "UPDATE Products "
                         + "SET quantity_in_stock = ? "
                         + "WHERE name = ?";
            var preparedStatement = connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, updatedQtyInStock);
            preparedStatement.setString(2, dishName);
            preparedStatement.executeUpdate();
        }
        catch (Exception exception) {
            System.out.println("error updating qty in stock");
        }
    }
    
    public static ResultSet retrievePurchasedOrder(My_SQL_Connection connection){
        ResultSet currentOrderSet = null;
        try {
            int customerID = getCurrentCustomerID(connection);
            String query = "SELECT " 
                         + "OI.quantity, "
                         + "P.name, "
                         + "OI.unit_price "
                         + "FROM Orders O "
                         + "JOIN Order_Items OI "
                         +      "USING (order_id) "
                         + "JOIN Products P "
                         +      "USING (product_id) "
                         + "WHERE O.customer_id = ?";
            var preparedStatement = connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, customerID);
            currentOrderSet = preparedStatement.executeQuery();
        }
        catch(Exception exception) {
            System.out.println("error getting orders of the current customer");
        }
        return currentOrderSet;
    }
    
    public static void cancelOrder(My_SQL_Connection connection, String dishName) {
        try {
            int orderIDToBeDeleted = getOrderIDToBeDeleted(connection, dishName);
            String query1 = "DELETE FROM order_items "
                          + "WHERE order_id = ?";
            var preparedStatement1 = connection.getConnection().prepareStatement(query1);
            preparedStatement1.setInt(1, orderIDToBeDeleted);
            preparedStatement1.executeUpdate();
                        
            String query2 = "DELETE FROM orders "
                          + "WHERE order_id = ?";
            var preparedStatement2 = connection.getConnection().prepareStatement(query2);
            preparedStatement2.setInt(1, orderIDToBeDeleted);
            preparedStatement2.executeUpdate();
        }
        catch (Exception exception) {
            System.out.println("error deleting an order");
        }
    }
    
    public static void updateEmailAddress(My_SQL_Connection connection, JTextField emailAddressField) {
        try {
            int customerID = getCurrentCustomerID(connection);
            String updatedEmailAddress = emailAddressField.getText();
            
            String query = "UPDATE customers "
                         + "SET email_address = ? "
                         + "WHERE customer_id = ?";
            var preparedStatement = connection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, updatedEmailAddress);
            preparedStatement.setInt(2, customerID);
            preparedStatement.executeUpdate();
        }
        catch (Exception exception) {
            System.out.println("error updating email address");
        }
    }

    public static void updatePassword(My_SQL_Connection connection, JPasswordField passwordField) {
        try {
            int customerID = getCurrentCustomerID(connection);
            String updatedPassword = new String(passwordField.getPassword());
            
            String query = "UPDATE customers "
                         + "SET password = MD5(?) "
                         + "WHERE customer_id = ?";
            var preparedStatement = connection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, updatedPassword);
            preparedStatement.setInt(2, customerID);
            preparedStatement.executeUpdate();
        }
        catch (Exception exception) {
            System.out.println("error updating password");
        }
    }
    
    public static void updateAddress(My_SQL_Connection connection, JTextField completeAddressField, 
                                     JComboBox barangayAddress, String specificAddress) {
        try {
            int customerID = getCurrentCustomerID(connection);
            if (specificAddress.equalsIgnoreCase(("complete address"))) {
                String updatedCompleteAddress = completeAddressField.getText();
                String query = "UPDATE customers "
                             + "SET complete_address = ? "
                             + "WHERE customer_id = ?";
                var preparedStatement = connection.getConnection().prepareStatement(query);
                preparedStatement.setString(1, updatedCompleteAddress);
                preparedStatement.setInt(2, customerID);
                preparedStatement.executeUpdate();
            }
            else {
                String updatedBarangayAddress = barangayAddress.getSelectedItem().toString();
                String query = "UPDATE customers "
                             + "SET barangay = ? "
                             + "WHERE customer_id = ?";
                var preparedStatement = connection.getConnection().prepareStatement(query);
                preparedStatement.setString(1, updatedBarangayAddress);
                preparedStatement.setInt(2, customerID);
                preparedStatement.executeUpdate();
            }
        }
        catch (Exception exception) {
            System.out.println("error updating address");
        }
    }
    
    public static void updateName(My_SQL_Connection connection, String name, String namePart) {
        try {
            int customerID = getCurrentCustomerID(connection);
            if (namePart.equalsIgnoreCase(("first name"))) {
                String query = "UPDATE customers "
                             + "SET first_name = ? "
                             + "WHERE customer_id = ?";
                var preparedStatement = connection.getConnection().prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, customerID);
                preparedStatement.executeUpdate();
            }
            else {
                String query = "UPDATE customers "
                             + "SET last_name = ? "
                             + "WHERE customer_id = ?";
                var preparedStatement = connection.getConnection().prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, customerID);
                preparedStatement.executeUpdate();
            }
        }
        catch (Exception exception) {
            System.out.println("error updating name");
        }
    }
    
    public static void updateContactNumber(My_SQL_Connection connection, String contactNumber) {
        try {
            int customerID = getCurrentCustomerID(connection);
            String query = "UPDATE customers "
                         + "SET phone = ? "
                         + "WHERE customer_id = ?";
            var preparedStatement = connection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, contactNumber);
            preparedStatement.setInt(2, customerID);
            preparedStatement.executeUpdate();
        }
        catch (Exception exception) {
            System.out.println("error updating contact number");
        }
    }
    
    public static String retrieveFirstName(My_SQL_Connection connection) {
        String firstName = "";
        try {
            int customerID = getCurrentCustomerID(connection);
            String query = "SELECT "
                         + "first_name "
                         + "FROM Customers "
                         + "WHERE customer_id = ?";
            var preparedStatement = connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, customerID);
            ResultSet nameSet = preparedStatement.executeQuery();
            if(nameSet.next())
                firstName = nameSet.getString("first_name");
        }
        catch(Exception exception) {
            
        }
        return firstName;
    }
    
    public static String retrieveLastName(My_SQL_Connection connection) {
        String lastName = "";
        try {
            int customerID = getCurrentCustomerID(connection);
            String query = "SELECT "
                         + "last_name "
                         + "FROM Customers "
                         + "WHERE customer_id = ?";
            var preparedStatement = connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, customerID);
            ResultSet nameSet = preparedStatement.executeQuery();
            if(nameSet.next())
                lastName = nameSet.getString("last_name");
        }
        catch(Exception exception) {
            
        }
        return lastName;
    }
    
    public static String retrieveContactNumber(My_SQL_Connection connection) {
        String contactNumber = "";
        try {
            int customerID = getCurrentCustomerID(connection);
            String query = "SELECT "
                         + "phone "
                         + "FROM Customers "
                         + "WHERE customer_id = ?";
            var preparedStatement = connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, customerID);
            ResultSet phoneNumberSet = preparedStatement.executeQuery();
            if(phoneNumberSet.next())
                contactNumber = phoneNumberSet.getString("phone");
        }
        catch(Exception exception) {
            
        }
        return contactNumber;
    }
    
    public static String retrieveStreetAddress(My_SQL_Connection connection) {
        String streetAddress = "";
        try {
            int customerID = getCurrentCustomerID(connection);
            String query = "SELECT "
                         + "complete_address "
                         + "FROM Customers "
                         + "WHERE customer_id = ?";
            var preparedStatement = connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, customerID);
            ResultSet phoneNumberSet = preparedStatement.executeQuery();
            if(phoneNumberSet.next())
                streetAddress = phoneNumberSet.getString("complete_address");
        }
        catch(Exception exception) {
            
        }
        return streetAddress;
    }
    
    public static String retrieveBarangayAddress(My_SQL_Connection connection) {
        String barangayAddress = "";
        try {
            int customerID = getCurrentCustomerID(connection);
            String query = "SELECT "
                         + "barangay "
                         + "FROM Customers "
                         + "WHERE customer_id = ?";
            var preparedStatement = connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, customerID);
            ResultSet barangayAddressSet = preparedStatement.executeQuery();
            if(barangayAddressSet.next())
                barangayAddress = barangayAddressSet.getString("barangay");
        }
        catch(Exception exception) {
            
        }
        return barangayAddress;
    }
    
    public static String retrieveEmailAddress(My_SQL_Connection connection) {
        String emailAddress = "";
        try {
            int customerID = getCurrentCustomerID(connection);
            String query = "SELECT "
                         + "email_address "
                         + "FROM Customers "
                         + "WHERE customer_id = ?";
            var preparedStatement = connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, customerID);
            ResultSet emailAddressSet = preparedStatement.executeQuery();
            if(emailAddressSet.next())
                emailAddress = emailAddressSet.getString("email_address");
        }
        catch(Exception exception) {
            
        }
        return emailAddress;
    }
    
    private static int getOrderedQuantity(My_SQL_Connection connection, String dishName) {
        int orderedQuantity = 0;
        try {
            int customerID = getCurrentCustomerID(connection);
            String query = "SELECT "
                         +       "quantity "
                         + "FROM Order_Items OI "
                         + "JOIN Orders O "
                         +                "USING (order_id) "
                         + "JOIN Products P "
                         +                "USING (product_id) "
                         + "WHERE P.name = ? AND O.customer_id = ?";
            var preparedStatement = connection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, dishName);
            preparedStatement.setInt(2, customerID);
            ResultSet orderedQtySet = preparedStatement.executeQuery();
            if(orderedQtySet.next())
                orderedQuantity = orderedQtySet.getInt("quantity");
        }
        catch (Exception exception) {
            System.out.println("error getting ordered quantity");
        }
        return orderedQuantity;
    }
    
    private static int getQuantityInStock(My_SQL_Connection connection, String dishName) {
        int quantityInStock = 0;
        try {
            String query = "SELECT "
                         + "quantity_in_stock "
                         + "FROM Products "
                         + "WHERE name = ?";
            var preparedStatement = connection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, dishName);
            ResultSet qtyStockSet = preparedStatement.executeQuery();
            if(qtyStockSet.next())
                quantityInStock = qtyStockSet.getInt("quantity_in_stock");
        }
        catch (Exception exception) {
            System.out.println("error getting qty in stock");
        }
        return quantityInStock;
    }
    
    private static int getOrderIDToBeDeleted(My_SQL_Connection connection, String dishName) {
        int orderIDToBeDeleted = 0;
        try {
            int customerID = getCurrentCustomerID(connection);
            int productID = getProductID(connection, dishName);
            String query = "SELECT "
                         +       "order_id "
                         + "FROM order_items oi "
                         + "JOIN orders o "
                         +       "USING(order_id) "
                         + "WHERE (o.customer_id = ?) AND (oi.product_id = ?)";
            var preparedStatement = connection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, customerID);
            preparedStatement.setInt(2, productID);
            ResultSet currentOrderID = preparedStatement.executeQuery();
            if (currentOrderID.next() == true)
                orderIDToBeDeleted = currentOrderID.getInt("order_id");
        }
        catch (Exception exception) {
            System.out.println("error getting order_id to be deleted");
        }
        return orderIDToBeDeleted;
    }
    
    private static int getProductID(My_SQL_Connection connection, String dishName) {
        int productID = 0;
        try {
            String query = "SELECT product_id "
                         + "FROM products " 
                         + "WHERE name = ?";
            var preparedStatement = connection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, dishName);
            ResultSet currentProductID = preparedStatement.executeQuery();
            if(currentProductID.next())
                productID = currentProductID.getInt("product_id");
        }
        catch(Exception exception) {
            
        }
        return productID;
    }
    
    private static int getLastInsertedID(PreparedStatement preparedStatement) {
        int lastInsertID = 0;
        try {
            var resultSetID = preparedStatement.getGeneratedKeys();
            if (resultSetID.next() == true)
                lastInsertID = resultSetID.getInt(1);
        }
        catch (Exception exception) {
            System.out.println("error gettig last inserted ID");
        }
        return lastInsertID;
    }

    private static int getCurrentCustomerID(My_SQL_Connection connection) {
        int customerID = 0;
        try {
            String query = "SELECT customer_id "
                         + "FROM customers " 
                         + "WHERE email_address = ?";
            var preparedStatement = connection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, currentEmailAddress);
            ResultSet currentCustomerID = preparedStatement.executeQuery();
            if(currentCustomerID.next())
                customerID = currentCustomerID.getInt("customer_id");
        }
        catch(Exception exception) {
            System.out.println("error finding customer ID");
        }
        return customerID;
    }
    
    public static void setEmailAddress(String emailAddress) {
        currentEmailAddress = emailAddress;
    }
}