package com.sugarbox2d.debug;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.OBBViewportTransform;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.pooling.arrays.IntArray;
import org.jbox2d.pooling.arrays.Vec2Array;

/**
 * widely based on
 */
// http://code.google.com/p/jbox2d/source/browse/trunk/jbox2d-testbed/src/main/java/org/jbox2d/testbed/framework/j2d/DebugDrawJ2D.java
public class SimplestDebugDraw extends DebugDraw {

	public static int circlePoints = 13;
	private final ColorPool cpool = new ColorPool();
	private final Vec2Array vec2Array = new Vec2Array();
	private final Vec2 sp1 = new Vec2();
	private final Vec2 sp2 = new Vec2();
	private final Vec2 saxis = new Vec2();
	// TODO change IntegerArray to a specific class for int[] arrays
	private final Vec2 temp = new Vec2();
	private final Vec2 temp2 = new Vec2();
	private final static IntArray xIntsPool = new IntArray();
	private final static IntArray yIntsPool = new IntArray();

	private JPanel panel;

	public SimplestDebugDraw(JPanel panel, float scale) {
		super(new OBBViewportTransform());
		viewportTransform.setYFlip(true);
		setCamera(panel.getWidth() / 2, panel.getHeight() / 2, scale);
		this.panel = panel;
		setFlags(e_shapeBit | e_jointBit | e_pairBit); 
	}

	@Override
	public void drawCircle(Vec2 center, float radius, Color3f color) {
		Vec2[] vecs = vec2Array.get(circlePoints);
		generateCirle(center, radius, vecs, circlePoints);
		drawPolygon(vecs, circlePoints, color);
	}

	@Override
	public void drawPoint(Vec2 argPoint, float argRadiusOnScreen,
			Color3f argColor) {
		getWorldToScreenToOut(argPoint, sp1);
		Graphics2D g = getGraphics();

		Color c = cpool.getColor(argColor.x, argColor.y, argColor.z);
		g.setColor(c);
		sp1.x -= argRadiusOnScreen;
		sp1.y -= argRadiusOnScreen;
		g.fillOval((int) sp1.x, (int) sp1.y, (int) argRadiusOnScreen * 2,
				(int) argRadiusOnScreen * 2);
	}

	@Override
	public void drawSegment(Vec2 p1, Vec2 p2, Color3f color) {
		getWorldToScreenToOut(p1, sp1);
		getWorldToScreenToOut(p2, sp2);

		Graphics2D g = getGraphics();
		Color c = cpool.getColor(color.x, color.y, color.z);
		g.setColor(c);

		g.drawLine((int) sp1.x, (int) sp1.y, (int) sp2.x, (int) sp2.y);
	}

	public void drawAABB(AABB argAABB, Color3f color) {
		Vec2 vecs[] = vec2Array.get(4);
		argAABB.getVertices(vecs);
		drawPolygon(vecs, 4, color);
	}

	@Override
	public void drawSolidCircle(Vec2 center, float radius, Vec2 axis,
			Color3f color) {
		Vec2[] vecs = vec2Array.get(circlePoints);
		generateCirle(center, radius, vecs, circlePoints);
		drawSolidPolygon(vecs, circlePoints, color);
		if (axis != null) {
			saxis.set(axis).mulLocal(radius).addLocal(center);
			drawSegment(center, saxis, color);
		}
	}

	@Override
	public void drawSolidPolygon(Vec2[] vertices, int vertexCount, Color3f color) {

		// inside
		Graphics2D g = getGraphics();
		int[] xInts = xIntsPool.get(vertexCount);
		int[] yInts = yIntsPool.get(vertexCount);

		for (int i = 0; i < vertexCount; i++) {
			getWorldToScreenToOut(vertices[i], temp);
			xInts[i] = (int) temp.x;
			yInts[i] = (int) temp.y;
		}

		Color c = cpool.getColor(color.x, color.y, color.z, .4f);
		g.setColor(c);
		g.fillPolygon(xInts, yInts, vertexCount);

		// outside
		drawPolygon(vertices, vertexCount, color);
	}

	@Override
	public void drawString(float x, float y, String s, Color3f color) {
		Graphics2D g = getGraphics();
		if (g == null) {
			return;
		}
		Color c = cpool.getColor(color.x, color.y, color.z);
		g.setColor(c);
		g.drawString(s, x, y);
	}

	@Override
	public void drawTransform(Transform xf) {
		Graphics2D g = getGraphics();
		getWorldToScreenToOut(xf.position, temp);
		temp2.setZero();
		float k_axisScale = 0.4f;

		Color c = cpool.getColor(1, 0, 0);
		g.setColor(c);

		temp2.x = xf.position.x + k_axisScale * xf.R.col1.x;
		temp2.y = xf.position.y + k_axisScale * xf.R.col1.y;
		getWorldToScreenToOut(temp2, temp2);
		g.drawLine((int) temp.x, (int) temp.y, (int) temp2.x, (int) temp2.y);

		c = cpool.getColor(0, 1, 0);
		g.setColor(c);
		temp2.x = xf.position.x + k_axisScale * xf.R.col2.x;
		temp2.y = xf.position.y + k_axisScale * xf.R.col2.y;
		getWorldToScreenToOut(temp2, temp2);
		g.drawLine((int) temp.x, (int) temp.y, (int) temp2.x, (int) temp2.y);
	}

	// CIRCLE GENERATOR

	private void generateCirle(Vec2 argCenter, float argRadius,
			Vec2[] argPoints, int argNumPoints) {
		float inc = MathUtils.TWOPI / argNumPoints;

		for (int i = 0; i < argNumPoints; i++) {
			argPoints[i].x = (argCenter.x + MathUtils.cos(i * inc) * argRadius);
			argPoints[i].y = (argCenter.y + MathUtils.sin(i * inc) * argRadius);
		}
	}

	private Graphics2D getGraphics() {
		return (Graphics2D) panel.getGraphics();
	}

}
