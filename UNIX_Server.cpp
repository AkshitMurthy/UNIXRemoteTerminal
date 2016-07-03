#include <sys/socket.h> //socket(), accept(),bind(),listen()
#include <arpa/inet.h> //htons(),htonl()
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include<iostream>
using namespace std;

int main()
{
    int listenfd = 0, connfd = 0,n=0;
    struct sockaddr_in serv_addr; 

    char sendBuff[10];
		    
    //int socket(Domain(Protocol family),Type(Semantics),Protocol);	
    listenfd = socket(AF_INET, SOCK_STREAM, 0); //AF:Address Family, PF:Protocol Family, INET: Internet
    if(listenfd<0)
    {
	printf("Couldnt create socket");
	exit(0);
    }
    //memset(string str,char c,int n): copies c into the first n chars of str.
    memset(&serv_addr, '0', sizeof(serv_addr));
    memset(sendBuff, '0', sizeof(sendBuff)); 

    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = htonl(INADDR_ANY);
    serv_addr.sin_port = htons(3000); 

    if(bind(listenfd, (struct sockaddr*)&serv_addr, sizeof(serv_addr))<0) //binds the sockaddr struct to the socket created
    {
	cout<<"bind() error";
	exit(0);
   }
	
    cout<<"Server listening"<<endl;

    listen(listenfd, 10); //10: backlog: max length to which q of pending conns may go 

    while(1)
    {
        connfd = accept(listenfd, (struct sockaddr*)NULL, NULL); //accept(sockid,struct sockaddr*,structlen_t);
	cout<<"Connected"<<endl;
	pid_t p=fork();			//Spawn a new process for each new client

	if(p<0)
	{
	   cout<<"Error servicing request\n";
	}
	else if(p>0)wait();
	else
	{
	   while(1)
	   {
		char recvBuff[1000];
		close(listenfd);
		if ( (n = read(connfd, recvBuff, sizeof(recvBuff)-1)) > 0)
	    	{
			recvBuff[n]=0;
			cout<<"Recevied:"<<recvBuff<<endl;
			if(strcmp(recvBuff,"exit")==0)exit(0);
			system(recvBuff);		//Running the command on terminal
	    	} 

		else if(n <= 0)
	    	{
			printf("\n Connecction terminated\n");
			close(connfd);
		 	exit(0);
	    	} 
	
		
           }
	}
    }
}
