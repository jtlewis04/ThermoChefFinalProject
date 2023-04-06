import com.fazecast.jSerialComm.*;
import java.io.InputStream;
import java.io.IOException;



public class App {
    public static SerialPort comport;
    public static InputStream data;
    public static void main(String[] args) throws IOException, InterruptedException{
        SerialPort[] allAvailableComPorts = SerialPort.getCommPorts();
        
        for(SerialPort eachComPort:allAvailableComPorts)
            System.out.println("List of all available serial ports: " + eachComPort.getSystemPortName());
        comport = SerialPort.getCommPort(allAvailableComPorts[0].getSystemPortName());
        comport.setComPortTimeouts(0, 0, 0);
        comport.setComPortParameters(9600, 8, 1, 0);
        if(comport.openPort())
        {
            System.out.println("Port open");
            for(int i = 0; i<500; i++){
            data = comport.getInputStream();
                if(data.available()>0){
                    byte[] serialBuffer = new byte[data.available()];
                    data.readNBytes(serialBuffer, 0, data.available());
                    for(byte num:serialBuffer)
                    {
                        System.out.println(num);
                    }
                }
            Thread.sleep(50);
            }
        }
      
    }
}
