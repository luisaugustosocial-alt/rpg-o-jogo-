package com.luisaugusto.hogwarts;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class HogwartsRPGGame extends ApplicationAdapter {
    private PerspectiveCamera camera;
    private ModelBatch batch;
    private Environment environment;
    private ModelBuilder builder;
    private final Array<Model> ownedModels = new Array<>();
    private final Array<ModelInstance> scene = new Array<>();
    private final Array<Enemy> enemies = new Array<>();
    private Model spellModel;
    private ModelInstance spell;
    private Vector3 spellVelocity = new Vector3();
    private boolean spellActive;
    private float yaw = 180f;
    private float pitch = -8f;
    private float playerSpeed = 8f;
    private int mission = 0;
    private int defeated = 0;
    private float spellCooldown = 0f;
    private String message = "Missao 1: explore o patio e derrote o Trasgo";
    private float messageTimer = 8f;

    private static final String[] MISSIONS = {
        "Missao 1: explore o patio e derrote o Trasgo",
        "Missao 2: encontre a criatura na biblioteca",
        "Missao 3: sobreviva ao Dementador no corredor",
        "Missao 4: conclua o treino de feiticos",
        "Todas as missoes iniciais foram concluidas!"
    };

    @Override public void create() {
        batch = new ModelBatch();
        builder = new ModelBuilder();
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.65f, 0.65f, 0.72f, 1f));
        environment.add(new DirectionalLight().set(0.9f, 0.86f, 0.76f, -0.4f, -1f, -0.3f));

        camera = new PerspectiveCamera(70f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(0f, 2.2f, 10f);
        camera.near = 0.1f;
        camera.far = 220f;
        camera.lookAt(0, 1, 0);
        camera.update();

        createWorld();
        spawnEnemy("TRASGO", new Vector3(0, 1, -9), Color.valueOf("61715d"), 3);
        Gdx.input.setCursorCatched(true);
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override public boolean mouseMoved(int screenX, int screenY) {
                if (Gdx.input.isCursorCatched()) {
                    yaw -= Gdx.input.getDeltaX() * 0.18f;
                    pitch = Math.max(-70f, Math.min(45f, pitch + Gdx.input.getDeltaY() * 0.15f));
                }
                return true;
            }
            @Override public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.ESCAPE) Gdx.input.setCursorCatched(!Gdx.input.isCursorCatched());
                if (keycode == Input.Keys.E) castSpell();
                if (keycode == Input.Keys.F) advanceMission();
                return true;
            }
            @Override public boolean touchDown(int x, int y, int pointer, int button) {
                if (button == Input.Buttons.LEFT) castSpell();
                return true;
            }
        });
    }

    private Model makeBox(float w, float h, float d, Color color) {
        Model m = builder.createBox(w, h, d,
            new Material(ColorAttribute.createDiffuse(color)),
            VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        ownedModels.add(m);
        return m;
    }

    private Model makeSphere(float size, Color color) {
        Model m = builder.createSphere(size, size, size, 18, 18,
            new Material(ColorAttribute.createDiffuse(color)),
            VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        ownedModels.add(m);
        return m;
    }

    private void add(Model model, float x, float y, float z) {
        ModelInstance i = new ModelInstance(model);
        i.transform.setToTranslation(x, y, z);
        scene.add(i);
    }

    private void createWorld() {
        add(makeBox(34, 0.4f, 42, Color.valueOf("45494d")), 0, -0.3f, -8);
        Model wall = makeBox(34, 8, 1, Color.valueOf("77716a"));
        add(wall, 0, 3.6f, -28);
        add(wall, -17, 3.6f, -8);
        add(wall, 17, 3.6f, -8);
        Model tower = makeBox(5, 15, 5, Color.valueOf("69676d"));
        add(tower, -8, 7.3f, -24);
        add(tower, 8, 7.3f, -24);
        Model window = makeBox(1.2f, 2.1f, 0.12f, Color.valueOf("9ec9e8"));
        for (int z = -23; z <= -12; z += 5) {
            add(window, -16.35f, 3f, z);
            add(window, 16.35f, 3f, z);
        }
        Model treeTrunk = makeBox(0.8f, 4f, 0.8f, Color.valueOf("55402d"));
        Model foliage = makeSphere(4f, Color.valueOf("263f34"));
        for (int i = 0; i < 12; i++) {
            float x = (i % 2 == 0 ? -1 : 1) * (20 + (i % 3) * 3);
            float z = -26 + i * 3;
            add(treeTrunk, x, 1.7f, z);
            add(foliage, x, 4f, z);
        }
        Model torch = makeSphere(0.45f, Color.valueOf("ffb54a"));
        add(torch, -5, 2.2f, -5);
        add(torch, 5, 2.2f, -5);
        add(torch, -5, 2.2f, -17);
        add(torch, 5, 2.2f, -17);
    }

    private void spawnEnemy(String name, Vector3 position, Color color, int hp) {
        Model body = makeBox(1.5f, 2.2f, 1.1f, color);
        Model head = makeSphere(0.9f, color.cpy().lerp(Color.LIGHT_GRAY, 0.25f));
        ModelInstance bodyI = new ModelInstance(body);
        bodyI.transform.setToTranslation(position.x, position.y, position.z);
        ModelInstance headI = new ModelInstance(head);
        headI.transform.setToTranslation(position.x, position.y + 1.55f, position.z);
        scene.add(bodyI);
        scene.add(headI);
        enemies.add(new Enemy(name, position.cpy(), hp, bodyI, headI));
    }

    private void castSpell() {
        if (spellCooldown > 0f || spellActive || mission >= 4) return;
        spellCooldown = 0.22f;
        if (spellModel == null) spellModel = makeSphere(0.42f, Color.CYAN);
        spell = new ModelInstance(spellModel);
        Vector3 forward = camera.direction.cpy().nor();
        spell.transform.setToTranslation(camera.position.cpy().mulAdd(forward, 1.2f));
        spellVelocity.set(forward).scl(26f);
        spellActive = true;
        message = "FEITICO LANCADO!";
        messageTimer = 1.5f;
    }

    private void advanceMission() {
        if (mission < 4 && enemies.size == 0) {
            mission++;
            message = MISSIONS[mission];
            messageTimer = 6f;
            if (mission == 1) spawnEnemy("ARANHIDO", new Vector3(-3, 1, -13), Color.valueOf("483b48"), 4);
            if (mission == 2) spawnEnemy("DEMENTADOR", new Vector3(3, 1, -16), Color.valueOf("34333f"), 5);
            if (mission == 3) {
                spawnEnemy("TRASGO", new Vector3(-3, 1, -13), Color.valueOf("61715d"), 3);
                spawnEnemy("DEMENTADOR", new Vector3(3, 1, -17), Color.valueOf("34333f"), 4);
            }
        } else if (enemies.size > 0) {
            message = "Derrote todos os inimigos antes de avancar!";
            messageTimer = 2.5f;
        }
    }

    @Override public void render() {
        float dt = Math.min(Gdx.graphics.getDeltaTime(), 0.04f);
        ScreenUtils.clear(0.035f, 0.055f, 0.075f, 1f, true);
        spellCooldown = Math.max(0, spellCooldown - dt);
        messageTimer = Math.max(0, messageTimer - dt);

        Vector3 forward = new Vector3(camera.direction.x, 0, camera.direction.z).nor();
        Vector3 right = new Vector3(forward).crs(Vector3.Y).nor();
        if (Gdx.input.isKeyPressed(Input.Keys.W)) camera.position.mulAdd(forward, playerSpeed * dt);
        if (Gdx.input.isKeyPressed(Input.Keys.S)) camera.position.mulAdd(forward, -playerSpeed * dt);
        if (Gdx.input.isKeyPressed(Input.Keys.A)) camera.position.mulAdd(right, -playerSpeed * dt);
        if (Gdx.input.isKeyPressed(Input.Keys.D)) camera.position.mulAdd(right, playerSpeed * dt);
        camera.position.x = Math.max(-14, Math.min(14, camera.position.x));
        camera.position.z = Math.max(-30, Math.min(12, camera.position.z));
        camera.position.y = 2.2f;
        camera.direction.set((float)Math.sin(Math.toRadians(yaw)) * (float)Math.cos(Math.toRadians(pitch)),
            (float)Math.sin(Math.toRadians(pitch)),
            (float)Math.cos(Math.toRadians(yaw)) * (float)Math.cos(Math.toRadians(pitch))).nor();
        camera.up.set(Vector3.Y);
        camera.update();

        if (spellActive) {
            Vector3 p = new Vector3();
            spell.transform.getTranslation(p);
            p.mulAdd(spellVelocity, dt);
            spell.transform.setToTranslation(p);
            boolean hit = false;
            for (int i = enemies.size - 1; i >= 0; i--) {
                Enemy e = enemies.get(i);
                if (p.dst(e.position.cpy().add(0, 1, 0)) < 1.6f) {
                    e.hp--;
                    message = "IMPACTO! " + e.name;
                    messageTimer = 1.2f;
                    if (e.hp <= 0) {
                        scene.removeValue(e.body, true);
                        scene.removeValue(e.head, true);
                        enemies.removeIndex(i);
                        defeated++;
                        message = e.name + " derrotado!";
                        messageTimer = 2.5f;
                    }
                    hit = true;
                    break;
                }
            }
            if (hit || p.dst(camera.position) > 55) spellActive = false;
        }

        batch.begin(camera);
        for (ModelInstance i : scene) batch.render(i, environment);
        if (spellActive && spell != null) batch.render(spell, environment);
        batch.end();

        Gdx.graphics.setTitle("Hogwarts RPG | WASD mover | mouse olhar | E/clique esquerdo: feitico | F: proxima missao | " +
            MISSIONS[Math.min(mission, 4)] + " | Inimigos: " + enemies.size + " | Derrotados: " + defeated +
            (messageTimer > 0 ? " | " + message : ""));
    }

    @Override public void dispose() {
        batch.dispose();
        for (Model model : ownedModels) model.dispose();
    }

    private static class Enemy {
        final String name;
        final Vector3 position;
        int hp;
        final ModelInstance body, head;
        Enemy(String name, Vector3 position, int hp, ModelInstance body, ModelInstance head) {
            this.name = name; this.position = position; this.hp = hp; this.body = body; this.head = head;
        }
    }
}
