/**
 * 
 * Component to translate drawing context and provide new origin from outer
 * components
 * 
 * @param cfg.player
 *            actor or b2body to provide GetPosition
 * @param cfg.ctx
 *            drawing context
 * @param cfg.width
 *            canvas width
 * @param cfg.height
 *            canvas height
 */
function CameraSugar(cfg) {

	// publish config options
	this.cfg = cfg;

	this.pos = {
		x : 0,
		y : 0
	};

	this.step = function() {
		cfg.ctx.translate(0, 0);
		var v = cfg.player.GetPosition();
		this.pos.x = -v.x * cfg.scale + cfg.width / 2;
		this.pos.y = -v.y * cfg.scale + cfg.height / 2;
		cfg.ctx.translate(this.pos.x, this.pos.y);
	};
}