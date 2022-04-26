#include "draw.h"

void prepareScene(void) {
	SDL_SetRenderDrawColor(app.renderer, 0x87, 0xCE, 0xEB, 255);
	SDL_RenderClear(app.renderer);
}

void presentScene(void) {
	SDL_RenderPresent(app.renderer);
}

SDL_Texture* loadTexture(char* filename) {
	SDL_Texture* texture;

	SDL_LogMessage(SDL_LOG_CATEGORY_APPLICATION, SDL_LOG_PRIORITY_INFO, "Loading [%s]", filename);

	texture = IMG_LoadTexture(app.renderer, filename);

	return texture;
}

void blit(SDL_Texture* texture, int x, int y, float angle) {
	SDL_Rect dest;


	SDL_QueryTexture(texture, NULL, NULL, &dest.w, &dest.h);

	dest.x = x;
	dest.y = y;
	SDL_RenderCopyEx(app.renderer, texture, NULL, &dest, angle, NULL, SDL_FLIP_NONE);

	//for (int i = -1; i<=1; i++) {
	//	for (int j=-1; j<=1; j++) {
	//		dest.x = x+i*SCREEN_WIDTH;
	//		dest.y = y+j*SCREEN_HEIGHT;
	//		SDL_RenderCopyEx(app.renderer, texture, NULL, &dest, angle, NULL, SDL_FLIP_NONE);
	//	}
	//}
}
