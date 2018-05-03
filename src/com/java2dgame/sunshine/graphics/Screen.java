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

	public void render() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// (x + y * width) way to convert coordinates to int[]
				int tileIndex = (x / 16) + (y / 16) * 64;
				pixels[x + y * width] = tiles[tileIndex];
			}
		}
	}

	/** Clear pixels of the screen */
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

}
