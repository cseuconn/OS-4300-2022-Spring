//Include all the different things we'll be using
const Game = require('./gamelogic.js');

const express = require('express');
const url = require('url');
const http = require('http');
const mysql = require('mysql');
const session = require('express-session');
const path = require('path');

const app = express();
const server = app.listen(3000);

console.log('Running!');

app.use(express.static('../public'));

const socket = require('socket.io');
const io = socket(server);

let games = new Map();

io.sockets.on('connection', socket => {
    console.log(`new connection from id: ${socket.id}`);
   // console.log(socket);

    const sockUrl = socket.handshake.headers.referer;
    const id = url.parse(sockUrl, true).query.id;
    if(id && games.has(id) && games.get(id).hasRoom() && games.get(id).interval == undefined) {
        socket.join(id);
        let playerId = games.get(id).addConnection(socket);
        socket.emit('your-id', {playerId});
    } else if(!games.has(id)) { //invalid code
        socket.emit('err', {
            errMsg: `Room code: [${id}] is not valid`
        });
    } else if(!games.get(id).hasRoom()) { //room full
        socket.emit('err', {
            errMsg: `Room: [${id}] is already full`
        });
    } else if(games.get(id).interval != undefined) { //game started
        socket.emit('err', {
            errMsg: `Game in room: [${id}] has already started`
        });
    }

    socket.on('disconnect', () => {
        if(id && games.has(id)) {
            games.get(id).removeConnection(socket);
        }
        console.log(`user, id: ${socket.id}, disconnected`);
    });

    socket.on('create', data => { 
        const id = data.id;

        let game = new Game(data.numPlayers, io.to(id), id);
        games.set(id, game);

        console.log('new game');

        //game.start();
    });

    socket.on('start', () => { 
        if(id && games.has(id)) {
            games.get(id).start();
            console.log('start');
        }
    });

    socket.on('move', (data) => { 
        if(id && games.has(id)) {
            games.get(id).move(data.dirStr, socket);
        }
    });
});

const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'password',
    database: 'nodelogin'
});
connection.connect(function(err) {
    if (err) throw err;
    console.log("Connected!");
});
//Init express
app.use(session({
	secret: 'secret',
	resave: true,
	saveUninitialized: true
}));
app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(express.static(path.join(__dirname, '/css')));

//Declare the route that will bring us to login
//use a GET request so wheen the user comes to index.html it directs them to login
app.get('/', function(request, response){
    response.sendFile(path.join(__dirname + '/index.html'));
});

//Similarly, this is the redrection for the AUTH action
app.post('/auth', function(request, response){
    let username = request.body.username;
    let password = request.body.password;

    if(username && password){
        connection.query('SELECT * FROM accounts WHERE username = ? AND password = ?', [username, password], function(error, results, fields) {
			// If there is an issue with the query, output the error
			if (error) throw error;
			// If the account exists
			if (results.length > 0) {
				// Authenticate the user
				request.session.loggedin = true;
                console.log("Successful login from user: " + username);
				request.session.username = username;
				// Redirect to home page
				response.redirect('/home');
			} else {
				// response.send('Incorrect Username and/or Password!');
                response.redirect('/err.html');
			}			
			response.end();
		});
    } else{
        response.send('Please Enter Username and Password!');
        response.end();
    }
});

//Also need to write a redirection for the REG action
app.post('/reg', function(request, response){
    let username = request.body.username;
    let password = request.body.password;
    let passed = true;
    if(username && password){
        //Check if that username is taken
        connection.query('SELECT * FROM accounts WHERE username = ?', [username], function(error, results, fields) {
			// If there is an issue with the query, output the error
			if (error) throw error;
			// If the account exists
			if (results.length > 0) {
                passed = false;
                response.redirect('/taken.html');
                response.end();
            }
            if(results.length == 0){
                //If we are here, then we know that the username they tried isn't taken
                connection.query("INSERT INTO `accounts` (`username`, `password`) VALUES (?, ?)", [username, password], function (err, result) {
                    if (err) throw err;
                    //If they successfully register, then log them in
                    request.session.loggedin = true;
                    request.session.username = username;
                    response.redirect('/home');
                    response.end();
                });
            }
        }); 
    }else{
        response.send('Please Enter Username and Password!');
        response.end();
    }
});
//Handle the landing page once logged in
app.get('/home', function(request, response) {
	// If the user is loggedin
    console.log("HERE");
	if (request.session.loggedin) {
		// Output username
		// response.send('Welcome back, ' + request.session.username + '!');
        console.log(__dirname);
        const public = path.resolve(__dirname, '../public');
        response.sendFile(path.join(public+ '/home.html'));
	} else {
		// Not logged in
		response.send('Please login to view this page!');
	}
	//response.end();
});