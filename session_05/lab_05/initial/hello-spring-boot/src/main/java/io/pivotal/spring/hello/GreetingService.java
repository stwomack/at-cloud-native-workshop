package io.pivotal.spring.hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Meter.Type;
import io.micrometer.core.instrument.MeterRegistry;


@Component
public class GreetingService {

    @Value("${greeting}")
    String greeting;

	@Bean
	MeterRegistryCustomizer<MeterRegistry> addCounterRegistry() {
	    return registry -> registry.config().namingConvention().name("counter.services.greeting.invoked", Type.COUNTER);
	}
	
	private final Counter greetingCounter;
	
	public GreetingService(MeterRegistry registry) {       
        this.greetingCounter = registry.counter("counter.services.greeting.invoked");
    }
	
	public String getGreeting() {
		this.greetingCounter.increment();
        return greeting;
    }
	
}
