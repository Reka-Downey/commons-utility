package me.junbin.commons.converter.custom;

import me.junbin.commons.converter.custom.gson.GsonEnum;
import me.junbin.commons.converter.custom.jpa.JpaEnum;
import me.junbin.commons.converter.custom.mybatis.MyBatisEnum;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/2/12 19:53
 * @description : 半具现化：将实现相同效果的抽象方法修改为 default 方法，内容实现为调用统一的抽象方法
 */
public interface CustomEnum<E extends Enum<E>> extends GsonEnum<E>, MyBatisEnum<E>, JpaEnum<E> {

    /**
     * @see #convert()
     */
    @Override
    default String serialize() {
        return convert();
    }

    /**
     * @see #recover(String)
     */
    @Override
    default E deserialize(String json) {
        return recover(json);
    }

    /**
     * @see #convert()
     */
    @Override
    default String convertToDatabaseColumn() {
        return convert();
    }

    /**
     * @see #recover(String)
     */
    @Override
    default E convertToEntityAttribute(String dbData) {
        return recover(dbData);
    }

    /**
     * 将当前枚举转换成 Json 需要显示的字符串或者 数据库 中需要存储的内容
     *
     * @return Json字符串 或者 数据库字段内容
     */
    String convert();

    /**
     * 将 Json字符串 或者 数据库字段内容 恢复成枚举
     *
     * @param data Json字符串 或者 数据库字段内容
     * @return 对应的枚举
     */
    E recover(String data);

}
