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

class Comet:
    """
    Description
    """

    def __init__(self, yStart = 0):
        self.height = 96
        self.width = 96
        self.img = pygame.image.load("comet.png")
        self.img = pygame.transform.scale(self.img, (self.width, self.height))
        self.img = pygame.transform.rotate(self.img, 90)

        self.position = [random.randint(0, SCREEN_WIDTH - self.width), yStart - self.height]
        self.velocity = [0.0, -200.0]
        self.timeLastChange = 0.0 # Last time the image flipped
        self.animationTime = 0.2 # Time in seconds for image to flip along its y-axis, purpose is to animate

    def get_hitbox(self):
        return ((self.position[x], self.position[y]), (self.position[x] + self.width, self.position[y] + self.height))

    def update_position(self, dt):
        if self.position[y] > SCREEN_HEIGHT: # If the comet is below the screen, put it above the screen at a random position along the x-axis
            self.position[x] = random.randint(0, SCREEN_WIDTH - self.width) 
            self.position[y] = -self.height
        else:
            self.position[y] -= self.velocity[y] * dt

        self.timeLastChange += dt
        if self.timeLastChange > self.animationTime:
            self.img = pygame.transform.flip(self.img, True, False)
            self.timeLastChange -= self.animationTime

    def respawn(self):
        self.position = [random.randint(0, SCREEN_WIDTH - self.width), self.position[y] - SCREEN_HEIGHT - self.height]

    def render(self, surface):
        surface.blit(self.img, self.position)
