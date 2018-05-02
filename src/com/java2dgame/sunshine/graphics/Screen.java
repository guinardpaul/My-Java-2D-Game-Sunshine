package com.java2dgame.sunshine.graphics;

public class Screen {

	public int width;
	public int height;
	public int[] pixels;

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}

}
