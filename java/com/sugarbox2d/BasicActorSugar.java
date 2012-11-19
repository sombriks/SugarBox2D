package com.sugarbox2d;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class BasicActorSugar {

	private Body body;
	private JPanel panel;
	private Image img;
	private float scale;

	public BasicActorSugar(Body body, JPanel panel, Image img, float scale) {
		this.scale = scale;
		this.panel = panel;
		this.body = body;
		this.img = img;
	}

	public void step() {
		float w = 0, h = 0;
		float a = body.getAngle();
		Vec2 v = body.getPosition();
		switch (body.getFixtureList().getShape().getType()) {
		case CIRCLE:
			float r = body.getFixtureList().getShape().m_radius;
			w = r;
			h = r;
			break;
		case POLYGON:
			PolygonShape shp = (PolygonShape) body.getFixtureList().getShape();
			Vec2[] v2 = shp.getVertices();
			w = (float) Math.sqrt(v2[0].x * v2[0].x);
			h = (float) Math.sqrt(v2[0].y * v2[0].y);
			break;
		}
		Graphics2D g2 = (Graphics2D) panel.getGraphics();
		g2 = (Graphics2D) g2.create();
		g2.translate(v.x * scale, v.y * scale);
		g2.rotate(a);
		g2.drawImage(img, //
				(int) (-v.x * scale), //
				(int) (-v.y * scale), //
				(int) w, //
				(int) h, //
				0, 0, img.getWidth(null), img.getHeight(null), null);
		g2.dispose();
	}
}
