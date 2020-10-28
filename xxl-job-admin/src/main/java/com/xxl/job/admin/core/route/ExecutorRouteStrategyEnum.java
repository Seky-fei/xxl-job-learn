package com.xxl.job.admin.core.route;

import com.xxl.job.admin.core.route.strategy.*;
import com.xxl.job.admin.core.util.I18nUtil;

/**
 * Created by xuxueli on 17/3/10.
 * 路由策略: 任务执行的路由策略(ExecutorRouter是具体的路由策略算法)
 */
public enum ExecutorRouteStrategyEnum {
    /**
     * 固定选择第一个机器
     */
    FIRST(I18nUtil.getString("jobconf_route_first"), new ExecutorRouteFirst()),
    /**
     * 固定选择最后一个机器
     */
    LAST(I18nUtil.getString("jobconf_route_last"), new ExecutorRouteLast()),
    /**
     * 轮询在线的机器
     */
    ROUND(I18nUtil.getString("jobconf_route_round"), new ExecutorRouteRound()),
    /**
     * 随机选择在线的机器
     */
    RANDOM(I18nUtil.getString("jobconf_route_random"), new ExecutorRouteRandom()),
    /**
     * 一致性HASH: 每个任务按照Hash算法固定选择某一台机器，且所有任务均匀散列在不同机器上
     */
    CONSISTENT_HASH(I18nUtil.getString("jobconf_route_consistenthash"), new ExecutorRouteConsistentHash()),
    /**
     * 最不经常使用：使用频率最低的机器优先被选举
     */
    LEAST_FREQUENTLY_USED(I18nUtil.getString("jobconf_route_lfu"), new ExecutorRouteLFU()),
    /**
     * 最近最久未使用：最久未使用的机器优先被选举
     */
    LEAST_RECENTLY_USED(I18nUtil.getString("jobconf_route_lru"), new ExecutorRouteLRU()),
    /**
     * 故障转移：按照顺序依次进行心跳检测，第一个心跳检测成功的机器选定为目标执行器并发起调度
     */
    FAILOVER(I18nUtil.getString("jobconf_route_failover"), new ExecutorRouteFailover()),
    /**
     * 忙碌转移：按照顺序依次进行空闲检测，第一个空闲检测成功的机器选定为目标执行器并发起调度
     */
    BUSYOVER(I18nUtil.getString("jobconf_route_busyover"), new ExecutorRouteBusyover()),
    /**
     * 分片广播：广播触发对应集群中所有机器执行一次任务(轮询算法在代码里用"执行器任务分片参数"实现)
     */
    SHARDING_BROADCAST(I18nUtil.getString("jobconf_route_shard"), null);

    ExecutorRouteStrategyEnum(String title, ExecutorRouter router) {
        this.title = title;
        this.router = router;
    }

    private String title;
    private ExecutorRouter router;

    public String getTitle() {
        return title;
    }
    public ExecutorRouter getRouter() {
        return router;
    }

    public static ExecutorRouteStrategyEnum match(String name, ExecutorRouteStrategyEnum defaultItem){
        if (name != null) {
            for (ExecutorRouteStrategyEnum item: ExecutorRouteStrategyEnum.values()) {
                if (item.name().equals(name)) {
                    return item;
                }
            }
        }
        return defaultItem;
    }

}
