package cs4120;

import java.util.Scanner;
import java.io.*;
import java.net.*;

public class TexterClient {
	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);

		System.out.println("clent started");
		try {
			Socket socket = new Socket("localhost", 8000);

			System.out.println("clent connected to server");

			ObjectOutputStream outputToServer = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream  inputFromServer = new ObjectInputStream(socket.getInputStream());


			while(true) {
				System.out.println("Enter a message: ");
				String message = input.nextLine();

				outputToServer.writeObject(message);

				message = (String)inputFromServer.readObject();

				System.out.println("Server: " + message);

				//				message = (String)inputFromServer.readObject();
			}}catch(Exception ex) {System.out.println(ex);}
	}
}

