import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

public class MIS_socket {
    public Socket socket;
    public String filename;
    public int s=0;

    public MIS_socket(String name) throws IOException {
        filename=name;
    }

    public int request(String file_name) throws IOException {
        socket= new Socket(InetAddress.getByName("127.0.0.1"),9000);
        filename=file_name;
        String string=file_name+","+"None"+","+"None"+","+"None";
        //request filename
        OutputStream os = socket.getOutputStream();
        os.write(string.getBytes());
        socket.shutdownOutput();
        //receive file
        InputStream inputStream_file = socket.getInputStream();
        FileOutputStream file = new FileOutputStream(new File("local.txt"));
        byte[] buffer_file = new byte[1024];
        int len_file;
        while((len_file=inputStream_file.read(buffer_file))!=-1){
            file.write(buffer_file,0,len_file);
        }
        file.close();
        inputStream_file.close();
        os.close();
        socket.close();
        return 1;
    }

    public int create(String LN,String birth,String[] l) throws IOException {
        socket= new Socket(InetAddress.getByName("127.0.0.1"),9000);
        OutputStream os = socket.getOutputStream();
        String String_create=filename+","
                +LN+"," +birth+","
                +"create"+","
                +l[0]+","+l[1]+","+l[2]+","+l[3]+","+l[4]+","+l[5]+","+l[6]+","+l[7]+","+l[8]+","+l[9];
        os.write(String_create.getBytes());
        socket.shutdownOutput();

        InputStream inputStream_rec = socket.getInputStream();
        ByteArrayOutputStream byte_rec = new ByteArrayOutputStream();
        byte[] buffer_file = new byte[1024];
        int len_id;
        while((len_id=inputStream_rec.read(buffer_file))!=-1){
            byte_rec.write(buffer_file,0,len_id);
        }
        String rec = byte_rec.toString();
        inputStream_rec.close();
        byte_rec.close();
        os.close();
        socket.close();
        if(Objects.equals(rec, "ok")){
            return 1;
        }
        return 0;
    }

    public int modify(int index,int id,String new_data) throws IOException {
        socket= new Socket(InetAddress.getByName("127.0.0.1"),9000);
        OutputStream os = socket.getOutputStream();
        String String_modify = filename+","+index+","+id+","+"modify"+","+new_data;
        os.write(String_modify.getBytes());
        socket.shutdownOutput();

        InputStream inputStream_rec = socket.getInputStream();
        ByteArrayOutputStream byte_rec = new ByteArrayOutputStream();
        byte[] buffer_file = new byte[1024];
        int len_id;
        while((len_id=inputStream_rec.read(buffer_file))!=-1){
            byte_rec.write(buffer_file,0,len_id);
        }
        String rec = byte_rec.toString();
        inputStream_rec.close();
        byte_rec.close();
        os.close();
        socket.close();
        if(Objects.equals(rec, "ok")){
            return 1;
        }
        return 0;
    }

    public int delete(String LN,String birth) throws IOException {
        socket= new Socket(InetAddress.getByName("127.0.0.1"),9000);
        OutputStream os = socket.getOutputStream();
        String String_create=filename+","+LN+","+birth+","+"delete"+","+"None"+","+"None";
        os.write(String_create.getBytes());
        socket.shutdownOutput();

        InputStream inputStream_rec = socket.getInputStream();
        ByteArrayOutputStream byte_rec = new ByteArrayOutputStream();
        byte[] buffer_file = new byte[1024];
        int len_id;
        while((len_id=inputStream_rec.read(buffer_file))!=-1){
            byte_rec.write(buffer_file,0,len_id);
        }
        String rec = byte_rec.toString();
        inputStream_rec.close();
        byte_rec.close();
        os.close();
        socket.close();
        if(Objects.equals(rec, "ok")){
            return 1;
        }
        return 0;
    }

}
