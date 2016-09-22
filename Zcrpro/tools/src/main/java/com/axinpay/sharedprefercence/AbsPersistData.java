package com.axinpay.sharedprefercence;

import android.util.Log;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

abstract public class AbsPersistData extends Observable {
	public static final String KEY_FOR_ALL_CHANGES = "";
	private static final String TAG = "AbsPersistData";
	private Field[] fields;
	private final Map<String, List<OnDataChangedListener>> onDataChangedListeners = new HashMap<String, List<OnDataChangedListener>>();

	public interface OnDataChangedListener{
		void onDataChanged(String fieldName, Object oldValue, Object newValue);
	}

	public void load(){
		for(Field f: getAllPersistFields()){
			String key = f.getName();

			if(!has(key)){
				continue;
			}

			Object value = read(key, f.getGenericType());
			Log.v(TAG, String.format("load %s's field: %s = [%s]", getClass().getName(), key, value));

			setFieldValue(f, value);
		}
	}

	protected void setFieldValue(Field f, Object value){
		try {
			if(value == null){
				Type c = f.getType();
				if(c == boolean.class){
					value = false;
				}
				else if(c == byte.class){
					value = (byte)0;
				}
				else if(c == short.class){
					value = (short)0;
				}
				else if(c == int.class){
					value = 0;
				}
				else if(c == long.class){
					value = (long)0;
				}
				else if(c == float.class){
					value = 0.0f;
				}
				else if(c == double.class){
					value = 0.0d;
				}
				else if(c == char.class){
					value = (char)0;
				}
			}

			f.set(this, value);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private Object getFieldValue(Field f){
		Object value = null;
		try {
			value = f.get(this);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		if(value == null){
			Type c = f.getType();
			if(c == boolean.class){
				value = false;
			}
			else if(c == byte.class){
				value = (byte)0;
			}
			else if(c == short.class){
				value = (short)0;
			}
			else if(c == int.class){
				value = 0;
			}
			else if(c == long.class){
				value = (long)0;
			}
			else if(c == float.class){
				value = 0.0f;
			}
			else if(c == double.class){
				value = 0.0d;
			}
			else if(c == char.class){
				value = (char)0;
			}
		}

			return value;
		}

	public void save(){
		HashMap<String, Object> values = new HashMap<String, Object>();
		for(Field f: getAllPersistFields()){
			String key = f.getName();
			Object value = getFieldValue(f);
			Log.v(TAG, String.format("save %s's field: %s = [%s]", getClass().getName(), key, value));
			values.put(key, value);

			// check values if has listeners
			boolean hasListener = onDataChangedListeners.containsKey(key) || onDataChangedListeners.containsKey(KEY_FOR_ALL_CHANGES);
			if(hasListener){
				final String fieldName = key;
				final Object oldValue = read(fieldName, f.getType()); // 从SP取值出来，之前存的是old
				final Object newValue = value;                        // 从声明了序列化的变量取值来，随着内存中更改继而继续序列化，所以是new

				if(!Util.equals(oldValue, value)){
					synchronized (onDataChangedListeners){
						Log.v(TAG, String.format("notify data %s changed from %s to %s", fieldName, oldValue, newValue));
						if(onDataChangedListeners.containsKey(KEY_FOR_ALL_CHANGES)){
							for(OnDataChangedListener listener: onDataChangedListeners.get(KEY_FOR_ALL_CHANGES)){
								listener.onDataChanged(fieldName, oldValue, newValue);
							}
						}
						if(onDataChangedListeners.containsKey(key)){
							for(OnDataChangedListener listener: onDataChangedListeners.get(key)){
								listener.onDataChanged(fieldName, oldValue, newValue);
							}
						}
					}
				}
			}
		}

		write(values);
	}

	private Field[] getAllPersistFields(){
		if(fields == null){

			ArrayList<Field> fieldList = new ArrayList<Field>();
			Class clazz = getClass();
			do{
				for(Field f: clazz.getDeclaredFields()){
					if(Modifier.isStatic(f.getModifiers())){
						continue;
					}

					if(f.getAnnotation(Persist.class) != null){
						f.setAccessible(true);
						fieldList.add(f);
					}
				}

				clazz = clazz.getSuperclass();
			}while(clazz != AbsPersistData.class && clazz != Object.class);

			fields =  fieldList.toArray(new Field[fieldList.size()]);
		}

		return fields;
	}

	protected Field getPersistField(String name) throws NoSuchFieldException {
		for(Field f: getAllPersistFields()){
			if(f.getName().equals(name)){
				return f;
			}
		}

		throw new NoSuchFieldException();
	}

	abstract public void write(Map<String, Object> values);
	abstract public boolean has(String key);
	abstract public <T>T read(String key, Type clazz);
	abstract protected AbsPersistData getDefaultInstance();
	public void clear(){
		AbsPersistData defaultInstance = getDefaultInstance();
		if(defaultInstance == null){
			Log.w(TAG, "Can not create default instance: " + getClass().getName());
		}

		for(Field f: getAllPersistFields()){
			Object defaultValue;
			try {
				defaultValue = defaultInstance != null? f.get(defaultInstance): null;

				setFieldValue(f, defaultValue);
			} catch (IllegalAccessException ignore) {
				Log.w(TAG, String.format("Can not access %s's field %s in default instance", getClass().getName(), f.getName()));
			}
		}
	}

	public void addOnDataChangedListener(OnDataChangedListener listener){
		addOnDataChangedListener(KEY_FOR_ALL_CHANGES, listener);
	}

	public void addOnDataChangedListener(String fieldName, OnDataChangedListener listener){
		synchronized (onDataChangedListeners){
			if(!onDataChangedListeners.containsKey(fieldName)){
				onDataChangedListeners.put(fieldName, new ArrayList<OnDataChangedListener>());
			}

			onDataChangedListeners.get(fieldName).add(listener);
		}

	}

	public void removeOnDataChangedListener(OnDataChangedListener listener){
		synchronized (onDataChangedListeners){
			ArrayList<String> keysToRemove = new ArrayList<String>();
			for(String key: onDataChangedListeners.keySet()){
				List<OnDataChangedListener> listeners = onDataChangedListeners.get(key);
				listeners.remove(listener);
				if(listeners.size() == 0){
					keysToRemove.add(key);
				}
			}

			for(String key: keysToRemove){
				onDataChangedListeners.remove(key);
			}
		}
	}

}
