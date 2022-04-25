const cols = 40;
const rows = 40;
const scl = 10;

function color(r, g, b) {
    return `rgb(${r},${g},${b})`;   
}

class Game {
    constructor(numPlayers, room, gameId) {
        this.numPlayers = numPlayers;
        this.room = room;
        this.gameId = gameId;
        this.frameCount = 0;
        this.tickDelay = 100;
        this.connections = new Set();
        this.interval = undefined;
        this.playerCountAtStart;

        this.players = [];
        this.players.push( new Player(cols-5, rows-5, -1, 0, color(0,0,255), color(0,0,128)) );
        this.players.push( new Player(4, 4, 1, 0, color(255,0,0), color(128,0,0)) );
        this.players.push( new Player(cols-5, 4, 0, 1, color(0,128,128), color(0,64,64)) );
        this.players.push( new Player(4, rows-5, 0, -1, color(0,150,32), color(0,75,16)) );
    
        this.sockIndex = {};
    }

    hasRoom() {
        return this.connections.size < this.numPlayers;
    }

    addConnection(socket) {
        if(!this.connections.has(socket)) {
            this.connections.add(socket);
            console.log('new game connection');
            this.room.emit('connection-change', {
                connections: this.connections.size,
                maxConnections: this.numPlayers
            });
            this.sockIndex[socket.id] = this.connections.size-1;
            return this.connections.size-1;
        }
    }

    removeConnection(socket) {
        if(this.connections.delete(socket)) {
            console.log('lost game connection');
            this.room.emit('connection-change', {
                connections: this.connections.size,
                maxConnections: this.numPlayers
            });
            if(this.connections.size == 0) {
                clearInterval(this.interval);
                this.interval = undefined;
            }
        }
    }

    start() {
        this.interval = setInterval(() => {
            this.frameCount++;
            this.room.emit('tick', {
                frameCount: this.frameCount,
                players: this.players,
                connections: this.connections.size,
                cols,
                scl,
                rows
            }); 
            this.playerCountAtStart = this.connections.size-1;
            for(let i = this.players.length-1; i >= 0; i--) {
                let p = this.players[i];
                if(p.checkHit(this.players.slice(0, this.playerCountAtStart))) {
                  //console.log('HIT');
                  p.alive = false;
                  //tokill.push(this.players.splice(i, 1)[0]);
                  //playing = this.players.length > 1;
                }
                //console.log(p.frameSkip);
                if(this.frameCount % p.frameSkip == 0) {
                  p.move();
                }
            }

        }, this.tickDelay);
    }

    move(d, sock) {
        console.log(sock.id);
        console.log(this.sockIndex[sock.id]);
        console.log(d);
        switch(d) {
            case 'up'   : this.players[this.sockIndex[sock.id]].setdir( 0, -1); break;
            case 'down' : this.players[this.sockIndex[sock.id]].setdir( 0,  1); break;
            case 'left' : this.players[this.sockIndex[sock.id]].setdir(-1,  0); break;
            case 'right': this.players[this.sockIndex[sock.id]].setdir( 1,  0); break;
        }

    }
}

class Player {
    constructor(x, y, vx, vy, col1, col2) {
        this.trail = [{x,y}];
        this.col1 = col1;
        this.col2 = col2;
        this.setdir(vx,vy);
        this.slowSkip = 6;
        this.fastSkip = 3;
        this.frameSkip = this.slowSkip;
        this.sw = 1;
        this.alive = true;
    }

    setdir(x, y) {
        this.dir = {x, y};
    }
      
    move() {
      if(this.dir.x != 0) {
        this.trail.push({
          x: this.trail[this.trail.length -1].x + this.dir.x,
          y: this.trail[this.trail.length -1].y
        });
      } else if(this.dir.y != 0) {
        this.trail.push({
          x: this.trail[this.trail.length -1].x,
          y: this.trail[this.trail.length -1].y + this.dir.y
        });
      }
    }
    
    checkSpeed(players) {
      for(let other of players) {      
        if(other === this) continue;
        
        for(let t of other.trail) {
          if(this.trail[this.trail.length -1].y == t.y) {
          if(this.trail[this.trail.length -1].x == t.x+1 || this.trail[this.trail.length -1].x == t.x-1) {
            return true;
          }
          } else if(this.trail[this.trail.length -1].x == t.x){
            if(this.trail[this.trail.length -1].y == t.y+1 || this.trail[this.trail.length -1].y == t.y-1) {
            return true;
          }
          }
        }
      }
    }
    
    checkHit(players) {
      if(this.trail[this.trail.length -1].x < 0 || this.trail[this.trail.length -1].x >= cols || this.trail[this.trail.length -1].y < 0 || this.trail[this.trail.length -1].y >= rows) return true;
      
      for(let other of players) {
        let trail = other.trail;
        
        if(other === this) trail = other.trail.slice(0, -1);
        
        for(let t of trail) {
          if(this.trail[this.trail.length -1].x == t.x && this.trail[this.trail.length -1].y == t.y) {
            return true;
          }
        }
      }
      
      this.frameSkip = this.checkSpeed(players) ? this.fastSkip : this.slowSkip;
    }
    
    /*show() {
      noStroke();
      beginShape();
      for(let t of this.trail) {
        vertex((t.x+0.5)*scl, (t.y+0.5)*scl);
        fill(100);
        rect(t.x*scl, t.y*scl, scl, scl);
      }
      stroke(this.col1);
      strokeWeight(scl*this.sw*0.8);
      strokeCap(PROJECT);
      noFill();
      endShape();
      
      beginShape();
      for(let t of this.trail) {
        vertex((t.x+0.5)*scl, (t.y+0.5)*scl);
        //fill(100);
        //rect(t.x*scl, t.y*scl, scl, scl);
      }
      stroke(lerpColor(this.col1, color(255), 0.5));
      strokeWeight(scl*this.sw*0.3);
      strokeCap(PROJECT);
      noFill();
      endShape();
      
      fill(this.col2);
      noStroke();
      //if(this.frameSkip === this.fastSkip) fill(255,255,0);
      rect(this.trail[this.trail.length -1].x*scl, this.trail[this.trail.length -1].y*scl, scl*this.sw, scl*this.sw);
    
      // let cs = this.checkSpeed(ps);
      // if(!cs) return;
      // fill(0,255,255);
      // rect(cs.x*scl, cs.y*scl, scl, scl);
    }*/
}

module.exports = Game;