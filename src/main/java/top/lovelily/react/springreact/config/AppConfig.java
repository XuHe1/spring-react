package top.lovelily.react.springreact.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Date;
import java.util.TimeZone;

/**
 * Desc: AppConfig
 * Author: xuhe
 * Date: 2019/3/11 3:18 PM
 * Version: 1.0
 */
@Configuration
public class AppConfig {
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        SimpleModule customModule = new SimpleModule("noahModule", new Version(1, 0, 0, "", "", ""));
        customModule.addSerializer(Long.class, new ToStringSerializer());
        customModule.addSerializer(Date.class, new DateConvertHelper.Serializer());
        customModule.addDeserializer(Date.class, new DateConvertHelper.Deserializer());
        return new ObjectMapper()
                .registerModule(customModule)
                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                .disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .setTimeZone(TimeZone.getTimeZone("GMT+8"));
    }
}
