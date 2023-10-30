package com.denizdogan.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import java.util.ArrayList;
import java.util.Random;

public class SurvivorBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture mainCharacter;
	Texture skeleton;
	Texture gameOverTexture;
	Circle mainCharacterCircle;
	Texture bee;
	Texture ufo;
	ArrayList<GameActor> enemies;
	ArrayList<GameActor> bullets;
	float mainCharacterX;
	float mainCharacterY;
	float velocity = 0;
	final float gravity = 0.4f;
	int gameState;
	Random random;
	Button fireButton;
	ShapeRenderer shapeRenderer;
	int numberOfEnemies;
	int score;
	BitmapFont scoreFont;
	BitmapFont playAgain;
	final String playAgainText = "Tap to Play Again";
	long startTime;
	long currentTime;
	@Override
	public void create () {


		//fireButton = new TextButton("StartGame", null, "big");
		//fireButton.setSize(200,200);
		//fireButton.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight()/2);



		batch = new SpriteBatch();
		background = new Texture("background_game_assets/full-bg.png");
		mainCharacter = new Texture("main_character.png");
		ufo = new Texture("ufo_enemy_game_character.png");
		bee = new Texture("bee_enemy_game_character/PNG/1.png");
		skeleton = new Texture("skeleton.png");
		gameOverTexture = new Texture("game_over.png");
		mainCharacterX = GameConstants.WIDTH / 4;
		mainCharacterY = GameConstants.HEIGHT / 2;
		enemies = new ArrayList<>();
		bullets = new ArrayList<>();
		mainCharacterCircle =  new Circle();
		random = new Random();
		scoreFont = new BitmapFont();
		playAgain = new BitmapFont();
		playAgain.setColor(Color.RED);
		playAgain.getData().setScale(8);
		scoreFont.setColor(Color.RED);
		scoreFont.getData().setScale(5);
		numberOfEnemies = 5;
		score = 0;
		shapeRenderer = new ShapeRenderer();
	}

	@Override
	public void render () {
		// like update function on unity
		batch.begin();
		if(gameState == 1){

			if(Gdx.input.justTouched()){
				velocity = -7;
			}
			if(mainCharacterY > 0){
				velocity = velocity + gravity;
				if(mainCharacterY < GameConstants.HEIGHT - GameConstants.GAME_ACTOR_HEIGHT){
					mainCharacterY = mainCharacterY - velocity;
				}
				else{
					mainCharacterY--;
				}

			}
			else {
				gameState = 2;
			}
			batch.draw(background, 0, 0, GameConstants.WIDTH, GameConstants.HEIGHT);
			batch.draw(mainCharacter, mainCharacterX, mainCharacterY, GameConstants.GAME_ACTOR_WIDTH, GameConstants.GAME_ACTOR_HEIGHT);
			scoreFont.draw(batch, String.valueOf(score), GameConstants.SCORE_X, GameConstants.SCORE_Y);
			mainCharacterCircle.set(mainCharacterX + GameConstants.CIRCLE_OFFSET_X, mainCharacterY + GameConstants.CIRCLE_OFFSET_Y, GameConstants.CIRCLE_OFFSET_X / 1.5f);
			drawEnemies();
			moveEnemies();
			checkBounds();
			checkCollisions();

			if(currentTime - startTime > 5){
				startTime = System.currentTimeMillis() / 1000;
				addEnemy();
			}
		}
		else if(gameState == 0){
			if(Gdx.input.justTouched()){
				gameState = 1;
				startTime = System.currentTimeMillis() / 1000;
				fillInEnemies();
			}
			batch.draw(background, 0, 0, GameConstants.WIDTH, GameConstants.HEIGHT);
			batch.draw(mainCharacter, mainCharacterX, mainCharacterY, GameConstants.GAME_ACTOR_WIDTH, GameConstants.GAME_ACTOR_HEIGHT);
			scoreFont.draw(batch, String.valueOf(score), GameConstants.SCORE_X, GameConstants.SCORE_Y);
		}
		else if (gameState == 2) {
			if(Gdx.input.justTouched()){
				enemies.clear();
				mainCharacterX = GameConstants.WIDTH / 4;
				mainCharacterY = GameConstants.HEIGHT / 2;
				velocity = 0;
				score = 0;
				gameState = 0;
				numberOfEnemies = 5;
			}
			batch.draw(gameOverTexture, 0, 0, GameConstants.WIDTH, GameConstants.HEIGHT);
			scoreFont.draw(batch, String.valueOf(score), GameConstants.SCORE_X, GameConstants.SCORE_Y);
			playAgain.draw(batch, playAgainText, GameConstants.WIDTH / 3.8f, GameConstants.HEIGHT / 8);
		}
		currentTime = System.currentTimeMillis() / 1000;
		batch.end();
//
//		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//		shapeRenderer.setColor(Color.BLACK);
//		shapeRenderer.circle(mainCharacterCircle.x, mainCharacterCircle.y, mainCharacterCircle.radius);
//
//		for(GameActor g : enemies){
//			shapeRenderer.circle(g.getCircle().x, g.getCircle().y , g.getCircle().radius);
//		}
//
//		shapeRenderer.end();
	}

	public void drawEnemies(){

		if(enemies.size() > 0){
			for (GameActor gameActor : enemies){
				batch.draw(gameActor.getTexture(), gameActor.getStartX(), gameActor.getStartY(), gameActor.getWidth(), gameActor.getHeight());
			}
		}
	}
	public void checkBounds(){
		if (enemies.size() > 0){
			for(GameActor gameActor : enemies){
				if(gameActor.getStartX() < -gameActor.getWidth()){
					gameActor.setStartX(random.nextInt(300) + Gdx.graphics.getWidth()) ;
					gameActor.setStartY(random.nextInt(Gdx.graphics.getHeight()));
					gameActor.setSpeed(random.nextInt(10) + 1);
					gameActor.setScored(false);
					int whichEnemy = random.nextInt(3);
					if ( whichEnemy== 0){
						gameActor.setPieceID(0);
						gameActor.setTexture(bee);
						gameActor.setWidth(GameConstants.GAME_ACTOR_WIDTH);
						gameActor.setHeight(GameConstants.GAME_ACTOR_HEIGHT);
						gameActor.getCircle().set(gameActor.getStartX() + GameConstants.CIRCLE_OFFSET_X, gameActor.getStartY() + GameConstants.CIRCLE_OFFSET_Y, GameConstants.CIRCLE_OFFSET_X);
					}
					else if(whichEnemy == 1){
						gameActor.setPieceID(1);
						gameActor.setTexture(ufo);
						gameActor.setWidth(GameConstants.GAME_ACTOR_WIDTH);
						gameActor.setHeight(GameConstants.GAME_ACTOR_HEIGHT);
						gameActor.getCircle().set(gameActor.getStartX() + GameConstants.CIRCLE_OFFSET_X, gameActor.getStartY() + GameConstants.CIRCLE_OFFSET_Y, GameConstants.CIRCLE_OFFSET_X);
					}
					else if(whichEnemy == 2){
						gameActor.setPieceID(2);
						gameActor.setTexture(skeleton);
						gameActor.setWidth(GameConstants.GAME_ACTOR_WIDTH*2);
						gameActor.setHeight(GameConstants.GAME_ACTOR_HEIGHT*2);
						gameActor.getCircle().set(gameActor.getStartX() + gameActor.getWidth() / 2, gameActor.getStartY() + gameActor.getHeight() / 2, GameConstants.CIRCLE_OFFSET_X * 2);
					}
				}
				if(mainCharacterX > gameActor.getStartX() && !gameActor.isScored()){
					gameActor.setScored(true);
					score += 1;
				}
			}
		}
	}
	public void moveEnemies(){
		if (enemies.size() > 0){
			for(GameActor gameActor : enemies){
				gameActor.setStartX(gameActor.getStartX() - gameActor.getSpeed());

				if (gameActor.getPieceID() != 2){
					gameActor.getCircle().set(gameActor.getStartX() + GameConstants.CIRCLE_OFFSET_X, gameActor.getStartY() + GameConstants.CIRCLE_OFFSET_Y, GameConstants.CIRCLE_OFFSET_X);
				}
				else{
					gameActor.getCircle().set(gameActor.getStartX() + gameActor.getWidth() / 2, gameActor.getStartY() + gameActor.getHeight() / 2, GameConstants.CIRCLE_OFFSET_X * 2);
				}


			}
		}
	}
	@Override
	public void dispose () {

	}
	public void checkCollisions(){
		for (GameActor g : enemies){
			if(Intersector.overlaps(mainCharacterCircle, g.getCircle())){
				gameState = 2;
			}
		}
	}
	public void fillInEnemies() {
		for (int i = 0;i < numberOfEnemies; i++){
			addEnemy();
		}
	}
	public void addEnemy(){
		Texture texture = null;
		Circle circle = new Circle();
		float startX, startY, width, height, speed;
		int whichEnemy = random.nextInt(3);

		startX = random.nextInt(300) + GameConstants.WIDTH;
		startY = random.nextInt((int)GameConstants.HEIGHT - (int)GameConstants.GAME_ACTOR_HEIGHT);
		if (whichEnemy == 0){
			texture = bee;
			width = GameConstants.GAME_ACTOR_WIDTH;
			height = GameConstants.GAME_ACTOR_HEIGHT;
			circle.set(startX + GameConstants.CIRCLE_OFFSET_X, startY + GameConstants.CIRCLE_OFFSET_Y, GameConstants.CIRCLE_OFFSET_X);
		}
		else if(whichEnemy == 1){
			texture = ufo;
			width = GameConstants.GAME_ACTOR_WIDTH;
			height = GameConstants.GAME_ACTOR_HEIGHT;
			circle.set(startX + GameConstants.CIRCLE_OFFSET_X, startY + GameConstants.CIRCLE_OFFSET_Y, GameConstants.CIRCLE_OFFSET_X);
		}
		else {
			texture = skeleton;
			width = GameConstants.GAME_ACTOR_WIDTH *2;
			height = GameConstants.GAME_ACTOR_HEIGHT*2;
			circle.set(startX + width / 2, startY + height / 2, GameConstants.CIRCLE_OFFSET_X * 2);
		}


		speed = random.nextInt(10) + 1;

		GameActor gameActor = new GameActor(whichEnemy ,texture, startX, startY, width, height, speed, circle, false);
		enemies.add(gameActor);
	}
}
