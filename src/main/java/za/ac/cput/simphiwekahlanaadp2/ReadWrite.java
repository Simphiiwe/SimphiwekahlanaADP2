/**
 *This is my Read and Write file where I will perform the instructions required.
 * @author Simphiwe Kahlana (220162891)
 * 02 June 2021
 */
package za.ac.cput.simphiwekahlanaadp2;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


public class ReadWrite {
    private ObjectInputStream input;
    private BufferedWriter bufferw;
    
    
    
    static ArrayList<Customer> customerList = new ArrayList<>();
    static ArrayList<Supplier> supplierList = new ArrayList<>();
    
    public void openFile(){
        try{
            input = new ObjectInputStream (new FileInputStream ("stakeholder.ser"));
        }
        catch (IOException ioe){
        System.out.println(" Ser file opening error! " + ioe.getMessage());
    }
  }
    public void closeFile(){
        try{
            input.close();
        }
        catch (IOException ioe){
            System.out.println("Ser File closing error! "+ ioe.getMessage());
        }
    }
    
    public void readFromFile(){
    try{
        while(true){
            Object tempObject = input.readObject();
            if (tempObject instanceof Customer){
                customerList.add((Customer)tempObject);
            }else{
                supplierList.add((Supplier)tempObject);
            }
        }
    }
    catch(EOFException eofe){
        System.out.println("Reached reading of serialized end");
    }
    catch(ClassNotFoundException ioe){
        System.out.println("Class cannot not be found error reading serialized file! " +ioe.getMessage());
    }
    catch(IOException ioe){
        System.out.println("Error reading from serialized file " +ioe.getMessage() );
    }
    finally{
        closeFile();
        System.out.println("***The Serialized file has been closed.");
    }
    
    }
    
    public void sortAscendinOrder(){
        try{
      
	   Collections.sort(customerList.stHolderId);
	   for(Customer str: customerList);
        
    }
        
        catch(Exception e){
            System.out.println("Sorting in Ascending order failed!");
        }
    
    }
    
    public void calculateAge(){
        
        
    
    }
    
    
    public void dateReformat(){
        try{
            DateFormat outputFormat = new SimpleDateFormat("dd MM yyy");
            DateFormat inputFormat = new SimpleDateFormat ("yyyy-MM-dd");
            for (int j = 0; j< customerList.size(); j++){
            String inputDate = customerList.get(j).getDateOfBirth();
            Date date = inputFormat.parse(inputDate);
            customerList.get(j).setDateOfBirth(outputFormat.format(date));
            }
        }
        catch (ParseException e){
            System.out.println("Date formatting error! ");
        }
    }
    
    public void customerWriteFile(){
        try{
            int rentingCounter = 0;
            bufferw.write("================================================Customers========================================\n");
            bufferw.write(String.format("%s-10s%-12s%-12s%-12s%-17s%-15s%\n", "ID","Name","Surname","Adress","Date of birth","Credit","Age")); 
            bufferw.write("=================================================================================================\n");
            for (int i = 0; 1 < customerList.size(); i++){
                bufferw.write(String.format("%-10s%-12s%-12s%-12s%-17sR %s-13.2f%d\n", customerList.get(i).getStHolderId(), customerList.get(i).getFirstName(),
                customerList.get(i).getSurName(),customerList.get(i).getAddress(),customerList.get(i).getDateOfBirth(),customerList.get(i).getCredit(),
                calculateAge(customerList.get(i).getDateOfBirth())));
                if (customerList.get(i).getCanRent()== true){
                    rentingCounter++;
                }
                
             }
            bufferw.write("\nNumber of Customers able to rent: "+rentingCounter);
            bufferw.write("\nNumber of Customers NOT able to rent: "+ (customerList.size()- rentingCounter));
          }
        catch (IOException e){
            System.out.println("Writing to Customer File Error");
        }
        finally {
            closeFile();
            System.out.println("****Customer File Closed****");
        }
    }
    
    
    public static void main(String[] args) {
        ReadWrite readW = new ReadWrite();
        readW.openFile();
        readW.closeFile();
        readW.readFromFile();
        readW.sortAscendinOrder();
        readW.calculateAge();
        readW.customerWriteFile();
    }
}
