package com.example.fqw.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class ScheduledTask {
    private TaskExecutor executor;
    private ApplicationContext context;

    @Autowired
    public ScheduledTask(@Qualifier("executor")TaskExecutor executor, ApplicationContext context) {
        this.executor = executor;
        this.context = context;
    }
    @Scheduled(fixedRate = 30000)
    public void start(){
            ThreadTask task = context.getBean(ThreadTask.class);
            //task.set(some parameters...) для предеачи параметров в задачу
            executor.execute(task);
    }
}
