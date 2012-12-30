var _escala = 40;
var world = new b2World(new b2Vec2(0, 10), true);
var canvas = document.getElementById("c");
var ctx = canvas.getContext("2d");
var actorList = [];

var wm = new WorldSugar({
	scale : _escala,
	running : true,
	world : world,
	debug : true,
	ctx : ctx
});

var jogador = new BasicActorSugar({
	body : wm.makeCircle(10, 10, 1.1, 1.1, true),
	image : "../images/awesome.png",
	scale : _escala,
	ctx : ctx
});
actorList.push(jogador);

var cm = new CameraSugar({
	height : canvas.height,
	width : canvas.width,
	player : jogador,
	scale : _escala,
	ctx : ctx
});

// mouse
var mm = new MouseSugar({
	world : world,
	canvas : canvas,
	scale : _escala,
	camera : cm
});

// create ground
wm.makeBox(0.5, 0.5, 50, 0.2);// up
wm.makeBox(0.5, 20, 50, 0.2);// down
wm.makeBox(0.5, 0.5, 0.2, 20);// left
wm.makeBox(50, 0.5, 0.2, 20);// right

// create some objects
for ( var i = 0; i < 100; ++i) {
	var x = Math.random() * 10 + 5;
	var y = Math.random() * 10 + 5;
	var w = Math.random() * 1.3 + 0.1;
	var h = Math.random() * 1.1 + 0.1;
	var r = Math.random();
	if (r < 0.5)
		actorList.push(new BasicActorSugar({
			body : wm.makeBox(x, y, w, h, true),
			image : "../images/box.jpg",
			scale : _escala,
			ctx : ctx
		}));
	else
		actorList.push(new BasicActorSugar({
			body : wm.makeCircle(x, y, w, true),
			image : "../images/bigball.png",
			scale : _escala,
			ctx : ctx
		}));
}

var boo = true;

// update
function update() {
	if (boo) {

		ctx.translate(0, 0);
		ctx.clearRect(0, 0, canvas.width, canvas.height);
		ctx.save();

		cm.step();
		wm.step();
		mm.step();
		
		var k = actorList.length;
		while (k--)
			actorList[k].step();
		
		ctx.restore();
	}

}

// mainloop
window.setInterval(update, 1000 / 60);
