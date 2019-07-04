package com.learnreactivespring.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelCastCacheConfig {
	@Bean
    public Config hazelcastConfig(){
       return new Config().setInstanceName("hazelcast-instance")
                .addMapConfig(new MapConfig().setName("itemCache")
                .setMaxSizeConfig(new MaxSizeConfig(300,MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                .setEvictionPolicy(EvictionPolicy.LRU)
                .setTimeToLiveSeconds(2000));
	}
}
