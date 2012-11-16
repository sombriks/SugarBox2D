package com.sugarbox2d.demo;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import com.sugarbox2d.BasicActorSugar;
import com.sugarbox2d.WorldSugar;

public class Demo1 {

	public Demo1() {
		final List<BasicActorSugar> actorList = new ArrayList<BasicActorSugar>();
		final JFrame jf = new JFrame("Demo 1");
		final JPanel jp = new JPanel();
		jf.setLayout(new GridLayout());
		jf.add(jp);
		jf.setSize(800, 600);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final World w = new World(new Vec2(0, 0), true);
		final WorldSugar ws = new WorldSugar(w, jp);
		final BasicActorSugar jogador = new BasicActorSugar(ws.makeCircle(10,
				10, 1.1f, true));
		actorList.add(jogador);

		new Thread() {
			public void run() {
				while (true) {

					slp();
				}
			};
		}.start();
	}

	private void slp() {
		try {
			Thread.sleep(1000 / 60);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Demo1();
	}
}
