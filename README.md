UNIX Remote Terminal is Android app that allows a person to control a UNIX system through an Android phone
The project/folder has the following files:
1. The Android app project(for development)
2. The app (for testing)
3. The server code to run on the host
4. The README file

Steps to run:
1.Run the server file 'UNIX_Server.cpp' on a UNIX system:
 Open terminal and type the following commands:
	
	g++ -o <output_name> UNIX_Server.cpp

   followed by
   	
   	./<output_name>
 
  The server is running as the prompt says.
  
2. Install and run the app on an Android device

3. Enter the IP address of the UNIX system. (this can be found by the 'ifconfig' command)

4. Once the app shows 'Connected' and the server shows 'Connected', the connection is established, else there is some error.

5. Enter any command on the app.( there are obviously some limitations)
	
