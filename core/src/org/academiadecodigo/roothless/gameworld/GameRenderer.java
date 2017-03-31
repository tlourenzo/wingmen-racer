package org.academiadecodigo.roothless.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import org.academiadecodigo.roothless.gameobjects.Obstacle;
import org.academiadecodigo.roothless.gameobjects.Player;
import org.academiadecodigo.roothless.helpers.AssetLoader;

/**
 * Created by codecadet on 30/03/17.
 */
public class GameRenderer {

    private GameWorld gameWorld;
    private PerspectiveCamera cam;
    private Environment environment;
    // private CameraInputController camController;
    private ModelBatch modelBatch;
    private ModelInstance floor1, floor2, floor3, floor4, floor5, floor6, floor7, playerInstance;
    private Texture back;
    private SpriteBatch batcher;
    private boolean firstObj;
    private int counter;
    private List<Obstacle> obstacles;
    private Player player;

    public GameRenderer(GameWorld gameWorld) {

        this.gameWorld = gameWorld;

        obstacles = new LinkedList<Obstacle>();

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        modelBatch = new ModelBatch();
        batcher = new SpriteBatch();



        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(0, 4, 7);
        cam.lookAt(0, 0, 0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();
        firstObj = false;

        initAssets();


        //camController = new CameraInputController(cam);
        //Gdx.input.setInputProcessor(camController);
    }


    public void render (float runTime) {

        //camController.update();

        Gdx.gl.glViewport(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        batcher.begin();
        batcher.draw(new TextureRegion(back), 0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batcher.end();

        modelBatch.begin(cam);
        modelBatch.render(floor1, environment);
        modelBatch.render(floor2, environment);
        modelBatch.render(floor3, environment);
        modelBatch.render(floor4, environment);
        modelBatch.render(floor5, environment);
        modelBatch.render(floor6, environment);
        modelBatch.render(floor7, environment);
        modelBatch.render(playerInstance, environment);

        floor1.transform.translate(0,0, 5*Gdx.graphics.getDeltaTime());
        floor2.transform.translate(0,0, 5*Gdx.graphics.getDeltaTime());
        floor3.transform.translate(0,0, 5*Gdx.graphics.getDeltaTime());
        floor4.transform.translate(0,0, 5*Gdx.graphics.getDeltaTime());
        floor5.transform.translate(0,0, 5*Gdx.graphics.getDeltaTime());
        floor6.transform.translate(0,0, 5*Gdx.graphics.getDeltaTime());
        floor7.transform.translate(0,0, 5*Gdx.graphics.getDeltaTime());

        if (runTime - counter > 0) {
            counter++;
            obstacles.add(new Obstacle(0,0,0 ,0,0,0, 5));
            firstObj = true;
            gameWorld.getScroll().firstObj = true;
        }

        if (gameWorld.getScroll().isFirstObj()) {

            for (Obstacle obstacle: obstacles) {
                modelBatch.render(obstacle.getInstance(), environment);
                obstacle.getInstance().transform.translate(0,0,5*Gdx.graphics.getDeltaTime());
            }
        }

        player = gameWorld.getPlayer();
        playerInstance.transform.translate(player.getPosition().x, player.getPosition().y, player.getPosition().z);

        modelBatch.end();



    }

    public void initAssets() {
        floor1 = gameWorld.getScroll().getFloor1().getModelInstance();
        floor2 = gameWorld.getScroll().getFloor2().getModelInstance();
        floor3 = gameWorld.getScroll().getFloor3().getModelInstance();
        floor4 = gameWorld.getScroll().getFloor4().getModelInstance();
        floor5 = gameWorld.getScroll().getFloor5().getModelInstance();
        floor6 = gameWorld.getScroll().getFloor6().getModelInstance();
        floor7 = gameWorld.getScroll().getFloor7().getModelInstance();

        playerInstance = gameWorld.getPlayer().getPlayerInstance();

        back = AssetLoader.backGround;

        floor1.transform.translate(0,0,0);
        floor2.transform.translate(0,0,-7);
        floor3.transform.translate(0,0,-14);
        floor4.transform.translate(0,0,-21);
        floor5.transform.translate(0,0,-28);
        floor6.transform.translate(0,0,-35);
        floor7.transform.translate(0,0,-42);


    }
}