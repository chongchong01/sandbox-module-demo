package com.sandbox.modules;

import com.alibaba.jvm.sandbox.api.Information;
import com.alibaba.jvm.sandbox.api.Module;
import com.alibaba.jvm.sandbox.api.annotation.Command;
import com.alibaba.jvm.sandbox.api.listener.ext.Advice;
import com.alibaba.jvm.sandbox.api.listener.ext.AdviceListener;
import com.alibaba.jvm.sandbox.api.listener.ext.EventWatchBuilder;
import com.alibaba.jvm.sandbox.api.resource.ModuleEventWatcher;
import com.sandbox.util.ParamSupported;
import org.kohsuke.MetaInfServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Map;

@MetaInfServices(Module.class)
@Information(id = "add-log-moudle", author = "summer",version = "0.0.1" )
public class AddLogModule extends ParamSupported implements Module {
    private final Logger lifeCLogger = LoggerFactory.getLogger("DEBUG-LIFECYCLE-LOGGER");

    @Resource
    private ModuleEventWatcher moduleEventWatcher;

    @Command("addLog")// 模块命令名
    public void addLog(final Map<String, String> param) {
        //解析参数
        final String cnPattern =getParameter(param, "class");
        final String mnPattern = getParameter(param, "method");


        new EventWatchBuilder(moduleEventWatcher)
                .onClass(cnPattern)
                .includeSubClasses()
                .includeBootstrap()
                .onBehavior(mnPattern)
                .onWatch(new AdviceListener() {
                    @Override
                    protected void before(Advice advice) throws Throwable {
                        //获取方法的所有参数
                        Object[] parameterArray = advice.getParameterArray();
                        if (parameterArray != null) {
                            for (int i = 0; i < parameterArray.length; i++) {
                                if (null !=parameterArray[i]){
                                    lifeCLogger.info("打印方法第"+i+"个参数值为:"+parameterArray[i]+",类型为:" +parameterArray[i].getClass().getName());
                                }
                            }

                        }
                    }
                });
    }

}
