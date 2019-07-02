package org.jeeplus.cmd;

import org.jeeplus.cmd.equipment.EquipmentCmd;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        System.out.println(System.currentTimeMillis());
        System.out.println(EquipmentCmd.getDeviceKey("192.168.1.253"));
        System.out.println(System.currentTimeMillis());
    }
}
