/*
 * Copyright 2011 vvakame <vvakame@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.vvakame.util.jsonpullparser.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;

import net.vvakame.util.jsonpullparser.JsonFormatException;
import net.vvakame.util.jsonpullparser.JsonPullParser;
import net.vvakame.util.jsonpullparser.JsonPullParser.State;
import static net.vvakame.util.jsonpullparser.util.JsonUtil.*;

/**
 * JSONの配列 [] に対応するJavaクラス.
 * @author vvakame
 */
public class JsonArray extends ArrayList<Object> {

	private static final long serialVersionUID = -3685725206266732067L;

	ArrayList<State> stateList = new ArrayList<State>();


	/**
	 * JSONの文字列表現をパースし {@link JsonArray} に変換します.
	 * @param json パース対象のJSON
	 * @return パース結果の {@link JsonArray}
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	public static JsonArray fromString(String json) throws IOException, JsonFormatException {
		JsonPullParser parser = JsonPullParser.newParser(json);
		return fromParser(parser);
	}

	/**
	 * JSONの文字列表現をパースし {@link JsonArray} に変換します.
	 * @param parser パースに利用する {@link JsonPullParser}
	 * @return パース結果の {@link JsonArray}
	 * @throws IOException
	 * @throws JsonFormatException
	 * @author vvakame
	 */
	public static JsonArray fromParser(JsonPullParser parser) throws IOException,
			JsonFormatException {
		State state = parser.getEventType();

		if (state == State.VALUE_NULL) {
			return null;
		} else if (state != State.START_ARRAY) {
			throw new JsonFormatException("unexpected token. token=" + state);
		}

		JsonArray jsonArray = new JsonArray();
		while ((state = parser.lookAhead()) != State.END_ARRAY) {
			jsonArray.add(getValue(parser), state);
		}
		parser.getEventType();

		return jsonArray;
	}

	/**
	 * インスタンスが保持する内容をJSONにシリアライズしwriterに書きこむ.
	 * @param writer 書込み先
	 * @throws IOException
	 * @author vvakame
	 */
	public void toJson(Writer writer) throws IOException {
		startArray(writer);

		int size = size();
		for (int i = 0; i < size; i++) {
			put(writer, get(i));
			if (i + 1 < size) {
				addSeparator(writer);
			}
		}

		endArray(writer);
	}

	/**
	 * インスタンスの index 番目の要素を {@link Boolean} として取得します.<br>
	 * {@code null} または {@link Boolean} が保持されていない場合、 {@link IllegalStateException} が発生します.
	 * @param index
	 * @return index番目の値
	 * @throws IllegalStateException indexに {@code null} または {@link Boolean} が保持されていない場合
	 * @author vvakame
	 */
	public Boolean getBooleanOrNull(int index) throws IllegalStateException {
		State state = stateList.get(index);
		switch (state) {
			case VALUE_NULL:
				return null;
			case VALUE_BOOLEAN:
				return (Boolean) get(index);
			default:
				throw new IllegalStateException("unexpected token. token=" + state);
		}
	}

	/**
	 * インスタンスの index 番目の要素を {@link String} として取得します.<br>
	 * {@code null} または {@link String} が保持されていない場合、 {@link IllegalStateException} が発生します.
	 * @param index
	 * @return index番目の値
	 * @throws IllegalStateException indexに {@code null} または {@link String} が保持されていない場合
	 * @author vvakame
	 */
	public String getStringOrNull(int index) throws IllegalStateException {
		State state = stateList.get(index);
		switch (state) {
			case VALUE_NULL:
				return null;
			case VALUE_STRING:
				return (String) get(index);
			default:
				throw new IllegalStateException("unexpected token. token=" + state);
		}
	}

	/**
	 * インスタンスの index 番目の要素を {@link Long} として取得します.<br>
	 * {@code null} または {@link Long} が保持されていない場合、 {@link IllegalStateException} が発生します.
	 * @param index
	 * @return index番目の値
	 * @throws IllegalStateException indexに {@code null} または {@link Long} が保持されていない場合
	 * @author vvakame
	 */
	public Long getLongOrNull(int index) throws IllegalStateException {
		State state = stateList.get(index);
		switch (state) {
			case VALUE_NULL:
				return null;
			case VALUE_LONG:
				return (Long) get(index);
			default:
				throw new IllegalStateException("unexpected token. token=" + state);
		}
	}

	/**
	 * インスタンスの index 番目の要素を {@link Double} として取得します.<br>
	 * {@code null} または {@link Double} が保持されていない場合、 {@link IllegalStateException} が発生します.
	 * @param index
	 * @return index番目の値
	 * @throws IllegalStateException indexに {@code null} または {@link Double} が保持されていない場合
	 * @author vvakame
	 */
	public Double getDoubleOrNull(int index) throws IllegalStateException {
		State state = stateList.get(index);
		switch (state) {
			case VALUE_NULL:
				return null;
			case VALUE_DOUBLE:
				return (Double) get(index);
			default:
				throw new IllegalStateException("unexpected token. token=" + state);
		}
	}

	/**
	 * インスタンスの index 番目の要素を {@link JsonArray} として取得します.<br>
	 * {@code null} または {@link JsonArray} が保持されていない場合、 {@link IllegalStateException} が発生します.
	 * @param index
	 * @return index番目の値
	 * @throws IllegalStateException indexに {@code null} または {@link JsonArray} が保持されていない場合
	 * @author vvakame
	 */
	public JsonArray getJsonArrayOrNull(int index) throws IllegalStateException {
		State state = stateList.get(index);
		switch (state) {
			case VALUE_NULL:
				return null;
			case START_ARRAY:
				return (JsonArray) get(index);
			default:
				throw new IllegalStateException("unexpected token. token=" + state);
		}
	}

	/**
	 * インスタンスの index 番目の要素を {@link JsonHash} として取得します.<br>
	 * {@code null} または {@link JsonHash} が保持されていない場合、 {@link IllegalStateException} が発生します.
	 * @param index
	 * @return index番目の値
	 * @throws IllegalStateException indexに {@code null} または {@link JsonHash} が保持されていない場合
	 * @author vvakame
	 */
	public JsonHash getJsonHashOrNull(int index) throws IllegalStateException {
		State state = stateList.get(index);
		switch (state) {
			case VALUE_NULL:
				return null;
			case START_HASH:
				return (JsonHash) get(index);
			default:
				throw new IllegalStateException("unexpected token. token=" + state);
		}
	}

	/**
	 * インスタンスの index 番目の要素が何かを {@link State} として取得します.<br>
	 * ただし、 {@link JsonHash} を保持している場合は {@link State#START_HASH} が返ります.<br>
	 * {@link JsonArray} を保持している場合は {@link State#START_ARRAY} が返ります.<br>
	 * @param index
	 * @return index番目の要素の種類
	 * @author vvakame
	 */
	public State getState(int index) {
		return stateList.get(index);
	}

	static Object getValue(JsonPullParser parser) throws IOException, JsonFormatException {
		State state = parser.lookAhead();
		switch (state) {
			case VALUE_BOOLEAN:
				parser.getEventType();
				return parser.getValueBoolean();
			case VALUE_STRING:
				parser.getEventType();
				return parser.getValueString();
			case VALUE_DOUBLE:
				parser.getEventType();
				return parser.getValueDouble();
			case VALUE_LONG:
				parser.getEventType();
				return parser.getValueLong();
			case VALUE_NULL:
				parser.getEventType();
				return null;
			case START_ARRAY:
				return fromParser(parser);
			case START_HASH:
				return JsonHash.fromParser(parser);
			default:
				throw new JsonFormatException("unexpected token. token=" + state);
		}
	}

	/**
	 * 渡された引数が {@link State} の何にあたるかを判定し返します.<br>
	 * 
	 * @param obj
	 *            判定したいオブジェクト
	 * @return {@link State} の何にあたるか.
	 */
	State isState(Object obj) {
		State state = null;
		if (obj == null) {
			state = State.VALUE_NULL;
		} else if (obj instanceof String) {
			state = State.VALUE_STRING;
		} else if (obj instanceof Boolean) {
			state = State.VALUE_BOOLEAN;
		} else if (obj instanceof Double || obj instanceof Float) {
			state = State.VALUE_DOUBLE;
		} else if (obj instanceof Byte || obj instanceof Short || obj instanceof Integer
				|| obj instanceof Long) {
			state = State.VALUE_LONG;
		} else if (obj instanceof JsonArray) {
			state = State.START_ARRAY;
		} else if (obj instanceof JsonHash) {
			state = State.START_HASH;
		} else {
			throw new IllegalArgumentException(obj.getClass().getCanonicalName()
					+ " is not supported");
		}
		return state;
	}

	/**
	 * 指定された位置に、渡されたオブジェクト、 {@link State} をセットします.
	 * 
	 * @param index
	 *            挿入位置
	 * @param obj
	 *            保持するオブジェクト
	 * @param state
	 *            保持するオブジェクトの {@link State} 表現
	 */
	public void add(int index, Object obj, State state) {
		stateList.add(index, state);
		super.add(index, obj);
	}

	/**
	 * 指定された位置に、渡されたオブジェクトをセットします.<br> {@link #isState(Object)} を利用し {@link State}
	 * を推測しますが、意図しない挙動に繋がる可能性があるので利用しないほうがよいです.
	 * 
	 * @param index
	 *            挿入位置
	 * @param obj
	 *            保持するオブジェクト
	 */
	@Deprecated
	@Override
	public void add(int index, Object obj) {
		State state = isState(obj);
		add(index, obj, state);
	}

	/**
	 * リストの末尾に、渡されたオブジェクト、 {@link State} をセットします.
	 * 
	 * @param obj
	 *            保持するオブジェクト
	 * @param state
	 *            保持するオブジェクトの {@link State} 表現
	 * @return {@link Collection#add(Object)} 参照
	 */
	public boolean add(Object obj, State state) {
		stateList.add(state);
		return super.add(obj);
	}

	/**
	 * リストの末尾に、渡されたオブジェクトをセットします.<br> {@link #isState(Object)} を利用し {@link State}
	 * を推測しますが、意図しない挙動に繋がる可能性があるので利用しないほうがよいです.
	 * 
	 * @param obj
	 *            保持するオブジェクト
	 */
	@Deprecated
	@Override
	public boolean add(Object obj) {
		State state = isState(obj);
		return add(obj, state);
	}

	/**
	 * 指定された要素全てをリストの末尾に追加します.<br> {@link #isState(Object)}を利用し {@link State}
	 * を推測しますが、意図しない挙動に繋がる可能性があるので利用しないほうがよいです.
	 * 
	 * @param args
	 *            追加するオブジェクト群
	 */
	@Deprecated
	@Override
	public boolean addAll(Collection<? extends Object> args) {
		boolean result = false;
		for (Object obj : args) {
			result = true;
			add(obj);
		}
		return result;
	}

	/**
	 * 指定された要素全てをリストの指定された位置に追加します.<br> {@link #isState(Object)}を利用し {@link State}
	 * を推測しますが、意図しない挙動に繋がる可能性があるので利用しないほうがよいです.
	 * 
	 * @oaram start 挿入開始位置
	 * @param args
	 *            追加するオブジェクト群
	 */
	@Deprecated
	@Override
	public boolean addAll(int start, Collection<? extends Object> args) {
		boolean result = false;
		for (Object obj : args) {
			result = true;
			add(start++, obj);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		stateList.clear();
		super.clear();
	}

	/**
	 * 動作を定義していないため動作しません.
	 * 
	 * @throws UnsupportedOperationException
	 */
	@Override
	public Object clone() {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object remove(int index) {
		stateList.remove(index);
		return super.remove(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean remove(Object obj) {
		if (super.contains(obj)) {
			int index = super.indexOf(obj);
			stateList.remove(index);
			super.remove(index);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * unknown
	 * @param index 
	 * @param obj 
	 * @param state 
	 * @return 指定位置に元々入っていたインスタンス
	 */
	public Object set(int index, Object obj, State state) {
		stateList.set(index, state);
		return super.set(index, obj);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object set(int index, Object obj) {
		State state = isState(obj);
		return set(index, obj, state);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] toArray() {
		return super.toArray();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T>T[] toArray(T[] arg0) {
		return super.toArray(arg0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void trimToSize() {
		stateList.trimToSize();
		super.trimToSize();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof JsonArray) {
			JsonArray ary = (JsonArray) obj;
			int size = size();
			if (size != ary.size()) {
				return false;
			}
			for (int i = 0; i < size; i++) {
				if (!stateList.get(i).equals(ary.stateList.get(i))) {
					return false;
				} else if (get(i) == null && ary.get(i) != null) {
					return false;
				} else if (get(i) == null && ary.get(i) == null) {
					continue;
				} else if (!get(i).equals(ary.get(i))) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean removeAll(Collection<?> args) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> args) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		StringWriter writer = new StringWriter();
		try {
			toJson(writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer.toString();
	}
}
