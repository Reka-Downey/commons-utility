package me.junbin.commons.converter.api;

import me.junbin.commons.converter.api.gson.GsonEnum;
import me.junbin.commons.converter.api.jpa.JpaEnum;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:junbinzhong@linkcm.com">发送邮件</a>
 * @createDate : 2017/5/28 15:44
 * @description :
 */
public interface JpaGsonEnum<E extends Enum<E>> extends GsonEnum<E>, JpaEnum<E> {

    /**
     * @see #freeze()
     */
    @Override
    default String serialize() {
        return freeze();
    }

    /**
     * @see #unfreeze(String)
     */
    @Override
    default E deserialize(String json) {
        return unfreeze(json);
    }

    /**
     * @see #freeze()
     */
    @Override
    default String convertToDatabaseColumn() {
        return freeze();
    }

    /**
     * @see #unfreeze(String)
     */
    @Override
    default E convertToEntityAttribute(String dbData) {
        return unfreeze(dbData);
    }

    /**
     * 冻结过程
     * 将当前枚举转换成 Json 需要显示的字符串或者 数据库 中需要存储的内容
     *
     * @return Json字符串 或者 数据库字段内容
     */
    String freeze();

    /**
     * 解冻过程
     * 将 Json字符串 或者 数据库字段内容 恢复成枚举
     *
     * @param data Json字符串 或者 数据库字段内容
     * @return 对应的枚举
     */
    E unfreeze(String data);

}
