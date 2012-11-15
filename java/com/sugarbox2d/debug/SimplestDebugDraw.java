package com.sugarbox2d.debug;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.OBBViewportTransform;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;

public class SimplestDebugDraw extends DebugDraw {

	public SimplestDebugDraw() {
		super(new OBBViewportTransform());
	    viewportTransform.setYFlip(true);
	}

	@Override
	public void drawCircle(Vec2 arg0, float arg1, Color3f arg2) {
		
	}

	@Override
	public void drawPoint(Vec2 arg0, float arg1, Color3f arg2) {
		
	}

	@Override
	public void drawSegment(Vec2 arg0, Vec2 arg1, Color3f arg2) {
		
	}

	@Override
	public void drawSolidCircle(Vec2 arg0, float arg1, Vec2 arg2, Color3f arg3) {
		
	}

	@Override
	public void drawSolidPolygon(Vec2[] arg0, int arg1, Color3f arg2) {
		
	}

	@Override
	public void drawString(float arg0, float arg1, String arg2, Color3f arg3) {
		
	}

	@Override
	public void drawTransform(Transform arg0) {
		
	}

}
