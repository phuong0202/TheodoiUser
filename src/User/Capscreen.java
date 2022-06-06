/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Socket;
import javax.imageio.ImageIO;

/**
 *
 * @author Thanh Phuong
 */
public class Capscreen implements Runnable{
	private Socket client;
       
	public Capscreen(Socket client) {
		this.client = client;   
               
	}
	
	@Override
	public void run() {
		try {					                                      
                  Toolkit toolkit = Toolkit.getDefaultToolkit();
             Dimension dimensions = toolkit.getScreenSize();
                 Robot robot = new Robot();  // Robot class
                 BufferedImage screenshot = robot.createScreenCapture(new Rectangle(dimensions));
                 ImageIO.write(screenshot,"png",client.getOutputStream());
                 client.close();                                
                } catch (Exception e) {
		}
                
	}
        
}
