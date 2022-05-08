import math
import pygame
import random
import sys

FPS = 60
SCREEN_HEIGHT = 900
SCREEN_WIDTH = 1800
WHITE = (255, 255, 255)
x = 0
y = 1

class Bullet:
    """
    Description
    """

    def __init__(self): # playerParameters = (height, width, angle, position), position = [position x, position y]
        self.height = 32
        self.width = 32
        self.bulletImg = pygame.transform.scale(pygame.image.load("bullet.png"), (self.width, self.height))
        self.img = self.bulletImg

        self.on_standby()

    def is_active(self):
        return self.active

    def get_hitbox(self):
        return ((self.position[x], self.position[y]), (self.position[x] + self.width, self.position[y] + self.height))

    def on_standby(self):
        self.active = False
        self.angle = 90
        self.position = [0 - 2 * self.width, 0]
        self.velocity = [0.0, 0.0]

    def fire(self, pHeight, pWidth, pAngle, pPosition): # playerParameters = (height, width, angle, position), position = [position x, position y]
        self.active = True
        self.angle = pAngle
        self.position = [pPosition[x] + (pWidth - self.width) / 2, pPosition[y] + (pHeight - self.height) / 2] # Bullet will spawn inside the player
        self.velocity = [1200.0 * math.cos(math.radians(pAngle)), 1200.0 * math.sin(math.radians(pAngle))] # Velocity = 1200 pixels per second

        self.img = pygame.transform.rotate(self.bulletImg, (self.angle - 90) % 360)

    def update_position(self, dt):
        if self.active:
            self.position[x] += self.velocity[x] * dt
            if self.position[x] < 0 - self.width or self.position[x] > SCREEN_WIDTH:
                self.on_standby()
                    
            self.position[y] -= self.velocity[y] * dt # The y-axis is essentially inverted (top of the screen is y=0 and the bottom of the screen is y=SCREEN_HEIGHT), which is why we are subtracting from the y-coordinate
            if self.position[y] < 0 - self.height or self.position[y] > SCREEN_HEIGHT:
                self.on_standby()

    def render(self, surface):
        if(self.active):
            surface.blit(self.img, self.position)
