package com.sugarbox2d.demo;

import java.awt.BorderLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import com.sugarbox2d.BasicActorSugar;
import com.sugarbox2d.WorldSugar;

public class Demo1 {

	public Demo1() throws Exception {
		final float escala = 10;
		final List<BasicActorSugar> actorList = new ArrayList<BasicActorSugar>();
		final JFrame jf = new JFrame("Demo 1");
		final JPanel jp = new JPanel();
		jf.setLayout(new BorderLayout());
		jf.add(jp, BorderLayout.CENTER);
		jf.setSize(800, 600);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Image awesome = ImageIO.read(//
				getClass().getResourceAsStream("awesome.png"));
		Image bigball = ImageIO.read(//
				getClass().getResourceAsStream("bigball.png"));
		Image box = ImageIO.read(//
				getClass().getResourceAsStream("box.jpg"));

		final World world = new World(new Vec2(0, 0), true);
		final WorldSugar ws = new WorldSugar(world, jp, escala);

		// create ground
		ws.makeBox(0.5f, 0.5f, 50f, 0.2f);// up
		ws.makeBox(0.5f, 20f, 50f, 0.2f);// down
		ws.makeBox(0.5f, 0.5f, 0.2f, 20f);// left
		ws.makeBox(50f, 0.5f, 0.2f, 20f);// right

		final BasicActorSugar jogador = new BasicActorSugar(//
				ws.makeCircle(50, 50, 1.3f, true), jp, awesome, escala);
		actorList.add(jogador);
		// create some objects
		int i = 100;
		while (i-- > 0) {
			float x = (float) (Math.random() * 10 + 5);
			float y = (float) (Math.random() * 10 + 5);
			float w = (float) (Math.random() * 1.3 + 0.1);
			float h = (float) (Math.random() * 1.1 + 0.1);
			float r = (float) Math.random();
			if (r < 0.5)
				actorList.add(new BasicActorSugar(ws.makeBox(x, y, w, h, true),//
						jp, box, escala));
			else
				actorList.add(new BasicActorSugar(ws.makeCircle(x, y, w, true),//
						jp, bigball, escala));
		}

		jf.setVisible(true);
		while (true) {
			jp.getGraphics().clearRect(0, 0, jp.getWidth(), jp.getHeight());
			ws.step();
			for (BasicActorSugar a : actorList)
				a.step();
			Thread.sleep(1000 / 60);
		}
	}

	public static void main(String[] args) throws Exception {
		new Demo1();
	}
}
