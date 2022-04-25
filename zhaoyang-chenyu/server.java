import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class server {
    public static void main(String[] args) throws IOException {
        Map<String, Integer> d = new HashMap<>();
        d.put("test.txt",1);
        d.put("new.txt",1);

        ServerSocket serverSocket = new ServerSocket(9000);
        while(true){
            Socket socket = serverSocket.accept();

            InputStream inputStream_filename = socket.getInputStream();
            ByteArrayOutputStream byte_file = new ByteArrayOutputStream();
            byte[] buffer_file = new byte[1024];
            int len_id;
            while((len_id=inputStream_filename.read(buffer_file))!=-1){
                byte_file.write(buffer_file,0,len_id);
            }
            String filename = byte_file.toString();
            System.out.println(filename);
            String[] co=filename.split(",");
            if(Objects.equals(co[1], "None"))
            {
                //send file
                OutputStream outputStream_file = socket.getOutputStream();
                FileInputStream fileInputStream_file = new FileInputStream(co[0]);
                byte[] buffer = new byte[1024];
                int len;
                while ((len=fileInputStream_file.read(buffer))!=-1){
                    outputStream_file.write(buffer,0,len);
                }
                fileInputStream_file.close();
                outputStream_file.close();
            }
            else if(Objects.equals(co[3],"create") & d.get(co[0])==1)
            {
                d.put(co[0],0);
                float f= Float.parseFloat(co[9]);
                PatientDatabase patientDatabase = new PatientDatabase();
                patientDatabase.new_database(co[0]);
                patientDatabase.enter(co[1],co[2],co[4],co[5],co[6],co[7],co[8],f,co[10],co[11],co[12],co[13]);
                patientDatabase.save();

                OutputStream os = socket.getOutputStream();
                String String_rec="ok";
                System.out.println(String_rec);
                os.write(String_rec.getBytes());
                os.close();
                d.put(co[0],1);
            }
            else if(Objects.equals(co[3],"delete") & d.get(co[0])==1)
            {
                d.put(co[0],0);
                PatientDatabase patientDatabase = new PatientDatabase();
                patientDatabase.new_database(co[0]);
                patientDatabase.delete(co[1],co[2]);
                patientDatabase.save();

                OutputStream os = socket.getOutputStream();
                String String_rec="ok";
                System.out.println(String_rec);
                os.write(String_rec.getBytes());
                os.close();
                d.put(co[0],1);
            }
            else if(Objects.equals(co[3],"modify") & d.get(co[0])==1)
            {
                d.put(co[0],0);
                int index = Integer.parseInt(co[1]);
                int id = Integer.parseInt(co[2]);
                PatientDatabase patientDatabase = new PatientDatabase();
                patientDatabase.new_database(co[0]);
                patientDatabase.update(index,id,co[4]);
                patientDatabase.save();

                OutputStream os = socket.getOutputStream();
                String String_rec="ok";
                System.out.println(String_rec);
                os.write(String_rec.getBytes());
                os.close();
                d.put(co[0],1);
            }
            else if(d.get(co[0])==0)
            {
                OutputStream os = socket.getOutputStream();
                String String_rec="fail";
                System.out.println(String_rec);
                os.write(String_rec.getBytes());
                os.close();
            }




            byte_file.close();
            inputStream_filename.close();
            socket.close();
        }
    }
}
