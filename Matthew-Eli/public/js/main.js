let connectedSpan, maxSpan, canvas, gc, width, height;

let socket;

function move(dirStr) {
    socket.emit('move', { dirStr });
}

function showCycle(data, i) {
    let cycle = data.players[i];
    let cols = data.cols;
    //let rows = data.rows;
    let scl = width/cols;
    console.log(cycle.col1);
    gc.beginPath();
    gc.lineWidth = Math.floor(scl*cycle.sw*0.8);
    gc.lineCap = "square";
    gc.strokeStyle = cycle.col1;
    gc.moveTo((cycle.trail[0].x+0.5)*scl, (cycle.trail[0].y+0.5)*scl);
    for(let t of cycle.trail) {
      gc.lineTo((t.x+0.5)*scl, (t.y+0.5)*scl);
      //fill(100);
      //rect(t.x*scl, t.y*scl, scl, scl);
    }
    gc.stroke();
    //strokeWeight(scl*cycle.sw*0.8);
    //strokeCap(PROJECT);
    //noFill();
    //endShape();

    /*gc.fillStyle = 'goldenrod';
    gc.fillRect(0*scl, 0*scl, scl*cycle.sw, scl*cycle.sw);
    gc.fillRect((cols-1)*scl, (cols-1)*scl, scl*cycle.sw, scl*cycle.sw);*/
  
    gc.beginPath();
    gc.lineWidth = Math.floor(scl*cycle.sw*0.3);
    gc.lineCap = "square";
    gc.strokeStyle = 'rgb(255,255,255,0.5)';
    for(let t of cycle.trail) {
      gc.lineTo((t.x+0.5)*scl, (t.y+0.5)*scl);
    }
    gc.stroke();

    gc.fillStyle = cycle.col2;
    gc.fillRect(cycle.trail.at(-1).x*scl, cycle.trail.at(-1).y*scl, scl*cycle.sw, scl*cycle.sw);
    
    // let cs = this.checkSpeed(ps);
    // if(!cs) return;
    // fill(0,255,255);
    // rect(cs.x*scl, cs.y*scl, scl, scl);
}

function onload() {
    socket = io('http://25.77.62.75:3000/');

    canvas = document.getElementById('canvas');
    let room_label = document.getElementById('roomCodeP');
    const urlParams = new URLSearchParams(window.location.search);
    const room_number = urlParams.get('id');
    // console.log(room_number);
    room_label.innerHTML = room_number;

    resize();
    gc = canvas.getContext("2d");
    
    gc.fillStyle = 'grey';
    gc.fillRect(0, 0, width, height);
    gc.stroke();

    connectedSpan = document.getElementById('connectedSpan');
    maxSpan = document.getElementById('maxSpan');

    socket.on('tick', data => {
        gc.fillStyle = 'grey';
        gc.fillRect(0, 0, width, height);
        for(let i = 0; i < data.connections; i++) {
            if(data.players[i].alive) showCycle(data, i);
        }
        /*gc.fillStyle = 'green';
        gc.fillRect(0,       0,        1, 1);
        gc.fillRect(width-1, 0,        1, 1)
        gc.fillRect(0,       height-1, 1, 1);
        gc.fillRect(width-1, height-1, 1, 1);*/
    });

    socket.on('connection-change', data => {
        console.log(data);
        connectedSpan.innerHTML = data.connections;
        maxSpan.innerHTML = data.maxConnections;
    });

    socket.on('your-id', data => {
        myId = data.playerId;
    });

    socket.on('err', data => {
        alert(data.errMsg);
        window.location.href = `http://25.77.62.75:3000/home.html`;
    });

    document.getElementById('startButton').onclick = function(event) {
        socket.emit('start', {} );
    }
}

function resize() {
    width = window.getComputedStyle(canvas, null).getPropertyValue("width")
    canvas.setAttribute('width', width);
    height = window.getComputedStyle(canvas, null).getPropertyValue("height")
    canvas.setAttribute('height', height);
    width = Math.floor(parseFloat(width));
    height = Math.floor(parseFloat(height));
}

let mouseIsDown = false;
function mouseup(event) {
    mouseIsDown = false;
}

function mousedown(event) {
    mouseIsDown = true;
}

function mousemove(event) {
    if(!mouseIsDown) return;
    gc.fillStyle = 'red';
    gc.fillRect(event.offsetX-13, event.offsetY-13, 26, 26);
    /*socket.emit('mouse', {  
        x: event.offsetX,
        y: event.offsetY
    });*/
}