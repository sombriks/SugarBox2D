/**
 * utilitário para fornecer um meio simples de criar o mundo
 * 
 * @param cfg.world
 *            instância de b2World para fazermos as simulações
 * @param cfg.ctx
 *            contexto de desenho
 * @param cfg.scale
 *            zoom. Tipo isso
 * @param cfg.debug
 *            se devemos ativar o modo de debug de desenho
 * @param cfg.running
 *            se a simulação começa parada ou não
 */
function WorldSugar(cfg) {

	// publish config options
	this.cfg = cfg;

	function mkBody(x, y, w, h, isDynamic, isCircle) {
		var fixDef = new b2FixtureDef;
		if (isCircle)
			fixDef.shape = new b2CircleShape(w);
		else {
			fixDef.shape = new b2PolygonShape();
			fixDef.shape.SetAsBox(w, h);
		}
		fixDef.density = 1.0;
		fixDef.friction = 0.1;
		fixDef.restitution = 0.1;
		var bodyDef = new b2BodyDef();
		bodyDef.type = isDynamic ? b2Body.b2_dynamicBody : b2Body.b2_staticBody;
		bodyDef.position.Set(x, y);
		var body = cfg.world.CreateBody(bodyDef);
		body.CreateFixture(fixDef);
		return body;
	}

	this.makeCircle = function(x, y, r, isDynamic) {
		return mkBody(x, y, r, 0, isDynamic, true);
	};

	this.makeBox = function(x, y, w, h, isDynamic) {
		return mkBody(x, y, w, h, isDynamic, false);
	};

	this.debugEnabled = function(b) {
		var debugDraw = null;
		if (b) {
			debugDraw = new b2DebugDraw();
			// setup debug draw
			debugDraw.SetSprite(cfg.ctx);
			debugDraw.SetDrawScale(cfg.scale);
			debugDraw.SetFillAlpha(0.7);
			debugDraw.SetLineThickness(0.9);
			debugDraw.SetFlags(//
			b2DebugDraw.e_shapeBit //
					| b2DebugDraw.e_jointBit // 
					| b2DebugDraw.e_pairBit //
			// | b2DebugDraw.e_centerOfMassBit //
			// | b2DebugDraw.e_aabbBit //
			);
		}
		cfg.world.SetDebugDraw(debugDraw);
	};

	this.running = function(p) {
		cfg.running = p;
	};

	this.step = function() {
		if (cfg.running) {
			cfg.world.Step(1 / 60, 10, 10);
			cfg.world.DrawDebugData();
			cfg.world.ClearForces();
		}
	};

	this.debugEnabled(cfg.debug);
}