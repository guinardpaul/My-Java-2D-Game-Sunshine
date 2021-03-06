package com.java2dgame.sunshine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.java2dgame.sunshine.graphics.Screen;
import com.java2dgame.sunshine.input.Keyboard;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	/** WIDTH */
	public static final int WIDTH = 300;
	/** HEIGHT */
	public static final int HEIGHT = WIDTH / 16 * 9;
	/** SCALE */
	public static final int SCALE = 3;
	/** TITLE */
	public static final String TITLE = "Sunshine";

	/** Display thread */
	private Thread thread;
	/** JFrame */
	private JFrame frame;
	/** Running */
	private boolean running = false;

	/** Screen */
	private Screen screen;
	/** Keyboard input */
	private Keyboard keyInput;

	/** Temporary x, y value for moving the map */
	private int x = 0, y = 0;

	/** Buffered image */
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	/** Pixels of the buffered image */
	public int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	/** Instantiate new Game */
	public Game() {
		Dimension screenSize = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		setPreferredSize(screenSize);
		setMinimumSize(screenSize);
		setMaximumSize(screenSize);

		screen = new Screen(WIDTH, HEIGHT);
		frame = new JFrame();
		keyInput = new Keyboard();

		addKeyListener(keyInput);
	}

	/** Start the game */
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	/** Stop the game */
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/** Run method for the game */
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		// Focus window when opened
		requestFocus();
		// Game loop
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			// Update 60 times per second
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;

			// Display ups and fps to title
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(updates + " ups, " + frames + " fps");
				frame.setTitle(TITLE + "  |  " + updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
		}
	}

	/** Update method */
	public void update() {
		// KeyEvent update
		keyInput.update();
		if (keyInput.up) {
			y--;
		}
		if (keyInput.down) {
			y++;
		}
		if (keyInput.left) {
			x--;
		}
		if (keyInput.right) {
			x++;
		}
	}

	/** Render method */
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		screen.clear();

		screen.render(x, y);

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}

		Graphics g = bs.getDrawGraphics();

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

		g.dispose();
		bs.show();
	}

	/** Launch the game */
	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle(Game.TITLE);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);

		game.start();
	}

}
