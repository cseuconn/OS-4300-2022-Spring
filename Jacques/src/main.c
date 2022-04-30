#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <math.h>
#include <time.h>
#include "header.h"

int width, height;
int curx, cury;

int main(int argc, char* argv[]){
    time_t t;
    srand((unsigned)time(&t));
    width = atoi(argv[1]);
    height = atoi(argv[2]);
    maze_t maze = {NULL,0,0};
    maze.w = width;
    maze.h = height;
    allocateMaze(&maze);
    printMaze(&maze);
    designMaze(&maze);
    printMaze(&maze);
    return 0;
}

int neighbor(maze_t* maze){
    int result[4];
    int hasNeighbors = 0;
    int out;
    memset(result, 0, sizeof(result));
    if((maze->matrix[cury][curx].y > 0) && maze->matrix[cury][curx].north &&
        !maze->matrix[cury-1][curx].visited){ // Possible north
        result[0] = 1;
        hasNeighbors = 1;
    }
    if((maze->matrix[cury][curx].x < width-1) && maze->matrix[cury][curx].east &&
        !maze->matrix[cury][curx+1].visited){ // Possible east
        result[1] = 1;
        hasNeighbors = 1;
    }
    if((maze->matrix[cury][curx].y < height-1) && maze->matrix[cury][curx].south &&
        !maze->matrix[cury+1][curx].visited){ // Possible south
        result[2] = 1;
        hasNeighbors = 1;
    }
    if((maze->matrix[cury][curx].x > 0) && maze->matrix[cury][curx].west &&
        !maze->matrix[cury][curx-1].visited){ // Possible west
        result[3] = 1;
        hasNeighbors = 1;
    }

    if(hasNeighbors){
        while(1){
            if(result[out = (rand() % (sizeof(result)/sizeof(result[0])))]){
                return out;
            }   
        }
    }
    else return -1;
}

void designMaze(maze_t* maze){
    curx = 0; cury = 0;
    int randNeighbor;
    stack_t* stack = (struct stack_t*)malloc(sizeof(struct stack_t));
    stack->cell = (cell_t*)malloc(sizeof(cell_t)*maze->w*maze->h);
    stack->top = 0;
    stack->cell[stack->top].x = maze->matrix[cury][curx].x;
    stack->cell[stack->top].y = maze->matrix[cury][curx].y;
    
    maze->matrix[cury][curx].visited = 1;

    while(stack->top > -1){
        if((randNeighbor = neighbor(maze)) != -1){
            removeEdge(maze,randNeighbor);
            maze->matrix[cury][curx].visited = 1;
            stack->top++;
            stack->cell[stack->top] = maze->matrix[cury][curx];
            //printMaze(maze);
			// print the changes to maze step by step with this printMaze()
        }
        else{
            stack->top--;
            curx = stack->cell[stack->top].x;
            cury = stack->cell[stack->top].y;
        }
    }
}

void removeEdge(maze_t* maze, int edge){
    if(edge == 0){ // Remove current north edge
        maze->matrix[cury][curx].north = 0;
        maze->matrix[cury-1][curx].south = 0;
        cury--;
    }
    if(edge == 1){ // Remove current east edge
        maze->matrix[cury][curx].east = 0;
        maze->matrix[cury][curx+1].west = 0;
        curx++;
    }
    if(edge == 2){ // Remove current south edge
        maze->matrix[cury][curx].south = 0;
        maze->matrix[cury+1][curx].north = 0;
        cury++;
    }
    if(edge == 3){ // Remove current west edge
        maze->matrix[cury][curx].west = 0;
        maze->matrix[cury][curx-1].east = 0;
        curx--;
    }
}

void allocateMaze(maze_t* maze){
    maze->matrix = malloc(sizeof(cell_t*) * maze->h);
    for (int i = 0; i < maze->h; i++) {
        maze->matrix[i] = malloc(sizeof(cell_t) * maze->w);
        for (int j = 0; j < maze->w; j++) {
            cell_t cell = {j,i,0,1,1,1,1};
            maze->matrix[i][j] = cell;
        }
    }
}

void printMaze(maze_t* maze){
    char bufferH[30*width];
    char bufferW[30*width];
    for(int i = 0; i < height; i++){
        memset(bufferH, 0, sizeof bufferH);
        memset(bufferW, 0, sizeof bufferW);
        strcat(bufferH,"\033[0;31m\u2588");
        for(int j = 0; j < width; j++){
            if(maze->matrix[i][j].north == 0)
                strcat(bufferH,"\033[0;30m\u2588\033[0;31m\u2588");
            else
                strcat(bufferH,"\033[0;31m\u2588\u2588");
            if(maze->matrix[i][j].west == 0){
                strcat(bufferW,"\033[0;30m\u2588");
            }
            else{
                strcat(bufferW,"\033[0;31m\u2588");
            }
            if(maze->matrix[i][j].visited == 1){
                strcat(bufferW,"\033[0;30m\u2588");
            }
            else{
                strcat(bufferW,"\033[0;36m\u2588");
            }
        }
        printf("%s\n",bufferH);
        printf("%s\033[0;31m\u2588\n",bufferW);
    }
    for(int i = 0; i < 2*width+1; i++){
        printf("\033[0;31m\u2588");
    }
    printf("\n\n");
}
