package net.vvakame.util.jsonpullparser.builder;

/**
 * Jsonの各要素に対応した実行時ビルダ提供クラス.
 * @author vvakame
 * @param <T>
 */
public class JsonPropertyMeta<T> implements JsonPropertyBuilderCreator {

	private Class<? extends JsonPropertyCoder<T>> coderClass;

	private String name;


	@Override
	@SuppressWarnings("unchecked")
	public JsonPropertyBuilder<T> get() {
		return getBuilder();
	}

	/**
	 * the constructor.
	 * @param coderClass
	 * @param name
	 * @category constructor
	 */
	public JsonPropertyMeta(Class<? extends JsonPropertyCoder<T>> coderClass, String name) {
		this.coderClass = coderClass;
		this.name = name;
	}

	JsonPropertyBuilder<T> getBuilder() {
		return new JsonPropertyBuilder<T>(coderClass, name);
	}

	/**
	 * JSONと対応付ける場合のKey名を設定する.
	 * @param name
	 * @return 新しいビルダ
	 * @author vvakame
	 */
	public JsonPropertyBuilder<T> name(String name) {
		return new JsonPropertyBuilder<T>(coderClass, name);
	}
}
