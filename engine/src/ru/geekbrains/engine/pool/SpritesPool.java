package ru.geekbrains.engine.pool;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.engine.sprite.Sprite;

public abstract class SpritesPool<T extends Sprite> {//пул объектов; в виде дженерика - для всех объектов, который наследуются от Sprite
	protected final List<T> activeObjects = new ArrayList<T>();//список активных объектов
	protected final List<T> freeObjects = new ArrayList<T>();//список объектов в режиме ожидания

	protected abstract T newObject();//будет создавать новый экземпляр объекта

	public T obtain(){//возращать новый, либо объект из списка
		T object;
		if(freeObjects.isEmpty()){//если пустой, создаем новый объект
			object = newObject();
		}else{//иначе достаем из списка
			object = freeObjects.remove(freeObjects.size() - 1);//и возращает и  удаляет из списка; берем последний
		}
		activeObjects.add(object);//добавляем в лист активных
		debugLog();
		return object;
	}

	public void updateActiveSprites(float delta){//передаем объектам события; обновление активных объектов
		for (int i = 0; i < activeObjects.size(); i++) {
			Sprite sprite = activeObjects.get(i);
			if(sprite.isDestroyed()){
				throw new RuntimeException("Попытка обновления объекта, помеченного на удаление");
			}
			sprite.update(delta);
		}
	}

	//метод, который все активные методы помеченные на удаление во freeObjects
	public void freeAllDestroyedActiveObjects(){
		for (int i = 0; i < activeObjects.size(); i++) {
			T sprite = activeObjects.get(i);
			if(sprite.isDestroyed()){
				free(sprite);
				i--;
				sprite.flushDestroy();
			}
		}
	}

	public void drawActiveObjects(SpriteBatch batch){//отрисовка активных объектов
		for (int i = 0; i < activeObjects.size(); i++) {
			Sprite sprite = activeObjects.get(i);
			if(sprite.isDestroyed()){
				throw new RuntimeException("Попытка отрисовки объекта, помеченного на удаление");
			}
			sprite.draw(batch);
		}
	}

	public void freeAllActiveObjects(){//перевести все активные объекты в режим ожидания
		freeObjects.addAll(activeObjects);
		activeObjects.clear();//все активные объекты стали неактивными
	}

	private void free(T object){//удаление объектов
		if(!activeObjects.remove(object)){
			throw new RuntimeException("Попытка удаления несуществующего объкта");
		}
		freeObjects.add(object);
		debugLog();
	}

	//сколько объектов активных, сколько в ожидании
	protected void debugLog(){

	}

	//метод списка активных объектов
	public List<T> getActiveObjects() {
		return activeObjects;
	}

	//если пул объектов не нужен
	public void dispose(){
		activeObjects.clear();
		freeObjects.clear();
	}
}
