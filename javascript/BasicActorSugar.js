/**
 * This helps you to create a simple actor to handle a ball picture
 * 
 * @param cfg.image
 *            hopefully the ball image
 * @param cfg.body
 *            b2Body to recover spatial information
 * @param cfg.scale
 *            pixel-to-meters constraint
 * @param cfg.ctx
 *            drawing context
 */
function BasicActorSugar(cfg) {

	// publish config options
	this.cfg = cfg;

	var img = new Image();

	this.setImage = function(image) {
		cfg.image = image;
		img.src = cfg.image;
	};
	this.setImage(cfg.image);

	this.GetPosition = function() {
		return cfg.body.GetPosition();
	};

	this.step = function() {

		var p = cfg.body.GetPosition();
		var px = p.x * cfg.scale;
		var py = p.y * cfg.scale;
		var w = 0;
		var h = 0;
		if (cfg.body.GetFixtureList().GetShape().GetType() == b2Shape.e_circleShape) {
			w = cfg.body.GetFixtureList().GetShape().GetRadius();
			h = w;
		} else if (cfg.body.GetFixtureList().GetShape().GetType() == b2Shape.e_polygonShape) {
			var v = cfg.body.GetFixtureList().GetShape().GetVertices();
			w = Math.sqrt(v[0].x * v[0].x);
			h = Math.sqrt(v[0].y * v[0].y);
		}
		w *= cfg.scale;
		h *= cfg.scale;

		var a = cfg.body.GetAngle();

		cfg.ctx.save();
		cfg.ctx.translate(px, py);
		cfg.ctx.rotate(a);
		// the image is a circle. We trust it.
		cfg.ctx.drawImage(img, -w, -h, w * 2, h * 2);
		cfg.ctx.restore();
	};
}