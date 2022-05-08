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

class Player:
    """
    Represents the player.
    """

    def __init__(self, angle, position, controls): # 0 degrees is spacecraft facing 3 o'clock, controls = (accelerate, counter-clockwise, clockwise, fire)
        self.height = 64 # Height of the sprite
        self.width = 64 # Width of the sprite
        self.spacecraftIdle = pygame.transform.scale(pygame.image.load("spacecraft_idle.png"), (self.width, self.height))
        self.spacecraftAccel = pygame.transform.scale(pygame.image.load("spacecraft_accel.png"), (self.width, self.height))
        self.img = self.spacecraftIdle # Current sprite

        self.UP = controls[0]
        self.LEFT = controls[1]
        self.RIGHT = controls[2]
        self.FIRE = controls[3]
        
        self.angle = angle # In degrees, must be an integer
        self.angularVelocity = 270 # In degrees per second       
        self.position = position # [(SCREEN_WIDTH - self.width) / 2, SCREEN_HEIGHT - 2 * self.height] # [x position, y position]
        self.velocity = [0.0, 0.0] # (x velocity, y velocity), in pixels per second
        self.acceleration = 1000 # In pixels per second squared

        self.respawnAngle = angle + 0
        self.respawnPosition = [position[x], position[y]]

        self.timeLastFired = 0.0 # Last time the player fired (not really, only counts up until timeLastFired is greater than fireCooldown)
        self.fireCooldown = 0.6

        self.maxVelocity = 10000
        self.rocketOn = False
        
        self.lives = 10

    def get_height(self):
        return self.height

    def get_width(self):
        return self.width

    def get_angle(self):
        return self.angle

    def get_position(self):
        return self.position

    def get_hitbox(self):
        return ((self.position[x], self.position[y]), (self.position[x] + self.width, self.position[y] + self.height))

    def update_position(self, pressedKeys, dt):
        # Calculating angle
        if pressedKeys[self.LEFT]:
            self.angle += int(self.angularVelocity * dt)
            if self.angle != 0:
                self.angle %= 360

        if pressedKeys[self.RIGHT]:
            self.angle -= int(self.angularVelocity * dt)
            if self.angle != 0:
                self.angle %= 360

        # Calculating velocity
        if pressedKeys[self.UP]:
            self.velocity[x] += self.acceleration * math.cos(math.radians(self.angle)) * dt
            self.velocity[y] += self.acceleration * math.sin(math.radians(self.angle)) * dt

            """
            if math.sqrt(self.velocity[0] * self.velocity[0] + self.velocity[1] * self.velocity[1]) > self.maxVelocity:
                self.velocity[0] = self.maxVelocity * math.cos(math.radians(self.angle))
                self.velocity[1] = self.maxVelocity * math.sin(math.radians(self.angle))
            """

            self.rocketOn = True
        else:
            self.rocketOn = False

        # if pressedKeys[pygame.K_DOWN]:

        # Calculating position
        self.position[x] += self.velocity[x] * dt
        if self.position[x] < 0:
            self.position[x] = 0
            self.velocity[x] = 0
        elif self.position[x] > SCREEN_WIDTH - self.width:
            self.position[x] = SCREEN_WIDTH - self.width
            self.velocity[x] = 0
                
        self.position[y] -= self.velocity[y] * dt # The y-axis is essentially inverted (top of the screen is y=0 and the bottom of the screen is y=SCREEN_HEIGHT), which is why we are subtracting from the y-coordinate
        if self.position[y] < 0:
            self.position[y] = 0
            self.velocity[y] = 0
        elif self.position[y] > SCREEN_HEIGHT - self.height:
            self.position[y] = SCREEN_HEIGHT - self.height
            self.velocity[y] = 0

        # Animation
        if(self.rocketOn):
            self.img = pygame.transform.rotate(self.spacecraftAccel, (self.angle - 90) % 360)
        else:
            self.img = pygame.transform.rotate(self.spacecraftIdle, (self.angle - 90) % 360)

    def can_fire(self, pressedKeys, dt):
        if pressedKeys[self.FIRE] and self.timeLastFired > self.fireCooldown:
            self.timeLastFired = 0.0
            return True
        else:
            if self.timeLastFired < self.fireCooldown:
                self.timeLastFired += dt
            return False

    def respawn(self):
        self.angle = self.respawnAngle
        self.position = [self.respawnPosition[x], self.respawnPosition[y]]
        self.velocity = [0.0, 0.0]
        self.img = pygame.transform.rotate(self.spacecraftIdle, (self.angle - 90) % 360)

    def render(self, surface):
        surface.blit(self.img, self.position)
