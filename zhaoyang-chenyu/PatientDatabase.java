import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Scanner;

public class PatientDatabase {
    public Patient[] new_patient = new Patient[100];
    public String filename = "";

    public PatientDatabase()
    {
        for (int i = 0; i < 100; i++)//set for each employee
        {
            Patient a_patient = new Patient();
            new_patient[i] = a_patient;//initialize
        }
    }

    public int new_database(String file_name)//get input from user
    {
        Scanner inputStream;
        try {//check if the file exists
            inputStream = new Scanner(new File(file_name));
        } catch (FileNotFoundException e) {
            return 0;
        }
        int i = 0;
        while (inputStream.hasNextLine()) {//go through all the lines
            String[] line = new String[12];
            for (int j=0;j<12;j++)
            {
                line[j] = inputStream.nextLine();//split the line by ',' and store the data in array
            }
            float f = Float.parseFloat(line[6]);
            new_patient[i].Update_all(line[1], line[0], line[2], line[3], line[4], line[5], line[7], f, line[8], line[9], line[10], line[11]);
            i++;
        }
        filename=file_name;
        inputStream.close();
        return 1;
    }

    public int enter(String new_last_name, String new_first_name, String new_address, String New_phone_number, String New_data_of_birth, String New_insurance_type, String New_patient_type,float New_co_pay, String New_name_of_the_physician, String New_contact_phone_number_of_the_physician, String New_allergies, String New_illnesses)
    {
        int index =0;
        index = find(new_last_name,New_data_of_birth);
        if(index!=100)
        {
            return 0;
        }

        int i=0;
        while (!Objects.equals(new_patient[i].getLast_name(), "None"))
        {
            i++;
        }
        new_patient[i].Update_all(new_last_name,
                new_first_name,
                new_address,
                New_phone_number,
                New_data_of_birth,
                New_insurance_type,
                New_patient_type,
                New_co_pay,
                New_name_of_the_physician,
                New_contact_phone_number_of_the_physician,
                New_allergies,
                New_illnesses);
        return 1;
    }

    public int delete(String Last_name,String birth)
    {
        int index =0;
        index = find(Last_name, birth);
        if(index==100)
        {
            return 0;
        }
        new_patient[index]=new Patient();
        return 1;
    }

    public int find(String Last_name,String birth)
    {
        int index = 0;
        for(int i = 0;i<100;i++)
        {
            if(Objects.equals(new_patient[i].getLast_name(), Last_name)&Objects.equals(new_patient[i].getData_of_Birth(), birth))
            {
                index=i;
                break;
            }
            else if(i==99){index=100;}
        }
        return index;
    }

    public int update(int index,int id,String new_data)
    {
        if (index>99){return 0;}
//        System.out.println("1.first name");
//        System.out.println("2.last name");
//        System.out.println("3.address");
//        System.out.println("4.phone number");
//        System.out.println("5.insurance type");
//        System.out.println("6.co pay");
//        System.out.println("7.patient type");
//        System.out.println("8.name of the physician");
//        System.out.println("9.contact phone number of the physician");
//        System.out.println("10.allergies");
//        System.out.println("11.illnesses");
//        System.out.println("12.quit");
//        System.out.print("Enter the id of subset you want to update or quit: ");
//        int id =keyboard.nextInt();
//            if (id==12){
//                break;
//            }
//            else if(id>0 & id<13)
//            {
//                System.out.print("Enter the new data");
//                String new_data=keyboard.nextLine();
        if(id==0)
        {
            new_patient[index].updateFirst_name(new_data);
        }
        if(id==1)
        {
            new_patient[index].updateLast_name(new_data);
        }
        if(id==2)
        {
            new_patient[index].updateAddress(new_data);
        }
        if(id==3)
        {
            new_patient[index].updatePhone_number(new_data);
        }
        if(id==4)
        {
            new_patient[index].updateInsurance_Type(new_data);
        }
        if(id==5)
        {
            float f = Float.parseFloat(new_data);
            new_patient[index].updateCo_pay(f);
        }
        if(id==6)
        {
            new_patient[index].updatePatient_Type(new_data);
        }
        if(id==7)
        {
            new_patient[index].updateName_of_the_physician(new_data);
        }
        if(id==8)
        {
            new_patient[index].updateContact_phone_number_of_the_physician(new_data);
        }
        if(id==9)
        {
            new_patient[index].updateAllergies(new_data);
        }
        if(id==10)
        {
            new_patient[index].updateIllnesses(new_data);
        }
        return 1;
    }

    public int[] search(int id, String new_data)
    {
//        Scanner keyboard = new Scanner(System.in);
//        System.out.println("1.insurance type");
//        System.out.println("2.patient type");
//        System.out.println("3.name of the physician");
//        System.out.println("4.allergies");
//        System.out.println("5.illnesses");
//        System.out.println("6.quit");
//        System.out.print("Enter the id of subset you want to search or quit: ");
//        int id =keyboard.nextInt();
//        while(true)
//        {
        int[] index=new int[100];
        for(int i = 0;i<100;i++)
        {
            if(id==0 & Objects.equals(new_patient[i].getInsurance_Type(), new_data) & (!Objects.equals(new_patient[i].getLast_name(), "None")))
            {
                index[i]=1;
            }
            if(id==1 & Objects.equals(new_patient[i].getPatient_Type(), new_data) & (!Objects.equals(new_patient[i].getLast_name(), "None")))
            {
                index[i]=1;
            }
            if(id==2 & Objects.equals(new_patient[i].getName_of_the_physician(), new_data) & (!Objects.equals(new_patient[i].getLast_name(), "None")))
            {
                index[i]=1;
            }
            if(id==3 & Objects.equals(new_patient[i].getAllergies(), new_data) & (!Objects.equals(new_patient[i].getLast_name(), "None")))
            {
                index[i]=1;
            }
            if(id==4 & Objects.equals(new_patient[i].getIllnesses(), new_data) & (!Objects.equals(new_patient[i].getLast_name(), "None")))
            {
                index[i]=1;
            }
        }
        return index;
    }

    public void save()
    {
        PrintWriter outputStream = null;
        try
        {
            outputStream = new PrintWriter(new File(filename));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error opening the file " + filename);
            System.exit(0);
        }
        for (int i=0;i<100;i++)
        {
            if (!Objects.equals(new_patient[i].getLast_name(), "None"))
            {
                String[] line = new String[12];
                line[0] = new_patient[i].getFirst_name();
                line[1] = new_patient[i].getLast_name();
                line[2] = new_patient[i].getAddress();
                line[3] = new_patient[i].getPhone_number();
                line[4] = new_patient[i].getData_of_Birth();
                line[5] = new_patient[i].getInsurance_Type();
                line[6] = String.valueOf(new_patient[i].getCo_pay());
                line[7] = new_patient[i].getPatient_Type();
                line[8] = new_patient[i].getName_of_the_physician();
                line[9] = new_patient[i].getContact_phone_number_of_the_physician();
                line[10] = new_patient[i].getAllergies();
                line[11] = new_patient[i].getIllnesses();
                for(int j = 0;j<12;j++) {
                    outputStream.println(line[j]);
                }
            }
        }
        outputStream.close();
    }

}
