import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class server {
    public static void main(String[] args) throws IOException {
        Map<String, Integer> lock = new HashMap<>();
        lock.put("test.txt",1);
        lock.put("new.txt",1);

        ServerSocket serverSocket = new ServerSocket(9000);
        while(true){
            Socket socket = serverSocket.accept();
            //receive request
            InputStream inputStream_filename = socket.getInputStream();
            ByteArrayOutputStream byte_file = new ByteArrayOutputStream();
            byte[] buffer_file = new byte[1024];
            int len_id;
            while((len_id=inputStream_filename.read(buffer_file))!=-1){
                byte_file.write(buffer_file,0,len_id);
            }
            String new_request = byte_file.toString();
            String[] request=new_request.split(",");
            if(Objects.equals(request[1], "None"))
            {
                //send file
                OutputStream outputStream_file = socket.getOutputStream();
                FileInputStream fileInputStream_file = new FileInputStream(request[0]);
                byte[] buffer = new byte[1024];
                int len;
                while ((len=fileInputStream_file.read(buffer))!=-1){
                    outputStream_file.write(buffer,0,len);
                }
                fileInputStream_file.close();
                outputStream_file.close();
            }
            else if(Objects.equals(request[3],"create") & lock.get(request[0])==1)
            {
                lock.put(request[0],0);
                float f= Float.parseFloat(request[9]);
                PatientDatabase patientDatabase = new PatientDatabase();
                patientDatabase.new_database(request[0]);
                patientDatabase.enter(request[1],request[2],request[4],request[5],request[6],request[7],request[8],f,request[10],request[11],request[12],request[13]);
                patientDatabase.save();

                OutputStream os = socket.getOutputStream();
                String String_rec="ok";
                System.out.println(String_rec);
                os.write(String_rec.getBytes());
                os.close();
                lock.put(request[0],1);
            }
            else if(Objects.equals(request[3],"delete") & lock.get(request[0])==1)
            {
                lock.put(request[0],0);
                PatientDatabase patientDatabase = new PatientDatabase();
                patientDatabase.new_database(request[0]);
                patientDatabase.delete(request[1],request[2]);
                patientDatabase.save();

                OutputStream os = socket.getOutputStream();
                String String_rec="ok";
                System.out.println(String_rec);
                os.write(String_rec.getBytes());
                os.close();
                lock.put(request[0],1);
            }
            else if(Objects.equals(request[3],"modify") & lock.get(request[0])==1)
            {
                lock.put(request[0],0);
                int index = Integer.parseInt(request[1]);
                int id = Integer.parseInt(request[2]);
                PatientDatabase patientDatabase = new PatientDatabase();
                patientDatabase.new_database(request[0]);
                patientDatabase.update(index,id,request[4]);
                patientDatabase.save();

                OutputStream os = socket.getOutputStream();
                String String_rec="ok";
                System.out.println(String_rec);
                os.write(String_rec.getBytes());
                os.close();
                lock.put(request[0],1);
            }
            else if(lock.get(request[0])==0)
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
