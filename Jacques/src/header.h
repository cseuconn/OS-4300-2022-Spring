typedef struct maze_t{ // maze matrix struct
    struct cell_t **matrix; // matrix of cells
    int w; // size of maze
    int h;
}maze_t;

typedef struct cell_t{ // maze cell
    int x,y; // coordinate of cell
    int visited; // have we visited this cell yet?
    int north,east,south,west; // walls
}cell_t;

typedef struct stack_t{
    cell_t* cell;
    int top;
}stack_t;

void allocateMaze(maze_t* maze);
void printMaze(maze_t* maze);
void designMaze(maze_t* maze);
int neighbor(maze_t* maze);
void removeEdge(maze_t* maze, int edge);


















/*typedef struct maze_t{ // maze matrix struct
    struct cell **matrix;
    int w; // size of maze
    int h;
}maze_t;

typedef struct cell_t{ // maze cell
    int x,y; // coordinate of cell
    int visited;
    int north,east,south,west; // walls
    //char type;
}cell_t;

void allocateMaze(cell_t** maze, int width, int height);
void printMaze(cell_t** maze, int width, int height);
void designMaze(cell_t** maze);
int neighbor(cell_t cell);*/