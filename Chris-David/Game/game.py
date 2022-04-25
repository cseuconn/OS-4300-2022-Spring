# The default controls are w, a, d and spacebar for player 1, and the arrow keys and right CTRL for player 2

import math
import pygame
import random
import sys

from bullet import *
from comet import *
from player import *

FPS = 60
SCREEN_HEIGHT = 900
SCREEN_WIDTH = 1800
WHITE = (255, 255, 255)
BLACK = (0, 0, 0)
GREEN = (0, 255, 0)
BLUE = (0, 0, 128)
x = 0
y = 1

class App:
    """
    Class responsible for program control flow.
    """

    def __init__(self):
        self.screen = pygame.display.get_surface()
        self.screen_rect = self.screen.get_rect()
        self.clock = pygame.time.Clock()
        self.game_over = False
        self.done = False
        self.font = pygame.font.Font('freesansbold.ttf', 32)
        
        self.comets = [Comet(-i) for i in range(0, SCREEN_HEIGHT, SCREEN_HEIGHT // 4)] # List of comets, comets start with negative yStart so that they spawn above the screen (the top of the screen is y = 0)
        self.player1 = Player(90, [(SCREEN_WIDTH - 64) * 1 / 4, SCREEN_HEIGHT - 3 * 64], (pygame.K_w, pygame.K_a, pygame.K_d, pygame.K_SPACE)) # Tuple is for controls
        self.player2 = Player(90, [(SCREEN_WIDTH - 64) * 3 / 4, SCREEN_HEIGHT - 3 * 64], (pygame.K_UP, pygame.K_LEFT, pygame.K_RIGHT, pygame.K_RCTRL))

        self.p1Bullets = [Bullet() for i in range(0, 4, 1)] # Generates 4 bullets for each player, is put on the side for now
        self.p2Bullets = [Bullet() for i in range(0, 4, 1)]

    def overlap(self, h1, h2): # h2 needs to be the smaller hitbox
            # ((x0, y0), (x1, y1))	
            xMatch = ((h2[0][x] > h1[0][x] and h2[0][x] < h1[1][x]) or (h2[1][x] > h1[0][x] and h2[1][x] < h1[1][x]))
            yMatch = ((h2[0][y] > h1[0][y] and h2[0][y] < h1[1][y]) or (h2[1][y] > h1[0][y] and h2[1][y] < h1[1][y]))
            return xMatch and yMatch

    def event_loop(self):
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                self.done = True

    def update_position(self, pressedKeys, dt):
        # pressedKeys = pygame.key.get_pressed()
        for comet in self.comets:
            comet.update_position(dt)
        self.player1.update_position(pressedKeys, dt)
        self.player2.update_position(pressedKeys, dt)
        for p1Bullet in self.p1Bullets:
            p1Bullet.update_position(dt)
        for p2Bullet in self.p2Bullets:
            p2Bullet.update_position(dt)

    def check_fire(self, pressedKeys, dt):
        if(self.player1.can_fire(pressedKeys, dt)):
            for p1Bullet in self.p1Bullets:
                if(not p1Bullet.is_active()): # Finds a bullet in the list is is not active
                    p1Bullet.fire(self.player1.get_height(), self.player1.get_width(), self.player1.get_angle(), self.player1.get_position()) # And fires it
                    break;

        if(self.player2.can_fire(pressedKeys, dt)):
            for p2Bullet in self.p2Bullets:
                if(not p2Bullet.is_active()): # Finds a bullet in the list is is not active
                    p2Bullet.fire(self.player2.get_height(), self.player2.get_width(), self.player2.get_angle(), self.player2.get_position()) # And fires it
                    break;

    def check_collide(self):
        for comet in self.comets:
            if(self.overlap(comet.get_hitbox(), self.player1.get_hitbox())):
                self.player1.lives-=1
                self.player1.respawn()
                comet.respawn()
            if(self.overlap(comet.get_hitbox(), self.player2.get_hitbox())):
                self.player2.lives-=1
                self.player2.respawn()
                comet.respawn()

        for p1Bullet in self.p1Bullets:
            if(p1Bullet.is_active() and self.overlap(self.player2.get_hitbox(), p1Bullet.get_hitbox())):
                self.player2.lives-=1
                self.player2.respawn()
                p1Bullet.on_standby()

        for p2Bullet in self.p2Bullets:
            if(p2Bullet.is_active() and self.overlap(self.player1.get_hitbox(), p2Bullet.get_hitbox())):
                self.player1.lives-=1
                self.player1.respawn()
                p2Bullet.on_standby()


    def render(self):
        if(self.game_over == True):
            self.screen.fill(WHITE)
            if (self.player1.lives < 1):
                    self.screen.blit(self.font.render("PLAYER 2 WINS!", True, GREEN, BLUE), (SCREEN_WIDTH/2,SCREEN_HEIGHT/2))
            elif (self.player2.lives < 1):
                    self.screen.blit(self.font.render("PLAYER 1 WINS!", True, GREEN, BLUE), (SCREEN_WIDTH/2,SCREEN_HEIGHT/2))
            self.screen.blit(self.font.render("Press spacebar to play again or esc to exit", True, GREEN, BLUE), (SCREEN_WIDTH/2,SCREEN_HEIGHT/2+100))
            pygame.display.update()
        else:
                self.screen.fill(WHITE)
                for comet in self.comets:
                    comet.render(self.screen)
                for p1Bullet in self.p1Bullets:
                    p1Bullet.render(self.screen)
                for p2Bullet in self.p2Bullets:
                    p2Bullet.render(self.screen)
                self.player1.render(self.screen)
                self.player2.render(self.screen)
                self.screen.blit(self.font.render("PLAYER 1 LIVES: " + str(self.player1.lives), True, GREEN, BLUE), (0,0))
                self.screen.blit(self.font.render("PLAYER 2 LIVES: " + str(self.player2.lives), True, GREEN, BLUE), (SCREEN_WIDTH-310,0))
                pygame.display.update()
    
    def check_over(self, pressedKeys):
        if (self.player1.lives < 1 or self.player2.lives < 1):
            self.game_over = True
            self.player1.respawn()
            self.player2.respawn()
        if (self.game_over == True):
            if (pressedKeys[pygame.K_SPACE]):
                for comet in self.comets:
                    comet.respawn()
                self.player1.lives = 10
                self.player2.lives = 10
                self.game_over = False
            elif (pressedKeys[pygame.K_ESCAPE]):
                self.done = True


    def main_loop(self):
        dt = 0
        self.clock.tick(FPS)
        while not self.done:
            self.event_loop()
            pressedKeys = pygame.key.get_pressed()
            self.update_position(pressedKeys, dt)
            self.check_fire(pressedKeys, dt)
            self.check_collide()
            self.check_over(pressedKeys)
            self.render()
            dt = self.clock.tick(FPS)/1000.0 # clock.tick delays so that the game runs at the target FPS and returns milliseconds since the last call to clock.tick
		

def main():
    pygame.init()
    pygame.display.set_caption("Game")
    pygame.display.set_mode((SCREEN_WIDTH, SCREEN_HEIGHT))
    App().main_loop()
    pygame.quit()
    sys.exit()

if __name__ == "__main__":
    main()
