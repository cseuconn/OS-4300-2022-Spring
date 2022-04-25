public class Patient {
    private String Last_name, First_name, Address, Phone_number, Date_of_Birth;
    private float Co_pay;
    private final MedicalConditions medicalConditions;
    private Insurance_Type insurance_Type;
    private Patient_Type patient_type;

    public Patient()//Default constructor
    {
        Last_name= "None";
        First_name = "None";
        Address = "None";
        Phone_number = "None";
        Date_of_Birth = "None";
        insurance_Type= Insurance_Type.GOVERNMENT;
        patient_type = Patient_Type.ADULT;
        Co_pay = 0;
        medicalConditions = new MedicalConditions();
    }

    public void Update_all(String new_last_name, String new_first_name, String new_address, String New_phone_number, String New_data_of_birth, String New_insurance_type, String New_patient_type,float New_co_pay, String New_name_of_the_physician, String New_contact_phone_number_of_the_physician, String New_allergies, String New_illnesses)
    {
        Last_name= new_last_name;
        First_name = new_first_name;
        Address = new_address;
        Phone_number = New_phone_number;
        Date_of_Birth = New_data_of_birth;
        switch (New_insurance_type)
        {
            case "Private":
                insurance_Type=Insurance_Type.PRIVATE;
                break;
            case "Government":
                insurance_Type=Insurance_Type.GOVERNMENT;
                break;
        }
        switch (New_patient_type)
        {
            case "Pediatric":
                patient_type=Patient_Type.PEDIATRIC;
                break;
            case "Adult":
                patient_type=Patient_Type.ADULT;
                break;
            case "Geriatric":
                patient_type=Patient_Type.GERIATRIC;
                break;
        }
        Co_pay = New_co_pay;
        medicalConditions.create(New_name_of_the_physician,New_contact_phone_number_of_the_physician,New_allergies,New_illnesses);
    }

    public String getFirst_name()
    {
        return First_name;
    }

    public String getLast_name()
    {
        return Last_name;
    }

    public String getAddress()
    {
        return  Address;
    }

    public String getPhone_number()
    {
        return Phone_number;
    }

    public String getData_of_Birth()
    {
        return Date_of_Birth;
    }

    public String getInsurance_Type()
    {
        String str = "";
        switch (insurance_Type)
        {
            case PRIVATE:
                str = "Private";
                break;
            case GOVERNMENT:
                str = "Government";
                break;
        }
        return str;
    }

    public String getPatient_Type()
    {
        String str= "";
        switch (patient_type)
        {
            case PEDIATRIC:
                str = "Pediatric";
                break;
            case ADULT:
                str = "Adult";
                break;
            case GERIATRIC:
                str = "Geriatric";
                break;
        }
        return str;
    }

    public float getCo_pay()
    {
        return Co_pay;
    }

    public void updateFirst_name(String New_First_name)
    {
        First_name=New_First_name;
    }

    public void updateLast_name(String New_Last_name)
    {
        Last_name=New_Last_name;
    }

    public void updateAddress(String New_address)
    {
        Address=New_address;
    }

    public void updatePhone_number(String New_Phone_number)
    {
        Phone_number=New_Phone_number;
    }

    public void updateData_of_Birth(String New_Data_of_Birth)
    {
        Date_of_Birth =New_Data_of_Birth;
    }

    public void updateInsurance_Type(String New_Insurance_Type)
    {
        switch (New_Insurance_Type)
        {
            case "Private":
                insurance_Type=Insurance_Type.PRIVATE;
                break;
            case "Government":
                insurance_Type=Insurance_Type.GOVERNMENT;
                break;
        }
    }

    public void updatePatient_Type(String New_Patient_Type)
    {
        switch (New_Patient_Type)
        {
            case "Pediatric":
                patient_type=Patient_Type.PEDIATRIC;
                break;
            case "Adult":
                patient_type=Patient_Type.ADULT;
                break;
            case "Geriatric":
                patient_type=Patient_Type.GERIATRIC;
                break;
        }
    }

    public void updateCo_pay(float New_Co_pay)
    {
        Co_pay=New_Co_pay;
    }
    //MedicalConditions
    public String getName_of_the_physician()
    {
        return medicalConditions.getName_of_the_physician();
    }

    public String getContact_phone_number_of_the_physician()
    {
        return medicalConditions.getContact_phone_number_of_the_physician();
    }

    public String getAllergies()
    {
        return medicalConditions.getAllergies();
    }

    public String getIllnesses()
    {
        return medicalConditions.getIllnesses();
    }

    public void updateName_of_the_physician(String New_Name_of_the_physician)
    {
        medicalConditions.updateName_of_the_physician(New_Name_of_the_physician);
    }

    public void updateContact_phone_number_of_the_physician(String New_Contact_phone_number_of_the_physician)
    {
        medicalConditions.updateContact_phone_number_of_the_physician(New_Contact_phone_number_of_the_physician);
    }

    public void updateAllergies(String New_Allergies)
    {
        medicalConditions.updateAllergies(New_Allergies);
    }

    public void updateIllnesses(String New_Illnesses)
    {
        medicalConditions.updateIllnesses(New_Illnesses);
    }

    public void print_all()
    {
        System.out.println("Last_name: "+getFirst_name());
        System.out.println("First_name: "+getLast_name());
        System.out.println("Address: "+getAddress());
        System.out.println("Phone_number: "+getPhone_number());
        System.out.println("Data_of_Birth: "+getData_of_Birth());
        System.out.println("Insurance_Type: "+getInsurance_Type());
        System.out.println("Patient_Type: "+getPatient_Type());
        System.out.println("Co_pay: "+getCo_pay());
        System.out.println("Name_of_the_physician: "+getName_of_the_physician());
        System.out.println("Contact_phone_number_of_the_physician: "+getContact_phone_number_of_the_physician());
        System.out.println("Allergies: "+getAllergies());
        System.out.println("Illnesses: "+getIllnesses());
    }

    private enum Insurance_Type
    {
        GOVERNMENT,PRIVATE
    }

    private enum Patient_Type
    {
        PEDIATRIC,ADULT,GERIATRIC
    }




}
