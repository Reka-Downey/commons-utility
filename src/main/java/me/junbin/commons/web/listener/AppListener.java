package me.junbin.commons.web.listener;

import me.junbin.commons.util.SpringHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:junbinzhong@linkcm.com">发送邮件</a>
 * @createDate : 2017/5/28 22:08
 * @description : 参考 <a href="http://www.tuicool.com/articles/VbaIviF">spring 容器加载完成后执行某个方法</a>
 * <p>
 * 但是这个时候，会存在一个问题，在 web 项目中（spring mvc），
 * 系统会存在两个容器，一个是 root application context,
 * 另一个就是我们自己的 projectName-servlet context（作为 root application context 的子容器）。
 * <p>
 * 这种情况下，就会造成 onApplicationEvent 方法被执行两次。
 * 为了避免上面提到的问题，我们可以只在 root application context 初始化完成后调用逻辑代码，
 * 其他的容器的初始化完成，则不做任何处理
 * <p>
 * ★★ 本类使用方法：在 Spring 的配置文件中注册当前类或者指定扫描当前包，
 * 就可以在 {@link SpringHolder} 中得到相应的 {@link ApplicationContext}
 */
@Service
public final class AppListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 只有当加载的容器是 Root ApplicationContext 时才执行 setter 方法
        if (event.getApplicationContext().getParent() == null) {
            SpringHolder.setAppCtx(event.getApplicationContext());
        }
    }

}
