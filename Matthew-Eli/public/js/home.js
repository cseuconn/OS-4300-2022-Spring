let socket;

function onload() {
    socket = io('http://25.77.62.75:3000/');


    document.getElementById('hostButton').onclick = function(event) {
        const id = randomId();
        socket.emit('create', {numPlayers: 3, id});

        window.location.href = `http://25.77.62.75:3000/game.html?id=${id}`;
    };

    document.getElementById('joinButton').onclick = function(event) {
        const id = document.getElementById('joinInput').value;
        window.location.href = `http://25.77.62.75:3000/game.html?id=${id}`;
    };
}

function randomId() {
    const chars = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789~_-';
    let id = '';
    for(let i = 0; i < 16; i++) {
        id += chars[Math.floor(Math.random()*chars.length)];
    }
    return id;
}