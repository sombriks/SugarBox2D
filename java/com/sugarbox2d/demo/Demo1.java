package com.sugarbox2d.demo;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import com.sugarbox2d.WorldSugar;

public class Demo1 {

	public Demo1() {
		final JFrame jf = new JFrame("Demo 1");
		final JPanel jp = new JPanel();
		jf.setLayout(new GridLayout());
		jf.add(jp);
		jf.setSize(800, 600);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final World w = new World(new Vec2(0, 0), true);
		final WorldSugar ws = new WorldSugar(w, jp);
	}

	public static void main(String[] args) {
		new Demo1();
	}
}
