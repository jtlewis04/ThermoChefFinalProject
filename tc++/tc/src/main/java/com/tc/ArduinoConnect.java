package com.tc;

import com.fazecast.jSerialComm.*;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.String;

public class ArduinoConnect {
    private static SerialPort comport;
    private static InputStream data;
    public static ArrayList<Integer> x;
    public static ArrayList<Integer> y;

    public static void readArduino() throws IOException, InterruptedException {
        x = new ArrayList<>();
        y = new ArrayList<>();
        SerialPort[] allAvailableComPorts = SerialPort.getCommPorts();



        // Lists all available serial and connects to the first one
        // Prerequisite to function: Arduino Uno is the only free Serial connection
        for (SerialPort eachComPort : allAvailableComPorts)
            System.out.println("List of all available serial ports: " + eachComPort.getSystemPortName());
        comport = SerialPort.getCommPort(allAvailableComPorts[0].getSystemPortName());
        comport.setComPortTimeouts(0, 0, 0);
        comport.setComPortParameters(9600, 8, 1, 0);
        if (comport.openPort()) {
            System.out.println("Port open");
            for (int i = 0; i < 500; i++) {
                data = comport.getInputStream();
                if (data.available() > 0) {

                    // Creates a byte array to store all bytes in the Serial connection
                    
                    byte[] serialBuffer = new byte[data.available()];
                    // Assigns bytes from Serial conenction to byte array
                    data.readNBytes(serialBuffer, 0, data.available());
                    if(serialBuffer.length==3)
                    {
                        int combinedByte = (serialBuffer[1]<<8) | serialBuffer[2];
                        System.out.println(combinedByte);
                        System.out.println(serialBuffer[0]);
                        x.add(combinedByte);
                        y.add(Byte.toUnsignedInt(serialBuffer[0]));
                    }
                    else
                    {
                        for(Byte bite:serialBuffer)
                        {
                            System.out.println(bite);
                        }
                        x.add(Byte.toUnsignedInt(serialBuffer[1]));
                        y.add(Byte.toUnsignedInt(serialBuffer[0]));
                    }
                }
                // Timeout enables proper reading
                Thread.sleep(100);
            }
        }
    }
}
