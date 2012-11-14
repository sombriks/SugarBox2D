package com.sugarbox2d;

import javax.swing.JComponent;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import com.sugarbox2d.debug.SimplestDebugDraw;

public class WorldSugar {

	private World b2World;
	private boolean running = true;

	public WorldSugar(World b2World, JComponent drawingArea) {
		this.b2World = b2World;
	}

	private Body makeBody(int x, int y, int w, int h, boolean isDynamic,
			boolean isCircle) {
		BodyDef bd = new BodyDef();
		bd.position = new Vec2(x, y);
		bd.type = isDynamic ? BodyType.DYNAMIC : BodyType.STATIC;
		Body body = b2World.createBody(bd);
		FixtureDef fd = new FixtureDef();
		if (isCircle) {
			CircleShape c = new CircleShape();
			fd.shape = c;
			c.m_radius = w;
		} else {
			PolygonShape p = new PolygonShape();
			p.setAsBox(w, h);
			fd.shape = p;
		}
		body.createFixture(fd);
		return body;
	}

	public Body makeBox(int x, int y, int w, int h) {
		return makeBox(x, y, w, h, false);
	}

	public Body makeBox(int x, int y, int w, int h, boolean isDynamic) {
		return makeBody(x, y, w, h, isDynamic, false);
	}

	public Body makeCircle(int x, int y, int r) {
		return makeCircle(x, y, r, false);
	}

	public Body makeCircle(int x, int y, int r, boolean isDynamic) {
		return makeBody(x, y, r, 0, isDynamic, false);
	}

	public void debugEnabled(boolean b) {
		DebugDraw d = null;
		if (b) {
			b2World.setDebugDraw(new SimplestDebugDraw());
			// FIXME terminar a implementação de debugdraw
		}
		b2World.setDebugDraw(d);
	}

	public void running(boolean running) {
		this.running = running;
	}

	public void step() {
		if (running) {
			b2World.step(1.0f / 60.0f, 10, 10);
			b2World.drawDebugData();
			b2World.clearForces();
		}
	}
}
