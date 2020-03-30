package com.sandbox.modules;

import com.alibaba.jvm.sandbox.api.Information;
import com.alibaba.jvm.sandbox.api.Module;
import com.alibaba.jvm.sandbox.api.annotation.Command;
import com.alibaba.jvm.sandbox.api.listener.ext.EventWatchBuilder;
import com.alibaba.jvm.sandbox.api.resource.ModuleEventWatcher;
import com.sandbox.advicelistener.CallChainAdviceListener;
import org.kohsuke.MetaInfServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 调用链追踪模块
 */
@MetaInfServices(Module.class)
@Information(id = "call-chain", author = "summer", version = "0.0.1")
public class CallChainModule implements Module {
    private static final Logger log = LoggerFactory.getLogger(CallChainModule.class);
    @Resource
    private ModuleEventWatcher moduleEventWatcher;

    @Command("print")
    public void print(Map<String, String> req) {
        String classPattern = req.get("method");
        String behaviorPattern = req.get("behaviorPattern");
        if (classPattern == null || classPattern.trim().isEmpty() || behaviorPattern == null || behaviorPattern.trim().isEmpty()) {
            log.warn("类匹配模式和方法匹配模式都不能为空");
            return;
        }
        log.info("print call-chain msg, classPattern:" + classPattern + ", behaviorPattern:" + behaviorPattern);
        new EventWatchBuilder(moduleEventWatcher)
                .onClass(classPattern).includeSubClasses()
                .onBehavior(behaviorPattern)
                .onWatching().withCall().withLine()
                .onWatch(new CallChainAdviceListener());
    }
}
