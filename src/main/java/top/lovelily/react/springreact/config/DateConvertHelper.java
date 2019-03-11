package top.lovelily.react.springreact.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.common.base.Strings;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.IOException;
import java.util.Date;

/**
 * Desc: DateConvertHelper
 * Author: xuhe
 * Date: 2019/3/11 3:23 PM
 * Version: 1.0
 */
public class DateConvertHelper {
    private static final String FMT_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static class Serializer extends JsonSerializer<Date> {
        @Override
        public void serialize(Date value, JsonGenerator generator, SerializerProvider provider) throws IOException {
            if (value == null) {
                generator.writeNull();
            } else {
                generator.writeString(new DateTime(value).toString(FMT_ISO_8601));
            }
        }
    }

    public static class Deserializer extends JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            String dateAsString = parser.getText();
            if (Strings.isNullOrEmpty(dateAsString)) {
                return null;
            } else {
                return DateTime.parse(dateAsString, DateTimeFormat.forPattern(FMT_ISO_8601)).toDate();
            }
        }
    }
}