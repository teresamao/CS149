/* cs149 Assignment6

The purpose of this assignment is to practice making UNIX I/O system calls in C. 
In a multiplexed manner, your main process will read from multiple files and also from 
the standard input (the terminal). 

*/

#include <sys/wait.h>
#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

int main(int argc, char *argv[])
{
   int pfd[2];
   pid_t cpid;
   char buf;
   int i = 0;
   int timeout = 0;

   assert(argc == 2);

   if (pipe(pfd) == -1) { perror("pipe"); exit(EXIT_FAILURE); }

   cpid = fork();
   if (cpid == -1) { perror("fork"); exit(EXIT_FAILURE); }

   if (cpid == 0) {    /* Child write to pipe */
       close(pfd[0]);	       /* Close unused read end */
       while(!timeout) {
	       i++;
	       write(pdf[0], "message" + i, strlen(message) + 1);
	       //sleep random time
	   }
       close(pfd[1]);
       _exit(EXIT_SUCCESS);

   } else {	       /* Parent read from argv[1] to pipe */
       close(pfd[1]);	       /* Close unused write end */

       while (read(pfd[0], &buf, 1) > 0)
	   write(STDOUT_FILENO, &buf, 1);

       write(STDOUT_FILENO, "\n", 1);
       close(pfd[0]);	      
       wait(NULL);	       /* Wait for child */
       exit(EXIT_SUCCESS);
   }
}