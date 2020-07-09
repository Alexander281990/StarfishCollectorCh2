package com.starfish.alex.ivan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public class BaseActor extends Actor {

    // переменные для анимации
    private Animation<TextureRegion> animation;
    private float elapsedTime;
    private boolean animationPaused;

    // переменные для плавного движения
    private Vector2 velocityVec;

    // переменные для ускорения обьекта
    private Vector2 accelerationVec;
    private float acceleration;

    // переменные для максимальной скорости и замедления обьекта
    private float maxSpeed;
    private float deceleration;

    // переменная для многоугольника "Polygon"(для обнаружения столкновения обьектов)
    private Polygon boundaryPolygon;

    public BaseActor(float x, float y, Stage s) {
        super();
        setPosition(x, y);
        s.addActor(this);

        // инициализация переменных для анимации
        animation = null;
        elapsedTime = 0;
        animationPaused = false;

        // инициализация переменных для плавного движения
        velocityVec = new Vector2(0,0);

        // инициализация переменных для ускорения
        acceleration = 0;
        accelerationVec = new Vector2(0, 0);

        // инициализация переменных для максимальной скорости и ускорения
        maxSpeed = 1000;
        deceleration = 0;

    }

    // метод для настройки анимации
    public void setAnimation(Animation<TextureRegion> anim) {
        animation = anim;
        TextureRegion tr = animation.getKeyFrame(0);
        float w = tr.getRegionWidth();
        float h = tr.getRegionHeight();
        setSize(w, h);
        setOrigin(w/2, h/2);

        // код для автомотического создания формы Polygon, для обнаружения столкновения
        if (boundaryPolygon == null)
            setBoundaryRectangle();
    }

    // метод для изменения значения animationPaused
    public void setAnimationPaused(boolean paused) {
        animationPaused = paused;
    }

    //
    public void act(float dt) {
        super.act(dt);
        if (!animationPaused)
            elapsedTime += dt;
    }

    // переопределенный метод рисования сласса Actor
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Color c = getColor();
        batch.setColor(c.r, c.g, c.b, c.a);

        if (animation != null && isVisible())
            batch.draw(animation.getKeyFrame(elapsedTime), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    // метод, который загружает отдельные файлы изображений и использует их для создания обьуктов Animation
    public Animation<TextureRegion> loadAnimationFromFiles(String[] fileNames, float frameDuration, boolean loop)
    {
        int fileCount = fileNames.length;
        Array<TextureRegion> textureArray = new Array<TextureRegion>();

        for (int n = 0; n < fileCount; n++)
        {
            String fileName = fileNames[n];
            Texture texture = new Texture( Gdx.files.internal(fileName) );
            texture.setFilter( Texture.TextureFilter.Linear, Texture.TextureFilter.Linear );
            textureArray.add( new TextureRegion( texture ) );
        }

        Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, textureArray);

        if (loop)
            anim.setPlayMode(Animation.PlayMode.LOOP);
        else
            anim.setPlayMode(Animation.PlayMode.NORMAL);

        if (animation == null)
            setAnimation(anim);

        return anim;
    }

    // метод, который загружает таблицу изображений и использует их для создания обьуктов Animation
    public Animation<TextureRegion> loadAnimationFromSheet(String fileName, int rows, int cols, float frameDuration, boolean loop)
    {
        Texture texture = new Texture(Gdx.files.internal(fileName), true);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        int frameWidth = texture.getWidth() / cols;
        int frameHeight = texture.getHeight() / rows;

        TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);

        Array<TextureRegion> textureArray = new Array<TextureRegion>();

        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                textureArray.add( temp[r][c] );

        Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, textureArray);

        if (loop)
            anim.setPlayMode(Animation.PlayMode.LOOP);
        else
            anim.setPlayMode(Animation.PlayMode.NORMAL);

        if (animation == null)
            setAnimation(anim);

        return anim;
    }

    // метод для использования аднокадровой анимации и отоброжения неподвижного изображения(Для обеспечения согласованности)
    public Animation<TextureRegion> loadTexture(String fileName) {
        String[] fileNames = new String[1];
        fileNames[0] = fileName;
        return loadAnimationFromFiles(fileNames, 1, true);
    }

    // метод для проверки завершения анимации
    public boolean isAnimationFinished() {
        return animation.isAnimationFinished(elapsedTime);
    }

    // методы для установки и получения скорости и угла движения обьекта Actor
    public void setSpeed(float speed) {
        if (velocityVec.len() == 0)
            velocityVec.set(speed, 0);
        else
            velocityVec.setLength(speed);
    }

    public float getSpeed() {
        return velocityVec.len();
    }

    public void setMotionAngle(float angle) {
        velocityVec.setAngle(angle);
    }

    public float getMotionAngle() {
        return velocityVec.angle();
    }

    // метод, который узнает когда движется обьект, а когда нет
    public boolean isMoving() {
        return (getSpeed() > 0);
    }

    // метод устанавливает величину вектора для ускорения обьекта
    public void setAcceleration(float acc) {
        acceleration = acc;
    }

    // метод для самого ускорения обьекта в указанном направлении
    public void accelerateAtAngle(float angle) {
        accelerationVec.add(new Vector2(acceleration, 0).setAngle(angle));
    }

    // метод, который ускоряет обьект в том направлении, в котором он находится в данный момент
    public void accelerateForward() {
        accelerateAtAngle(getRotation());
    }

    // методы для установки максимальной скорости и замедления
    public void setMaxSpeed(float ms) {
        maxSpeed = ms;
    }

    public void setDeceleration(float dec) {
        deceleration = dec;
    }

    // метод для вычисления скорости, ускорения, направления, положения обьекта, и ОБНОВЛЕНИЯ ЭТИХ ПАРАМЕТРОВ
    public void applyPhysics(float dt) {
        velocityVec.add(accelerationVec.x * dt, accelerationVec.y * dt);
        float speed = getSpeed();

        if (accelerationVec.len() == 0)
            speed -= deceleration * dt;

        speed = MathUtils.clamp(speed, 0, maxSpeed);

        setSpeed(speed);

        moveBy(velocityVec.x * dt, velocityVec.y * dt);

        accelerationVec.set(0, 0);
    }

    // метод для создания прямоугольного многоугольника
    public void setBoundaryRectangle() {
        float w = getWidth();
        float h = getHeight();
        float[] vertices = {0, 0, w, 0, w, h, 0, h};
        boundaryPolygon = new Polygon(vertices);
    }

    // метод для инициализации многоугольной фигуры
    public void setBoundaryPolygon(int numSide) {
        float w = getWidth();
        float h = getHeight();

        float[] vertices = new float[2 * numSide];
        for (int i = 0; i < numSide; i ++) {
            float angle = i * 6.28f / numSide;
            vertices[2 * i] = w/2 * MathUtils.cos(angle) + w/2;
            vertices[2 * i + 1] = h/2 * MathUtils.sin(angle) + h/2;
        }
        boundaryPolygon = new Polygon(vertices);
    }

    // метод, который возвращает многоугольник столкновения для актера
    public Polygon getBoundaryPolygon() {
        boundaryPolygon.setPosition(getX(), getY());
        boundaryPolygon.setOrigin(getOriginX(), getOriginY());
        boundaryPolygon.setRotation(getRotation());
        boundaryPolygon.setScale(getScaleX(), getScaleY());

        return boundaryPolygon;
    }

    // метод для обнаружения перекрытия полигонов(столкновения обьектов)
    public boolean overlaps(BaseActor other) {
        Polygon poly1 = this.getBoundaryPolygon();
        Polygon poly2 = other.getBoundaryPolygon();

        if (!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle()))
            return false;

        return Intersector.overlapConvexPolygons(poly1, poly2);
    }

    //
    public void centerAtPosition(float x, float y) {
        setPosition(x - getWidth()/2, y - getHeight()/2);
    }

    //
    public void centerAtActor(BaseActor other) {
        centerAtPosition(other.getX() + other.getWidth()/2, other.getY() + other.getHeight()/2);
    }

    // метод для изьенения прозрачности обьекта
    public void setOpacity(float opacity) {
        this.getColor().a = opacity;
    }



}
