#include "stage.h"

static void logic(void);
static void draw(void);
static void initPlayer(void);
static void fireBullet(void);
static void fireEnemyBullet(Entity* e);
static void doPlayer(void);
static void doFighters(void);
static void doEnemies(void);
static void clipPlayer(void);
static void doBullets(void);
static void spawnEnemies(void);
static void drawFighters(void);
static void drawBullets(void);
static int bulletHitFighter(Entity* b);
static void resetStage(void);

static Entity* player;
static SDL_Texture* bulletTexture;
static SDL_Texture* bulletHitTexture;
static SDL_Texture* enemyTexture1;
static SDL_Texture* enemyTexture2;
static SDL_Texture* enemyTexture3;
static SDL_Texture* enemyBulletTexture;
static int enemySpawnTimer;
static int stageResetTimer;

void initStage(void) {
	app.delegate.logic = logic;
	app.delegate.draw = draw;

	memset(&stage, 0, sizeof(Stage));
	stage.fighterTail = &stage.fighterHead;
	stage.bulletTail = &stage.bulletHead;

	/* pre cache the texture so we dont lond on each bullet init */
	bulletTexture = loadTexture("gfx/bullet.png");
	bulletHitTexture = loadTexture("gfx/boom.png");
	enemyTexture1 = loadTexture("gfx/enemy_rapid.png");
	enemyTexture2 = loadTexture("gfx/enemy_follow.png");
	enemyTexture3 = loadTexture("gfx/enemy_rapid.png");
	enemyBulletTexture = loadTexture("gfx/enemyBullet.png");


	resetStage();
}

static void resetStage(void) {
	Entity* e;

	while(stage.fighterHead.next) {
		e = stage.fighterHead.next;
		stage.fighterHead.next = e->next;
		free(e);
	}

	while (stage.bulletHead.next) {
		e = stage.bulletHead.next;
		stage.bulletHead.next = e->next;
		free(e);
	}

	memset(&stage, 0, sizeof(Stage));
	stage.fighterTail = &stage.fighterHead;
	stage.bulletTail = &stage.bulletHead;

	initPlayer();

	enemySpawnTimer = 0;

	stageResetTimer = FPS * 2;
}

static void initPlayer(void) {
	player = malloc(sizeof(Entity));
	memset(player, 0, sizeof(Entity));
	stage.fighterTail->next = player;
	stage.fighterTail = player;

	player->x = 100;
	player->y = 100;
	player->angle = 0.0;
	player->side = SIDE_PLAYER;
	player->health = 3;
	player->texture[0] = loadTexture("gfx/plane0.png");
	SDL_QueryTexture(player->texture[0], NULL, NULL, &player->w, &player->h);
}

static void logic(void) {
	doPlayer();

	doEnemies();

	doFighters();

	doBullets();

	spawnEnemies();

	clipPlayer();

	if (player == NULL && --stageResetTimer <= 0) {
		resetStage();
	}
}

static void doPlayer(void) {
	if (player != NULL) {
		//player->dx = player->dy = 0;

		if (player->reload > 0) {
			player->reload--;
		}
		player->dy = sin(player->angle)*PLAYER_SPEED;
		player->dx = cos(player->angle)*PLAYER_SPEED;

		if (app.keyboard[SDL_SCANCODE_UP]) {
			player->dy += 0.5*sin(player->angle)*PLAYER_SPEED;
			player->dx += 0.5*cos(player->angle)*PLAYER_SPEED;
		}
		if (app.keyboard[SDL_SCANCODE_DOWN]) {
			player->dy -= 0.5*sin(player->angle)*PLAYER_SPEED;
			player->dx -= 0.5*cos(player->angle)*PLAYER_SPEED;
		}
		if (app.keyboard[SDL_SCANCODE_RIGHT]) {
			player->angle+=0.025;
			//player->dx = PLAYER_SPEED;
		}
		if (app.keyboard[SDL_SCANCODE_LEFT]) {
			player->angle-=0.025;
			//player->dx = -PLAYER_SPEED;
		}
		if (app.keyboard[SDL_SCANCODE_SPACE] && player->reload == 0) {
			fireBullet();
		}
		if (player->angle > 2*PI) {
			player->angle-=2*PI;
		}
		if (player->angle < 0) {
			player->angle+=2*PI;
		}
	}
}

static void doEnemies(void) {
	Entity* e;

	for(e = stage.fighterHead.next; e != NULL; e = e->next) {
		if (e != player && player != NULL) {

			if (e->e_type == E_TYPE_RL) {
				// goes straight at dx init'd with
			}
			else if (e->e_type == E_TYPE_FOLLOW) {
				calcSlope(player->x + (player->w / 2), player->y + (player->h / 2), e->x, e->y, &e->dx, &e->dy);
				e->dx*=ENEMY_SPEED;
				e->dy*=ENEMY_SPEED;
			}
			else { // rapid fire guy

			}

			if (--e->reload <= 0) {
				fireEnemyBullet(e);
			}
		}
	}
}

static void fireEnemyBullet(Entity* e) {
	Entity* bullet;

	bullet = malloc(sizeof(Entity));
	memset(bullet, 0, sizeof(Entity));
	stage.bulletTail->next = bullet;
	stage.bulletTail = bullet;

	bullet->x = e->x;
	bullet->y = e->y;
	bullet->health = 500;
	bullet->texture[0] = enemyBulletTexture;
	bullet->side = e->side;
	SDL_QueryTexture(bullet->texture[0], NULL, NULL, &bullet->w, &bullet->h);
	bullet->x += (e->w / 2) - (bullet->w / 2);
	bullet->y += (e->h / 2) - (bullet->h / 2);

	if (e->e_type == E_TYPE_RL) {
		calcSlope(player->x + (player->w / 2), player->y + (player->h / 2), e->x, e->y, &bullet->dx, &bullet->dy);
		bullet->dx*=ENEMY_BULLET_SPEED;
		bullet->dy*=ENEMY_BULLET_SPEED;
		e->reload = (rand() % FPS * 3);
	}
	else if (e->e_type == E_TYPE_RAPID) {
		calcSlope(player->x + (player->w / 2), player->y + (player->h / 2), e->x, e->y, &bullet->dx, &bullet->dy);
		bullet->dx*=ENEMY_BULLET_SPEED;
		bullet->dy*=ENEMY_BULLET_SPEED;
		e->reload = (rand() % FPS * 1);
	}
	else {
		e->reload = (rand() % FPS * 2);
		bullet->dx = e->dx*ENEMY_BULLET_SPEED;
		bullet->dy = e->dy*ENEMY_BULLET_SPEED;
	}


	bullet->side = SIDE_ENEMY;

}

static void doFighters(void) {
	Entity *e;
	Entity *prev;

	prev = &stage.fighterHead;

	for (e = stage.fighterHead.next; e != NULL; e = e->next) {
		e->x += e->dx;
		e->y += e->dy;
		if (e != player) { 
			e->angle = atan2(e->dy, e->dx);
		}
		if (e != player && e->x < -e->w) {
			e->health = 0;
		}

		if (e->health <= 0) {
			if (e == player) {
				player = NULL;
			}
			if (e == stage.fighterTail) {
				stage.fighterTail = prev;
			}
		
			prev->next = e->next;
			free(e);
			e = prev;
		}
		prev = e;
	}
}

static void clipPlayer(void) {
	if (player != NULL) {
		player->x = fmodf((player->x + SCREEN_WIDTH), SCREEN_WIDTH);
		player->y = fmodf((player->y + SCREEN_HEIGHT), SCREEN_HEIGHT);
	}
	//for (Entity* b = stage.bulletHead.next; b != NULL; b = b->next) {
	//	b->x = fmodf((b->x + SCREEN_WIDTH), SCREEN_WIDTH);
	//	b->y = fmodf((b->y + SCREEN_HEIGHT), SCREEN_HEIGHT);
	//}

}

static void spawnEnemies(void) {
	Entity* enemy;

	if (--enemySpawnTimer <= 0) {
		enemy = malloc(sizeof(Entity));
		memset(enemy, 0, sizeof(Entity));
		stage.fighterTail->next = enemy;
		stage.fighterTail = enemy;

		enemy->x = SCREEN_WIDTH;
		enemy->y = rand() % SCREEN_HEIGHT;

		enemy->e_type = rand() % 3; // n_types
		if (enemy->e_type == E_TYPE_RL) {
			enemy->health = 3;
			enemy->texture[0] = enemyTexture1;
			enemy->reload = FPS * (1 + (rand() % 3));

		}
		else if (enemy->e_type == E_TYPE_FOLLOW) {
			enemy->health = 1;
			enemy->texture[0] = enemyTexture2;
			enemy->reload = FPS * (2 + (rand() % 3));

		}
		else { // rapid fire guy
			enemy->health = 2;
			enemy->texture[0] = enemyTexture3;
			enemy->reload = FPS;

		}
		enemy->angle = 0;
		enemy->side = SIDE_ENEMY;
		SDL_QueryTexture(enemy->texture[0], NULL, NULL, &enemy->w, &enemy->h);
		
		enemy->dx = -(2 + (rand() % 4));

		enemySpawnTimer = 40 + (rand() % 60);
	}
}

static void drawFighters(void) {
	Entity* e;

	for (e = stage.fighterHead.next; e != NULL; e = e->next) {
		//SDL_LogMessage(SDL_LOG_CATEGORY_APPLICATION, SDL_LOG_PRIORITY_INFO, "Loading [%d]", (int)(roundf((e->angle / (2*M_PI) * 8))) % 8);
		blit(e->texture[0], e->x, e->y, e->angle*(180/PI));
	}
}

static void fireBullet(void) {
	Entity* bullet;

	bullet = malloc(sizeof(Entity));
	memset(bullet, 0, sizeof(Entity));

	stage.bulletTail->next = bullet;
	stage.bulletTail = bullet;

	bullet->x = player->x;
	bullet->y = player->y;
	bullet->angle = player->angle;
	bullet->side = SIDE_PLAYER;

	bullet->dy = sin(player->angle)*PLAYER_BULLET_SPEED;
	bullet->dx = cos(player->angle)*PLAYER_BULLET_SPEED;

	bullet->health = 500;
	bullet->texture[0] = bulletTexture;
	SDL_QueryTexture(bullet->texture[0], NULL, NULL, &bullet->w, &bullet->h);

	bullet->y += (player->h / 2) - (bullet->h / 2);

	player->reload = 8;
}

static void doBullets(void) {
	Entity* b;
	Entity* prev;

	prev = &stage.bulletHead;

	for (b = stage.bulletHead.next; b != NULL; b = b->next) {
		b->health--;

		b->x += b->dx;
		b->y += b->dy;

		if (bulletHitFighter(b) || b->health <= 0) {
			drawBullets();
			if (b == stage.bulletTail) {
				stage.bulletTail = prev;
			}
			prev->next = b->next;
			free(b);
			b = prev;
		}
		prev = b;
	}
}

static int bulletHitFighter(Entity* b) {
	Entity* e;

	for (e = stage.fighterHead.next; e != NULL; e = e->next) {
		if (e->side != b->side && collision(b->x, b->y, b->w, b->h, e->x, e->y, e->w, e->h)) {
			b->texture[0] = bulletHitTexture;
			b->health = 0;
			e->health--;

			return 1;
		}
	}
	return 0;
}

static void draw(void) {
	drawFighters();
	drawBullets();
}

static void drawBullets(void) {
	Entity* b;

	for (b = stage.bulletHead.next; b != NULL; b = b->next) {
		blit(b->texture[0], b->x, b->y, b->angle);
	}
}