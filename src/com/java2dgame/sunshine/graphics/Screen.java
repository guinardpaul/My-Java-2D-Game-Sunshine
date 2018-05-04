package com.java2dgame.sunshine.graphics;

import java.util.Random;

public class Screen {
	/** width */
	public int width;
	/** height */
	public int height;
	/** pixels of Screen */
	public int[] pixels;
	/** tiles map */
	public int[] tiles = new int[64 * 64];

	/** Random for tiles testing */
	private Random random = new Random();

	/** Instantiate Screen */
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];

		for (int i = 0; i < 64 * 64; i++) {
			tiles[i] = random.nextInt(0xffffff);
		}
	}

	/** Clear pixels of the screen */
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	/** Render screen */
	public void render(int xOffset, int yOffset) {
		for (int y = 0; y < height; y++) {
			int yRender = y + yOffset;
			for (int x = 0; x < width; x++) {
				int xRender = x + xOffset;
				// (x + y * width) way to convert coordinates to int[]
				int tileIndex = (xRender >> 4) + (yRender >> 4) * 64;
				pixels[x + y * width] = tiles[tileIndex];
			}
		}
	}

}
