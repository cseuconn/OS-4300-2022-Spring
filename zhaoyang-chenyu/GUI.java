import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class GUI {
    static PatientDatabase new_database = new PatientDatabase();
    static JFrame frame = new JFrame("GUI");
    static JPanel intro_panel = new JPanel();
    static JPanel main_panel = new JPanel();
    static JPanel create_panel = new JPanel();
    static JPanel delete_panel = new JPanel();
    static JPanel find_panel = new JPanel();
    static JPanel modify_panel = new JPanel();
    static JPanel search_panel = new JPanel();
    static String filename;

    static MIS_socket socket;

    static int check_file=0;

    private static void intro_panel()
    {
        intro_panel.setLayout(null);

        JLabel Label1 = new JLabel();
        Label1.setBounds(40,30,200,20);
        JTextField textField = new JTextField(20);
        textField.setBounds(30,60,200,20);
        JButton buttonUpload = new JButton("Enter");
        buttonUpload.setBounds(240,60,70,20);
        JLabel Label2 = new JLabel();
        Label2.setBounds(40,90,200,20);

        intro_panel.add(Label1);
        intro_panel.add(textField);
        intro_panel.add(buttonUpload);
        intro_panel.add(Label2);

        Label1.setText("Enter the file name");
        Label2.setText("File Not Found");
        Label2.setVisible(false);

        buttonUpload.addActionListener(e -> {
            try {
                socket=new MIS_socket(textField.getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                check_file=socket.request(textField.getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (check_file==1)
            {
                filename=textField.getText();
                new_database.new_database("local.txt");
                frame.remove(intro_panel);
                frame.setTitle("Menu");
                frame.setSize(250, 270);
                frame.setContentPane(main_panel);
                frame.validate();
                frame.repaint();
            }
            else
            {
                Label2.setVisible(true);
            }
        });
    }

    private static void main_panel()
    {
        main_panel.setLayout(null);

        JButton buttonEnter = new JButton("Enter a new patient");
        buttonEnter.setBounds(15,20,200,20);
        JButton buttonDelete = new JButton("Delete a patient");
        buttonDelete.setBounds(15,60,200,20);
        JButton buttonFind = new JButton("Find and display a Patient");
        buttonFind.setBounds(15,100,200,20);
        JButton buttonModify = new JButton("Modify a patient profile");
        buttonModify.setBounds(15,140,200,20);
        JButton buttonSearch = new JButton("Search the database");
        buttonSearch.setBounds(15,180,200,20);
        main_panel.add(buttonEnter);
        main_panel.add(buttonDelete);
        main_panel.add(buttonFind);
        main_panel.add(buttonModify);
        main_panel.add(buttonSearch);

        buttonEnter.addActionListener(e -> {
            frame.remove(main_panel);
            frame.setTitle("Create New Profile");
            frame.setSize(350,620);
            frame.setContentPane(create_panel);
            frame.validate();
            frame.repaint();
        });

        buttonDelete.addActionListener(e -> {
            frame.remove(main_panel);
            frame.setTitle("Delete A Profile");
            frame.setSize(350,270);
            frame.setContentPane(delete_panel);
            frame.validate();
            frame.repaint();
        });

        buttonFind.addActionListener(e -> {
            frame.remove(main_panel);
            frame.setTitle("Find A Profile");
            frame.setSize(350,270);
            frame.setContentPane(find_panel);
            frame.validate();
            frame.repaint();
        });

        buttonModify.addActionListener(e -> {
            frame.remove(main_panel);
            frame.setTitle("Modify A Profile");
            frame.setSize(350,400);
            frame.setContentPane(modify_panel);
            frame.validate();
            frame.repaint();
        });

        buttonSearch.addActionListener(e -> {
            frame.remove(main_panel);
            frame.setTitle("Search the Database");
            frame.setSize(350,270);
            frame.setContentPane(search_panel);
            frame.validate();
            frame.repaint();
        });

    }

    private static void create_panel()
    {
        create_panel.setLayout(null);

        JLabel lname = new JLabel("Last Name:");
        lname.setBounds(50,60,200,20);
        create_panel.add(lname);

        JTextField T_lname = new JTextField(20);
        T_lname.setBounds(50,80,200,20);
        create_panel.add(T_lname);

        JLabel fname = new JLabel("First Name:");
        fname.setBounds(50,20,200,20);
        create_panel.add(fname);

        JTextField T_fname = new JTextField(20);
        T_fname.setBounds(50,40,200,20);
        create_panel.add(T_fname);

        JLabel address = new JLabel("Address:");
        address.setBounds(50,100,200,20);
        create_panel.add(address);

        JTextField T_address = new JTextField(20);
        T_address.setBounds(50,120,200,20);
        create_panel.add(T_address);

        JLabel phone = new JLabel("Phone Number:");
        phone.setBounds(50,140,200,20);
        create_panel.add(phone);

        JTextField T_phone = new JTextField(20);
        T_phone.setBounds(50,160,200,20);
        create_panel.add(T_phone);

        JLabel birth = new JLabel("Date of Birth:");
        birth.setBounds(50,180,200,20);
        create_panel.add(birth);

        JTextField T_birth = new JTextField(20);
        T_birth.setBounds(50,200,200,20);
        create_panel.add(T_birth);

        JLabel  i_type= new JLabel("Insurance Type:");
        i_type.setBounds(50,220,200,20);
        create_panel.add(i_type);

        JComboBox<String> T_i_type = new JComboBox<>();
        T_i_type.setBounds(50,240,200,20);
        create_panel.add(T_i_type);

        JLabel co_pay = new JLabel("Co pay:");
        co_pay.setBounds(50,260,200,20);
        create_panel.add(co_pay);

        JTextField T_co_pay = new JTextField(20);
        T_co_pay.setBounds(50,280,200,20);
        create_panel.add(T_co_pay);

        JLabel p_type = new JLabel("Patient Type:");
        p_type.setBounds(50,300,200,20);
        create_panel.add(p_type);

        JComboBox<String> T_p_type = new JComboBox<>();
        T_p_type.setBounds(50,320,200,20);
        create_panel.add(T_p_type);

        JLabel phy = new JLabel("Name of the physician:");
        phy.setBounds(50,340,200,20);
        create_panel.add(phy);

        JTextField T_phy = new JTextField(20);
        T_phy.setBounds(50,360,200,20);
        create_panel.add(T_phy);

        JLabel phone_phy = new JLabel("Phone number of the physician:");
        phone_phy.setBounds(50,380,200,20);
        create_panel.add(phone_phy);

        JTextField T_phone_phy = new JTextField(20);
        T_phone_phy.setBounds(50,400,200,20);
        create_panel.add(T_phone_phy);

        JLabel allergies = new JLabel("Allergies:");
        allergies.setBounds(50,420,200,20);
        create_panel.add(allergies);

        JComboBox<String> T_allergies = new JComboBox<>();
        T_allergies.setBounds(50,440,200,20);
        create_panel.add(T_allergies);

        JLabel illnesses = new JLabel("Illnesses:");
        illnesses.setBounds(50,460,200,20);
        create_panel.add(illnesses);

        JComboBox<String> T_illnesses = new JComboBox<>();
        T_illnesses.setBounds(50,480,200,20);
        create_panel.add(T_illnesses);

        JButton buttonSubmit = new JButton("Submit");
        buttonSubmit.setBounds(150,540,100,20);
        create_panel.add(buttonSubmit);

        JButton buttonMenu = new JButton("Menu");
        buttonMenu.setBounds(40,540,100,20);
        create_panel.add(buttonMenu);

        JLabel Label1 = new JLabel();
        Label1.setBounds(50,510,200,20);
        Label1.setText("Patient Already Exists or Busy");
        create_panel.add(Label1);
        Label1.setVisible(false);

        JLabel Label2 = new JLabel();
        Label2.setBounds(50,510,200,20);
        Label2.setText("Success!");
        create_panel.add(Label2);
        Label2.setVisible(false);

        JLabel Label3 = new JLabel();
        Label3.setBounds(50,510,200,20);
        Label3.setText("Invalid Value");
        create_panel.add(Label3);
        Label3.setVisible(false);



        T_i_type.addItem("Private");
        T_i_type.addItem("Government");

        T_p_type.addItem("Pediatric");
        T_p_type.addItem("Adult");
        T_p_type.addItem("Geriatric");

        T_allergies.addItem("Food");
        T_allergies.addItem("Medication");
        T_allergies.addItem("Seasonal");
        T_allergies.addItem("None");
        T_allergies.addItem("Other");

        T_illnesses.addItem("Diabetes");
        T_illnesses.addItem("CHD");
        T_illnesses.addItem("Asthma");
        T_illnesses.addItem("None");
        T_illnesses.addItem("Other");


        buttonSubmit.addActionListener(e -> {
            try {
                socket.request(filename);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            new_database.new_database("local.txt");
            Label1.setVisible(false);
            Label2.setVisible(false);
            Label3.setVisible(false);
            String str_i_type = "", str_p_type = " ", str_allergies = " ", str_illnesses = "";
            switch (T_i_type.getSelectedIndex()) {
                case 0:
                    str_i_type = "Private";
                    break;
                case 1:
                    str_i_type = "Government";
                    break;
            }
            switch (T_p_type.getSelectedIndex()) {
                case 0:
                    str_p_type = "Pediatric";
                    break;
                case 1:
                    str_p_type = "Adult";
                    break;
                case 2:
                    str_p_type = "Geriatric";
                    break;
            }
            switch (T_allergies.getSelectedIndex()) {
                case 0:
                    str_allergies = "Food";
                    break;
                case 1:
                    str_allergies = "Medication";
                    break;
                case 2:
                    str_allergies = "Seasonal";
                    break;
                case 3:
                    str_allergies = "None";
                    break;
                case 4:
                    str_allergies = "Other";
                    break;
            }
            switch (T_illnesses.getSelectedIndex()) {
                case 0:
                    str_illnesses = "Diabetes";
                    break;
                case 1:
                    str_illnesses = "CHD";
                    break;
                case 2:
                    str_illnesses = "Asthma";
                    break;
                case 3:
                    str_illnesses = "None";
                    break;
                case 4:
                    str_illnesses = "Other";
                    break;
            }

            int check =1;

            try {
                Float.parseFloat(T_co_pay.getText());
            }
            catch (NumberFormatException exception)
            {
                check=0;
                Label3.setVisible(true);

            }
            if(!Objects.equals(T_lname.getText(), "") &
                    !Objects.equals(T_fname.getText(), "") &
                    !Objects.equals(T_address.getText(), "") &
                    !Objects.equals(T_phone.getText(), "") &
                    !Objects.equals(T_birth.getText(), "") &
                    !Objects.equals(T_co_pay.getText(), "") &
                    !Objects.equals(T_phy.getText(), "") &
                    !Objects.equals(T_phone_phy.getText(), "") &
                    check==1
            )
            {
                float f = Float.parseFloat(T_co_pay.getText());

                if(new_database.enter(
                        T_lname.getText(),
                        T_fname.getText(),
                        T_address.getText(),
                        T_phone.getText(),
                        T_birth.getText(),
                        str_i_type,
                        str_p_type,
                        f,
                        T_phy.getText(),
                        T_phone_phy.getText(),
                        str_allergies,
                        str_illnesses)==0)
                {

                    //new_database.new_patient[0].print_all();
                    Label1.setVisible(true);
                }
                else
                {
                    String[] strings = new String[10];
                    strings[0]=T_address.getText();
                    strings[1]=T_phone.getText();
                    strings[2]=T_birth.getText();
                    strings[3]=str_i_type;
                    strings[4]=str_p_type;
                    strings[5]=T_co_pay.getText();
                    strings[6]=T_phy.getText();
                    strings[7]=T_phone_phy.getText();
                    strings[8]=str_allergies;
                    strings[9]=str_illnesses;
                    int check_c=0;
                    try {
                        check_c=socket.create(T_lname.getText(),T_fname.getText(),strings);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    if(check_c==1) {
                        Label2.setVisible(true);
                    }
                    else {Label1.setVisible(true);}
                }
            }
            else {Label3.setVisible(true);}

        });



        buttonMenu.addActionListener(e -> {
            Label1.setVisible(false);
            Label2.setVisible(false);
            frame.remove(create_panel);
            frame.setTitle("Menu");
            frame.setSize(250, 270);
            frame.setContentPane(main_panel);
            frame.validate();
            frame.repaint();
        });
    }

    private static void delete_panel()
    {
        delete_panel.setLayout(null);

        JLabel lname = new JLabel("Last Name:");
        lname.setBounds(50,20,200,20);
        delete_panel.add(lname);

        JTextField T_lname = new JTextField(20);
        T_lname.setBounds(50,40,200,20);
        delete_panel.add(T_lname);

        JLabel birth = new JLabel("Date of Birth:");
        birth.setBounds(50,60,200,20);
        delete_panel.add(birth);

        JTextField T_birth = new JTextField(20);
        T_birth.setBounds(50,80,200,20);
        delete_panel.add(T_birth);

        JButton buttonDelete = new JButton("Delete");
        buttonDelete.setBounds(150,140,100,20);
        delete_panel.add(buttonDelete);

        JButton buttonMenu = new JButton("Menu");
        buttonMenu.setBounds(40,140,100,20);
        delete_panel.add(buttonMenu);

        JLabel Label1 = new JLabel();
        Label1.setBounds(50,110,200,20);
        Label1.setText("Profile Not Found or Busy");
        delete_panel.add(Label1);
        Label1.setVisible(false);

        JLabel Label2 = new JLabel();
        Label2.setBounds(50,110,200,20);
        Label2.setText("Success!");
        delete_panel.add(Label2);
        Label2.setVisible(false);

        buttonDelete.addActionListener(e -> {
            try {
                socket.request(filename);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            new_database.new_database("local.txt");
            Label1.setVisible(false);
            Label2.setVisible(false);
            if(new_database.delete(T_lname.getText(),T_birth.getText())==1)
            {
                int check_d=0;
                try {
                    check_d=socket.delete(T_lname.getText(),T_birth.getText());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if(check_d==1) {
                    Label2.setVisible(true);
                }
                else {
                    Label1.setVisible(true);
                }
            }
            else
            {
                Label1.setVisible(true);
            }
        });

        buttonMenu.addActionListener(e -> {
            Label1.setVisible(false);
            Label2.setVisible(false);
            frame.remove(delete_panel);
            frame.setTitle("Menu");
            frame.setSize(250, 270);
            frame.setContentPane(main_panel);
            frame.validate();
            frame.repaint();
        });
    }

    private static void find_panel()
    {
        find_panel.setLayout(null);

        JLabel lname = new JLabel("Last Name:");
        lname.setBounds(50,20,200,20);
        find_panel.add(lname);

        JTextField T_lname = new JTextField(20);
        T_lname.setBounds(50,40,200,20);
        find_panel.add(T_lname);

        JLabel birth = new JLabel("Date of Birth:");
        birth.setBounds(50,60,200,20);
        find_panel.add(birth);

        JTextField T_birth = new JTextField(20);
        T_birth.setBounds(50,80,200,20);
        find_panel.add(T_birth);

        JButton buttonFind = new JButton("Find");
        buttonFind.setBounds(150,140,100,20);
        find_panel.add(buttonFind);

        JButton buttonMenu = new JButton("Menu");
        buttonMenu.setBounds(40,140,100,20);
        find_panel.add(buttonMenu);

        JLabel Label1 = new JLabel();
        Label1.setBounds(50,110,200,20);
        Label1.setText("Profile Not Found");
        find_panel.add(Label1);
        Label1.setVisible(false);

        JLabel Label2 = new JLabel();
        Label2.setBounds(50,110,200,20);
        Label2.setText("Success!");
        find_panel.add(Label2);
        Label2.setVisible(false);

        int y = 170;
        int x = 0;
        JLabel L_fname = new JLabel();
        L_fname.setBounds(50, y + x * 20, 200, 20);
        find_panel.add(L_fname);
        L_fname.setVisible(false);

        x++;
        JLabel L_lname = new JLabel();
        L_lname.setBounds(50, y + x * 20, 200, 20);
        find_panel.add(L_lname);
        L_lname.setVisible(false);

        x++;
        JLabel L_address = new JLabel();
        L_address.setBounds(50, y + x * 20, 200, 20);
        find_panel.add(L_address);
        L_address.setVisible(false);

        x++;
        JLabel L_phone = new JLabel();
        L_phone.setBounds(50, y + x * 20, 200, 20);
        find_panel.add(L_phone);
        L_phone.setVisible(false);

        x++;
        JLabel L_birth = new JLabel();
        L_birth.setBounds(50, y + x * 20, 200, 20);
        find_panel.add(L_birth);
        L_birth.setVisible(false);

        x++;
        JLabel L_i_type = new JLabel();
        L_i_type.setBounds(50, y + x * 20, 200, 20);
        find_panel.add(L_i_type);
        L_i_type.setVisible(false);

        x++;
        JLabel L_co_pay = new JLabel();
        L_co_pay.setBounds(50, y + x * 20, 200, 20);
        find_panel.add(L_co_pay);
        L_co_pay.setVisible(false);

        x++;
        JLabel L_p_type = new JLabel();
        L_p_type.setBounds(50, y + x * 20, 200, 20);
        find_panel.add(L_p_type);
        L_p_type.setVisible(false);

        x++;
        JLabel L_phy = new JLabel();
        L_phy.setBounds(50, y + x * 20, 250, 20);
        find_panel.add(L_phy);
        L_phy.setVisible(false);

        x++;
        JLabel L_phone_phy = new JLabel();
        L_phone_phy.setBounds(50, y + x * 20, 250, 20);
        find_panel.add(L_phone_phy);
        L_phone_phy.setVisible(false);

        x++;
        JLabel L_allergies = new JLabel();
        L_allergies.setBounds(50, y + x * 20, 200, 20);
        find_panel.add(L_allergies);
        L_allergies.setVisible(false);

        x++;
        JLabel L_illnesses = new JLabel();
        L_illnesses.setBounds(50, y + x * 20, 200, 20);
        find_panel.add(L_illnesses);
        L_illnesses.setVisible(false);

        buttonFind.addActionListener(e -> {
            try {
                socket.request(filename);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            new_database.new_database("local.txt");
            Label1.setVisible(false);
            Label2.setVisible(false);
            L_lname.setVisible(false);
            L_fname.setVisible(false);
            L_address.setVisible(false);
            L_birth.setVisible(false);
            L_phone.setVisible(false);
            L_i_type.setVisible(false);
            L_co_pay.setVisible(false);
            L_p_type.setVisible(false);
            L_phy.setVisible(false);
            L_phone_phy.setVisible(false);
            L_allergies.setVisible(false);
            L_illnesses.setVisible(false);
            if(new_database.find(T_lname.getText(),T_birth.getText())!=100)
            {
                int index=new_database.find(T_lname.getText(),T_birth.getText());
                Label2.setVisible(true);
                L_lname.setVisible(true);
                L_fname.setVisible(true);
                L_address.setVisible(true);
                L_birth.setVisible(true);
                L_phone.setVisible(true);
                L_i_type.setVisible(true);
                L_co_pay.setVisible(true);
                L_p_type.setVisible(true);
                L_phy.setVisible(true);
                L_phone_phy.setVisible(true);
                L_allergies.setVisible(true);
                L_illnesses.setVisible(true);
                L_lname.setText("Last Name: "+new_database.new_patient[index].getLast_name());
                L_fname.setText("First name: "+new_database.new_patient[index].getFirst_name());
                L_address.setText("Address: "+new_database.new_patient[index].getAddress());
                L_birth.setText("Data of Birth: "+new_database.new_patient[index].getData_of_Birth());
                L_phone.setText("Phone Number: "+new_database.new_patient[index].getPhone_number());
                L_i_type.setText("Insurance Type: "+new_database.new_patient[index].getInsurance_Type());
                L_co_pay.setText("Co pay: "+String.valueOf(new_database.new_patient[index].getCo_pay()));
                L_p_type.setText("Patient Type: "+new_database.new_patient[index].getPatient_Type());
                L_phy.setText("Name of the Physician: "+new_database.new_patient[index].getName_of_the_physician());
                L_phone_phy.setText("Contact of the physician: "+new_database.new_patient[index].getContact_phone_number_of_the_physician());
                L_allergies.setText("Allergies: "+new_database.new_patient[index].getAllergies());
                L_illnesses.setText("Illnesses: "+new_database.new_patient[index].getIllnesses());
                frame.setSize(350, 500);
            }
            else
            {
                Label1.setVisible(true);
                frame.setSize(350,270);
            }
        });

        buttonMenu.addActionListener(e -> {
            Label1.setVisible(false);
            Label2.setVisible(false);
            L_lname.setVisible(false);
            L_fname.setVisible(false);
            L_address.setVisible(false);
            L_birth.setVisible(false);
            L_phone.setVisible(false);
            L_i_type.setVisible(false);
            L_co_pay.setVisible(false);
            L_p_type.setVisible(false);
            L_phy.setVisible(false);
            L_phone_phy.setVisible(false);
            L_allergies.setVisible(false);
            L_illnesses.setVisible(false);
            frame.remove(find_panel);
            frame.setTitle("Menu");
            frame.setSize(250, 270);
            frame.setContentPane(main_panel);
            frame.validate();
            frame.repaint();
        });
    }

    private static void modify_panel()
    {
        modify_panel.setLayout(null);

        JLabel lname = new JLabel("Last Name:");
        lname.setBounds(50,20,200,20);
        modify_panel.add(lname);

        JTextField T_lname = new JTextField(20);
        T_lname.setBounds(50,40,200,20);
        modify_panel.add(T_lname);

        JLabel birth = new JLabel("Date of Birth:");
        birth.setBounds(50,60,200,20);
        modify_panel.add(birth);

        JTextField T_birth = new JTextField(20);
        T_birth.setBounds(50,80,200,20);
        modify_panel.add(T_birth);

        JLabel type = new JLabel("Type of Data:");
        type.setBounds(50,100,200,20);
        modify_panel.add(type);

        JComboBox<String> T_type = new JComboBox<>();
        T_type.setBounds(50,120,200,20);
        modify_panel.add(T_type);

        JLabel new_data = new JLabel("Enter New Data:");
        new_data.setBounds(50,140,200,20);
        modify_panel.add(new_data);

        JTextField T_new_data = new JTextField(20);
        T_new_data.setBounds(50,160,200,20);
        modify_panel.add(T_new_data);
        T_new_data.setEnabled(false);

        JComboBox<String> T_i_type = new JComboBox<>();
        T_i_type.setBounds(50,160,200,20);
        modify_panel.add(T_i_type);
        T_i_type.setVisible(false);

        JComboBox<String> T_p_type = new JComboBox<>();
        T_p_type.setBounds(50,160,200,20);
        modify_panel.add(T_p_type);
        T_p_type.setVisible(false);

        JComboBox<String> T_allergies = new JComboBox<>();
        T_allergies.setBounds(50,160,200,20);
        modify_panel.add(T_allergies);
        T_allergies.setVisible(false);

        JComboBox<String> T_illnesses = new JComboBox<>();
        T_illnesses.setBounds(50,160,200,20);
        modify_panel.add(T_illnesses);
        T_illnesses.setVisible(false);

        JButton buttonSelect = new JButton("Select");
        buttonSelect.setBounds(250,120,70,20);
        modify_panel.add(buttonSelect);

        JButton buttonFind = new JButton("Modify");
        buttonFind.setBounds(150,220,100,20);
        modify_panel.add(buttonFind);
        buttonFind.setEnabled(false);

        JButton buttonMenu = new JButton("Menu");
        buttonMenu.setBounds(40,220,100,20);
        modify_panel.add(buttonMenu);

        JLabel Label1 = new JLabel();
        Label1.setBounds(50,190,200,20);
        Label1.setText("Profile Not Found or Busy");
        modify_panel.add(Label1);
        Label1.setVisible(false);

        JLabel Label2 = new JLabel();
        Label2.setBounds(50,190,200,20);
        Label2.setText("Success!");
        modify_panel.add(Label2);
        Label2.setVisible(false);

        JLabel Label3 = new JLabel();
        Label3.setBounds(50,190,250,20);
        Label3.setText("Click Select after change the type of data");
        modify_panel.add(Label3);
        Label3.setVisible(false);

        int y = 250;
        int x = 0;
        JLabel L_fname = new JLabel();
        L_fname.setBounds(50, y + x * 20, 200, 20);
        modify_panel.add(L_fname);
        L_fname.setVisible(false);

        x++;
        JLabel L_lname = new JLabel();
        L_lname.setBounds(50, y + x * 20, 200, 20);
        modify_panel.add(L_lname);
        L_lname.setVisible(false);

        x++;
        JLabel L_address = new JLabel();
        L_address.setBounds(50, y + x * 20, 200, 20);
        modify_panel.add(L_address);
        L_address.setVisible(false);

        x++;
        JLabel L_phone = new JLabel();
        L_phone.setBounds(50, y + x * 20, 200, 20);
        modify_panel.add(L_phone);
        L_phone.setVisible(false);

        x++;
        JLabel L_birth = new JLabel();
        L_birth.setBounds(50, y + x * 20, 200, 20);
        modify_panel.add(L_birth);
        L_birth.setVisible(false);

        x++;
        JLabel L_i_type = new JLabel();
        L_i_type.setBounds(50, y + x * 20, 200, 20);
        modify_panel.add(L_i_type);
        L_i_type.setVisible(false);

        x++;
        JLabel L_co_pay = new JLabel();
        L_co_pay.setBounds(50, y + x * 20, 200, 20);
        modify_panel.add(L_co_pay);
        L_co_pay.setVisible(false);

        x++;
        JLabel L_p_type = new JLabel();
        L_p_type.setBounds(50, y + x * 20, 200, 20);
        modify_panel.add(L_p_type);
        L_p_type.setVisible(false);

        x++;
        JLabel L_phy = new JLabel();
        L_phy.setBounds(50, y + x * 20, 250, 20);
        modify_panel.add(L_phy);
        L_phy.setVisible(false);

        x++;
        JLabel L_phone_phy = new JLabel();
        L_phone_phy.setBounds(50, y + x * 20, 250, 20);
        modify_panel.add(L_phone_phy);
        L_phone_phy.setVisible(false);

        x++;
        JLabel L_allergies = new JLabel();
        L_allergies.setBounds(50, y + x * 20, 200, 20);
        modify_panel.add(L_allergies);
        L_allergies.setVisible(false);

        x++;
        JLabel L_illnesses = new JLabel();
        L_illnesses.setBounds(50, y + x * 20, 200, 20);
        modify_panel.add(L_illnesses);
        L_illnesses.setVisible(false);

        T_type.addItem("First Name");
        T_type.addItem("Last Name");
        T_type.addItem("Address");
        T_type.addItem("Phone Number");
        T_type.addItem("Insurance Type");
        T_type.addItem("Co-pay");
        T_type.addItem("Patient Type");
        T_type.addItem("Name of the physician");
        T_type.addItem("Phone number of the physician");
        T_type.addItem("Allergies");
        T_type.addItem("Illnesses");

        T_i_type.addItem("Private");
        T_i_type.addItem("Government");

        T_p_type.addItem("Pediatric");
        T_p_type.addItem("Adult");
        T_p_type.addItem("Geriatric");

        T_allergies.addItem("Food");
        T_allergies.addItem("Medication");
        T_allergies.addItem("Seasonal");
        T_allergies.addItem("None");
        T_allergies.addItem("Other");

        T_illnesses.addItem("Diabetes");
        T_illnesses.addItem("CHD");
        T_illnesses.addItem("Asthma");
        T_illnesses.addItem("None");
        T_illnesses.addItem("Other");

        AtomicInteger change_check= new AtomicInteger();

        buttonSelect.addActionListener(e ->
        {
            Label3.setVisible(false);
            T_i_type.setVisible(false);
            T_new_data.setVisible(false);
            T_p_type.setVisible(false);
            T_allergies.setVisible(false);
            T_illnesses.setVisible(false);
            change_check.set(T_type.getSelectedIndex());
            System.out.println(T_type.getSelectedIndex());
            if(T_type.getSelectedIndex()==4)
            {
                T_i_type.setVisible(true);
            }
            else if(T_type.getSelectedIndex()==6)
            {
                T_p_type.setVisible(true);
            }
            else if (T_type.getSelectedIndex()==9)
            {
                T_allergies.setVisible(true);
            }
            else if (T_type.getSelectedIndex()==10)
            {
                T_illnesses.setVisible(true);
            }
            else
            {
                T_new_data.setVisible(true);
                T_new_data.setEnabled(true);
            }
            buttonFind.setEnabled(true);
        });

        buttonFind.addActionListener(e -> {
            try {
                socket.request(filename);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            new_database.new_database("local.txt");
            Label1.setVisible(false);
            Label2.setVisible(false);
            Label3.setVisible(false);
            L_lname.setVisible(false);
            L_fname.setVisible(false);
            L_address.setVisible(false);
            L_birth.setVisible(false);
            L_phone.setVisible(false);
            L_i_type.setVisible(false);
            L_co_pay.setVisible(false);
            L_p_type.setVisible(false);
            L_phy.setVisible(false);
            L_phone_phy.setVisible(false);
            L_allergies.setVisible(false);
            L_illnesses.setVisible(false);
            if(new_database.find(T_lname.getText(),T_birth.getText())!=100)
            {
                if(change_check.get()==T_type.getSelectedIndex())
                {
                    int index=new_database.find(T_lname.getText(),T_birth.getText());

                    String str = "";
                    if(T_type.getSelectedIndex()==4)
                    {
                        switch (T_i_type.getSelectedIndex()) {
                            case 0:
                                str = "Private";
                                break;
                            case 1:
                                str = "Government";
                                break;
                        }
                    }
                    else if(T_type.getSelectedIndex()==6)
                    {
                        switch (T_p_type.getSelectedIndex()) {
                            case 0:
                                str = "Pediatric";
                                break;
                            case 1:
                                str = "Adult";
                                break;
                            case 2:
                                str = "Geriatric";
                                break;
                        }
                    }
                    else if (T_type.getSelectedIndex()==9) {
                        switch (T_allergies.getSelectedIndex()) {
                            case 0:
                                str = "Food";
                                break;
                            case 1:
                                str = "Medication";
                                break;
                            case 2:
                                str = "Seasonal";
                                break;
                            case 3:
                                str = "None";
                                break;
                            case 4:
                                str = "Other";
                                break;
                        }
                    }
                    else if (T_type.getSelectedIndex()==10)
                    {
                        switch (T_illnesses.getSelectedIndex()) {
                            case 0:
                                str = "Diabetes";
                                break;
                            case 1:
                                str = "CHD";
                                break;
                            case 2:
                                str = "Asthma";
                                break;
                            case 3:
                                str = "None";
                                break;
                            case 4:
                                str = "Other";
                                break;
                        }
                    }
                    else
                    {
                        str = T_new_data.getText();
                    }
                    int check_m=0;
                    try {
                        check_m=socket.modify(index,change_check.get(),str);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    if(check_m==1) {
                        Label2.setVisible(true);
                        L_lname.setVisible(true);
                        L_fname.setVisible(true);
                        L_address.setVisible(true);
                        L_birth.setVisible(true);
                        L_phone.setVisible(true);
                        L_i_type.setVisible(true);
                        L_co_pay.setVisible(true);
                        L_p_type.setVisible(true);
                        L_phy.setVisible(true);
                        L_phone_phy.setVisible(true);
                        L_allergies.setVisible(true);
                        L_illnesses.setVisible(true);
                        L_lname.setText("Last Name: "+new_database.new_patient[index].getLast_name());
                        L_fname.setText("First name: "+new_database.new_patient[index].getFirst_name());
                        L_address.setText("Address: "+new_database.new_patient[index].getAddress());
                        L_birth.setText("Data of Birth: "+new_database.new_patient[index].getData_of_Birth());
                        L_phone.setText("Phone Number: "+new_database.new_patient[index].getPhone_number());
                        L_i_type.setText("Insurance Type: "+new_database.new_patient[index].getInsurance_Type());
                        L_co_pay.setText("Co pay: "+String.valueOf(new_database.new_patient[index].getCo_pay()));
                        L_p_type.setText("Patient Type: "+new_database.new_patient[index].getPatient_Type());
                        L_phy.setText("Name of the Physician: "+new_database.new_patient[index].getName_of_the_physician());
                        L_phone_phy.setText("Contact of the physician: "+new_database.new_patient[index].getContact_phone_number_of_the_physician());
                        L_allergies.setText("Allergies: "+new_database.new_patient[index].getAllergies());
                        L_illnesses.setText("Illnesses: "+new_database.new_patient[index].getIllnesses());
                        frame.setSize(350, 600);
                    }
                    else{
                        Label1.setVisible(true);
                        frame.setSize(350,400);
                    }
                }
                else
                {
                    Label3.setVisible(true);
                    frame.setSize(350,400);
                }
            }
            else
            {
                Label1.setVisible(true);
                frame.setSize(350,400);
            }
        });

        buttonMenu.addActionListener(e -> {
            Label1.setVisible(false);
            Label2.setVisible(false);
            Label3.setVisible(false);
            L_lname.setVisible(false);
            L_fname.setVisible(false);
            L_address.setVisible(false);
            L_birth.setVisible(false);
            L_phone.setVisible(false);
            L_i_type.setVisible(false);
            L_co_pay.setVisible(false);
            L_p_type.setVisible(false);
            L_phy.setVisible(false);
            L_phone_phy.setVisible(false);
            L_allergies.setVisible(false);
            L_illnesses.setVisible(false);
            frame.remove(main_panel);
            frame.setTitle("Menu");
            frame.setSize(250, 270);
            frame.setContentPane(main_panel);
            frame.validate();
            frame.repaint();
        });
    }

    private static void search_panel()
    {
        search_panel.setLayout(null);

        JLabel type = new JLabel("Type of Data:");
        type.setBounds(50,20,200,20);
        search_panel.add(type);

        JComboBox<String> T_type = new JComboBox<>();
        T_type.setBounds(50,40,200,20);
        search_panel.add(T_type);

        JButton buttonSearch = new JButton("Search");
        buttonSearch.setBounds(150,140,100,20);
        search_panel.add(buttonSearch);

        JButton buttonMenu = new JButton("Menu");
        buttonMenu.setBounds(40,140,100,20);
        search_panel.add(buttonMenu);

        JLabel Label1 = new JLabel();
        Label1.setBounds(50,110,200,20);
        Label1.setText("No Matching Profile");
        search_panel.add(Label1);
        Label1.setVisible(false);

        JLabel Label2 = new JLabel();
        Label2.setBounds(50,110,200,20);
        Label2.setText("Success!");
        search_panel.add(Label2);
        Label2.setVisible(false);

        JLabel Label3 = new JLabel();
        Label3.setBounds(50,110,250,20);
        Label3.setText("Click Select after change the type of data");
        search_panel.add(Label3);
        Label3.setVisible(false);

        JLabel new_data = new JLabel("Enter New Data:");
        new_data.setBounds(50,60,200,20);
        search_panel.add(new_data);

        JTextField T_new_data = new JTextField(20);
        T_new_data.setBounds(50,80,200,20);
        search_panel.add(T_new_data);
        T_new_data.setEnabled(false);

        JComboBox<String> T_i_type = new JComboBox<>();
        T_i_type.setBounds(50,80,200,20);
        search_panel.add(T_i_type);
        T_i_type.setVisible(false);

        JComboBox<String> T_p_type = new JComboBox<>();
        T_p_type.setBounds(50,80,200,20);
        search_panel.add(T_p_type);
        T_p_type.setVisible(false);

        JComboBox<String> T_allergies = new JComboBox<>();
        T_allergies.setBounds(50,80,200,20);
        search_panel.add(T_allergies);
        T_allergies.setVisible(false);

        JComboBox<String> T_illnesses = new JComboBox<>();
        T_illnesses.setBounds(50,80,200,20);
        search_panel.add(T_illnesses);
        T_illnesses.setVisible(false);

        JButton buttonSelect = new JButton("Select");
        buttonSelect.setBounds(250,40,70,20);
        search_panel.add(buttonSelect);

        JList<String> list_box = new JList<>();
        JScrollPane scrollPane = new JScrollPane(list_box);
        scrollPane.setBounds(30,170,250,300);
        search_panel.add(scrollPane);
        scrollPane.setVisible(false);

        T_type.addItem("Insurance Type");
        T_type.addItem("Patient Type");
        T_type.addItem("Name of the physician");
        T_type.addItem("Allergies");
        T_type.addItem("Illnesses");

        T_i_type.addItem("Private");
        T_i_type.addItem("Government");

        T_p_type.addItem("Pediatric");
        T_p_type.addItem("Adult");
        T_p_type.addItem("Geriatric");

        T_allergies.addItem("Food");
        T_allergies.addItem("Medication");
        T_allergies.addItem("Seasonal");
        T_allergies.addItem("None");
        T_allergies.addItem("Other");

        T_illnesses.addItem("Diabetes");
        T_illnesses.addItem("CHD");
        T_illnesses.addItem("Asthma");
        T_illnesses.addItem("None");
        T_illnesses.addItem("Other");

        AtomicInteger change_check= new AtomicInteger();

        buttonSelect.addActionListener(e ->
        {
            Label3.setVisible(false);
            T_i_type.setVisible(false);
            T_new_data.setVisible(false);
            T_p_type.setVisible(false);
            T_allergies.setVisible(false);
            T_illnesses.setVisible(false);
            change_check.set(T_type.getSelectedIndex());
            System.out.println(T_type.getSelectedIndex());
            if(T_type.getSelectedIndex()==0)
            {
                T_i_type.setVisible(true);
            }
            else if(T_type.getSelectedIndex()==1)
            {
                T_p_type.setVisible(true);
            }
            else if (T_type.getSelectedIndex()==3)
            {
                T_allergies.setVisible(true);
            }
            else if (T_type.getSelectedIndex()==4)
            {
                T_illnesses.setVisible(true);
            }
            else
            {
                T_new_data.setVisible(true);
                T_new_data.setEnabled(true);
            }
            buttonSearch.setEnabled(true);
        });



        buttonSearch.addActionListener(e -> {
            try {
                socket.request(filename);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            new_database.new_database("local.txt");
            Label1.setVisible(false);
            Label2.setVisible(false);
            Label3.setVisible(false);
            scrollPane.setVisible(false);

            if(change_check.get()==T_type.getSelectedIndex())
            {
                String str = "";
                if(T_type.getSelectedIndex()==0)
                {
                    switch (T_i_type.getSelectedIndex()) {
                        case 0:
                            str = "Private";
                            break;
                        case 1:
                            str = "Government";
                            break;
                    }
                }
                else if(T_type.getSelectedIndex()==1)
                {
                    switch (T_p_type.getSelectedIndex()) {
                        case 0:
                            str = "Pediatric";
                            break;
                        case 1:
                            str = "Adult";
                            break;
                        case 2:
                            str = "Geriatric";
                            break;
                    }
                }
                else if (T_type.getSelectedIndex()==3)
                {
                    switch (T_allergies.getSelectedIndex()) {
                        case 0:
                            str = "Food";
                            break;
                        case 1:
                            str = "Medication";
                            break;
                        case 2:
                            str = "Seasonal";
                            break;
                        case 3:
                            str = "None";
                            break;
                        case 4:
                            str = "Other";
                            break;
                    }
                }
                else if (T_type.getSelectedIndex()==4)
                {
                    switch (T_illnesses.getSelectedIndex()) {
                        case 0:
                            str = "Diabetes";
                            break;
                        case 1:
                            str = "CHD";
                            break;
                        case 2:
                            str = "Asthma";
                            break;
                        case 3:
                            str = "None";
                            break;
                        case 4:
                            str = "Other";
                            break;
                    }
                }
                else
                {
                    str = T_new_data.getText();
                }
                int[] index = new_database.search(change_check.get(),str);
                int j=-1;
                DefaultListModel<String> model = new DefaultListModel<>();
                for (int i=0;i<100;i++) {
                    int y = 170;
                    if(index[i]==1)
                    {
                        model.addElement("First name: "+new_database.new_patient[i].getFirst_name());
                        model.addElement("Last Name: "+new_database.new_patient[i].getLast_name());
                        model.addElement("Phone Number: "+new_database.new_patient[i].getPhone_number());
                        model.addElement("\n");
                        j++;
                    }
                }
                if(j==-1)
                {
                    Label1.setVisible(true);
                    scrollPane.setVisible(false);
                }
                else {
                    Label2.setVisible(true);
                    list_box.setModel(model);
                    scrollPane.setVisible(true);
                    frame.setSize(350, 550);
                }
            }
            else
            {
                Label3.setVisible(true);
                frame.setSize(350,270);
            }

        });

        buttonMenu.addActionListener(e -> {
            Label1.setVisible(false);
            Label2.setVisible(false);
            scrollPane.setVisible(false);
            frame.remove(search_panel);
            frame.setTitle("Menu");
            frame.setSize(250, 270);
            frame.setContentPane(main_panel);
            frame.validate();
            frame.repaint();
        });
    }

    public static void main(String[] args) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        intro_panel();
        main_panel();
        create_panel();
        delete_panel();
        find_panel();
        modify_panel();
        search_panel();

        frame.setContentPane(intro_panel);
        frame.setSize(370, 180);
        frame.setVisible(true);

    }

}
