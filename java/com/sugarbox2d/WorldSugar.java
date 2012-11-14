package com.sugarbox2d;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class WorldSugar {

	private World b2World;

	public WorldSugar(World b2World) {
		this.b2World = b2World;
	}

	public Body makeBody(int x, int y, int w, int h, boolean isDynamic,
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
}
