#include <editline/readline.h>
#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/errno.h>
#include <sys/wait.h>
#include <unistd.h>
#include <wordexp.h>

char **parse_input(char *input)
{
    char **command = malloc(8 * sizeof(char *));
    if (command == NULL) {
        perror("malloc failed");
        exit(1);
    }

    char *separator = " ";
    int i = 0;
    char *parsed = strtok(input, separator);

    while (parsed != NULL) {
        command[i] = parsed;
        i++;
        parsed = strtok(NULL, separator);
    }

    command[i] = NULL;

    return command;
}

int main(void)
{
    while (1) {
        char wd[1024];
        getcwd(wd, 1024);
        char prompt[1024];
        sprintf(prompt, "%s $ ", wd);

        char *input = readline(prompt);

        // Skip empty input
        if (strcmp(input, "") == 0 || strcmp(input, " ") == 0) {
            continue;
        }

        // Make a copy of the input before passing to `parse_input` to save command in shell history
        char input_copy[strlen(input)];
        strcpy(input_copy, input);
        add_history(input_copy);

        // char **arg_list = parse_input(input);
        // Parse input
        wordexp_t we;
        wordexp(input, &we, 0);
        char **arg_list = we.we_wordv;

        if (strcmp(arg_list[0], "exit") == 0) {
            // Free heap-allocated memory
            free(input);
            wordfree(&we);

            // Terminate shell
            exit(0);
        } else if (strcmp(arg_list[0], "cd") == 0) {
            if (arg_list[1] == NULL) {
                if (chdir(getenv("HOME")) < 0) {
                    perror("cd failed");
                }
            } else {
                if (chdir(arg_list[1]) < 0) {
                    perror("cd failed");
                }
            }

            continue;
        }

        // Check if command exists
        char which[1024];
        sprintf(which, "which %s > /dev/null 2>&1", arg_list[0]);
        if (system(which)) {
            printf("%s command was not found. Try something else\n", arg_list[0]);
            continue;
        }

        pid_t pid = fork();
        if (pid == 0) {
            // Execute command; if it fails, print an error
            if (execvp(arg_list[0], arg_list) == -1) {
                printf("%s command failed to run\n", arg_list[0]);
            }
        } else {
            // dead child is reaped
            waitpid(pid, NULL, 0);
        }

        free(input);
        wordfree(&we);
    }

    return 0;
}
