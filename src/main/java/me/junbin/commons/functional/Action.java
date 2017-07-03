package me.junbin.commons.functional;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:junbinzhong@linkcm.com">发送邮件</a>
 * @createDate : 2017/6/28 21:59
 * @description :
 */
@FunctionalInterface
public interface Action {

    void execute() throws Exception;

}
