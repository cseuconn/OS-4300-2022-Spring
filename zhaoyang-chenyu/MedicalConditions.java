public class MedicalConditions
{
    private String Name_of_the_physician, Contact_phone_number_of_the_physician;
    private allergies Allergies;
    private illnesses Illnesses;

    public MedicalConditions()//Default constructor
    {
        Name_of_the_physician = "None";
        Contact_phone_number_of_the_physician = "None";
        Allergies = allergies.FOOD;
        Illnesses = illnesses.DIABETES;
    }

    public void create(String New_name_of_the_physician, String New_contact_phone_number_of_the_physician, String New_allergies, String New_illnesses)
    {
        Name_of_the_physician=New_name_of_the_physician;
        Contact_phone_number_of_the_physician=New_contact_phone_number_of_the_physician;
        switch (New_allergies)
        {
            case "Food":
                Allergies = allergies.FOOD;
                break;
            case "Medication":
                Allergies = allergies.MEDICATION;
                break;
            case "Seasonal":
                Allergies = allergies.SEASONAL;
                break;
            case "None":
                Allergies = allergies.NONE;
                break;
            case "Other":
                Allergies = allergies.OTHER;
                break;
        }
        switch (New_illnesses)
        {
            case "Diabetes":
                Illnesses = illnesses.DIABETES;
                break;
            case "CHD":
                Illnesses = illnesses.CHD;
                break;
            case "Asthma":
                Illnesses = illnesses.ASTHMA;
                break;
            case "None":
                Illnesses = illnesses.NONE;
                break;
            case "Other":
                Illnesses = illnesses.OTHER;
                break;
        }
    }

    public String getName_of_the_physician()
    {
        return Name_of_the_physician;
    }

    public String getContact_phone_number_of_the_physician()
    {
        return Contact_phone_number_of_the_physician;
    }

    public String getAllergies()
    {
        String str = "";
        switch (Allergies)
        {
            case FOOD:
                str = "Food";
                break;
            case MEDICATION:
                str = "Medication";
                break;
            case SEASONAL:
                str = "Seasonal";
                break;
            case NONE:
                str = "None";
                break;
            case OTHER:
                str = "Other";
                break;
        }
        return str;
    }

    public String getIllnesses()
    {
        String str = "";
        switch (Illnesses)
        {
            case DIABETES:
                str = "Diabetes";
                break;
            case CHD:
                str = "CHD";
                break;
            case ASTHMA:
                str = "Asthma";
                break;
            case NONE:
                str = "None";
                break;
            case OTHER:
                str = "Other";
                break;
        }
        return str;
    }

    public void updateName_of_the_physician(String New_Name_of_the_physician)
    {
        Name_of_the_physician=New_Name_of_the_physician;
    }

    public void updateContact_phone_number_of_the_physician(String New_Contact_phone_number_of_the_physician)
    {
        Contact_phone_number_of_the_physician=New_Contact_phone_number_of_the_physician;
    }

    public void updateAllergies(String New_Allergies)
    {
        switch (New_Allergies)
        {
            case "Food":
                Allergies = allergies.FOOD;
                break;
            case "Medication":
                Allergies = allergies.MEDICATION;
                break;
            case "Seasonal":
                Allergies = allergies.SEASONAL;
                break;
            case "None":
                Allergies = allergies.NONE;
                break;
            case "Other":
                Allergies = allergies.OTHER;
                break;
        }
    }

    public void updateIllnesses(String New_Illnesses)
    {
        switch (New_Illnesses)
        {
            case "Diabetes":
                Illnesses = illnesses.DIABETES;
                break;
            case "CHD":
                Illnesses = illnesses.CHD;
                break;
            case "Asthma":
                Illnesses = illnesses.ASTHMA;
                break;
            case "None":
                Illnesses = illnesses.NONE;
                break;
            case "Other":
                Illnesses = illnesses.OTHER;
                break;
        }
    }

    public enum allergies
    {
        FOOD, MEDICATION, SEASONAL, NONE, OTHER
    }
    public enum illnesses
    {
        DIABETES, CHD, ASTHMA, NONE, OTHER
    }
}

