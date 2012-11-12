/**
 * helper to create dynamically the mousejoint
 * 
 * usage:
 * 
 * var _escala = 25; var world = new b2World(new b2Vec2(0, 0), true); var canvas =
 * document.getElementById("c");
 * 
 * //(...)
 * 
 * var mm = new MouseSugar({ world : world, canvas : canvas, scale : _escala
 * });
 * 
 * //(...)
 * 
 * function update(){ world.Step(1 / 60, 10, 10); world.DrawDebugData();
 * world.ClearForces(); mm.step(); }
 * 
 * @param cfg.world
 *            Box2D world
 * @param cfg.canvas
 *            DOM element to track mouse position
 * @param cfg.scale
 *            pixel to meters proportion used
 * @param cfg.camera
 *            if present, camera will be asked by translation coordinates
 * 
 */
function MouseSugar(cfg) {

	// publish config options
	this.cfg = cfg;

	var mouseX = undefined;
	var mouseY = undefined;
	var mousePVec = undefined;
	var isMouseDown = undefined;
	var selectedBody = undefined;
	var mouseJoint = undefined;
	var canvasPosition = undefined;

	// http://js-tut.aardon.de/js-tut/tutorial/position.html
	function getElementPosition(element) {
		var elem = element, tagname = "", x = 0, y = 0;
		while ((typeof (elem) == "object")
				&& (typeof (elem.tagName) != "undefined")) {
			y += elem.offsetTop;
			x += elem.offsetLeft;
			tagname = elem.tagName.toUpperCase();
			if (tagname == "BODY")
				elem = 0;
			if (typeof (elem) == "object") {
				if (typeof (elem.offsetParent) == "object")
					elem = elem.offsetParent;
			}
		}
		return {
			x : x,
			y : y
		};
	}

	function handleMouseMove(e) {
		var a = 0;
		var b = 0;
		if (cfg.camera) {
			a = cfg.camera.pos.x;
			b = cfg.camera.pos.y;
		}
		mouseX = (e.clientX - canvasPosition.x - a) / cfg.scale;
		mouseY = (e.clientY - canvasPosition.y - b) / cfg.scale;
	}

	function getBodyAtMouse() {
		mousePVec = new b2Vec2(mouseX, mouseY);
		var aabb = new b2AABB();
		aabb.lowerBound.Set(mouseX - 0.001, mouseY - 0.001);
		aabb.upperBound.Set(mouseX + 0.001, mouseY + 0.001);
		// Query the world for overlapping shapes.
		selectedBody = null;
		cfg.world.QueryAABB(getBodyCB, aabb);
		return selectedBody;
	}

	function getBodyCB(fixture) {
		if (fixture.GetBody().GetType() != b2Body.b2_staticBody) {
			if (fixture.GetShape().TestPoint(fixture.GetBody().GetTransform(),
					mousePVec)) {
				selectedBody = fixture.GetBody();
				return false;
			}
		}
		return true;
	}

	this.step = function() {
		if (isMouseDown && (!mouseJoint)) {
			var body = getBodyAtMouse();
			if (body) {
				var md = new b2MouseJointDef();
				md.bodyA = cfg.world.GetGroundBody();
				md.bodyB = body;
				md.target.Set(mouseX, mouseY);
				md.collideConnected = true;
				md.maxForce = 300.0 * body.GetMass();
				mouseJoint = cfg.world.CreateJoint(md);
				body.SetAwake(true);
			}
		}
		if (mouseJoint) {
			if (isMouseDown) {
				mouseJoint.SetTarget(new b2Vec2(mouseX, mouseY));
			} else {
				cfg.world.DestroyJoint(mouseJoint);
				mouseJoint = null;
			}
		}
	};

	canvasPosition = getElementPosition(cfg.canvas);

	document.addEventListener("mousedown", function(e) {
		isMouseDown = true;
		handleMouseMove(e);
		document.addEventListener("mousemove", handleMouseMove, true);
	}, true);

	document.addEventListener("mouseup", function() {
		document.removeEventListener("mousemove", handleMouseMove, true);
		isMouseDown = false;
		mouseX = undefined;
		mouseY = undefined;
	}, true);
}